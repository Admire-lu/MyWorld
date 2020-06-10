package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserId;
import com.cly.mineworld.service.vo.userRealnameInformation.UserRealNameInformationVo;
import java.util.List;
import java.util.Map;

public interface UserUcRnAuthenticationDao {


    /**
     * 实名认证
     * @param userId
     */
    void insertUserId(MUserId userId);

    /**
     * 查询实名信息详情
     * @param map
     * @return
     */
    List<UserRealNameInformationVo> findRealNameInformation(Map<String, Object> map);

    /**
     * 查询实名认证是否完成
     * @param map
     * @return
     */
    List<MUserId> selectUserId(Map<String, Object> map);

    /**
     * 查询用户实名认证订单数组
     * @param map
     * @return
     */
    List<MUserId> selectUserIdList(Map<String,Object> map);

    /**
     * 查询用户信息数组
     * @param map
     * @return
     */
    List<MUser> selectUserList(Map<String,Object> map);

    /**
     * 修改用户信息
     * @param map
     */
    void updateUser(Map<String,Object> map);
}
