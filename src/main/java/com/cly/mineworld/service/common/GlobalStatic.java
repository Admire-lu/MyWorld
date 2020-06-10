package com.cly.mineworld.service.common;

import redis.clients.jedis.JedisPool;

public class GlobalStatic {
	
	/**
	 * Redis连接池
	 */
	public static JedisPool JEDISPOOL = null;
	
	/**
	 * 用户任务运行状态
	 */
	public static boolean USER_TASK_RUN_STATUS = false;
	
	/**
	 * 平台收款Token地址
	 */
	public static String PLATFORM_TOKEN_ADDRESS = "0xaa19bd14832f0b2221f8c991dc0ae9f2298c5679";
}
