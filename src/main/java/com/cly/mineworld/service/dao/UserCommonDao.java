package com.cly.mineworld.service.dao;

import java.util.List;

import com.cly.mineworld.service.entity.MAppVersion;

public interface UserCommonDao {

	/**
	 * 查询应用版本数组
	 * @return
	 */
	List<MAppVersion> selectAppVersionList();
}
