package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.MUserFeedback;


public interface UserUcSettingDao {


    /**
     * 添加意见反馈
     * @param userFeedback
     */
    void insertFeedback(MUserFeedback userFeedback);
}
