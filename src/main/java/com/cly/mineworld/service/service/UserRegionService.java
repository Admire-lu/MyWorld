package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserRegionService {

	/**
	 * 获取区域
	 * @param jsonData
	 * @return
	 */
	ResultVo getRegions(String jsonData);
	
	/**
	 * 获取景点
	 * @param jsonData
	 * @return
	 */
	ResultVo getScenicSpots(String jsonData);
}
