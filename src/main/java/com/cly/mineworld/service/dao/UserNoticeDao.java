package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.vo.userNotice.UserNoticeVo;

import java.util.List;
import java.util.Map;

public interface UserNoticeDao {

	/**
	 * 查询公告数组
	 * @param map
	 * @return
	 */
	List<UserNoticeVo> selectNoticeList(Map<String, Object> map);

	/**
	 * 获取公告详情
	 * @param map
	 * @return
	 */
	List<UserNoticeVo> selectNoticeDetails(Map<String, Object> map);
}
