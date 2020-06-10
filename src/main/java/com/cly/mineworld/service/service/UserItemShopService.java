package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserItemShopService {

	/**
	 * 获取所有道具类别
	 * @param jsonData
	 * @return
	 */
	ResultVo getItemCategorys(String jsonData);
	
	/**
	 * 获取道具
	 * @param jsonData
	 * @return
	 */
	ResultVo getItems(String jsonData);
	
	/**
	 * 购买道具
	 * @param jsonData
	 * @return
	 */
	ResultVo buyItem(String jsonData);
}
