package com.cly.mineworld.service.service;

/**
 * 用户端任务调度服务
 * @author william
 *
 */
public interface UserTaskService {

	/**
	 * 每天用户收益计算
	 */
	void userProfitCalculateForDay();
}
