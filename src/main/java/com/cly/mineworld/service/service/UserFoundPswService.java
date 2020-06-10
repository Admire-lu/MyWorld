package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;



public interface UserFoundPswService {

    /**
     * 根据手机号/邮箱 修改密码
     * @param jsonData
     * @return
     */
    ResultVo foundPsw(String jsonData);

    /**
     * 发送验证码
     * @param jsonData
     * @return
     */
    ResultVo sendVerificationCode(String jsonData);

    /**
     * 校验手机/邮箱是否存在
     * @param jsonData
     * @return
     */
    ResultVo checkMobileOrEmailIsExist(String jsonData);
}
