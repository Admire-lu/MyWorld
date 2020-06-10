package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.*;

public interface UserItemDao {

	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 查询道具数组
	 * @param map
	 * @return
	 */
	List<MBaseItem> selectItemList(Map<String,Object> map);
	
	/**
	 * 查询用户道具数组
	 * @param map
	 * @return
	 */
	List<MUserItem> selectUserItemList(Map<String,Object> map);

	/**
	 * 修改用户道具
	 * @param userItem
	 */
	void updateUserItem(MUserItem userItem);

	/**
	 * 修改用户道具
	 * @param map
	 */
	//void updateUserItem(Map<String,Object> map);
	
	/**
	 * 查询用户总条数
	 * @param map
	 * @return
	 */
	int selectUserCount(Map<String,Object> map);
	
//	/**
//	 * 查询用户酒店id数组
//	 * @param map
//	 * @return
//	 */
//	List<Integer> selectUserHotelIds(Map<String,Object> map);
	
	/**
	 * 根据用户酒店id查询用户酒店信息
	 * @param userHotelId
	 * @return
	 */
	MUserHotel selectUserHotelById(int userHotelId);
	
	/**
	 * 查询用户酒店每天收益订单数组
	 * @param map
	 * @return
	 */
	List<MUserHotelDayProfitOrder> selectUserHotelDayProfitOrderList(Map<String,Object> map);
	
	/**
	 * 修改用户酒店每天收益历史订单
	 * @param order
	 */
	void updateUserHotelDayProfitOrder(MUserHotelDayProfitOrder order);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(MUser user);
	
	/**
	 * 新增用户收益
	 * @param profit
	 */
	void insertUserProfit(MUserProfit profit);
	
	/**
	 * 新增用户资金详细订单
	 * @param order
	 */
	void insertUserFundDetailOrder(MUserFundDetailOrder order);

	/**
	 * 返回一种道具下所有等级的道具STRid
	 * @param map
	 * @return
	 */
	List<String> selectItemStrIds(Map<String, Object> map);

	/**
	 * 获取道具价格
	 * @param map
	 * @return
	 */
	Double selectItemPrice(Map<String, Object> map);

	/**
	 * 获取道具名称
	 * @param map
	 * @return
	 */
    String selectItemName(Map<String, Object> map);
    
    /**
     * 随机获取用户酒店数组
     * @param map
     * @return
     */
    List<MUserHotel> selectUserHotelForRand(Map<String,Object> map);
    
    /**
     * 查询每天红包池数组
     * @param map
     * @return
     */
    List<MLuckyMoneyPoolDay> selectLuckyMoneyPoolDayList(Map<String,Object> map);
    
    /**
     * 修改每天红包池信息
     * @param pool
     */
    void updateLuckyMoneyPoolDay(MLuckyMoneyPoolDay pool);

	/**
	 * 增加用户道具使用历史
	 * @param itemUseHistory
	 */
	void insertUserItemUseHistory(MUserItemUseHistory itemUseHistory);
}
