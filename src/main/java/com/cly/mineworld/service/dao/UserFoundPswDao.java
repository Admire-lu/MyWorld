package com.cly.mineworld.service.dao;

import java.util.Map;

public interface UserFoundPswDao {

    /**
     * 查询用户总数
     * @param map
     * @return
     */
    int selectUserCount(Map<String,Object> map);

    /**
     * 更新用户密码
     * @param
     * @return
     */
    void updateUser(Map<String, Object> map);
}
