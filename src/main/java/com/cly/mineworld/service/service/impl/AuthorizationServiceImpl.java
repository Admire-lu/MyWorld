package com.cly.mineworld.service.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.service.AuthorizationService;
import com.cly.mineworld.service.service.RedisService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private RedisService redisService;

	@Override
	public ResultVo getToken(String jsonData) {
		//jsonData报文格式：{"data":{"sign":"123456"}}
		String result = "1";
		String message = "OK";
		jsonData="{'data':{'sign':'123456'}}";
		JSONObject jData = new JSONObject();
		if(null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String sign = data.getString("sign");
			if(null != sign && !"".equals(sign)) {
				String token = Utils.getUUID();
				Jedis jedis0 = redisService.getJedis();
				jedis0.select(0);//Token库
				jedis0.setex(token, 3600, Long.toString(Utils.getTimestamp()));
				jData.put("data", token);
				result = "1";
				message = "OK";
				jedis0.close();//关闭0库连接
			}else {
				result = "-2";//参数错误
				message = "Parameters error!";
			}
		}else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}
}
