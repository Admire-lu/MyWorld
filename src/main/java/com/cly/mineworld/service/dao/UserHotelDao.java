package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userHotel.UserHotelVo;
import com.cly.mineworld.service.vo.userHoterTeam.UserHotelTeamVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;

public interface UserHotelDao {


	/**
	 * 选择酒店
	 * @param map
	 * @return
	 */
	List<UserManagerTeamVo> chooseTheHotel(Map<String, Object> map);

	/**
	 * 查询酒店数组
	 * @param map
	 * @return
	 */
	List<MHotel> selectHotelList(Map<String,Object> map);
	
	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 新增用户酒店
	 * @param userHotel
	 */
	void insertUserHotel(MUserHotel userHotel);
	
	/**
	 * 修改用户
	 * @param map
	 */
	void updateUser(Map<String,Object> map);
	
	/**
	 * 查询景点总数
	 * @param map
	 * @return
	 */
	int selectScenicSpotCount(Map<String,Object> map);
	
	/**
	 * 新增用户消费记录
	 * @param history
	 */
	void insertUserConsumptionHistory(MUserConsumptionHistory history);
	
	/**
	 * 查询酒店管理团队数组
	 * @param map
	 * @return
	 */
	List<MHotelManagerTeam> selectHotelManagerTeamList(Map<String,Object> map);
	
	/**
	 * 查询用户酒店数组
	 * @param map
	 * @return
	 */
	List<MUserHotel> selectUserHotelList(Map<String,Object> map);
	
	/**
	 * 查询用户酒店管理团队数组
	 * @param map
	 * @return
	 */
	List<MUserHotelManagerTeam> selectUserHotelManagerTeamList(Map<String,Object> map);
	
	/**
	 * 新增用户酒店管理团队
	 * @param managerTeam
	 */
	void insertUserHotelManagerTeam(MUserHotelManagerTeam managerTeam);
	
	/**
	 * 修改用户酒店
	 * @param map
	 */
	void updateUserHotel(Map<String,Object> map);
	
	/**
	 * 修改用户酒店管理团队
	 * @param map
	 */
	void updateUserHotelManagerTeam(Map<String,Object> map);
	
	/**
	 * 联合用户酒店信息查询酒店信息
	 * @param map
	 * @return
	 */
	List<UserHotelVo> selectHotelJoinUserHotel(Map<String,Object> map);

	/**
	 * 联合用户酒店管理团队信息查询酒店管理团队信息
	 * @param map
	 * @return
	 */
	List<UserHotelTeamVo> selectHotelManagerTeamJoinUserHotelManagerTeam(Map<String, Object> map);

	/**
	 * 查询管理团队剩余时间
	 * @param map
	 * @return
	 */
	String findSurplusManagerTime(Map<String, Object> map);

	/**
	 * 查询用户一共有多少个酒店
	 * @param map
	 * @return
	 */
	Integer selectUserHotels(Map<String, Object> map);

	/**
	 * 我的酒店
	 * @param map
	 * @return
	 */
	List<UserHotelVo> selectHotelJoinMyHotel(Map<String, Object> map);

	/**
	 * 新增明细订单表
	 * @param detailOrder
	 */
    void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder);

	/**
	 * 查询定向vad
	 * @param map
	 * @return
	 */
	List<MActivity> selectDirectionalVADList(Map<String, Object> map);

	/**
	 * 更新定向地址错误
	 * @param map
	 */
	void updateUserActivity(Map<String, Object> map);

	/**
	 * 补充管理时间
	 * @param managerTeam
	 */
	void updateHotelManagerTeam(MUserHotelManagerTeam managerTeam);
}
