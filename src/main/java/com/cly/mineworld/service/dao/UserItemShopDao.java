package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.*;

public interface UserItemShopDao {

	/**
	 * 查询道具分类数组
	 * @param map
	 * @return
	 */
	List<MBaseItemCategory> selectItemCategoryList(Map<String,Object> map);
	
	/**
	 * 查询道具数组
	 * @param map
	 * @return
	 */
	List<MBaseItem> selectItemList(Map<String,Object> map);
	
	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);
	
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(MUser user);
	
	/**
	 * 查询用户道具数组
	 * @param map
	 * @return
	 */
	List<MUserItem> selectUserItemList(Map<String,Object> map);
	
	/**
	 * 新增用户道具
	 * @param userItem
	 */
	void insertUserItem(MUserItem userItem);
	
	/**
	 * 修改用户道具
	 * @param userItem
	 */
	void updateUserItem(MUserItem userItem);
	
	/**
	 * 新增用户消费历史
	 * @param history
	 */
	void insertUserConsumptionHistory(MUserConsumptionHistory history);
	
	/**
	 * 新增用户资金明细订单
	 * @param order
	 */
	void insertUserFundDetailOrder(MUserFundDetailOrder order);

	/**
	 * 查询道具剩余购买次数
	 * @param map
	 * @return
	 */
    List<MItemTotalCount> selectItemTotalCountList(Map<String, Object> map);

	/**
	 * 更新道具库存
	 * @param map
	 */
	void updateItemTotalCount(Map<String, Object> map);

	/**
	 * 查询用户拥有酒店的数量
	 * @param map
	 * @return
	 */
    Integer selectUserHotelCount(Map<String, Object> map);

	/**查询用户当日购买的次数
	 *
	 * @param map
	 * @return
	 */
	Integer selectUserByItemCount(Map<String, Object> map);

	/**
	 * 新增购买记录
	 * @param buyHistory
	 */
	void insertUserItemBuyHistory(MUserItemBuyHistory buyHistory);
}
