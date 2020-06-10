package com.cly.mineworld.service.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.dao.UserCommonDao;
import com.cly.mineworld.service.entity.MAppVersion;
import com.cly.mineworld.service.service.UserCommonService;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;

@Service
@Transactional
public class UserCommonServiceImpl implements UserCommonService {

	@Autowired
	private UserCommonDao userCommonDao;
	
	@Override
	public ResultVo getAppVersion(String jsonData) {
		//jsonData报文格式：{"data":{"":""}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if(null != jsonData && !"".equals(jsonData)) {
			List<MAppVersion> listAppVersion = userCommonDao.selectAppVersionList();
			jData.put("version", listAppVersion.get(0));
		}else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}
}
