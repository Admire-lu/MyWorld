package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserFoundPswDao;
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
public class UserFoundPswDaoImpl implements UserFoundPswDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 更新user
     * @param map
     */
    @Override
    public void updateUser(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUser u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("email")) {
            hql.append(" and u.email = '" + map.get("email").toString() + "'");
        }
        if (null != map.get("mobile")) {
            hql.append(" and u.mobile = '" + map.get("mobile").toString() + "'");
        }
        List<MUser> list = em.createQuery(hql.toString(), MUser.class).getResultList();
        if (list.size() > 0) {
            MUser user = em.find(MUser.class, list.get(0).getId(), lmt);
            if (null != map.get("psw")) {
                user.setPsw(map.get("psw").toString());
            }
            user.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(user);
        }
    }

    /**
     * 查询用户
     * @param map
     * @return
     */
    @Override
    public int selectUserCount(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append("select count(*) from MUser u ");
        hql.append(" where u.status = 1 ");
        if (map.get("nickname") != null) {
            hql.append(" and u.nickname = '" + map.get("nickname") + "'");
        }
        if (map.get("email") != null) {
            hql.append(" and u.email = '" + map.get("email") + "'");
        }
        if (map.get("mobile") != null) {
            hql.append(" and u.mobile = '" + map.get("mobile") + "'");
        }
        query = em.createQuery(hql.toString());
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
