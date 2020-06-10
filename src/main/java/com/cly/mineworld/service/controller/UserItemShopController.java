package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserItemShopService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 道具商店控制器
 * @author WilliamLam
 *
 */
@RestController
public class UserItemShopController {

	@Autowired
	private UserItemShopService userItemShopService;
	
	/**
	 * 获取所有道具分类
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/itemShop/getItemCategorys.html")
	public ResultVo getItemCategorys(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userItemShopService.getItemCategorys(jsonData);
	}
	
	/**
	 * 获取道具
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/itemShop/getItems.html")
	public ResultVo getItems(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userItemShopService.getItems(jsonData);
	}
	
	/**
	 * 购买道具
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/itemShop/buyItem.html")
	public ResultVo buyItem(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userItemShopService.buyItem(jsonData);
	}
}
