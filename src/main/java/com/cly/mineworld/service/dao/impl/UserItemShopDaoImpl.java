package com.cly.mineworld.service.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.entity.*;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.dao.UserItemShopDao;

@Repository
@Transactional
public class UserItemShopDaoImpl implements UserItemShopDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<MBaseItemCategory> selectItemCategoryList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MBaseItemCategory i ");
		hql.append(" where i.status = 1 ");
		return em.createQuery(hql.toString(), MBaseItemCategory.class).getResultList();
	}

	@Override
	public List<MBaseItem> selectItemList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MBaseItem i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("itemCategoryStrId")) {
			hql.append(" and i.itemCategoryStrId = '"+map.get("itemCategoryStrId").toString()+"' ");
		}
		if(null != map.get("itemStrId")) {
			hql.append(" and i.strId = '"+map.get("itemStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MBaseItem.class).getResultList();
	}

	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		if(null != map.get("userStrId")) {
			hql.append(" and u.strId = '"+map.get("userStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUser.class).getResultList();
	}

	@Override
	public void updateUser(MUser user) {
		em.merge(user);
	}

	@Override
	public List<MUserItem> selectUserItemList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserItem i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("userStrId")) {
			hql.append(" and i.userStrId = '"+map.get("userStrId").toString()+"' ");
		}
		if(null != map.get("itemStrId")) {
			hql.append(" and i.itemStrId = '"+map.get("itemStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUserItem.class).getResultList();
	}

	@Override
	public void insertUserItem(MUserItem userItem) {
		em.persist(userItem);
	}

	@Override
	public void updateUserItem(MUserItem userItem) {
		em.merge(userItem);
	}

	@Override
	public void insertUserConsumptionHistory(MUserConsumptionHistory history) {
		em.persist(history);
	}

	@Override
	public void insertUserFundDetailOrder(MUserFundDetailOrder order) {
		em.persist(order);
	}

	/**
	 * 查询道具剩余购买次数
	 * @param map
	 * @return
	 */
	@Override
	public List<MItemTotalCount> selectItemTotalCountList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MItemTotalCount i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("itemStrId")) {
			hql.append(" and i.itemStrId = '"+map.get("itemStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MItemTotalCount.class).getResultList();
	}

	/**
	 * 更新道具库存
	 * @param map
	 */
	@Override
	public void updateItemTotalCount(Map<String, Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MItemTotalCount u ");
		hql.append(" where u.status = 1 ");
		if (null != map.get("itemStrId")) {
			hql.append(" and u.itemStrId = '" + map.get("itemStrId").toString() + "'");
		}
		List<MItemTotalCount> list = em.createQuery(hql.toString(), MItemTotalCount.class).getResultList();
		if (list.size() > 0) {
			MItemTotalCount itemTotalCount = em.find(MItemTotalCount.class, list.get(0).getId(), lmt);
			if (null != map.get("itemCount")) {
				itemTotalCount.setItemCount(Integer.valueOf(map.get("itemCount").toString()));
			}
			itemTotalCount.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(itemTotalCount);
		}
	}

	//用户拥有酒店的数量
	@Override
	public Integer selectUserHotelCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		//select user_str_id, count(*) from m_user_hotel where status = 1 and user_str_id=''
		sql.append("select user_str_id, count(*) ");
		sql.append(" from m_user_hotel ");
		sql.append(" where status = 1  ");
		if(null != map.get("userStrId")) {
			sql.append(" and user_str_id = '"+map.get("userStrId").toString()+"' ");
		}
		Integer count = 0;
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			count = Integer.valueOf(rowArray[1].toString());
		}
		return count;
	}

	/**
	 * 查询用户当日购买的次数
	 * @param map
	 * @return
	 */
	@Override
	public Integer selectUserByItemCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select user_str_id, count(*) ");
		sql.append(" from m_user_item_buy_history ");
		sql.append(" where status = 1  ");
		if(null != map.get("userStrId")) {
			sql.append(" and user_str_id = '"+map.get("userStrId").toString()+"' ");
		}
		if(null != map.get("byDate")) {
			sql.append(" and buy_date = '"+map.get("byDate").toString()+"' ");
		}
		Integer count = 0;
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			count = Integer.valueOf(rowArray[1].toString());
		}
		return count;
	}

	@Override
	public void insertUserItemBuyHistory(MUserItemBuyHistory buyHistory) {
		em.persist(buyHistory);
	}
}
