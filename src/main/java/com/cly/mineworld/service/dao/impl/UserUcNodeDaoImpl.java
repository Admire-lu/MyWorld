package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.common.Utils;
import com.cly.mineworld.service.dao.UserUcNodeDao;
import com.cly.mineworld.service.dao.UserUcSettingDao;
import com.cly.mineworld.service.entity.MNodeInviteCode;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserFeedback;
import com.cly.mineworld.service.entity.MUserNodeOrder;
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
public class UserUcNodeDaoImpl implements UserUcNodeDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 激活码是否可用
     */
    @Override
    public List<MNodeInviteCode> selectInviteNodeOrder(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MNodeInviteCode u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.useUserStrId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("activationCode")) {
            hql.append(" and u.inviteCode = '" + map.get("activationCode").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MNodeInviteCode.class).getResultList();
    }

    /**
     * 查询用户节点信息审核状态
     * @param map
     * @return
     */
    @Override
    public List<MUserNodeOrder> selectNodeOrder(Map<String, Object> map) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserNodeOrder u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.userStrId = '" + map.get("userStrId").toString() + "' ");
        }
        if (null != map.get("activationCode")) {
            hql.append(" and u.inviteCode = '" + map.get("activationCode").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MUserNodeOrder.class).getResultList();
    }

    /**
     * 更新后台节点订单
     * @param map
     */
    @Override
    public void updateInviteCode(Map<String, Object> map) {
            LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
            StringBuffer hql = new StringBuffer();
            hql.append(" from MNodeInviteCode u ");
            hql.append(" where u.status = 1 ");
            if (null != map.get("activationCode")) {
                hql.append(" and u.inviteCode = '" + map.get("activationCode").toString() + "'");
            }
            List<MNodeInviteCode> list = em.createQuery(hql.toString(), MNodeInviteCode.class).getResultList();
            if (list.size() > 0) {
                MNodeInviteCode nodeInviteCode = em.find(MNodeInviteCode.class, list.get(0).getId(), lmt);
                if (null != map.get("codeUseStatus")) {
                    nodeInviteCode.setCodeUseStatus(Integer.valueOf(map.get("codeUseStatus").toString()));
                }
                if (null != map.get("userStrId")) {
                    nodeInviteCode.setUseUserStrId(map.get("userStrId").toString());
                }
                nodeInviteCode.setModifyTime(new Long(Utils.getTimestamp()).intValue());
                em.merge(nodeInviteCode);
            }
    }

    @Override
    public void updateNodeOrder(Map<String, Object> map) {
        LockModeType lmt = LockModeType.PESSIMISTIC_WRITE;
        StringBuffer hql = new StringBuffer();
        hql.append(" from MUserNodeOrder u ");
        hql.append(" where u.status = 1 ");
        if (null != map.get("userStrId")) {
            hql.append(" and u.userStrId = '" + map.get("userStrId").toString() + "'");
        }
        List<MUserNodeOrder> list = em.createQuery(hql.toString(), MUserNodeOrder.class).getResultList();
        if (list.size() > 0) {
            MUserNodeOrder nodeOrder = em.find(MUserNodeOrder.class, list.get(0).getId(), lmt);
            if (null != map.get("activationCode")) {
                nodeOrder.setInviteCode(map.get("activationCode").toString());
            }
            if (null != map.get("orderVerifyStatus")) {
                nodeOrder.setOrderVerifyStatus(Integer.valueOf(map.get("orderVerifyStatus").toString()));
            }
            if (null != map.get("orderReadStatus")) {
                nodeOrder.setOrderReadStatus(Integer.valueOf(map.get("orderReadStatus").toString()));
            }
            if (null != map.get("nodeLv")) {
                nodeOrder.setNodeLv(Integer.valueOf(map.get("nodeLv").toString()));
            }
            nodeOrder.setModifyTime(new Long(Utils.getTimestamp()).intValue());
            em.merge(nodeOrder);
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
        if (null != map.get("mobile")) {
            hql.append(" and u.mobile = '" + map.get("mobile").toString() + "' ");
        }
        return em.createQuery(hql.toString(), MUser.class).getResultList();
    }

    /**
     * 增加节点订单信息
     * @param nodeOrder
     */
    @Override
    public void insertNodeOrder(MUserNodeOrder nodeOrder) {
        em.persist(nodeOrder);
    }
}
