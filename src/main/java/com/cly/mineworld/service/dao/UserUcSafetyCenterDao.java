package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.MUser;
import java.util.List;
import java.util.Map;

public interface UserUcSafetyCenterDao {


    /**
     * 设置交易密码、手机号码、邮箱、昵称、头像、
     * @param map
     */
    void updateUser(Map<String, Object> map);

    /**
     * 判断是否有该手机号或邮箱
     * @param map
     * @return
     */
    int selectUserCount(Map<String, Object> map);

    /**
     * 获取用户信息
     * @param map
     * @return
     */
    List<MUser> selectUserList(Map<String, Object> map);
}
