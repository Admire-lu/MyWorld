package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserRegionService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-区域控制器
 * @author william
 *
 */
@RestController
public class UserRegionController {
	
	@Autowired
	private UserRegionService userRegionService;
	
	/**
	 * 获取区域
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/region/getRegions.html")
	public ResultVo getRegions(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userRegionService.getRegions(jsonData);
	}
	
	/**
	 * 获取景点
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/region/getScenicSpots.html")
	public ResultVo getScenicSpots(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userRegionService.getScenicSpots(jsonData);
	}
}
