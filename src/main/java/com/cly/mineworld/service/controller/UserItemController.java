package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserItemService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 我的道具控制器
 * @author WilliamLam
 *
 */
@RestController
public class UserItemController {

	@Autowired
	private UserItemService userItemService;
	
	/**
	 * 使用道具
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/userItem/userUseItem.html")
	public ResultVo userUseItem(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userItemService.userUseItem(jsonData);
	}
	
	/**
	 * 获取用户道具
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/userItem/getUserUseItem.html")
	public ResultVo getUserUseItem(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userItemService.getUserItems(jsonData);
	}
}
