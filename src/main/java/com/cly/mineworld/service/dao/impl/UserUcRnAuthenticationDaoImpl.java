package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcRnAuthenticationDao;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserId;
import com.cly.mineworld.service.vo.userRealnameInformation.UserRealNameInformationVo;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserUcRnAuthenticationDaoImpl implements UserUcRnAuthenticationDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    @Override
    public void insertUserId(MUserId userId) {
        em.persist(userId);
    }


    /**
     * 查询实名信息详情
     * @param map
     * @return
     */
    @Override
    public List<UserRealNameInformationVo> findRealNameInformation(Map<String, Object> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select *  from ");
        sql.append(" ( select ");
        sql.append(" user_str_id as userSrtId, ");
        sql.append(" id_no as idNo, ");
        sql.append(" id_realname as idRealName, ");
        sql.append(" id_image_one as idImageOne, ");
        sql.append(" id_image_two as idImageTwo, ");
        sql.append(" authentication_status as authenticationStatus, ");
        sql.append(" status ");
        sql.append(" from ");
        sql.append(" m_user_id ");
        sql.append(" where status = 1 ");
        //sql.append(" and authentication_status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and user_str_id = '" + map.get("userStrId").toString() + "') a");
        }
        sql.append(" join ");
        sql.append(" ( select ");
        sql.append(" nickname as nickName, ");
        sql.append(" mobile ");
        sql.append(" from ");
        sql.append(" m_user ");
        sql.append(" where status = 1 ");
        if (map.get("userStrId") != null) {
            sql.append(" and str_id = '" + map.get("userStrId").toString() + "') b");
        }
        List<UserRealNameInformationVo> listVo = new ArrayList<UserRealNameInformationVo>();
        List<Object> listResult = em.createNativeQuery(sql.toString()).getResultList();
        for (Object row : listResult) {
            Object[] rowArray = (Object[]) row;
            UserRealNameInformationVo vo = new UserRealNameInformationVo();
            vo.setUserStrId(rowArray[0].toString());
            vo.setIdNo(rowArray[1].toString());
            vo.setIdRealName(rowArray[2].toString());
            //vo.setIdImageOne(rowArray[3].toString());
            //vo.setIdImageTwo(rowArray[4].toString());
            //vo.setAuthenticationStatus("已认证");
            vo.setAuthenticationStatus(rowArray[5].toString());
            vo.setStatus(Integer.valueOf(rowArray[6].toString()));
            vo.setNickName(rowArray[7].toString());
            vo.setMobile(rowArray[8].toString());
            vo.setCountry("中国"); //待确定 todo
            listVo.add(vo);
        }
        return listVo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MUserId> selectUserId(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserId u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.userStrId = '" + map.get("userStrId") + "'");
        }
        return em.createQuery(hql.toString(), MUserId.class).getResultList();
    }

    /**
     * 查询用户实名认证订单数组
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MUserId> selectUserIdList(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserId m ");
        hql.append(" where m.status = 1 ");
        if(null != map.get("userStrId")) {
            hql.append(" and m.userStrId = '"+map.get("userStrId").toString()+"' ");
        }
        hql.append(" order by id desc ");
        Query query = em.createQuery(hql.toString());
        if(null != map.get("start")) {
            query.setFirstResult(Integer.parseInt(map.get("start").toString()));
            query.setMaxResults(Integer.parseInt(map.get("limit").toString()));
        }
        return query.getResultList();
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
    public void updateUser(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        hql.append(" and u.strId = '"+map.get("userStrId").toString()+"'");
        List<MUser> list = em.createQuery(hql.toString(),MUser.class).getResultList();
        if(list.size() > 0) {
            MUser user = em.find(MUser.class, list.get(0).getId(),lmt);
            if(null != map.get("tokenAddress")) {
                user.setTokenAddress(map.get("tokenAddress").toString());
            }
            user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(user);
        }
    }
}
