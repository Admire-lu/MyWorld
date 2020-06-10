package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserItemService {

	/**
	 * 用户使用道具
	 * @param jsonData
	 * @return
	 */
	ResultVo userUseItem(String jsonData);
	
	/**
	 * 获取用户道具
	 * @param jsonData
	 * @return
	 */
	ResultVo getUserItems(String jsonData);
}
