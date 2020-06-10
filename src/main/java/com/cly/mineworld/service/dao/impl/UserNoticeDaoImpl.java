package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserNoticeDao;
import com.cly.mineworld.service.vo.userNotice.UserNoticeVo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserNoticeDaoImpl implements UserNoticeDao {

	@PersistenceContext
	private EntityManager em;


	/**
	 * 获取所有公告
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserNoticeVo> selectNoticeList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		//select * from m_notice where release_status = 2 order by create_time  DESC
		sql.append(" select * from  m_notice ");
		sql.append(" where release_status = 1 and status = 1 ");
		sql.append(" order by create_time  DESC ");
		List<UserNoticeVo> listVo = new ArrayList<UserNoticeVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			UserNoticeVo vo = new UserNoticeVo();
			vo.setStrId(rowArray[1].toString());
			vo.setNoticeTitle(rowArray[2].toString());
			vo.setNoticeContent(rowArray[3].toString());
			vo.setReleaseStatus(Integer.valueOf(rowArray[4].toString()));
			vo.setCreatorStrId(rowArray[5].toString());
			vo.setCreateTime(Utils.timeStampToDate(Long.valueOf(rowArray[8].toString())));
			listVo.add(vo);
		}
		return listVo;
	}

	/**
	 * 获取公告详情
	 * @param map
	 * @return
	 */
	@Override
	public List<UserNoticeVo> selectNoticeDetails(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from  m_notice ");
		sql.append(" where release_status = 1 and status = 1 ");
		if (map.get("noticeStrId") != null) {
			sql.append(" and str_id = '" + map.get("noticeStrId").toString() + "'");
		}
		List<UserNoticeVo> listVo = new ArrayList<UserNoticeVo>();
		List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
		for (Object row : listResult) {
			Object[] rowArray = (Object[]) row;
			UserNoticeVo vo = new UserNoticeVo();
			vo.setStrId(rowArray[1].toString());
			vo.setNoticeTitle(rowArray[2].toString());
			vo.setNoticeContent(rowArray[3].toString());
			vo.setReleaseStatus(Integer.valueOf(rowArray[4].toString()));
			vo.setCreatorStrId(rowArray[5].toString());
			vo.setCreateTime(Utils.timeStampToDate(Long.valueOf(rowArray[8].toString())));
			listVo.add(vo);
		}
		return listVo;
	}
}
