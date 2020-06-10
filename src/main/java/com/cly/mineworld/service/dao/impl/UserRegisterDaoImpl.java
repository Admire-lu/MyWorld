package com.cly.mineworld.service.dao.impl;

import java.util.Arrays;
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
import com.cly.mineworld.service.dao.UserRegisterDao;

@Repository
@Transactional
public class UserRegisterDaoImpl implements UserRegisterDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;


	/**
	 * 查询用户是否在同一推荐线上
	 * @param map
	 * @return
	 */
	@Override
	public Boolean selectUserRecommend(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" str_id,");
		sql.append(" user_level_code,");
		sql.append(" id");
		sql.append(" from m_user");
		sql.append(" where status = 1 ");
		if (null != map.get("friendId")){
			sql.append("and id = " + map.get("friendId").toString());
		}
		Boolean isItCorrect = true;
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			String userLevelCode = rowArray[1].toString();
			String[] userLevelCodes = userLevelCode.split("-");
			List<String> userLevelCodeList = Arrays.asList(userLevelCodes);//获取的是当前用户user_level_code集合
			if (userLevelCodeList.contains(map.get("userId").toString())
					&& userLevelCodeList.contains(map.get("friendId").toString())){//当前用户的user_level_code有这个两个
				isItCorrect = true ;
			}else {
				isItCorrect = false;
			}
		}
		return isItCorrect;
	}
	
	@Override
	public int selectUserCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUser u ");
        hql.append(" where u.status = 1 ");
        if (map.get("nickname") != null) {
        	hql.append(" and u.nickname = '" + map.get("nickname")+ "'");
        }
        if (map.get("email") != null) {
        	hql.append(" and u.email = '" + map.get("email")+ "'");
        }
		if (map.get("mobile") != null) {
			hql.append(" and u.mobile = '" + map.get("mobile")+ "'");
		}
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		if(null != map.get("email")) {
			hql.append(" and u.email = '"+map.get("email").toString()+"' ");
		}
		if(null != map.get("mobile")) {
			hql.append(" and u.mobile = '"+map.get("mobile").toString()+"' ");
		}
		if(null != map.get("psw")) {
			hql.append(" and u.psw = '"+map.get("psw").toString()+"' ");
		}
		if(null != map.get("userStrId")) {
			hql.append(" and u.strId = '"+map.get("userStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(),MUser.class).getResultList();
	}

	@Override
	public void insertUser(MUser user) {
		em.persist(user);
	}

	@Override
	public void updateUser(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		hql.append(" and u.strId = '"+map.get("userStrId").toString()+"'");
		List<MUser> list = em.createQuery(hql.toString(),MUser.class).getResultList();
		if(list.size() > 0) {
			MUser user = em.find(MUser.class, list.get(0).getId(),lmt);
			if(null != map.get("userLeveCode")) {
				user.setUserLevelCode(map.get("userLeveCode").toString());
			}
			if(null != map.get("vad")) {
				user.setVad(Double.valueOf(map.get("vad").toString()));
			}
			user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(user);
		}
	}

	@Override
	public void insertUserRecommend(MUserRecommend userRecommend) {
		em.persist(userRecommend);
	}

	@Override
	public int selectUserRecommendCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUserRecommend r ");
        hql.append(" where r.status = 1 ");
        if (map.get("recommendUserStrId") != null) {
        	hql.append(" and r.recommendUserStrId = '" + map.get("recommendUserStrId")+ "'");
        }
        if (map.get("recommendedUserStrId") != null) {
        	hql.append(" and r.recommendedUserStrId = '" + map.get("recommendedUserStrId")+ "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public int selectUserHotelCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUserHotel h ");
        hql.append(" where h.status = 1 ");
        if (map.get("userStrId") != null) {
        	hql.append(" and h.userStrId = '" + map.get("userStrId")+ "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<MUserTeam> selectUserTeamList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeam t where t.status = 1  ");
		if(null != map.get("teamLeaderStrId")) {
			hql.append(" and t.teamLeaderStrId = '"+map.get("teamLeaderStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUserTeam.class).getResultList();
	}

	@Override
	public void insertUserTeam(MUserTeam userTeam) {
		em.persist(userTeam);
	}

	@Override
	public List<MUserRecommend> selectUserRecommendList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserRecommend r where r.status = 1  ");
		if(null != map.get("recommendUserStrId")) {
			hql.append(" and r.recommendUserStrId = '"+map.get("recommendUserStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUserRecommend.class).getResultList();
	}

	@Override
	public void insertUserTeamMember(MUserTeamMember userTeamMember) {
		em.persist(userTeamMember);
	}

	@Override
	public void updateUserTeam(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeam t ");
		hql.append(" where t.status = 1 ");
		hql.append(" and t.strId = '"+map.get("teamStrId").toString()+"'");
		List<MUserTeam> list = em.createQuery(hql.toString(),MUserTeam.class).getResultList();
		if(list.size() > 0) {
			MUserTeam userTeam = em.find(MUserTeam.class, list.get(0).getId(),lmt);
			if(null != map.get("teamCount")) {
				userTeam.setTeamCount(Integer.parseInt(map.get("teamCount").toString()));
			}
			userTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(userTeam);
		}
	}

	/**
	 * 给下级注册酒店增加团队收益
	 * @param map
	 */
	@Override
	public void updateUserTeamHotel(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeam t ");
		hql.append(" where t.status = 1 ");
		if (null != map.get("userStrId") ){
			hql.append(" and t.teamLeaderStrId = '"+map.get("userStrId").toString()+"'");
		}
		List<MUserTeam> list = em.createQuery(hql.toString(),MUserTeam.class).getResultList();
		if(list.size() > 0) {
			MUserTeam userTeam = em.find(MUserTeam.class, list.get(0).getId(),lmt);
			if(null != map.get("vad")) {
				userTeam.setTeamRechargeAmount(Double.parseDouble(map.get("vad").toString()));
			}
			userTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(userTeam);
		}
	}

	/**
	 * 获取酒店数组
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MHotel> selectHotelList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MHotel h ");
		hql.append(" where h.status = 1 ");
		if (null != map.get("hotelId")) {
			hql.append(" and h.id = '" + map.get("hotelId").toString() + "' ");
		}
		if (null != map.get("hotelStrId")) {
			hql.append(" and h.strId = '" + map.get("hotelStrId").toString() + "' ");
		}
		if (null != map.get("teamCategory")) {
			hql.append(" and h.hotelLv = '" + map.get("teamCategory").toString() + "' ");
		}
		query = em.createQuery(hql.toString());
		return query.getResultList();
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
	public void insertUserHotel(MUserHotel userHotel) {
		em.persist(userHotel);
	}

	@Override
	public void insertUserConsumptionHistory(MUserConsumptionHistory history) {
		em.persist(history);
	}

	/**
	 * 新增资金明细
	 * @param detailOrder
	 */
	@Override
	public void insertUserFundDetailOrder(MUserFundDetailOrder detailOrder) {
		em.persist(detailOrder);
	}
}
