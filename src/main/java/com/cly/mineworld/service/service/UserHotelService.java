package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserHotelService {

	/**
	 * 获取酒店
	 * @param jsonData
	 * @return
	 */
	ResultVo getHotels(String jsonData);
	
	/**
	 * 购买酒店
	 * @param jsonData
	 * @return
	 */
	ResultVo buyHotel(String jsonData);
	
	/**
	 * 获取用户的酒店
	 * @param jsonData
	 * @return
	 */
	ResultVo getUserHotels(String jsonData);
	
	/**
	 * 获取酒店管理团队
	 * @param jsonData
	 * @return
	 */
	ResultVo getHotelManagerTeams(String jsonData);

	/**
	 * 为酒店聘请酒店管理团队
	 * @param jsonData
	 * @return
	 */
	ResultVo addManagerTeamForHotel(String jsonData);

	/**
	 * 获取用户的酒店管理团队
	 * @param jsonData
	 * @return
	 */
	ResultVo getUserHotelManagerTeams(String jsonData);

	/**
	 * 修改用户酒店名称
	 * @param jsonData
	 * @return
	 */
    ResultVo updateUserHotelCustomName(String jsonData);

	/**
	 * 选择酒店
	 * @param jsonData
	 * @return
	 */
	ResultVo chooseTheHotel(String jsonData);

	/**
	 * 我的酒店
	 * @param jsonData
	 * @return
	 */
    ResultVo getMyHotels(String jsonData);

	/**
	 * 为酒店管理团队补充时间
	 * @param jsonData
	 * @return
	 */
	ResultVo timeForHotelManagementTeam(String jsonData);
}
