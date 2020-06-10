package com.cly.mineworld.service.dao;

import com.cly.mineworld.service.entity.MNodeInviteCode;
import com.cly.mineworld.service.entity.MUser;
import com.cly.mineworld.service.entity.MUserNodeOrder;

import java.util.List;
import java.util.Map;

public interface UserUcNodeDao {

    /**
     * 是否激活码是否可用
     * @param mapQuery
     * @return
     */
    List<MNodeInviteCode> selectInviteNodeOrder(Map<String, Object> mapQuery);

    /**
     * 查询用户信息
     * @param map
     * @return
     */
    List<MUser> selectUserList(Map<String, Object> map);

    /**
     * 生成用户节点订单信息
     * @param nodeOrder
     */
    void insertNodeOrder(MUserNodeOrder nodeOrder);

    /**
     * 查询用户节点中邀请码审核状态
     * @param map
     * @return
     */
    List<MUserNodeOrder> selectNodeOrder(Map<String, Object> map);

    /**
     * 修改后台节点邀请码状态为已使用
     * @param map
     */
    void updateInviteCode(Map<String, Object> map);

    /**
     * 修改用户节点订单
     * @param map
     */
    void updateNodeOrder(Map<String, Object> map);
}
