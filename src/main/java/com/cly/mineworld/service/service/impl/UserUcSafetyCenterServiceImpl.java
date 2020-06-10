package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.OssUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcSafetyCenterDao;
import com.cly.mineworld.service.service.RedisService;
import com.cly.mineworld.service.service.UserUcSafetyCenterService;
import com.cly.mineworld.service.thread.ThreadSendMail;
import com.cly.mineworld.service.thread.ThreadSendSmsCode;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户端-用户个人中心-安全中心-调度服务
 *
 * @author william
 */
@Service
@Transactional
public class UserUcSafetyCenterServiceImpl implements UserUcSafetyCenterService {

    @Autowired
    private UserUcSafetyCenterDao userUcSafetyCenterDao;

    @Autowired
    private RedisService redisService;


    /**
     * 根据用户StrId修改昵称
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updateNickName(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123",nickName":"1234567"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String nickName = data.getString("nickName");
            if (null != nickName && !"".equals(nickName) && null != userStrId && !"".equals(userStrId)) {
                //判断昵称的规范
                if (Utils.isNickName(nickName)) {
                    Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                    mapModifyUser.put("userStrId", userStrId);
                    mapModifyUser.put("nickName", nickName);
                    userUcSafetyCenterDao.updateUser(mapModifyUser);
                } else {
                    result = "-25";//用户名格式错误
                    message = "NikeName format error";
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
     * 修改用户头像
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updateHeadImage(String jsonData) {
        //jsonData报文格式： {"data":{"userStrId":"4b9b991686094c47b196f73e1098bf21","headImage":"1234567"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String headImage = data.getString("headImage");
            if (null != headImage && !"".equals(headImage) && null != userStrId && !"".equals(userStrId)) {
                //上传头像
                byte[] headImageByte = Base64.getDecoder().decode(headImage);
                ByteArrayInputStream headImageInputStream = new ByteArrayInputStream(headImageByte);
                String headImageName = OssUtils.uploadFileToOss(headImageInputStream);
                Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                mapModifyUser.put("userStrId", userStrId);
                mapModifyUser.put("headImage", headImageName);
                userUcSafetyCenterDao.updateUser(mapModifyUser);
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
     * 根据用户StrId设置交易密码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo setTransactionPsw(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","transactionPsw":"123456"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String transactionPsw = data.getString("transactionPsw");
            if (null != transactionPsw && !"".equals(transactionPsw) && null != userStrId && !"".equals(userStrId)) {
                //校验支付密码的规范性 TODO 纯数字还是、是否需要发送验证码？
                if (Utils.isValidPassword(transactionPsw)) {
                    //验证成功，根据邮箱号、去修改密码
                    Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                    mapModifyUser.put("userStrId", userStrId);
                    mapModifyUser.put("transactionPsw", Utils.md5(transactionPsw));
                    userUcSafetyCenterDao.updateUser(mapModifyUser);
                } else {
                    result = "-23";//密码长度错误
                    message = "transactionPsw length error";
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
     * 修改登陆密码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updatePassword(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","oldPassword":"123456","newPassword":"1234567"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String oldPassword = data.getString("oldPassword");
            String newPassword = data.getString("newPassword");
            if (null != oldPassword && !"".equals(oldPassword)
                    && null != newPassword && !"".equals(newPassword)
                    && null != userStrId && !"".equals(userStrId)) {
                //判断密码的规范
                if (Utils.isValidPassword(newPassword)) {
                    if (oldPassword.equals(newPassword)) {//判断两次输入是否一致
                        Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                        mapModifyUser.put("userStrId", userStrId);
                        mapModifyUser.put("newPassword", Utils.md5(newPassword));
                        userUcSafetyCenterDao.updateUser(mapModifyUser);
                    } else {
                        result = "-35";//两次输入的密码不一致
                        message = "Two inconsistencies in password input!";
                    }
                } else {
                    result = "-23";//密码长度错误
                    message = "transactionPsw length error";
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
     * 修改交易密码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updateTransactionPsw(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","oldTransactionPsw":"123456","newTransactionPsw":"1234567"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String oldTransactionPsw = data.getString("oldTransactionPsw");
            String newTransactionPsw = data.getString("newTransactionPsw");
            if (null != oldTransactionPsw && !"".equals(oldTransactionPsw)
                    && null != newTransactionPsw && !"".equals(newTransactionPsw)
                    && null != userStrId && !"".equals(userStrId)
                    ) {
                //判断密码的规则
                if (Utils.isValidPassword(newTransactionPsw)) {
                    if (oldTransactionPsw.equals(newTransactionPsw)) {//判断两次输入是否一致
                    Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                    mapModifyUser.put("userStrId", userStrId);
                    mapModifyUser.put("newTransactionPsw", Utils.md5(newTransactionPsw));
                    userUcSafetyCenterDao.updateUser(mapModifyUser);
                } else {
                    result = "-35";//两次输入的密码不一致
                    message = "Two inconsistencies in password input!";
                }
                } else {
                    result = "-23";//密码长度错误
                    message = "transactionPsw length error";
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
     * 根据用户名绑定更新手机
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updateMobile(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","mobileNumber":"123456",verifyCode":"682047"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String mobileNumber = data.getString("mobileNumber");
            String verifyCode = data.getString("verifyCode");
            if (null != mobileNumber && !"".equals(mobileNumber)
                    && null != userStrId && !"".equals(userStrId)
                    && null != verifyCode && !"".equals(verifyCode)) {
                //校验手机验证码
                Jedis jedis1 = redisService.getJedis();
                jedis1.select(1);//Token库
                if (null != jedis1.get(mobileNumber) && verifyCode.equals(jedis1.get(mobileNumber).toString())) {
                    //校验手机号码合法性
                    if (mobileNumber.length() == 11) {
                        if (Utils.isMobileNum(mobileNumber)) {
                            Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                            mapQueryUserCount.put("mobile", mobileNumber);
                            int count = userUcSafetyCenterDao.selectUserCount(mapQueryUserCount);
                            if (count > 0) { //手机号码已存在
                                result = "-10";
                                message = "Mobile is exist";
                            } else {
                                Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                                mapModifyUser.put("userStrId", userStrId);
                                mapModifyUser.put("mobileNumber", mobileNumber);
                                userUcSafetyCenterDao.updateUser(mapModifyUser);
                            }
                        } else {
                            result = "-22";// 手机格式不合法
                            message = "Mobile format error!";
                        }
                    } else {
                        result = "-21";// 手机号应为11位数
                        message = "Mobile length error!";
                    }
                } else {
                    result = "-20";//手机验证码错误
                    message = "Mobile verify code error!";
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
     * 根据用户名绑定更新邮箱
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo updateEmail(String jsonData) {
        //jsonData报文格式：{"data":{"userStrId":"123","email":"123456","verifyCode":"682047"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String userStrId = data.getString("userStrId");
            String email = data.getString("email");
            String verifyCode = data.getString("verifyCode");
            if (null != email && !"".equals(email)
                    && null != userStrId && !"".equals(userStrId)
                    && null != verifyCode && !"".equals(verifyCode)) {
                //校验验证码
                Jedis jedis1 = redisService.getJedis();
                jedis1.select(1);//Token库
                if (null != jedis1.get(email) && verifyCode.equals(jedis1.get(email).toString())) {
                    //校验邮箱
                    if (Utils.isEmail(email)) {
                        Map<String, Object> mapQueryUserCount = new HashMap<String, Object>();
                        mapQueryUserCount.put("email", email);
                        int count = userUcSafetyCenterDao.selectUserCount(mapQueryUserCount);
                        if (count > 0) {//email已经存在
                            result = "-11";
                            message = "Email is exist";
                        } else {
                            Map<String, Object> mapModifyUser = new HashMap<String, Object>();
                            mapModifyUser.put("userStrId", userStrId);
                            mapModifyUser.put("email", email);
                            userUcSafetyCenterDao.updateUser(mapModifyUser);
                        }
                    } else {
                        result = "-24";//邮箱格式错误
                        message = "Email format error!";
                    }
                } else {
                    result = "-12";//邮箱验证码错误
                    message = "Email verify code error!";
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
     * 发送手机验证码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo sendMobileCode(String jsonData) {
        //jsonData报文格式：{"data":{"mobile":"12345678901"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String mobile = data.getString("mobile");
            if (null != mobile && !"".equals(mobile)) {
                //校验手机号码合法性
                if (mobile.length() == 11) {
                    if (Utils.isMobileNum(mobile)) {
                        String verifyCode = Utils.getSixRamdomSum();
                        System.out.println("Mobile verify code : " + verifyCode);
                        Jedis jedis1 = redisService.getJedis();
                        jedis1.select(1);//手机验证码库库
                        jedis1.setex(mobile, 120, verifyCode);
                        jedis1.close();//关闭0库连接
                        ThreadSendSmsCode tsm = new ThreadSendSmsCode(mobile, "【我的世界】您的验证码是：" + verifyCode, "0");
                        Thread t = new Thread(tsm);
                        t.start();
                    } else {
                        result = "-22";// 手机格式错误
                        message = "Mobile format error!";
                    }
                } else {
                    result = "-21";// 手机号应为11位数
                    message = "Mobile length error!";
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
     * 发送邮箱验证码
     * @param jsonData
     * @return
     */
    @Override
    public ResultVo sendEmailCode(String jsonData) {
        //jsonData报文格式：{"data":{"email":"123456@qq.com"}}
        String result = "1";
        String message = "OK";
        JSONObject jData = new JSONObject();
        if (null != jsonData && !"".equals(jsonData)) {
            JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
            String email = data.getString("email");
            if (null != email && !"".equals(email)) {
                //校验邮箱的规范
                if (Utils.isEmail(email)) {
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
                    result = "-24";//邮箱格式错误
                    message = "Email format error!";
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
