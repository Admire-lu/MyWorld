package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserFoundPswDao;
import com.cly.mineworld.service.service.RedisService;
import com.cly.mineworld.service.service.UserFoundPswService;
import com.cly.mineworld.service.thread.ThreadSendMail;
import com.cly.mineworld.service.thread.ThreadSendSmsCode;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class  UserFoundPswServiceImpl implements UserFoundPswService {

    @Autowired
    private UserFoundPswDao userFoundPswDao;
    @Autowired
    private RedisService redisService;


    /**
     * 设置密码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo foundPsw(String jsonData) {
        //jsonData报文格式：{"data":{"foundType":"12345678901","pswOne":"123456","pswTwo":"123456","verifyCode":"2234"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String foundType = data.getString("foundType");
            String pswOne = data.getString("pswOne");
            String pswTwo = data.getString("pswTwo");
            String verifyCode = data.getString("verifyCode");
            if (null != foundType && !"".equals(foundType)
                    && null != verifyCode && !"".equals(verifyCode)
                    && null != pswOne && !"".equals(pswOne)
                    && null != pswTwo && !"".equals(pswTwo)
                    ) {
                if (foundType.indexOf("@") != -1) {
                    //邮箱方式设置密码
                    String email = foundType;
                    //校验邮箱的规范
                    if (Utils.isEmail(email)) {
                        //校验验证码
                        Jedis jedis1 = redisService.getJedis();
                        jedis1.select(1);
                        if (null != jedis1.get(email) && verifyCode.equals(jedis1.get(email).toString())) {
                                //判断密码是否符合规范
                                if (Utils.isValidPassword(pswOne)) {
                                    if (pswOne.equals(pswTwo)){ //两次输入的密码一致
                                        //验证成功，根据邮箱号、去修改密码
                                        Map<String, Object> mapUpdateUser = new HashMap<String, Object>();
                                        mapUpdateUser.put("email", email);
                                        mapUpdateUser.put("psw", Utils.md5(pswOne));
                                        userFoundPswDao.updateUser(mapUpdateUser);
                                    }else {
                                        result = "-35";//两次输入的密码不一致
                                        message = "Two inconsistencies in password input!";
                                    }
                                } else {
                                    result = "-23";//密码长度错误
                                    message = "Password length error";
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
                    //手机方式设置密码
                    String mobile = foundType;
                    //校验手机号码合法性
                    if (mobile.length() == 11) {
                        if (Utils.isMobileNum(mobile)) {
                            //校验验证码
                            Jedis jedis1 = redisService.getJedis();
                            jedis1.select(1);
                            if (null != jedis1.get(mobile) && verifyCode.equals(jedis1.get(mobile).toString())) {
                                    //判断密码是否符合规范
                                    if (Utils.isValidPassword(pswOne)) {
                                        if (pswOne.equals(pswTwo)){ //两次输入的密码一致
                                            //验证成功，根据邮箱号、去修改密码
                                            Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                                            mapModifyUser.put("mobile", mobile);
                                            mapModifyUser.put("psw", Utils.md5(pswOne));
                                            userFoundPswDao.updateUser(mapModifyUser);
                                        }else {
                                            result = "-35";//两次输入的密码不一致
                                            message = "Two inconsistencies in password input!";
                                        }
                                    } else {
                                        result = "-23";//密码长度错误
                                        message = "Password length error";
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
                        int count = userFoundPswDao.selectUserCount(mapQueryUserCount);
                        if (count > 0) {//email已经存在
                            String verifyCode = Utils.getSixRamdomSum();
                            System.out.println("Email verify code : " + verifyCode);
                            Jedis jedis1 = redisService.getJedis();
                            jedis1.select(1);//邮件验证码库库
                            jedis1.setex(email, 120, verifyCode);
                            jedis1.close();//关闭0库连接
                            ThreadSendMail tsm = new ThreadSendMail(email, "我的世界邮件验证码", "您的验证码是：" + verifyCode);
                            Thread t = new Thread(tsm);
                            t.start();
                        } else {
                            result = "-13"; //此用户不存在
                            message = "User is not exist!";
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
                            int count = userFoundPswDao.selectUserCount(mapQueryUserCount);
                            if (count > 0) {//Mobile已经存在
                                String verifyCode = Utils.getSixRamdomSum();
                                System.out.println("Mobile verify code : " + verifyCode);
                                Jedis jedis1 = redisService.getJedis();
                                jedis1.select(1);//手机验证码库库
                                jedis1.setex(mobile, 120, verifyCode);
                                jedis1.close();//关闭0库连接
                                ThreadSendSmsCode tsm = new ThreadSendSmsCode(mobile, verifyCode, "0");
                                Thread t = new Thread(tsm);
                                t.start();
                            } else {
                                result = "-13"; //此用户不存在
                                message = "User is not exist!";
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
     * 校验手机或邮箱是否存在
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
                        int count = userFoundPswDao.selectUserCount(mapQueryUserCount);
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
                            int count = userFoundPswDao.selectUserCount(mapQueryUserCount);
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
}
