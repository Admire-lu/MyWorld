package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userTask.TaskHotelVo;

public interface UserTaskDao {

	/**
	 * 查询用户计算收益任务历史总数
	 * @param map
	 * @return
	 */
	int selectUserProfitTaskHistoryCount(Map<String,Object> map);
	
	/**
	 * 查询用户收益任务历史数组
	 * @param map
	 * @return
	 */
	List<MUserProfitTaskHistory> selectUserProfitTaskHistoryList(Map<String,Object> map);
	
	/**
	 * 新增用户收益计算任务历史
	 * @param history
	 */
	void insertUserProfitTaskHistory(MUserProfitTaskHistory history);
	
	/**
	 * 查询酒店VO数组
	 * @param map
	 * @return
	 */
	List<TaskHotelVo> selectHotelVoList(Map<String,Object> map);
	
	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 新增用户收益
	 * @param userProfit
	 */
	void insertUserProfit(MUserProfit userProfit);
	
	/**
	 * 修改用户
	 * @param map
	 */
	void updateUser(Map<String,Object> map);
	
	/**
	 * 查询用户对象
	 * @param map
	 * @return
	 */
	MUser selectUser(Map<String,Object> map);
	
	/**
	 * 根据用户id（int）查询有酒店正在工作的用户
	 * @param map
	 * @return
	 */
	List<MUser> selectUserForHotelDoBusinessByUserId(int userId);
	
	/**
	 * 模糊查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserListByLike(Map<String,Object> map);
	
	/**
	 * 查询推荐人总数
	 * @param map
	 * @return
	 */
	int selectRecommendCount(Map<String,Object> map);
	
	/**
	 * 联合用户表查询用户酒店
	 * @param map
	 * @return
	 */
	List<MUserHotel> selectUserHotelJoinUser(Map<String,Object> map);
	
	/**
	 * 修改超时的用户酒店工作状态
	 * @param map
	 * @return
	 */
	void updateTimeOverUserHotelDoBusinessStatus(Map<String,Object> map);
	
	/**
	 * 修改超时的用户酒店管理团队工作状态
	 */
	void updateTimeOverUserHotelManagerTeamWorkStatus(Map<String,Object> map);
	
	/**
	 * 查询用户团队数组
	 * @param map
	 * @return
	 */
	List<MUserTeam> selectUserTeamList(Map<String,Object> map);
	
	/**
	 * 计算用户当天总收益
	 * @param map
	 * @return
	 */
	double sumUserTotalProfitForCurrentDay(Map<String,Object> map);
	
	/**
	 * 查询用户团队会员数组
	 * @param map
	 * @return
	 */
	List<MUserTeamMember> selectUserTeamMemberList(Map<String,Object> map);
	
	/**
	 * 查询酒店VO总数
	 * @param map
	 * @return
	 */
	int selectHotelVoCount(Map<String,Object> map);
	
	/**
	 * 新增用户酒店每天收益历史订单
	 * @param order
	 */
	void insertUserHotelDayProfitOrder(MUserHotelDayProfitOrder order);
	
	/**
	 * 修改用户酒店
	 * @param userHotel
	 */
	void updateUserHotel(MUserHotel userHotel);
	
	/**
	 *根据用户酒店id查询用户酒店信息
	 * @param userHotelId
	 * @return
	 */
	MUserHotel selectUserHotelByUserHotelId(int userHotelId);

	/**
	 * 新增资金
	 * @param detailOrder
	 */
	void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder);
}
