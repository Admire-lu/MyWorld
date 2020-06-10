package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserHotelService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-酒店控制器
 * @author william
 *
 */
@RestController
public class UserHotelController {

	@Autowired
	private UserHotelService userHotelService;

	/**
	 * 我的酒店
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/getMyHotels.html")
	public ResultVo getMyHotels(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userHotelService.getMyHotels(jsonData);
	}

	/**
	 * 选择酒店
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/chooseTheHotel.html")
	public ResultVo chooseTheHotel(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userHotelService.chooseTheHotel(jsonData);
	}

	/**
	 * 修改用户酒店名称
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/updateUserHotelCustomName.html")
	public ResultVo updateUserHotelCustomName(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userHotelService.updateUserHotelCustomName(jsonData);
	}
	
	/**
	 * 获取酒店
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/getHotels.html")
	public ResultVo getHotels(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userHotelService.getHotels(jsonData);
	}
	
	/**
	 * 购买酒店
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/buyHotel.html")
	public ResultVo buyHotel(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userHotelService.buyHotel(jsonData);
	}
	
	/**
	 * 获取酒店管理团队
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/getHotelManagerTeams.html")
	public ResultVo getHotelManagerTeams(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userHotelService.getHotelManagerTeams(jsonData);
	}
	
	/**
	 * 为酒店增加管理团队
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/addManagerTeamForHotel.html")
	public ResultVo addManagerTeamForHotel(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userHotelService.addManagerTeamForHotel(jsonData);
	}

	/**
	 * 为酒店管理团队补充时间
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/timeForHotelManagementTeam.html")
	public ResultVo timeForHotelManagementTeam(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userHotelService.timeForHotelManagementTeam(jsonData);
	}

	/**
	 * 获取用户酒店
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/getUserHotels.html")
	public ResultVo getUserHotels(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userHotelService.getUserHotels(jsonData);
	}

	/**
	 * 获取用户-酒店-管理团队
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/hotel/getUserHotelManagerTeams.html")
	public ResultVo getUserHotelManagerTeams(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userHotelService.getUserHotelManagerTeams(jsonData);
	}
}
