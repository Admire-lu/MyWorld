package com.cly.mineworld.service.service.impl;

import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.dao.UserRegionDao;
import com.cly.mineworld.service.entity.MRegion;
import com.cly.mineworld.service.entity.MScenicSpot;
import com.cly.mineworld.service.service.UserRegionService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserRegionServiceImpl implements UserRegionService {

	@Autowired
	private UserRegionDao userRegionDao;
	
	@Override
	public ResultVo getRegions(String jsonData) {
		//jsonData报文格式：{"data":{"":""}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if(null != jsonData && !"".equals(jsonData)) {
			List<MRegion> listRegion = userRegionDao.selectRegionList(new HashMap<String,Object>());
			jData.put("regions", listRegion);
		}else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}

	@Override
	public ResultVo getScenicSpots(String jsonData) {
		//jsonData报文格式：{"data":{"":""}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if(null != jsonData && !"".equals(jsonData)) {
			List<MScenicSpot> listScenicSpot = userRegionDao.selectScenicSpotList(new HashMap<String,Object>());
			jData.put("scenicSpots", listScenicSpot);
		}else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}
}
