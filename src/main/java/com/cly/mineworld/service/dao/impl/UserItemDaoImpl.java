package com.cly.mineworld.service.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.entity.*;
import org.springframework.stereotype.Repository;
import com.cly.mineworld.service.dao.UserItemDao;

@Repository
@Transactional
public class UserItemDaoImpl implements UserItemDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<MUser> selectUserList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUser u ");
		hql.append(" where u.status = 1 ");
		if(null != map.get("userStrId")) {
			hql.append(" and u.strId = '"+map.get("userStrId").toString()+"' ");
		}
		Query query = em.createQuery(hql.toString());
		if(null != map.get("start")) {
			query.setFirstResult(Integer.parseInt(map.get("start").toString()));
			query.setMaxResults(Integer.parseInt(map.get("limit").toString()));
		}
		return query.getResultList();
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

	/**
	 * 查询用户道具数组
	 * @param map
	 * @return
	 */
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
		if(null != map.get("itemLv")) {
			hql.append(" and i.itemLv = "+map.get("itemLv").toString()+" ");
		}
		if(null != map.get("greaterItemUseCount")) {//大于
			hql.append(" and i.itemUseCount > "+map.get("greaterItemUseCount").toString()+" ");
		}
		if(null != map.get("itemCategoryStrId")) {
			hql.append(" and i.itemCategoryStrId = '"+map.get("itemCategoryStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUserItem.class).getResultList();
	}

	@Override
	public void updateUserItem(MUserItem userItem) {
		em.merge(userItem);
	}

	/*@Override
	public void updateUserItem(Map<String,Object> map) {
		LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserItem u ");
		hql.append(" where u.status = 1 ");
		hql.append(" and u.strId = '"+map.get("userStrId").toString()+"'");
		hql.append(" and u.itemStrId = '"+map.get("itemStrId").toString()+"'");
		List<MUserItem> list = em.createQuery(hql.toString(),MUserItem.class).getResultList();
		if(list.size() > 0) {
			MUserItem userItem = em.find(MUserItem.class, list.get(0).getId(),lmt);
			if(null != map.get("itemUseCount")) {
				userItem.setItemUseCount(Integer.valueOf(map.get("itemUseCount").toString()));
			}
			userItem.setModifyTime(new Long(Utils.getTimestamp()).intValue());
			em.merge(userItem);
		}
	}*/

	@Override
	public int selectUserCount(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) from MUser u ");
		hql.append(" where u.status = 1 ");
		return Integer.parseInt(em.createNativeQuery(hql.toString()).getSingleResult().toString());
	}

//	@Override
//	public List<Integer> selectUserHotelIds(Map<String, Object> map) {
//		StringBuffer hql = new StringBuffer();
//		hql.append(" select h.id from MUserHotel h ,MHotel t ");
//		hql.append(" where h.status = 1 and h.hotelStrId = t.strId ");
//		hql.append(" and h.doBusinessStatus = 1 ");
//		if(null != map.get("noUserStrId")) {
//			hql.append(" and h.userStrId != '"+map.get("noUserStrId")+"' ");
//		}
//		if(null != map.get("hotelLv")) {
//			hql.append(" and t.hotelLv = "+map.get("hotelLv")+" ");
//		}
//		return em.createQuery(hql.toString(), Integer.class).getResultList();
//	}

	@Override
	public MUserHotel selectUserHotelById(int userHotelId) {
		return em.find(MUserHotel.class, userHotelId);
	}

	@Override
	public List<MUserHotelDayProfitOrder> selectUserHotelDayProfitOrderList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserHotelDayProfitOrder o ");
		hql.append(" where o.status = 1 ");
		if(null != map.get("userHotelId")) {
			hql.append(" and o.userHotelId = "+map.get("userHotelId").toString()+" ");
		}
		if(null != map.get("profitDate")) {
			hql.append(" and o.profitDate = '"+map.get("profitDate").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MUserHotelDayProfitOrder.class).getResultList();
	}

	@Override
	public void updateUserHotelDayProfitOrder(MUserHotelDayProfitOrder order) {
		em.merge(order);
	}

	@Override
	public void updateUser(MUser user) {
		em.merge(user);
	}

	@Override
	public void insertUserProfit(MUserProfit profit) {
		em.persist(profit);
	}

	@Override
	public void insertUserFundDetailOrder(MUserFundDetailOrder order) {
		em.persist(order);
	}

	@Override
	public List<String> selectItemStrIds(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("select i.strId from MBaseItem i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("itemCategoryStrId")) {
			hql.append(" and i.itemCategoryStrId = '"+map.get("itemCategoryStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString()).getResultList();
	}

	/**
	 * 获取道具价格
	 * @param map
	 * @return
	 */
	@Override
	public Double selectItemPrice(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("select i.itemPrice from MBaseItem i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("itemStrId")) {
			hql.append(" and i.strId = '"+map.get("itemStrId").toString()+"' ");
		}
		return Double.valueOf(em.createQuery(hql.toString()).getSingleResult().toString());
	}

	/**
	 * 获取道具名称
	 * @param map
	 * @return
	 */
	@Override
	public String selectItemName(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append("select i.itemName from MBaseItem i ");
		hql.append(" where i.status = 1 ");
		if(null != map.get("itemStrId")) {
			hql.append(" and i.strId = '"+map.get("itemStrId").toString()+"' ");
		}
		return em.createQuery(hql.toString()).getSingleResult().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MUserHotel> selectUserHotelForRand(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MUserHotel h ");
		hql.append(" where h.status = 1 and h.profit > 0");
		if(null != map.get("hotelStrId")) {
			hql.append(" and h.hotelStrId = '"+map.get("hotelStrId").toString()+"' ");
		}
		if(null != map.get("scenicSpotStrId")) {
			hql.append(" and h.scenicSpotStrId = '"+map.get("scenicSpotStrId").toString()+"' ");
		}
		if(null != map.get("noUserStrId")) {
			hql.append(" and h.userStrId != '"+map.get("noUserStrId").toString()+"' ");
		}
		if(null != map.get("rand")) {
			hql.append(" ORDER BY RAND() ");
		}
		Query query = em.createQuery(hql.toString());
		if(null != map.get("start")) {
			query.setFirstResult(Integer.parseInt(map.get("start").toString()));
			query.setMaxResults(Integer.parseInt(map.get("limit").toString()));
		}
		return query.getResultList();
	}

	@Override
	public List<MLuckyMoneyPoolDay> selectLuckyMoneyPoolDayList(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from MLuckyMoneyPoolDay p ");
		hql.append(" where p.status = 1 ");
		if(null != map.get("poolDate")) {
			hql.append(" and p.poolDate = '"+map.get("poolDate").toString()+"' ");
		}
		return em.createQuery(hql.toString(), MLuckyMoneyPoolDay.class).getResultList();
	}

	@Override
	public void updateLuckyMoneyPoolDay(MLuckyMoneyPoolDay pool) {
		em.merge(pool);
	}

	@Override
	public void insertUserItemUseHistory(MUserItemUseHistory itemUseHistory) {
		em.persist(itemUseHistory);
	}
}
