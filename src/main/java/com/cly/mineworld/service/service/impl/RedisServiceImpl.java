package com.cly.mineworld.service.service.impl;

import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.GlobalStatic;
import com.cly.mineworld.service.service.RedisService;
import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService {
	
	@Override
	public Jedis getJedis() {
		return GlobalStatic.JEDISPOOL.getResource();
	}
}
