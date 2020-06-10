package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserCommonService {

	/**
	 * 获取应用版本
	 * @param jsonData
	 * @return
	 */
	ResultVo getAppVersion(String jsonData);
}
