package com.cly.mineworld.service.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.cly.mineworld.service.common.GlobalStatic;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private Environment env;
    
    @Value("${spring.redis.host}")  
    private String redisIp;
    @Value("${spring.redis.port}")  
    private String redisPort;
    @Value("${spring.redis.jedis.pool.max-wait}")  
    private String redisMaxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")  
    private String redisIdle;
    @Value("${spring.redis.jedis.pool.max-active}")  
    private String redisMaxActive;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("**** Mine world Service Start ****");
		init();
	}
	
	/**
	 * init
	 */
	private void init(){
		initRedisPool();
	}
	
	private void initRedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //配置最大jedis实例数
        config.setMaxTotal(Integer.parseInt(redisMaxActive));
        //配置资源池最大闲置数
        config.setMaxIdle(Integer.parseInt(redisIdle));
        //等待可用连接的最大时间
        config.setMaxWaitMillis(Integer.parseInt(redisMaxWait));
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        GlobalStatic.JEDISPOOL = new JedisPool(config,redisIp,Integer.parseInt(redisPort));
	}
}
