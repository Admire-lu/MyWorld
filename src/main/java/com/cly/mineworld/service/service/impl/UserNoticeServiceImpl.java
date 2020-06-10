package com.cly.mineworld.service.service.impl;

import com.cly.mineworld.service.common.MineWorldUtils;
import com.cly.mineworld.service.dao.UserNoticeDao;
import com.cly.mineworld.service.service.UserNoticeService;
import com.cly.mineworld.service.vo.userNotice.UserNoticeVo;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserNoticeServiceImpl implements UserNoticeService {

	@Autowired
	private UserNoticeDao UserNoticeDao;


	/**
	 * 获取所有公告
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getNotices(String jsonData) {
		//jsonData报文格式：{"data":{"pageNum":" ","pageSize":" "}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String pageNum = data.getString("pageNum");
			String pageSize = data.getString("pageSize");
			if (null != pageNum && !"".equals(pageNum)
					&& null != pageSize && !"".equals(pageSize)) {
				Map<String, Object> mapQueryNotice = new HashMap<String, Object>();
				List<UserNoticeVo> list = UserNoticeDao.selectNoticeList(mapQueryNotice);
				if (list.size() > 0) {
					jData.put("notices", list);
				} else {
					result = "-2";//参数错误
					message = "Parameters error!";
				}
			} else {
				result = "-2";//参数错误
				message = "Parameters error!";
			}
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}

	/**
	 * 获取公告详情
	 * @param jsonData
	 * @return
	 */
	@Override
	public ResultVo getNoticeDetails(String jsonData) {
		//jsonData报文格式：{"data":{"noticeStrId":"c5c931a8bbdf49eab110960a1bd2b3d9"}}
		String result = "1";
		String message = "OK";
		JSONObject jData = new JSONObject();
		if (null != jsonData && !"".equals(jsonData)) {
			JSONObject data = JSONObject.fromObject(jsonData).getJSONObject("data");
			String noticeStrId = data.getString("noticeStrId");
			if (null != noticeStrId && !"".equals(noticeStrId)) {
				Map<String, Object> mapQueryNotice = new HashMap<String, Object>();
				mapQueryNotice.put("noticeStrId", noticeStrId);
				List<UserNoticeVo> list = UserNoticeDao.selectNoticeDetails(mapQueryNotice);
				if (list.size() > 0) {
					jData.put("notice", list);
				}
			} else {
				result = "-2";//参数错误
				message = "Parameters error!";
			}
		} else {
			result = "-2";//参数错误
			message = "Parameters error!";
		}
		return MineWorldUtils.createResultVo(result, message, jData);
	}
}
