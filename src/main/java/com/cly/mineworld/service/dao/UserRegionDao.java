package com.cly.mineworld.service.dao;

import java.util.List;
import java.util.Map;
import com.cly.mineworld.service.entity.MRegion;
import com.cly.mineworld.service.entity.MScenicSpot;

public interface UserRegionDao {

	/**
	 * 查询区域数组
	 * @param map
	 * @return
	 */
	List<MRegion> selectRegionList(Map<String,Object> map);
	
	/**
	 * 查询景点数组
	 * @param map
	 * @return
	 */
	List<MScenicSpot> selectScenicSpotList(Map<String,Object> map);
}
