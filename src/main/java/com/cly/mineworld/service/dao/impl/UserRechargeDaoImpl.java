package com.cly.mineworld.service.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserRechargeDao;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserProfit;
import com.cly.mineworld.service.entity.MUserRechargeOrder;
import com.cly.mineworld.service.entity.MUserTeam;

@Repository
@Transactional
public class UserRechargeDaoImpl implements UserRechargeDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;
	
	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u  ");
		hql.append(" where u.status = 1 ");
		if(null != map.get("tokenAddress")) {
			hql.append(" and u.tokenAddress = '"+map.get("tokenAddress").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUser.class).getResultList();
	}

	@Override
	public int selectUserRechargeOrderCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) from MUserRechargeOrder o ");
		hql.append(" where o.status = 1 ");
		if(null != map.get("rechargeTokenOrderId")) {
			hql.append(" and o.rechargeTokenOrderId = '"+map.get("rechargeTokenOrderId").toString()+"' ");
		}
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public void insertUserRechargeOrder(MUserRechargeOrder order) {
		em.persist(order);
	}

	@Override
	public void updateUser(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		hql.append(" and u.strId = '"+map.get("userStrId").toString()+"'");
		List<MUser> list = em.createQuery(hql.toString(), MUser.class).getResultList();
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
	public void insertUserProfit(MUserProfit userProfit) {
		em.persist(userProfit);
	}

	@Override
	public List<MUserTeam> selectUserTeamByUserStrId(String userStrId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM MUserTeam t ");
		hql.append(" where t.status = 1 and t.teamLeaderStrId != '"+userStrId+"' ");
		hql.append(" AND t.strId in ");
		hql.append(" (SELECT m.teamStrId AS teamStrId FROM MUserTeamMember m WHERE m.memberStrId = '"+userStrId+"') ");
		return em.createQuery(hql.toString(), MUserTeam.class).getResultList();
	}

	@Override
	public void updateUserTeam(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserTeam t ");
		hql.append(" where t.status = 1 ");
		hql.append(" and t.strId = '"+map.get("userTeamStrId").toString()+"'");
		List<MUserTeam> list = em.createQuery(hql.toString(),MUserTeam.class).getResultList();
		if(list.size() > 0) {
			MUserTeam userTeam = em.find(MUserTeam.class, list.get(0).getId(),lmt);
			if(null != map.get("teamRechargeAmount")) {
				userTeam.setTeamRechargeAmount(Double.parseDouble(map.get("teamRechargeAmount").toString()));
			}
			if(null != map.get("teamLv")) {
				userTeam.setTeamLv(Integer.parseInt(map.get("teamLv").toString()));
			}
			userTeam.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(userTeam);
		}
	}

	@Override
	public int checkUserTeamLv(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) FROM m_user_team t WHERE t.`status` = 1 AND t.`team_lv` = "+map.get("teamLv").toString()+" AND t.`str_id` IN  ");
		sql.append(" ( ");
		sql.append(" SELECT tm.`team_str_id` AS teamStrId FROM m_user_team_member tm WHERE tm.`status` = 1 AND tm.`team_str_id` IN  ");
		sql.append(" (SELECT m.`team_str_id` AS teamStrId FROM m_user_team_member m WHERE m.`member_str_id` = '"+map.get("memberStrId").toString()+"' ) ");
		sql.append(" ) ");
		Query query = em.createNativeQuery(sql.toString());
		Object obj = query.getSingleResult();
		return Integer.parseInt(obj.toString());
	}
}
