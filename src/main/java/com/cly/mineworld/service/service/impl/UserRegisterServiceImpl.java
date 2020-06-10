package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.thread.ThreadSendSmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserRegisterDao;
import com.cly.mineworld.service.service.RedisService;
import com.cly.mineworld.service.service.UserRegisterService;
import com.cly.mineworld.service.thread.ThreadSendMail;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRegisterDao userRegisterDao;
    @Autowired
    private RedisService redisService;


    /**
     * 注册下级
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo registeredSubordinate(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","registerType":"123@qq.com",
        // "verifyCode":"123456","nickname":"123","hotelId":"1","registrationMethod":"15012630328"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String registerType = data.getString("registerType");
            String verifyCode = data.getString("verifyCode");
            String nickname = data.getString("nickname");
            String hotelId = data.getString("hotelId");
            String registrationMethod = data.getString("registrationMethod");//邀请人手机号/邮箱
            if (null != registerType && !"".equals(registerType)
                    && null != verifyCode && !"".equals(verifyCode)
                    && null != hotelId && !"".equals(hotelId)
                    && null != nickname && !"".equals(nickname)
                    && null != registrationMethod && !"".equals(registrationMethod)
                    ) {
                if (registerType.indexOf("@") != -1) {
                    //邮箱方式注册/判断邮箱格式是否符合规范
                    String email = registerType;
                    String mobile = "";
                    //校验邮箱的规范
                    if (Utils.isEmail(email)) {
                        Jedis jedis1 = redisService.getJedis();
                        jedis1.select(1);//Token库
                        if (null != jedis1.get(email) && verifyCode.equals(jedis1.get(email).toString())) { //校验邮箱验证码
                            //判断用户名是否符合规范
                            if (Utils.isNickName(nickname)) {
                                //获取邀请人信息Friend
                                Map<String, Object> mapQueryFriend = new HashMap<String, Object>();
                                if (registrationMethod.indexOf("@") != -1) {//邮箱
                                    mapQueryFriend.put("email", registrationMethod);
                                } else {//手机
                                    mapQueryFriend.put("mobile", registrationMethod);
                                }
                                List<MUser> friendList = userRegisterDao.selectUserList(mapQueryFriend);
                                MUser friend = friendList.get(0);//邀请人
                                //获取个人用户信息
                                Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                                mapQueryUser.put("userStrId", userStrId);
                                List<MUser> userList = userRegisterDao.selectUserList(mapQueryUser);
                                MUser user = userList.get(0);//用户本人
                                MUser registeredUser = new MUser();//预设邀请人
                                //获取要购买的酒店信息
                                Map<String, Object> queryHotelListMap = new HashMap<String, Object>();
                                queryHotelListMap.put("hotelId", hotelId);
                                List<MHotel> listHotel = userRegisterDao.selectHotelList(queryHotelListMap);
                                if (friend.getStrId() == userStrId) {//判断是否是本人 Registered users
                                    registeredUser = user;
                                    //判断用户本人VAD是否足够
                                    if (listHotel.get(0).getStartCost() <= user.getVad()) {
                                        if (null != registeredUser.getMobile() && "".equals(registeredUser.getEmail())){
                                            data.put("typeOfInvitation", registeredUser.getMobile());
                                        }else {
                                            data.put("typeOfInvitation", registeredUser.getEmail());
                                        }
                                        Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                        mapQueryUserCount.put("email", email);
                                        int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                        if (count > 0) {
                                            result = "-11";//email已经存在
                                            message = "Email is exist";
                                        } else {
                                            //保存用户信息
                                            String psw = email;
                                            Map<String, String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                            result = saveMap.get("result");
                                            message = saveMap.get("message");
                                            String strId = saveMap.get("strId");
                                            //为下级购买酒店 map.put("strId",strId);
                                            Map<String, String> byHotelMap = buyHotel(strId, listHotel.get(0).getStrId(),registeredUser.getStrId(),userStrId);
                                            result = byHotelMap.get("result");
                                            message = byHotelMap.get("message");
                                            String userHotelStrId = byHotelMap.get("userHotelStrId");
                                            //*减少用户vad*//*
                                            Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                            modifyUserMap.put("userStrId", userStrId);
                                            modifyUserMap.put("vad", user.getVad() - listHotel.get(0).getStartCost());
                                            userRegisterDao.updateUser(modifyUserMap);
                                            //增加消费记录/并更新资金明细表
                                            addRecordsOfConsumption(user.getStrId(), listHotel.get(0).getStrId(), userHotelStrId);
                                        }
                                    } else {
                                        result = "-15";//vad不足
                                        message = "Vad is not enough";
                                    }
                                } else { //邀请人不是本人 再判断输入的好友账号是否是同一推荐线上的
                                    //判断是否是在同一推荐线上/暂且判断，只能为节点以下的用户去注册下级
                                    Map<String, Object> recommendUserQueryMap = new HashMap<String, Object>();
                                    recommendUserQueryMap.put("userId", user.getId());
                                    recommendUserQueryMap.put("friendId", friend.getId());
                                    Boolean isItCorrect = userRegisterDao.selectUserRecommend(recommendUserQueryMap);
                                    if (isItCorrect) {//同一推荐线
                                        registeredUser = friend;
                                        //判断用户本人VAD是否足够
                                        if (listHotel.get(0).getStartCost() <= user.getVad()) {
                                            if (null != registeredUser.getMobile() && "".equals(registeredUser.getEmail())){
                                                data.put("typeOfInvitation", registeredUser.getMobile());
                                            }else {
                                                data.put("typeOfInvitation", registeredUser.getEmail());
                                            }
                                            Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                            mapQueryUserCount.put("email", email);
                                            int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                            if (count > 0) {
                                                result = "-11";//email已经存在
                                                message = "Email is exist";
                                            } else {
                                                //保存用户信息
                                                String psw = email;
                                                Map<String, String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                                result = saveMap.get("result");
                                                message = saveMap.get("message");
                                                String strId = saveMap.get("strId");
                                                //为下级购买酒店 map.put("strId",strId);
                                                Map<String, String> byHotelMap = buyHotel(strId, listHotel.get(0).getStrId(),registeredUser.getStrId(),userStrId);
                                                result = byHotelMap.get("result");
                                                message = byHotelMap.get("message");
                                                String userHotelStrId = byHotelMap.get("userHotelStrId");
                                                //*减少用户vad*//*
                                                Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                                modifyUserMap.put("userStrId", userStrId);
                                                modifyUserMap.put("vad", user.getVad() - listHotel.get(0).getStartCost());
                                                userRegisterDao.updateUser(modifyUserMap);
                                                //增加消费记录/并更新资金明细表
                                                addRecordsOfConsumption(user.getStrId(), listHotel.get(0).getStrId(), userHotelStrId);
                                            }
                                        } else {
                                            result = "-15";//vad不足
                                            message = "Vad is not enough";
                                        }
                                    } else {
                                        result = "-45";//此用户不在推荐线上
                                        message = "This friend is not on the recommendation line！";
                                    }
                                }
                            } else {
                                result = "-25";//用户名格式错误
                                message = "NikeName format error";
                            }
                        } else {
                            result = "-12";//验证码错误
                            message = "verify code error!";
                        }
                        jedis1.close();
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {
                    //手机方式注册
                    String mobile = registerType;
                    String email = "";
                    //校验手机号码合法性
                    if (mobile.length() == 11) {
                        if (Utils.isMobileNum(mobile)) {
                            Jedis jedis1 = redisService.getJedis();
                            jedis1.select(1);//Token库
                            if (null != jedis1.get(mobile) && verifyCode.equals(jedis1.get(mobile).toString())) { //校验手机验证码
                                //判断用户名是否符合规范
                                if (Utils.isNickName(nickname)) {
                                    //获取邀请人信息Friend
                                    Map<String, Object> mapQueryFriend = new HashMap<String, Object>();
                                    if (registrationMethod.indexOf("@") != -1) {//邮箱
                                        mapQueryFriend.put("email", registrationMethod);
                                    } else {//手机
                                        mapQueryFriend.put("mobile", registrationMethod);
                                    }
                                    List<MUser> friendList = userRegisterDao.selectUserList(mapQueryFriend);
                                    MUser friend = friendList.get(0);//邀请人
                                    //获取个人用户信息
                                    Map<String, Object> mapQueryUser = new HashMap<String, Object>();
                                    mapQueryUser.put("userStrId", userStrId);
                                    List<MUser> userList = userRegisterDao.selectUserList(mapQueryUser);
                                    MUser user = userList.get(0);//用户本人
                                    MUser registeredUser = new MUser();
                                    //获取要购买的酒店信息
                                    Map<String, Object> queryHotelListMap = new HashMap<String, Object>();
                                    queryHotelListMap.put("hotelId", hotelId);
                                    List<MHotel> listHotel = userRegisterDao.selectHotelList(queryHotelListMap);
                                    if (friend.getStrId() == userStrId) {//判断是否是本人 Registered users
                                        registeredUser = user;
                                        //判断用户本人VAD是否足够
                                        if (listHotel.get(0).getStartCost() <= user.getVad()) {
                                            if (null != registeredUser.getMobile() && "".equals(registeredUser.getEmail())){
                                                data.put("typeOfInvitation", registeredUser.getMobile());
                                            }else {
                                                data.put("typeOfInvitation", registeredUser.getEmail());
                                            }
                                            Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                            mapQueryUserCount.put("mobile", mobile);
                                            int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                            if (count > 0) {
                                                result = "-10";//手机已经存在
                                                message = "Mobile is exist";
                                            } else {
                                                //保存用户信息
                                                String psw = mobile;
                                                Map<String, String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                                result = saveMap.get("result");
                                                message = saveMap.get("message");
                                                String strId = saveMap.get("strId");
                                                //为下级购买酒店 map.put("strId",strId);
                                                Map<String, String> byHotelMap = buyHotel(strId, listHotel.get(0).getStrId(),registeredUser.getStrId(),userStrId);
                                                result = byHotelMap.get("result");
                                                message = byHotelMap.get("message");
                                                String userHotelStrId = byHotelMap.get("userHotelStrId");
                                                //*减少用户vad*//*
                                                Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                                modifyUserMap.put("userStrId", userStrId);
                                                modifyUserMap.put("vad", user.getVad() - listHotel.get(0).getStartCost());
                                                userRegisterDao.updateUser(modifyUserMap);
                                                //增加消费记录/并更新资金明细表
                                                addRecordsOfConsumption(user.getStrId(), listHotel.get(0).getStrId(),userHotelStrId);
                                            }
                                        }else {
                                            result = "-15";//vad不足
                                            message = "Vad is not enough";
                                        }
                                    } else { //邀请人不是本人 再判断输入的好友账号是否是同一推荐线上的
                                        //判断是否是在同一推荐线上/暂且判断，只能为节点以下的用户去注册下级
                                        Map<String, Object> recommendUserQueryMap = new HashMap<String, Object>();
                                        recommendUserQueryMap.put("userId", user.getId());
                                        recommendUserQueryMap.put("friendId", friend.getId());
                                        Boolean isItCorrect = userRegisterDao.selectUserRecommend(recommendUserQueryMap);
                                        if (isItCorrect) {//同一推荐线
                                            registeredUser = friend;
                                            //判断用户本人VAD是否足够
                                            if (listHotel.get(0).getStartCost() <= user.getVad()) {
                                                if (null != registeredUser.getMobile() && "".equals(registeredUser.getEmail())){
                                                    data.put("typeOfInvitation", registeredUser.getMobile());
                                                }else {
                                                    data.put("typeOfInvitation", registeredUser.getEmail());
                                                }
                                                Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                                mapQueryUserCount.put("mobile", mobile);
                                                int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                                if (count > 0) {
                                                    result = "-10";//手机已经存在
                                                    message = "Mobile is exist";
                                                } else {
                                                    //保存用户信息
                                                    String psw = mobile;
                                                    Map<String, String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                                    result = saveMap.get("result");
                                                    message = saveMap.get("message");
                                                    String strId = saveMap.get("strId");
                                                    //为下级购买酒店 map.put("strId",strId);
                                                    Map<String, String> byHotelMap = buyHotel(strId, listHotel.get(0).getStrId(),registeredUser.getStrId(),userStrId);
                                                    result = byHotelMap.get("result");
                                                    message = byHotelMap.get("message");
                                                    String userHotelStrId = byHotelMap.get("userHotelStrId");
                                                    //*减少用户vad*//*
                                                    Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                                                    modifyUserMap.put("userStrId", userStrId);
                                                    modifyUserMap.put("vad", user.getVad() - listHotel.get(0).getStartCost());
                                                    userRegisterDao.updateUser(modifyUserMap);
                                                    //增加消费记录/并更新资金明细表
                                                    addRecordsOfConsumption(user.getStrId(), listHotel.get(0).getStrId(),userHotelStrId);
                                                }
                                            }else {
                                                result = "-15";//vad不足
                                                message = "Vad is not enough";
                                            }
                                        } else {
                                            result = "-45";//此用户不在推荐线上
                                            message = "This friend is not on the recommendation line！";
                                        }
                                    }
                                } else {
                                    result = "-25";//用户名格式错误
                                    message = "NikeName format error";
                                }
                           } else {
                                result = "-12";//验证码错误
                                message = "verify code error!";
                            }
                            jedis1.close();
                        } else {
                            result = "-22";// 手机格式错误
                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 校验手机或者邮箱是否存在
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo checkMobileOrEmailIsExist(String jsonData) {
        //jsonData报文格式：{"data":{"checkType":"12345678910"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String checkType = data.getString("checkType");
            if (null != checkType && !"".equals(checkType)) {
                if (checkType.indexOf("@") != -1) { //校验邮箱
                    String email = checkType;
                    //校验邮箱的规范
                    if (Utils.isEmail(email)) {
                        Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                        mapQueryUserCount.put("email", email);
                        int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                        if (count > 0) {//邮箱已经存在
                            result = "-11";//邮箱已经存在
                            message = "Email is exist";
                        }
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {//校验手机
                    String mobile = checkType;
                    //校验手机号码合法性
                    if (mobile.length() == 11) {
                        if (Utils.isMobileNum(mobile)) {
                            Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                            mapQueryUserCount.put("mobile", mobile);
                            int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                            if (count > 0) {//手机号已经存在
                                result = "-10";//手机号已经存在
                                message = "Mobile is exist";
                            }
                        } else {
                            result = "-22";// 手机格式错误
                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 发送验证码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo sendVerificationCode(String jsonData) {
        //jsonData报文格式：{"data":{"verificationType":"12345678910"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String verificationType = data.getString("verificationType");
            if (null != verificationType && !"".equals(verificationType)) {
                if (verificationType.indexOf("@") != -1) {
                    //邮箱方式发送验证码
                    String email = verificationType;
                    if (Utils.isEmail(email)) {
                        Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                        mapQueryUserCount.put("email", email);
                        int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                        if (count > 0) {//email已经存在
                            result = "-11";
                            message = "Email is exist";
                        } else {
                            String verifyCode = Utils.getSixRamdomSum();
                            System.out.println("Email verify code : " + verifyCode);
                            Jedis jedis1 = redisService.getJedis();
                            jedis1.select(1);//邮件验证码库库
                            jedis1.setex(email, 120, verifyCode);
                            jedis1.close();//关闭0库连接
                            ThreadSendMail tsm = new ThreadSendMail(email, "我的世界邮件验证码", "您的验证码是：" + verifyCode);
                            Thread t = new Thread(tsm);
                            t.start();
                        }
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {
                    //手机方式发送验证码
                    String mobile = verificationType;
                    //校验手机号码合法性
                    if (mobile.length() == 11) {
                        if (Utils.isMobileNum(mobile)) {
                            Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                            mapQueryUserCount.put("mobile", mobile);
                            int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                            if (count > 0) {//Mobile已经存在
                                result = "-10";
                                message = "Mobile is exist";
                            } else {
                                String verifyCode = Utils.getSixRamdomSum();
                                System.out.println("Mobile verify code : " + verifyCode);
                                Jedis jedis1 = redisService.getJedis();
                                jedis1.select(1);//手机验证码库库
                                jedis1.setex(mobile, 120, verifyCode);
                                jedis1.close();//关闭0库连接
                                ThreadSendSmsCode tsm = new ThreadSendSmsCode(mobile, verifyCode, "0");
                                Thread t = new Thread(tsm);
                                t.start();
                            }
                        } else {
                            result = "-22";// 手机格式错误
                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 注册用户
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo userRegister(String jsonData) {
        //jsonData报文格式：{"data":{"registerType":"123@qq.com","verifyCode":"123456","psw":"123456","nickname":"123","typeOfInvitation":"15012630328"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String registerType = data.getString("registerType");//注册类型/邮箱/手机1
            String verifyCode = data.getString("verifyCode");//验证码
            String psw = data.getString("psw");//密码
            String nickname = data.getString("nickname");//昵称
            if (null != registerType && !"".equals(registerType)
                    && null != verifyCode && !"".equals(verifyCode)
                    && null != psw && !"".equals(psw)
                    && null != nickname && !"".equals(nickname)
                    ) {
                if (registerType.indexOf("@") != -1) {
                    //邮箱方式注册/判断邮箱格式是否符合规范
                    String email = registerType;
                    String mobile = "";
                    //校验邮箱的规范
                    if (Utils.isEmail(email)) {
                        Jedis jedis1 = redisService.getJedis();
                        jedis1.select(1);//Token库
                        if (null != jedis1.get(email) && verifyCode.equals(jedis1.get(email).toString())) { //校验邮箱验证码
                            //判断用户名是否符合规范
                            if (Utils.isNickName(nickname)) {
                                //判断密码是否符合规范
                                if (Utils.isValidPassword(psw)) {
                                    Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                    mapQueryUserCount.put("email", email);
                                    int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                    if (count > 0) {//email已经存在
                                        result = "-11";
                                        message = "Email is exist";
                                    } else {
                                        //保存用户信息
                                        Map<String,String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                        result = saveMap.get("result");
                                        message = saveMap.get("message");
                                    }
                                } else {
                                    result = "-23";//密码长度错误
                                    message = "Password length error";
                                }
                            } else {
                                result = "-25";//用户名格式错误
                                message = "NikeName format error";
                            }
                     } else {
                            result = "-12";//验证码错误
                            message = "verify code error!";
                        }
                        jedis1.close();
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {
                    //手机方式注册
                    String mobile = registerType;
                    String email = "";
                    //校验手机号码合法性
                    if (mobile.length() == 11) {
                        if (Utils.isMobileNum(mobile)) {
                            Jedis jedis1 = redisService.getJedis();
                            jedis1.select(1);//Token库
                           //if (null != jedis1.get(mobile) && verifyCode.equals(jedis1.get(mobile).toString())) { //校验手机验证码
                                //判断用户名是否符合规范
                                if (Utils.isNickName(nickname)) {
                                    //判断密码是否符合规范
                                    if (Utils.isValidPassword(psw)) {
                                        Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                                        mapQueryUserCount.put("mobile", mobile);
                                        int count = userRegisterDao.selectUserCount(mapQueryUserCount);
                                        if (count > 0) {//手机已经存在
                                            result = "-10";
                                            message = "Mobile is exist";
                                        } else {
                                            //保存用户信息
                                            Map<String,String> saveMap = saveMuser(data, email, mobile, nickname, psw);
                                            result = saveMap.get("result");
                                            message = saveMap.get("message");
                                        }
                                    } else {
                                        result = "-23";//密码长度错误
                                        message = "Password length error";
                                    }
                                } else {
                                    result = "-25";//用户名格式错误
                                    message = "NikeName format error";
                                }
                          /* } else {
                                result = "-12";//验证码错误
                                message = "verify code error!";
                            }*/
                            jedis1.close();
                        } else {
                            result = "-22";// 手机格式错误

                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
                }
            } else {
                result = "-2";//参数错误
                message = "Parameters error!";
            }
        } else {
            result = "-2";//参数错误
            message = "Parameters error!";
        }
        return MineWorldUtils.createResultVo(result, message, jData);
    }

    /**
     * 保存用户信息
     * @param data
     * @param email
     * @param mobile
     * @param nickname
     * @param psw
     * @return
     */
    private Map<String,String> saveMuser(JSONObject data, String email, String mobile, String nickname, String psw) {
        Map<String, String> map = new HashMap<>();
        MUser recommendUser = null;//推荐人
        if (null != data.getString("typeOfInvitation")
                && !"".equals(data.getString("typeOfInvitation"))) {//有推荐人
            String typeOfInvitation = data.getString("typeOfInvitation");
            /*查询推荐人信息*/
            Map<String, Object> mapQueryUsers = new HashMap<String, Object>();
            if (typeOfInvitation.indexOf("@") != -1) {
                mapQueryUsers.put("email", typeOfInvitation); //邮箱
            } else if (typeOfInvitation.length() == 11) {
                mapQueryUsers.put("mobile", typeOfInvitation);//手机
            } else {
                mapQueryUsers.put("userStrId", typeOfInvitation);//userStrId
            }
        /*    if(Utils.isMobileNum(typeOfInvitation)) {
            	mapQueryUsers.put("email", typeOfInvitation); //邮箱
            }else if(Utils.isEmail(typeOfInvitation)) {
            	mapQueryUsers.put("mobile", typeOfInvitation);//手机
            }else {
            	mapQueryUsers.put("userStrId", typeOfInvitation);//userStrId
            }*/
            List<MUser> recommendUserList = userRegisterDao.selectUserList(mapQueryUsers);
            /*校验推荐人是否已经有酒店*/
            /* Map<String, Object> mapQueryUserHotelCount = new HashMap<String, Object>();
            mapQueryUserHotelCount.put("userStrId", recommendUserList.get(0).getStrId());
            int userHotelCount = userRegisterDao.selectUserHotelCount(mapQueryUserHotelCount);//查询用户酒店数量
           if (recommendUserList.size() > 0 && userHotelCount > 0) {//推荐人存在并且推荐人已经有酒店
                recommendUser = recommendUserList.get(0);
            }*/
            if (recommendUserList.size() > 0) {//获取推荐人信息
                recommendUser = recommendUserList.get(0);
                /*用户注册*/
                MUser user = new MUser();
                user.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                user.setEmail(email);
                user.setHeadImage("");
                user.setMobile(mobile);
                user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                user.setNickname(nickname);
                user.setPsw(Utils.md5(psw));
                user.setStatus(1);
                String strId = Utils.getUUID();
                user.setStrId(strId);
                map.put("strId",strId);
                user.setExp(0);
                user.setLv(1);
                user.setTransactionPsw("");
                user.setTokenAddress("");
                user.setFreezeAmount(0.00);
                user.setVad(0.00);
                user.setDeh(0.00);
                user.setUserType("1");//普通用户
                userRegisterDao.insertUser(user);
                /*层级编码*/
                String userLevelCode = Integer.toString(user.getId());
                if (null != recommendUser
                        && null != recommendUser.getUserLevelCode()
                        && !"".equals(recommendUser.getUserLevelCode())) {//推荐人有层级编码
                    /*新增用户推荐关系（直推）*/
                    MUserRecommend userRecommend = new MUserRecommend();
                    userRecommend.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    userRecommend.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    userRecommend.setRecommendedUserStrId(user.getStrId());//被推荐人
                    userRecommend.setRecommendUserStrId(recommendUser.getStrId());//推荐人
                    userRecommend.setStatus(1);
                    userRecommend.setStrId(Utils.getUUID());
                    userRegisterDao.insertUserRecommend(userRecommend);
                    /*层级编码*/
                    userLevelCode = recommendUser.getUserLevelCode() + "-" + user.getId();//推荐人层级编码 - 用户id
                    /*创建用户团队*/
                    createUserTeam(recommendUser, user);
                }
                /*修改用户层级编码*/
                Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                mapModifyUser.put("userLeveCode", userLevelCode);
                mapModifyUser.put("userStrId", user.getStrId());
                userRegisterDao.updateUser(mapModifyUser);
                map.put("result","1");
                map.put("message","ok");
            }else {//推荐人不存在 Recommender is not Exist!
                map.put("result","-30");
                map.put("message","Recommender is not Exist!");
            }
        }else { //无推荐人 Recommender number is null
            map.put("result","-31");
            map.put("message","Recommender number is null!");
        }
        return map;
    }

    /**
     * 创建用户团队
     *
     * @param recommendUser   推荐人
     * @param recommendedUser 被推荐人
     */
    private void createUserTeam(MUser recommendUser, MUser recommendedUser) {
        /*是否可以建立团队*/
        /*查询推荐人直推的人数*/
        Map<String, Object> mapQueryRecommendedCount = new HashMap<String, Object>();
        mapQueryRecommendedCount.put("recommendUserStrId", recommendUser.getStrId());
        int recommendedCount = userRegisterDao.selectUserRecommendCount(mapQueryRecommendedCount);
        if (recommendedCount > 2) {//超过3个直推，形成团队
            /*查询推荐人的团队是否已经存在*/
            Map<String, Object> mapQueryUserTeams = new HashMap<String, Object>();
            mapQueryUserTeams.put("teamLeaderStrId", recommendUser.getStrId());
            List<MUserTeam> listUserTeam = userRegisterDao.selectUserTeamList(mapQueryUserTeams);
            MUserTeam newUserTeam = null;
            if (listUserTeam.size() < 1) {//推荐人没有团队
                /*新建推荐人的团队*/
                newUserTeam = new MUserTeam();
                newUserTeam.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                newUserTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                newUserTeam.setStatus(1);
                newUserTeam.setStrId(Utils.getUUID());
                newUserTeam.setTeamCount(0);
                newUserTeam.setTeamDesc("");
                newUserTeam.setTeamLeaderStrId(recommendUser.getStrId());
                newUserTeam.setTeamLv(1);
                newUserTeam.setTeamName("");
                newUserTeam.setTeamRechargeAmount(0.0);
                userRegisterDao.insertUserTeam(newUserTeam);
                /*查询推荐人现在所有的直推用户*/
                Map<String, Object> mapQueryUserRecommends = new HashMap<String, Object>();
                mapQueryUserRecommends.put("recommendUserStrId", recommendUser.getStrId());
                List<MUserRecommend> listUserRecommend = userRegisterDao.selectUserRecommendList(mapQueryUserRecommends);
                for (MUserRecommend ur : listUserRecommend) {
                    /*把所有直推用户增加到团队*/
                    MUserTeamMember userTeamMember = new MUserTeamMember();
                    userTeamMember.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    userTeamMember.setMemberStrId(ur.getRecommendedUserStrId());//被推荐人
                    userTeamMember.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    userTeamMember.setStatus(1);
                    userTeamMember.setStrId(Utils.getUUID());
                    userTeamMember.setTeamStrId(newUserTeam.getStrId());
                    userRegisterDao.insertUserTeamMember(userTeamMember);
                }
                /*推荐人也是团队会员*/
                MUserTeamMember userTeamMember = new MUserTeamMember();
                userTeamMember.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                userTeamMember.setMemberStrId(recommendUser.getStrId());//推荐人
                userTeamMember.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                userTeamMember.setStatus(1);
                userTeamMember.setStrId(Utils.getUUID());
                userTeamMember.setTeamStrId(newUserTeam.getStrId());
                userRegisterDao.insertUserTeamMember(userTeamMember);
                /*修改团队人数*/
                Map<String,Object> mapModifyUserTeam = new HashMap<String,Object>();
                mapModifyUserTeam.put("teamStrId", newUserTeam.getStrId());
                mapModifyUserTeam.put("teamCount", listUserRecommend.size() + 1);//团队总人数：直推人数 + 自己
                userRegisterDao.updateUserTeam(mapModifyUserTeam);
            } else {
                /*把被推荐人加到推荐人的团队*/
                MUserTeamMember userTeamMember = new MUserTeamMember();
                userTeamMember.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                userTeamMember.setMemberStrId(recommendedUser.getStrId());//被推荐人
                userTeamMember.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                userTeamMember.setStatus(1);
                userTeamMember.setStrId(Utils.getUUID());
                userTeamMember.setTeamStrId(listUserTeam.get(0).getStrId());
                userRegisterDao.insertUserTeamMember(userTeamMember);
                /*修改团队人数*/
                Map<String,Object> mapModifyUserTeam = new HashMap<String,Object>();
                mapModifyUserTeam.put("teamStrId", listUserTeam.get(0).getStrId());
                mapModifyUserTeam.put("teamCount", recommendedCount + 1);//团队总人数：推荐人数  + 自己
                userRegisterDao.updateUserTeam(mapModifyUserTeam);
            }
        }
    }

    /**
     * 购买酒店
     * @param
     * @return
     */
    private Map<String, String> buyHotel(String newUserStrId, String hotelStrId,String friendStrIdTeam,String userStrId) {
        //jsonData报文格式：{"data":{"userStrId":"123","hotelStrId":"123","scenicSpotStrId":"123456","hotelCustomName":"夜巴黎"}}
        Map<String, String> map = new HashMap<>();
        String scenicSpotStrId = "4b9b991686094c47b196f73e1098bf21";
        //String hotelCustomName = "";
        //校验景点是否存在
        Map<String, Object> queryScenicSpotCount = new HashMap<String, Object>();
        queryScenicSpotCount.put("scenicSpotStrId", scenicSpotStrId);
        if (userRegisterDao.selectScenicSpotCount(queryScenicSpotCount) > 0) {
            //*校验用户是否存在
            Map<String, Object> queryNewUserListMap = new HashMap<String, Object>();
            queryNewUserListMap.put("userStrId", newUserStrId);
            List<MUser> newUserList = userRegisterDao.selectUserList(queryNewUserListMap);
            if (newUserList.size() > 0) {//用户存在
                MUser newUser = newUserList.get(0);//用户信息
                //*获取要购买的酒店信息*//*
                Map<String, Object> queryHotelListMap = new HashMap<String, Object>();
                queryHotelListMap.put("hotelStrId", hotelStrId);
                List<MHotel> listHotel = userRegisterDao.selectHotelList(queryHotelListMap);
                if (listHotel.size() > 0) {//酒店存在
                    MHotel hotel = listHotel.get(0);//酒店对象
                    //新增用户酒店
                    MUserHotel userHotel = new MUserHotel();
                    userHotel.setUserStrId(newUserStrId);
                    userHotel.setDoBusinessDays(0);
                    userHotel.setDoBusinessStatus(2);//默认停业中
                    userHotel.setHotelCustomName(" ");
                    userHotel.setHotelStrId(hotel.getStrId());
                    userHotel.setProfit(0.00);
                    userHotel.setScenicSpotStrId(scenicSpotStrId);
                    userHotel.setStatus(1);
                    userHotel.setStopBusinessDays(0);
                    String userHotelStrId = Utils.getUUID();
                    userHotel.setStrId(userHotelStrId);
                    userHotel.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    userHotel.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    userRegisterDao.insertUserHotel(userHotel);
                    //若此用户拥有团队，则将此次购买酒店团队的消费 加入团队充值总金额（经验值）
                    Map<String, Object> queryUserTeamMap = new HashMap<String, Object>();
                    queryUserTeamMap.put("teamLeaderStrId", friendStrIdTeam);
                    List<MUserTeam> userTeamList = userRegisterDao.selectUserTeamList(queryUserTeamMap);
                    if (userTeamList.size() > 0) { //则将此次购买酒店团队的消费  加入团队充值总金额(经验值)
                        MUserTeam userTeam = userTeamList.get(0);
                        Map<String, Object> modifyUserTeamMap = new HashMap<String, Object>();
                        modifyUserTeamMap.put("userStrId", friendStrIdTeam);
                        modifyUserTeamMap.put("vad", userTeam.getTeamRechargeAmount() + hotel.getStartCost());
                        userRegisterDao.updateUserTeamHotel(modifyUserTeamMap);
                    }
                    //给当前登录用户返现 购买酒店花费的3%
                    Map<String, Object> queryUserListMap = new HashMap<String, Object>();
                    queryUserListMap.put("userStrId", userStrId);//当前登录的用户
                    List<MUser> userList = userRegisterDao.selectUserList(queryUserListMap);
                    if (userList.size()>0){
                        MUser user = userList.get(0);
                        Map<String, Object> modifyUserMap = new HashMap<String, Object>();
                        modifyUserMap.put("userStrId", userStrId);
                        modifyUserMap.put("vad",user.getVad()+ hotel.getStartCost() * 0.03);
                        userRegisterDao.updateUser(modifyUserMap);
                    }
                    map.put("result", "1");
                    map.put("message", "OK");
                    map.put("userHotelStrId",userHotelStrId);
                } else {
                    //result = "-14";//酒店不存在
                    //message = "Hotel is not exist!";
                    map.put("result", "-14");
                    map.put("message", "Hotel is not exist!");
                }
            } else {//用户不存在
                //result = "-13";//用户不存在
                //message = "User is not exist!";
                map.put("result", "-13");
                map.put("message", "User is not exist!");
            }
        } else {
            //result = "-16";//景点不存在
            //message = "ScenicSpot is not exist!";
            map.put("result", "-16");
            map.put("message", "ScenicSpot is not exist!!");
        }
        return map;
    }

    /**
     * 增加主动注册好友的消费记录
     * @param
     * @return
     */
    private void addRecordsOfConsumption(String userStrId, String hotelStrId,String userHotelStrId) {
            //*校验用户是否存在
            Map<String, Object> queryUserListMap = new HashMap<String, Object>();
            queryUserListMap.put("userStrId", userStrId);
            List<MUser> userList = userRegisterDao.selectUserList(queryUserListMap);
                MUser user = userList.get(0);//用户信息
                //*获取要购买的酒店信息*//*
                Map<String, Object> queryHotelListMap = new HashMap<String, Object>();
                queryHotelListMap.put("hotelStrId", hotelStrId);
                List<MHotel> listHotel = userRegisterDao.selectHotelList(queryHotelListMap);
                    MHotel hotel = listHotel.get(0);
                    //*新增用户消费记录*//*
                    MUserConsumptionHistory history = new MUserConsumptionHistory();
                    history.setAfterAmount(user.getVad() - hotel.getStartCost());
                    history.setAmount(hotel.getStartCost());
                    history.setBeforeAmount(user.getVad());
                    history.setConsumptionCategoryStrId("a7ffd63bf6134b0dbf0daedda08462b9");//购买酒店
                    history.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    history.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    history.setStatus(1);
                    history.setStrId(Utils.getUUID());
                    history.setUserStrId(userStrId);
                    history.setSubOrderStrId(userHotelStrId);//关联订单ID
                    userRegisterDao.insertUserConsumptionHistory(history);
                    //*新增用户资金明细记录*//*
                    MUserFundDetailOrder detailOrder = new MUserFundDetailOrder();
                    detailOrder.setStrId(Utils.getUUID());
                    detailOrder.setUserStrId(userStrId);
                    detailOrder.setOrderCategory(3);
                    detailOrder.setRelationOrderStrId(userHotelStrId);//关联订单ID
                    detailOrder.setTokenCategory(1);
                    detailOrder.setAmount(hotel.getStartCost());
                    detailOrder.setBeforeAmount(user.getVad());
                    detailOrder.setAfterAmount(user.getVad() - hotel.getStartCost());
                    detailOrder.setRemarks("购买下级酒店");
                    detailOrder.setOrderStatus(1);
                    detailOrder.setStatus(1);
                    detailOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                    detailOrder.setCreateTime(new Long(Utils.getTimestamp()).intValue());
                    userRegisterDao.insertUserFundDetailOrder(detailOrder);
    }

}
