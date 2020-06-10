package com.cly.mineworld.service.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.*;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserTaskDao;
import com.cly.mineworld.service.vo.userTask.TaskHotelVo;

@Repository
@Transactional
public class UserTaskDaoImpl implements UserTaskDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;
	
	@Override
	public int selectUserProfitTaskHistoryCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUserProfitTaskHistory h ");
        hql.append(" where h.status = 1 ");
        if (map.get("taskDate") != null) {
        	hql.append(" and h.taskDate = '" + map.get("taskDate")+ "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<MUserProfitTaskHistory> selectUserProfitTaskHistoryList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserProfitTaskHistory h ");
		hql.append(" where h.status = 1 ");
        if (map.get("taskDate") != null) {
        	hql.append(" and h.taskDate = '" + map.get("taskDate")+ "'");
        }
		return em.createQuery(hql.toString(),MUserProfitTaskHistory.class).getResultList();
	}

	@Override
	public void insertUserProfitTaskHistory(MUserProfitTaskHistory history) {
		em.persist(history);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskHotelVo> selectHotelVoList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" h.str_id as hotelStrId, ");
		sql.append(" h.hotel_name as hotelName, ");
		sql.append(" h.hotel_lv as hotelLv, ");
		sql.append(" h.day_profit as dayProfit, ");
		sql.append(" h.day_pay as dayPay, ");
		sql.append(" u.userStrId as userStrId, ");
		sql.append(" u.userHotelId as userHotelId ");
		sql.append(" from m_hotel h ");
		sql.append(" join ");
		sql.append(" (SELECT uh.hotel_str_id AS hotelStrId ,uh.`user_str_id` AS userStrId,uh.id as userHotelId FROM m_user_hotel uh WHERE uh.str_id IN ");
		sql.append(" (SELECT t.`user_hotel_str_id` FROM m_user_hotel_manager_team t WHERE t.`work_status` = 1 and '" + map.get("lessWorkEndTime") + "' <= Date(t.work_end_time)) ");
		sql.append(" ) u " );
		sql.append(" on h.str_id = u.hotelStrId where 1=1 ");
		List<TaskHotelVo> listVo = new ArrayList<TaskHotelVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
		    Object[] rowArray = (Object[]) row;
		    TaskHotelVo vo = new TaskHotelVo();
		    vo.setHotelStrId(rowArray[0].toString());
		    vo.setHotelName(rowArray[1].toString());
		    vo.setHotelLv(Integer.parseInt(rowArray[2].toString()));
		    vo.setHotelDayProfit(Double.parseDouble(rowArray[3].toString()));
		    vo.setHotelDayPay(Double.parseDouble(rowArray[4].toString()));
		    vo.setUserStrId(rowArray[5].toString());
		    vo.setUserHotelId(Integer.parseInt(rowArray[6].toString()));
		    listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
        if (map.get("userStrId") != null) {
        	hql.append(" and u.strId = '" + map.get("userStrId")+ "'");
        }
		return em.createQuery(hql.toString(),MUser.class).getResultList();
	}

	@Override
	public void insertUserProfit(MUserProfit userProfit) {
		em.persist(userProfit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateUser(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		hql.append(" and u.strId = '"+map.get("userStrId").toString()+"'");
		query = em.createQuery(hql.toString());
		List<MUser> list = query.getResultList();
		if(list.size() > 0) {
			MUser user = em.find(MUser.class, list.get(0).getId(),lmt);
			if(null != map.get("vad")) {
				user.setVad(Double.parseDouble(map.get("vad").toString()));
			}
			user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(user);
		}
	}

	@Override
	public MUser selectUser(Map<String, Object> map) {
		MUser user = null;
		if(null != map.get("id")) {
			user = em.find(MUser.class, Integer.parseInt(map.get("id").toString()));
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MUser> selectUserForHotelDoBusinessByUserId(int userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT u.* FROM m_user u  ");
		sql.append(" LEFT JOIN ");
		sql.append(" m_user_hotel h  ");
		sql.append(" ON  ");
		sql.append(" u.`str_id` = h.`user_str_id`  ");
		sql.append(" WHERE  ");
		sql.append(" u.`status` = 1  ");
		sql.append(" AND  ");
		sql.append(" h.`do_business_status` = 1  ");
		sql.append(" AND  ");
		sql.append(" u.id = "+userId+" ");
		List<MUser> listVo = new ArrayList<MUser>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
		    Object[] rowArray = (Object[]) row;
		    MUser vo = new MUser();
		    vo.setId(Integer.parseInt(rowArray[0].toString()));
		    vo.setStrId(rowArray[1].toString());
		    vo.setVad(Double.parseDouble(rowArray[11].toString()));
		    vo.setUserLevelCode(rowArray[12].toString());
		    listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<MUser> selectUserListByLike(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
        if (map.get("userLevelCode") != null) {
        	hql.append(" and u.userLevelCode like '%" + map.get("userLevelCode").toString()+ "-%'");
        }
		return em.createQuery(hql.toString(),MUser.class).getResultList();
	}

	@Override
	public int selectRecommendCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUserRecommend r ");
        hql.append(" where r.status = 1 ");
        if (map.get("recommendUserStrId") != null) {
        	hql.append(" and r.recommendUserStrId = '" + map.get("recommendUserStrId")+ "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MUserHotel> selectUserHotelJoinUser(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select h.* FROM m_user_hotel h left join m_user u on h.user_str_id = u.str_id  WHERE u.id = "+map.get("userId").toString()+"");
		List<MUserHotel> listVo = new ArrayList<MUserHotel>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
		    Object[] rowArray = (Object[]) row;
		    MUserHotel vo = new MUserHotel();
		    vo.setId(Integer.parseInt(rowArray[0].toString()));
		    vo.setStrId(rowArray[1].toString());
		    vo.setDoBusinessStatus(Integer.parseInt(rowArray[7].toString()));
		    listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public void updateTimeOverUserHotelDoBusinessStatus(Map<String,Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE m_user_hotel uh SET uh.`do_business_status` = 2 ");
		sql.append(" WHERE uh.`status` = 1 AND uh.`str_id` IN  ");
		sql.append(" ( ");
		sql.append(" SELECT t.`user_hotel_str_id` AS 'userHotelStrId' FROM m_user_hotel_manager_team t ");
		sql.append(" WHERE t.`status` = 1 AND t.`work_status` = 1 AND t.`work_end_time` < DATE('"+map.get("nowDay")+"') ");
		sql.append(" ) ");
		em.createNativeQuery(sql.toString()).executeUpdate();
	}

	@Override
	public void updateTimeOverUserHotelManagerTeamWorkStatus(Map<String,Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE m_user_hotel_manager_team t SET t.`work_status` = 2 ");
		sql.append(" WHERE t.`status` = 1 AND t.`work_status` = 1 AND t.`work_end_time` < DATE('"+map.get("nowDay")+"') ");
		em.createNativeQuery(sql.toString()).executeUpdate();
	}

	@Override
	public List<MUserTeam> selectUserTeamList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeam t where t.status = 1 ");
		return em.createQuery(hql.toString(), MUserTeam.class).getResultList();
	}

	@Override
	public double sumUserTotalProfitForCurrentDay(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select sum(p.profitAmount) as totalProfit from MUserProfit p ");
        hql.append(" where p.status = 1 ");
        hql.append(" and p.userStrId = '"+map.get("userStrId").toString()+"' ");
        hql.append(" and p.createTime >= "+Long.parseLong(map.get("startTime").toString())+" ");
        hql.append(" and p.createTime <= "+Long.parseLong(map.get("endTime").toString())+" ");
        query = em.createQuery(hql.toString());
        if(null == query.getSingleResult()) {
        	return 0.00;
        }else {
            return Double.parseDouble(query.getSingleResult().toString());
        }
	}

	@Override
	public List<MUserTeamMember> selectUserTeamMemberList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeamMember m ");
		hql.append(" where m.status = 1 ");
		if(null != map.get("teamStrId")) {
			hql.append(" and m.teamStrId = '"+map.get("teamStrId").toString()+"' ");
		}
		if(null != map.get("notMemberStrId")) {
			hql.append(" and m.memberStrId != '"+map.get("notMemberStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(),MUserTeamMember.class).getResultList();
	}

	@Override
	public int selectHotelVoCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) FROM m_user_hotel_manager_team t WHERE t.`work_status` = 1 and '" + map.get("lessWorkEndTime") + "' <= Date(t.work_end_time) ");
		Object count = em.createNativeQuery(sql.toString()).getSingleResult();
		return Integer.parseInt(count.toString());
	}

	@Override
	public void insertUserHotelDayProfitOrder(MUserHotelDayProfitOrder order) {
		em.persist(order);
	}

	@Override
	public void updateUserHotel(MUserHotel userHotel) {
		em.merge(userHotel);
	}

	@Override
	public MUserHotel selectUserHotelByUserHotelId(int userHotelId) {
		return em.find(MUserHotel.class, userHotelId);
	}

	/**
	 * 新增资金明细订单
	 * @param detailOrder
	 */
	@Override
	public void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder) {
		em.persist(detailOrder);
	}
}
