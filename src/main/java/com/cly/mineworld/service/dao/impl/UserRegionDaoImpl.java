package com.cly.mineworld.service.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.dao.UserRegionDao;
import com.cly.mineworld.service.entity.MRegion;
import com.cly.mineworld.service.entity.MScenicSpot;

@Repository
@Transactional
public class UserRegionDaoImpl implements UserRegionDao {
	
	@PersistenceContext
	private EntityManager em;
	private Query query = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<MRegion> selectRegionList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MRegion r ");
		hql.append(" where r.status = 1 ");
		query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MScenicSpot> selectScenicSpotList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MScenicSpot s ");
		hql.append(" where s.status = 1 ");
		if(null != map.get("regionStrId")) {
			hql.append(" and s.regionStrId = '"+map.get("regionStrId").toString()+"' ");
		}
		query = em.createQuery(hql.toString());
		return query.getResultList();
	}
}
