package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcSafetyCenterDao;
import com.cly.mineworld.service.entity.MUser;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class UserUcSafetyCenterDaoImpl implements UserUcSafetyCenterDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 设置交易密码、手机号码、邮箱、修改登陆密码、头像
     * @param map
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateUser(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.strId = '" + map.get("userStrId").toString() + "' ");
        }
        List<MUser> list = em.createQuery(hql.toString(), MUser.class).getResultList();
        if (list.size() > 0) {
            MUser user = em.find(MUser.class, list.get(0).getId(), lmt);
            if (null != map.get("transactionPsw")) {
                user.setTransactionPsw(map.get("transactionPsw").toString());
            }
            if (null != map.get("mobileNumber")) {
                user.setMobile(map.get("mobileNumber").toString());
            }
            if (null != map.get("email")) {
                user.setEmail(map.get("email").toString());
            }
            if (null != map.get("newPassword")) {
                user.setPsw(map.get("newPassword").toString());
            }
            if (null != map.get("newTransactionPsw")) {
                user.setTransactionPsw(map.get("newTransactionPsw").toString());
            }
            if (null != map.get("nickName")) {
                user.setNickname(map.get("nickName").toString());
            }
            if (null != map.get("headImage")) {
                user.setHeadImage(map.get("headImage").toString());
            }
            user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(user);
        }
    }

    /**
     * 获取用户信息
     * @param map
     * @return
     */
    @Override
    public List<MUser> selectUserList(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.strId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("tokenAddress")) {
            hql.append(" and u.tokenAddress = '" + map.get("tokenAddress").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MUser.class).getResultList();
    }

    @Override
    public int selectUserCount(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("email")) {
            hql.append(" and u.email = '" + map.get("email") + "'");
        }
        if (null != map.get("mobile")) {
            hql.append(" and u.mobile = '" + map.get("mobile") + "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
