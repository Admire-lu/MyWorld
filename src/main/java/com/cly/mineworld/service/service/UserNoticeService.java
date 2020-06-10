package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

public interface UserNoticeService {

	/**
	 * 获取公告
	 * @param jsonData
	 * @return
	 */
	ResultVo getNotices(String jsonData);

	/**
	 * 获取公告详情
	 * @param jsonData
	 * @return
	 */
    ResultVo getNoticeDetails(String jsonData);
}
