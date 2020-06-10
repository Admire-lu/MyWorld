package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心--安全中心-调度服务
 * @author william
 *
 */
public interface UserUcSafetyCenterService {

    /**
     * 根据用户StrId设置交易密码
     * @param jsonData
     * @return
     * @throws Exception
     */
    ResultVo setTransactionPsw(String jsonData);

    /**
     * 根据用户StrId修改邮箱号
     * @param jsonData
     * @return
     * @throws Exception
     */
    ResultVo updateEmail(String jsonData);

    /**
     * 根据用户StrId修改手机号
     * @param jsonData
     * @return
     * @throws Exception
     */
    ResultVo updateMobile(String jsonData);

    /**
     * 发送手机验证码
     * @param jsonData
     * @return
     */
    ResultVo sendMobileCode(String jsonData);

    /**
     * 发送邮件验证码
     * @param jsonData
     * @return
     */
    ResultVo sendEmailCode(String jsonData);

    /**
     * 修改登陆密码
     * @param jsonData
     * @return
     */
    ResultVo updatePassword(String jsonData);

    /**
     * 修改交易密码
     * @param jsonData
     * @return
     */
    ResultVo updateTransactionPsw(String jsonData);

    /**
     * 修改昵称
     * @param jsonData
     * @return
     */
    ResultVo updateNickName(String jsonData);

    /**
     *修改头像
     * @param jsonData
     * @return
     */
    ResultVo updateHeadImage(String jsonData);
}
