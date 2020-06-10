package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;

import com.cly.mineworld.service.entity.MAppVersion;
import com.cly.mineworld.service.entity.MUser;

public interface UserLoginDao {

	/**
	 * 查询用户数组
	 * @param map
	 * @return
	 */
	List<MUser> selectUserList(Map<String,Object> map);

	/**
	 * 游戏版本校验
	 * @param map
	 * @return
	 */
    List<MAppVersion> selectAppVersionList(Map<String, Object> map);
}
