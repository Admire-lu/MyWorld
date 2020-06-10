package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserProfit;
import com.cly.mineworld.service.entity.MUserRechargeOrder;
import com.cly.mineworld.service.entity.MUserTeam;

public interface UserRechargeDao {

	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 查询用户充值订单总数
	 * @param map
	 * @return
	 */
	int selectUserRechargeOrderCount(Map<String,Object> map);
	
	/**
	 * 新增用户充值订单
	 * @param order
	 */
	void insertUserRechargeOrder(MUserRechargeOrder order);
	
	/**
	 * 修改用户
	 * @param map
	 */
	void updateUser(Map<String,Object> map);
	
	/**
	 * 新增用户收益记录
	 * @param userProfit
	 */
	void insertUserProfit(MUserProfit userProfit);
	
	/**
	 * 根据用户strId查询用户队伍
	 * @param userStrId
	 * @return
	 */
	List<MUserTeam> selectUserTeamByUserStrId(String userStrId);
	
	/**
	 * 修改用户团队
	 * @param map
	 */
	void updateUserTeam(Map<String,Object> map);
	
	/**
	 * 检查用户团队等级
	 * @param map
	 * @return
	 */
	int checkUserTeamLv(Map<String,Object> map);
}
