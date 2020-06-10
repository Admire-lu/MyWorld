package com.cly.mineworld.service.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import com.cly.mineworld.service.vo.userHoterTeam.UserHotelTeamVo;
import com.cly.mineworld.service.vo.userManagerTeam.UserManagerTeamVo;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserHotelDao;
import com.cly.mineworld.service.vo.userHotel.UserHotelVo;

import static java.lang.Double.*;

@Repository
@Transactional
public class UserHotelDaoImpl implements UserHotelDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;


	/**
	 * 查找我的管理团队信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserManagerTeamVo> chooseTheHotel(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select *  from ");
		sql.append(" ( select ");
		sql.append(" mt.user_str_id as userStrId, ");
		sql.append(" mt.user_hotel_str_id as userHotelStrId, ");
		sql.append(" mt.hotel_manager_team_str_id as hmtStrId, ");
		sql.append(" mt.work_start_time as startTime, ");
		sql.append(" mt.work_end_time as endTime, ");
		sql.append(" uh.hotel_custom_name as hotelName ");
		sql.append(" from m_user_hotel_manager_team mt ");
		sql.append(" right join m_user_hotel uh ");
		sql.append(" on mt.user_hotel_str_id = uh.str_id ");
		sql.append(" where ");
		sql.append(" mt.work_status = 1 ");
		sql.append(" and uh.status= 1 ");
		if (map.get("userStrId") != null) {
			sql.append(" and mt.user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		sql.append(" and");
		sql.append(" uh.str_id in  ");
		sql.append(" (select user_hotel_str_id from  m_user_hotel_manager_team where ");
		if (map.get("userStrId") != null) {
			sql.append("  user_str_id = '" + map.get("userStrId").toString() + "')) hn");
		}
		sql.append(" join ");
		sql.append(" ( select ");
		sql.append(" mh.team_name as teamName, ");
		sql.append(" mh.team_category as teamCategory ");
		sql.append(" from  m_hotel_manager_team mh ");
		sql.append(" right join m_user_hotel_manager_team mt  ");
		sql.append(" on mt.hotel_manager_team_str_id = mh.str_id ");
		sql.append(" where ");
		sql.append(" mt.work_status = 1 ");
		sql.append(" and mh.status= 1 ");
		if (map.get("userStrId") != null) {
			sql.append(" and mt.user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		sql.append(" and");
		sql.append(" mh.str_id in ");
		sql.append(" (select hotel_manager_team_str_id from  m_user_hotel_manager_team where ");
		if (map.get("userStrId") != null) {
			sql.append("  user_str_id = '" + map.get("userStrId").toString() + "')) tn");
		}
		List<UserManagerTeamVo> listVo = new ArrayList<UserManagerTeamVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			UserManagerTeamVo vo = new UserManagerTeamVo();
			String nowTime = Utils.getNowDateStr2(); //yyyy-MM-dd
			String workEndTime = rowArray[4].toString();
			Long surplusManagerTime = Utils.getDaysBetween(nowTime, workEndTime);
			vo.setUserStrId(rowArray[0].toString());
			vo.setUserHotelStrId(rowArray[1].toString());
			vo.setHotelManagerTeamStrId(rowArray[2].toString());
			vo.setWorkStartTime(rowArray[3].toString());
			vo.setWorkEndTime(workEndTime);
			vo.setHotelName(rowArray[5].toString());
			vo.setTeamName(rowArray[6].toString());
			vo.setTeamCategory(rowArray[7].toString());
			vo.setSurplusManagerTime(String.valueOf(surplusManagerTime));
			listVo.add(vo);
		}
		return listVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MHotel> selectHotelList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MHotel h ");
		hql.append(" where h.status = 1 ");
		if (null != map.get("hotelStrId")) {
			hql.append(" and h.strId = '" + map.get("hotelStrId").toString() + "' ");
		}
		if (null != map.get("teamCategory")) {
			hql.append(" and h.hotelLv = '" + map.get("teamCategory").toString() + "' ");
		}
		query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		if (null != map.get("userStrId")) {
			hql.append(" and u.strId = '" + map.get("userStrId").toString() + "' ");
		}
		query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@Override
	public void insertUserHotel(MUserHotel userHotel) {
		em.persist(userHotel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateUser(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		hql.append(" and u.strId = '" + map.get("userStrId").toString() + "'");
		query = em.createQuery(hql.toString());
		List<MUser> list = query.getResultList();
		if (list.size() > 0) {
			MUser user = em.find(MUser.class, list.get(0).getId(), lmt);
			if (null != map.get("vad")) {
				user.setVad(parseDouble(map.get("vad").toString()));
			}
			if (null != map.get("userLeveCode")) {
				user.setUserLevelCode(map.get("userLeveCode").toString());
			}
			user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(user);
		}
	}

	@Override
	public int selectScenicSpotCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from MScenicSpot s ");
		hql.append(" where s.status = 1 ");
		if (map.get("scenicSpotStrId") != null) {
			hql.append(" and s.strId = '" + map.get("scenicSpotStrId") + "'");
		}
		query = em.createQuery(hql.toString());
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public void insertUserConsumptionHistory(MUserConsumptionHistory history) {
		em.persist(history);
	}

	@Override
	public List<MHotelManagerTeam> selectHotelManagerTeamList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MHotelManagerTeam t ");
		hql.append(" where t.status = 1 ");
		return em.createQuery(hql.toString(), MHotelManagerTeam.class).getResultList();
	}

	/**
	 * 查询用户酒店列表
	 * @param map
	 * @return
	 */
	@Override
	public List<MUserHotel> selectUserHotelList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserHotel h ");
		hql.append(" where h.status = 1 ");
		if (map.get("userStrId") != null) {
			hql.append(" and h.userStrId = '" + map.get("userStrId").toString() + "'");
		}
		if (map.get("userHotelStrId") != null) {
			hql.append(" and h.strId = '" + map.get("userHotelStrId").toString() + "'");
		}
		if (map.get("hotelStrId") != null) {
			hql.append(" and h.hotelStrId = '" + map.get("hotelStrId").toString() + "'");
		}
		/*if (map.get("doBusinessStatus") != null) {
			hql.append(" and h.doBusinessStatus = '" + map.get("doBusinessStatus").toString() + "'");
		}*/
		return em.createQuery(hql.toString(), MUserHotel.class).getResultList();
	}

	/**
	 * 查询用户一个景点下一个多少个酒店
	 * @param map
	 * @return
	 */
	@Override
	public Integer selectUserHotels(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from MUserHotel t ");
		hql.append(" where t.status = 1 ");
		if (map.get("userStrId") != null) {
			hql.append(" and t.userStrId = '" + map.get("userStrId").toString() + "'");
		}
		if (map.get("scenicSpotStrId") != null) {
			hql.append(" and t.scenicSpotStrId = '" + map.get("scenicSpotStrId") + "'");
		}
		return Integer.valueOf(em.createQuery(hql.toString()).getSingleResult().toString());
	}

	@Override
	public List<MUserHotelManagerTeam> selectUserHotelManagerTeamList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserHotelManagerTeam t ");
		hql.append(" where t.status = 1 ");
		if (map.get("userHotelStrId") != null) {
			hql.append(" and t.userHotelStrId = '" + map.get("userHotelStrId").toString() + "'");
		}
		/*2019-10-30日修改 查询出用户这个酒店是否有管理团队，不考虑营业状态*/
		/*if (map.get("workStatus") != null) {
			hql.append(" and t.workStatus = " + map.get("workStatus") + "");
		}*/
		return em.createQuery(hql.toString(), MUserHotelManagerTeam.class).getResultList();
	}

	@Override
	public void insertUserHotelManagerTeam(MUserHotelManagerTeam managerTeam) {
		em.persist(managerTeam);
	}

	@Override
	public void updateUserHotel(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserHotel h ");
		hql.append(" where h.status = 1 ");
		if (null != map.get("userHotelStrId")) {
			hql.append(" and h.strId = '" + map.get("userHotelStrId").toString() + "'");
		}
		List<MUserHotel> list = em.createQuery(hql.toString(), MUserHotel.class).getResultList();
		if (list.size() > 0) {
			MUserHotel userHotel = em.find(MUserHotel.class, list.get(0).getId(), lmt);
			if (null != map.get("doBusinessStatus")) {
				userHotel.setDoBusinessStatus(Integer.parseInt(map.get("doBusinessStatus").toString()));
			}
			if (null != map.get("hotelCustomName")) {
				userHotel.setHotelCustomName(map.get("hotelCustomName").toString());
			}
			userHotel.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(userHotel);
		}
	}

	@Override
	public void updateUserHotelManagerTeam(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

	/**
	 * 用户酒店查询
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserHotelVo> selectHotelJoinUserHotel(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" h.str_id as hotelStrId, ");
		sql.append(" h.hotel_name as hotelName, ");
		sql.append(" h.hotel_desc as hotelDesc, ");
		sql.append(" h.hotel_image as hotelImage, ");
		sql.append(" h.start_cost as startCost, ");
		sql.append(" h.hotel_lv as hotelLv, ");
		sql.append(" h.day_profit as dayProfit, ");
		sql.append(" h.day_pay as dayPay, ");
		sql.append(" uh.hotel_custom_name as hotelCustomName, ");
		sql.append(" uh.do_business_days as doBusinessDays, ");
		sql.append(" uh.profit as profit, ");
		sql.append(" uh.do_business_status as doBusinessStatus,");
		sql.append(" uh.stop_business_days as stopBusinessDays, ");
		sql.append(" uh.str_id as userHotelStrId, ");
		sql.append(" uh.create_time as openTime ");
		sql.append(" from m_hotel h ");
		sql.append(" right join ");
		sql.append(" m_user_hotel uh ");
		sql.append(" on h.str_id = uh.hotel_str_id ");
		sql.append(" where ");
		sql.append(" uh.status = 1 ");
		if (map.get("userStrId") != null) {
			sql.append(" and uh.user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		if (map.get("scenicSpotStrId") != null) {
			sql.append(" and uh.scenic_spot_str_id = '" + map.get("scenicSpotStrId").toString() + "'");
		}//LIMIT 0,2
		if (map.get("pageNum") != null && map.get("pageSize") != null ){
			sql.append(" limit " +map.get("pageNum")+","+ map.get("pageSize"));
		}
		//获取已经完成营业的时间
		List<Map<String, Object>> completeTeamList = selectComplete(map);
		System.out.println(completeTeamList);
		//获取正在运营的时间
		List<Map<String, Object>> beingTeamList = selectBeing(map);//todo 需要酒店ID
		for (Map<String, Object> stringObjectMap : beingTeamList) {
			System.out.println(stringObjectMap.get("userHotelStrId"));
			System.out.println(stringObjectMap.get("alreadyManagerTime"));
		}
		System.out.println(beingTeamList);
		List<UserHotelVo> listVo = new ArrayList<UserHotelVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			UserHotelVo vo = new UserHotelVo();
			vo.setHotelStrId(rowArray[0].toString());
			vo.setHotelName(rowArray[1].toString());
			vo.setHotelDesc(rowArray[2].toString());
			vo.setHotelImage(rowArray[3].toString());
			vo.setStartCost(parseDouble(rowArray[4].toString()));
			vo.setHotelLv(Integer.parseInt(rowArray[5].toString()));
			vo.setDayProfit(parseDouble(rowArray[6].toString()));
			vo.setDayPay(parseDouble(rowArray[7].toString()));
			vo.setHotelCustomName(rowArray[8].toString());
			vo.setDoBusinessDays(Integer.parseInt(rowArray[9].toString()));
			vo.setProfit(Double.parseDouble(rowArray[10].toString()));
			vo.setDoBusinessStatus(Integer.parseInt(rowArray[11].toString()));
			vo.setStopBusinessDays(Integer.parseInt(rowArray[12].toString()));
			vo.setUserHotelStrId(rowArray[13].toString());
			vo.setOpenTime(Utils.timeStampToDate(Long.valueOf(rowArray[14].toString())));
			/*vo.setDoBusinessStatus(Integer.parseInt(rowArray[9].toString()));
 			vo.setStopBusinessDays(Integer.parseInt(rowArray[10].toString()));
			vo.setUserHotelStrId(rowArray[11].toString());
			vo.setOpenTime(Utils.timeStampToDate(Long.valueOf(rowArray[12].toString())));
			//循环外加计数器
			Integer doBusinessDays = 0;
			if (completeTeamList.size() > 0) {
				//有已经完成运营的
				for (Map<String, Object> completeMap : completeTeamList) {
					if (completeMap.get("userHotelStrId").equals(rowArray[11].toString())) {
						for (Map<String, Object> beginMap : beingTeamList) {
							if (beginMap.get("userHotelStrId").equals(rowArray[11].toString())) {
								doBusinessDays = (Integer) completeMap.get("countNumber") * 7 + (Integer) beginMap.get("alreadyManagerTime");
								vo.setSurplusManagerTime(String.valueOf(beginMap.get("surplusManagerTime")));
							}else {
								doBusinessDays = (Integer) completeMap.get("countNumber") * 7;
								vo.setSurplusManagerTime("0");
							}
						}
					}else {
						//没有已经完成运营的
						for (Map<String, Object> beginMap : beingTeamList) {
							if (beginMap.get("userHotelStrId").equals(rowArray[11].toString())) {
								doBusinessDays = (Integer) beginMap.get("alreadyManagerTime");
								vo.setSurplusManagerTime(String.valueOf(beginMap.get("surplusManagerTime")));
							}
						}
					}
				}
			} else {
				//没有已经完成运营的
					for (Map<String, Object> beginMap : beingTeamList) {
						if (beginMap.get("userHotelStrId").equals(rowArray[11].toString())) {
							doBusinessDays = (Integer) beginMap.get("alreadyManagerTime");
							vo.setSurplusManagerTime(String.valueOf(beginMap.get("surplusManagerTime")));
						}
					}
			}
			vo.setDoBusinessDays(doBusinessDays);
			Double profit  = doBusinessDays * Double.parseDouble(rowArray[6].toString());
			vo.setProfit(profit);*/
			listVo.add(vo);
		}
		return listVo;
	}

	/**
	 * 我的酒店
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserHotelVo> selectHotelJoinMyHotel(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" h.str_id as hotelStrId, "); //0
		sql.append(" h.hotel_name as hotelName, ");//1
		sql.append(" h.hotel_desc as hotelDesc, ");//2
		sql.append(" h.hotel_image as hotelImage, ");//3
		sql.append(" h.start_cost as startCost, ");//4
		sql.append(" h.hotel_lv as hotelLv, ");//5
		sql.append(" h.day_profit as dayProfit, ");//6
		sql.append(" h.day_pay as dayPay, ");//7
		sql.append(" uh.hotel_custom_name as hotelCustomName, ");//8
		sql.append(" uh.do_business_days as doBusinessDays, ");//9
		sql.append(" uh.profit as profit, ");//10
		sql.append(" uh.do_business_status as doBusinessStatus,");//11
		sql.append(" uh.stop_business_days as stopBusinessDays, ");//12
		sql.append(" uh.str_id as userHotelStrId, ");//13
		sql.append(" uh.create_time as openTime, ");//14
		sql.append(" uh.scenic_spot_str_id as scenicSpotStrId ");//15
		sql.append(" from m_hotel h ");
		sql.append(" right join ");
		sql.append(" m_user_hotel uh ");
		sql.append(" on h.str_id = uh.hotel_str_id ");
		sql.append(" where ");
		sql.append(" uh.status = 1 ");
		if (map.get("userStrId") != null) {
			sql.append(" and uh.user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		List<UserHotelVo> listVo = new ArrayList<UserHotelVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			UserHotelVo vo = new UserHotelVo();
			vo.setHotelStrId(rowArray[0].toString());
			vo.setHotelName(rowArray[1].toString());
			vo.setHotelDesc(rowArray[2].toString());
			vo.setHotelImage(rowArray[3].toString());
			vo.setStartCost(parseDouble(rowArray[4].toString()));
			vo.setHotelLv(Integer.parseInt(rowArray[5].toString()));
			vo.setDayProfit(parseDouble(rowArray[6].toString()));
			vo.setDayPay(parseDouble(rowArray[7].toString()));
			vo.setHotelCustomName(rowArray[8].toString());
			vo.setDoBusinessDays(Integer.parseInt(rowArray[9].toString()));//TODO已营业时间
			vo.setProfit(Double.parseDouble(rowArray[10].toString()));
			vo.setDoBusinessStatus(Integer.parseInt(rowArray[11].toString()));//营业状态
			vo.setStopBusinessDays(Integer.parseInt(rowArray[12].toString()));
			vo.setUserHotelStrId(rowArray[13].toString());
			vo.setOpenTime(Utils.timeStampToDate(Long.valueOf(rowArray[14].toString())));
			//获取正在运营的时间
			map.put("scenicSpotStrId",rowArray[15].toString());
			//map.put("userHotelStrId",rowArray[13].toString());//2019-10-13修改
			List<Map<String, Object>> beingTeamList = selectBeing(map);
			if ("1".equals(rowArray[11].toString())){//营业中
				for (Map<String, Object>  beingTeamMap: beingTeamList) {
					if (rowArray[13].toString().equals(beingTeamMap.get("userHotelStrId"))){
						vo.setSurplusManagerTime(String.valueOf(beingTeamMap.get("surplusManagerTime")));
					}
				}
			}else {
				vo.setSurplusManagerTime("0");
			}
			//根据scenicSpotStrId查询区域信息
			String region = selectRegion(rowArray[15].toString());
			if ("亚洲".equals(region)){
				vo.setRegionName("0");
			}
			if ("北美洲".equals(region)){
				vo.setRegionName("1");
			}
			if ("南美洲".equals(region)){
				vo.setRegionName("2");
			}
			if ("大洋洲".equals(region)){
				vo.setRegionName("3");
			}
			if ("欧洲".equals(region)){
				vo.setRegionName("4");
			}
			if ("非洲".equals(region)){
				vo.setRegionName("5");
			}
			if ("南极洲".equals(region)){
				vo.setRegionName("6");
			}
			listVo.add(vo);
		}
		return listVo;
	}

	/**
	 * 新增资金明细
	 * @param detailOrder
	 */
	@Override
	public void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder) {
       em.persist(detailOrder);
	}

	/**
	 * 查询定向vad数组
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MActivity> selectDirectionalVADList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MActivity a ");
		hql.append(" where a.status = 1 ");
		if (null != map.get("userStrId")) {
			hql.append(" and a.userStrId = '" + map.get("userStrId").toString() + "' ");
		}
		query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	/**
	 * 更新定向地址错误
	 * @param map
	 */
	@Override
	public void updateUserActivity(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MActivity a ");
		hql.append(" where a.status = 1 ");
		hql.append(" and a.userStrId = '" + map.get("userStrId").toString() + "'");
		query = em.createQuery(hql.toString());
		List<MActivity> list = query.getResultList();
		if (list.size() > 0) {
			MActivity activity = em.find(MActivity.class, list.get(0).getId(), lmt);
			if (null != map.get("vad")) {
				activity.setAmount(parseDouble(map.get("vad").toString()));
			}
			activity.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(activity);
		}
	}

	/**
	 * 补充管理时间
	 * @param managerTeam
	 */
	@Override
	public void updateHotelManagerTeam(MUserHotelManagerTeam managerTeam) {
		em.merge(managerTeam);
	}

	/**
     * 查询区域信息
     * @param
     * @return
     */
    private String selectRegion(String scenicSpotStrId) {
        StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" id, ");
		sql.append(" region_name as regionName ");
		sql.append(" from ");
		sql.append(" m_region ");
		sql.append(" where status = 1  ");
		sql.append(" and  str_id = ");
		sql.append("( select  region_str_id from m_scenic_spot  ");
		sql.append(" where status = 1 ");
		sql.append(" and str_id = '" + scenicSpotStrId + "')");
		String region = null;
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			region = rowArray[1].toString();
		}
		return region;
    }

    /**
	 * 已经运营完成的时间
	 * @param map
	 * @return
	 */
	private List<Map<String, Object>> selectComplete(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" user_hotel_str_id as userHotelStrId, ");
		sql.append(" count(user_hotel_str_id) as countNumber ");
		sql.append(" from m_user_hotel_manager_team ");
		sql.append(" where status = 1 ");
		sql.append(" and work_status = 2 ");
		sql.append(" and user_hotel_str_id in ");
		sql.append("  ( select str_id as userHotelStrId from m_user_hotel where ");
		if (map.get("userStrId") != null) {
			sql.append(" user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		if (map.get("scenicSpotStrId") != null) {
			sql.append(" and scenic_spot_str_id = '" + map.get("scenicSpotStrId").toString() + "')");
		}
		sql.append(" group by user_hotel_str_id");
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			Map<String, Object> completeMap = new HashMap<String, Object>();
			if (!"null".equals(rowArray[0] + "")){
				Integer countNumber = Integer.valueOf(rowArray[1].toString());
				completeMap.put("userHotelStrId", rowArray[0].toString());
				completeMap.put("countNumber", countNumber);
			}else {
				//停业状态
				completeMap.put("userHotelStrId", "停业中");
				completeMap.put("countNumber", 0.00);
			}
			listVo.add(completeMap);
		}
		return listVo;
	}

	/**
	 * 查询正在运营的团队
	 * @param map
	 * @return
	 */
	private List<Map<String, Object>> selectBeing(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" user_hotel_str_id as userHotelStrId, ");
		sql.append(" work_start_time as workStartTime, ");
		sql.append(" work_end_time as workEndTime ");
		sql.append(" from m_user_hotel_manager_team ");
		sql.append(" where status = 1 ");
		//sql.append(" and work_status = 1 ");
		sql.append(" and user_hotel_str_id in ");
		sql.append("  ( select str_id as userHotelStrId from m_user_hotel where ");
		if (map.get("userStrId") != null) {
			sql.append(" user_str_id = '" + map.get("userStrId").toString() + "'");
		}
		if (map.get("scenicSpotStrId") != null) {
			sql.append(" and scenic_spot_str_id = '" + map.get("scenicSpotStrId").toString() + "')");
		}
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			Map<String, Object> beingMap = new HashMap<String, Object>();
			String nowTime = Utils.getNowDateStr2();
			String workStartTime = rowArray[1].toString();
			String workEndTime = rowArray[2].toString();
			Long surplusManagerTime = Utils.getDaysBetween(nowTime, workEndTime);
			if (surplusManagerTime < 0) { //小于零时就返回零
				beingMap.put("surplusManagerTime", 0);
			} else {
				beingMap.put("surplusManagerTime", surplusManagerTime);
			}
			Integer alreadyManagerTime = Math.toIntExact(Utils.getDaysBetween(workStartTime, nowTime));
			beingMap.put("userHotelStrId", rowArray[0].toString());
			beingMap.put("alreadyManagerTime", alreadyManagerTime);
			listVo.add(beingMap);
		}
		return listVo;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserHotelTeamVo> selectHotelManagerTeamJoinUserHotelManagerTeam(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" ht.str_id as teamId, ");
		sql.append(" ht.team_name as teamName, ");
		sql.append(" ht.team_desc as teamDesc, ");
		sql.append(" ht.team_category as teamCategor, ");
		sql.append(" ht.team_image as teamImage, ");
		sql.append(" uht.work_status as workStatus, ");
		sql.append(" uht.work_start_time as workStartTime, ");
		sql.append(" uht.work_end_time as workEndTime ");
		sql.append(" from m_hotel_manager_team ht ");
		sql.append(" right join ");
		sql.append(" m_user_hotel_manager_team uht ");
		sql.append(" on ht.str_id = uht.hotel_manager_team_str_id ");
		sql.append(" where ");
		sql.append(" uht.status = 1 ");
		if (map.get("workStatus") != null) {
			sql.append(" and uht.work_status = '" + map.get("workStatus").toString() + "'");
		}
		if (map.get("userHotelStrId") != null) {
			sql.append(" and uht.user_hotel_str_id = '" + map.get("userHotelStrId").toString() + "'");
		}
		List<UserHotelTeamVo> listVo = new ArrayList<UserHotelTeamVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			String nowDateStr2 = Utils.getNowDateStr2();//yyyy-MM--dd
			String startTime = rowArray[6].toString();
			Long alreadyManagerTime = Utils.getDaysBetween(startTime, nowDateStr2);
			//UserHotelVo vo = new UserHotelVo();
			UserHotelTeamVo vo = new UserHotelTeamVo();
			vo.setTeamId(rowArray[0].toString());
			vo.setTeamName(rowArray[1].toString());
			vo.setTeamDesc(rowArray[2].toString());
			vo.setTeamCategor(rowArray[3].toString());
			vo.setTeamImage(rowArray[4].toString());
			vo.setWorkStatus(Integer.parseInt(rowArray[5].toString()));
			vo.setWorkStartTime(startTime);
			vo.setWorkEndTime(rowArray[7].toString());
			vo.setAlreadyManagerTime(alreadyManagerTime.toString()); //TODO 计算时间差
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public String findSurplusManagerTime(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		// select work_end_time as workEndTime from  m_user_hotel_manager_team where user_hotel_str_id = '3caa67ec609d4dd98b9de7b7a0be8c3b'
		sql.append(" select ");
		sql.append(" id ,");
		sql.append(" work_end_time as workEndTime ");
		sql.append(" from m_user_hotel_manager_team ");
		sql.append(" where status = 1 ");
		sql.append(" and work_status = 1 ");
		if (map.get("userHotelStrId") != null) {
			sql.append(" and user_hotel_str_id = '" + map.get("userHotelStrId").toString() + "'");
		}
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		String surplusManagerTime = "0";
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			String nowTime = Utils.getNowDateStr2(); //yyyy-MM-dd
			String workEndTime= rowArray[1].toString();
			surplusManagerTime = String.valueOf(Utils.getDaysBetween(nowTime, workEndTime));
			if (Integer.parseInt(surplusManagerTime) < 0) {
				surplusManagerTime = "0";
			}
		}
		return surplusManagerTime;
	}

}
