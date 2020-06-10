package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.*;

public interface UserRegisterDao {

	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	int selectUserCount(Map<String,Object> map);
	
	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(MUser user);
	
	/**
	 * 修改用户
	 * @param map
	 */
	void updateUser(Map<String,Object> map);
	
	/**
	 * 新增用户推荐关系
	 * @param userRecommend
	 */
	void insertUserRecommend(MUserRecommend userRecommend);

	/**
	 * 查询用户是否在同一推荐线上
	 * @param map
	 * @return
	 */
	Boolean selectUserRecommend(Map<String, Object> map);
	
	/**
	 * 查询用户推荐总数
	 * @param map
	 * @return
	 */
	int selectUserRecommendCount(Map<String,Object> map);
	
	/**
	 * 查询推荐人酒店总数
	 * @param map
	 * @return
	 */
	int selectUserHotelCount(Map<String,Object> map);
	
	/**
	 * 查询用户团队数组
	 * @param map
	 * @return
	 */
	List<MUserTeam> selectUserTeamList(Map<String,Object> map);
	
	/**
	 * 新增用户团队
	 * @param userTeam
	 */
	void insertUserTeam(MUserTeam userTeam);
	
	/**
	 * 查询用户直推数组
	 * @param map
	 * @return
	 */
	List<MUserRecommend> selectUserRecommendList(Map<String,Object> map);
	
	/**
	 * 新增用户团队会员
	 * @param userTeamMember
	 */
	void insertUserTeamMember(MUserTeamMember userTeamMember);
	
	/**
	 * 修改用户团队
	 * @param map
	 */
	void updateUserTeam(Map<String,Object> map);

	/**
	 * 获取酒店数组
	 * @param map
	 * @return
	 */
	List<MHotel> selectHotelList(Map<String, Object> map);

	/**
	 * 查询景点总数
	 * @param map
	 * @return
	 */
	int selectScenicSpotCount(Map<String,Object> map);

	/**
	 * 新增用户酒店
	 * @param userHotel
	 */
	void insertUserHotel(MUserHotel userHotel);

	/**
	 * 新增用户消费记录
	 * @param history
	 */
	void insertUserConsumptionHistory(MUserConsumptionHistory history);

	/**
	 * 给下级注册酒店增加团队收益
	 * @param map
	 */
	 void updateUserTeamHotel(Map<String, Object> map);

	/**
	 * 增加用户资金消费明细
 	 * @param detailOrder
	 */
	void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder);
}
