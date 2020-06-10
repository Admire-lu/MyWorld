package com.cly.mineworld.service.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.cly.mineworld.service.dao.UserCommonDao;
import com.cly.mineworld.service.entity.MAppVersion;

@Service
@Transactional
public class UserCommonDaoImpl implements UserCommonDao {

	@PersistenceContext
	private EntityManager em;
	private Query query = null;
	
	@Override
	public List<MAppVersion> selectAppVersionList() {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MAppVersion v ");
		hql.append(" where v.status = 1 ");
		return em.createQuery(hql.toString(),MAppVersion.class).getResultList();
	}

}
