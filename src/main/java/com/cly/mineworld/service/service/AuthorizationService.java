package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface AuthorizationService {

	/**
	 * 获取Token
	 * @param jsonData
	 * @return
	 */
	ResultVo getToken(String jsonData);
}
