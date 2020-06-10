package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserRegisterService {

	/**
	 * 用户注册
	 * @param jsonData
	 * @return
	 */
	ResultVo userRegister(String jsonData);

	/**
	 * 发送验证码
	 * @param jsonData
	 * @return
	 */
    ResultVo sendVerificationCode(String jsonData);

	/**
	 * 校验手机或者邮箱是否存在
	 * @param jsonData
	 * @return
	 */
	ResultVo checkMobileOrEmailIsExist(String jsonData);

	/**
	 * 注册下级
	 * @param jsonData
	 * @return
	 */
    ResultVo registeredSubordinate(String jsonData);
}
