package com.cly.mineworld.service.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.cly.mineworld.service.entity.MAppVersion;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.dao.UserLoginDao;
import com.cly.mineworld.service.entity.MUser;

@Repository
@Transactional
public class UserLoginDaoImpl implements UserLoginDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;
	
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
		return em.createQuery(hql.toString(),MUser.class).getResultList();
	}

	@Override
	public List<MAppVersion> selectAppVersionList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MAppVersion u ");
		hql.append(" where u.status = 1 ");
		if(null != map.get("appVersion")) {
			hql.append(" and u.androidVersion = '"+map.get("appVersion").toString()+"' ");
		}

		return em.createQuery(hql.toString(),MAppVersion.class).getResultList();
	}
}
