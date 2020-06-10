package com.cly.mineworld.service.dao.impl;

import com.cly.mineworld.service.dao.UserUcSettingDao;
import com.cly.mineworld.service.entity.MUserFeedback;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


@Repository
@Transactional
public class UserUcSettingDaoImpl implements UserUcSettingDao {

    @PersistenceContext
    private EntityManager em;
    private Query query = null;


    /**
     * 新增意见反馈
     * @param userFeedback
     */
    @Override
    public void insertFeedback(MUserFeedback userFeedback) {
        em.persist(userFeedback);
    }
}
