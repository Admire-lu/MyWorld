package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserLoginService {

	/**
	 * 用户登录--邮箱
	 * @param jsonData
	 * @return
	 */
	ResultVo userLogin(String jsonData);
}
