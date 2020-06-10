package com.cly.mineworld.service.service;

import redis.clients.jedis.Jedis;

public interface RedisService {

	/**
	 * redis表分配
	 * 0 = token
	 * 1 = 邮箱验证码
	 * 2 = 手机验证码
	 */
	
	/**
	 * 获取Jedis
	 * @return
	 */
	Jedis getJedis();
}
