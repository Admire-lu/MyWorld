package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心-节点-调度服务
 * @author william
 *
 */
public interface UserUcNodeService {

    /**
     * 激活码是否有效
     * @param jsonData
     * @return
     */
    ResultVo activationOrNot(String jsonData);

    /**
     * 添加节点
     * @param jsonData
     * @return
     */
    ResultVo applicationNode(String jsonData);

    /**
     * 查询节点信息
     * @param jsonData
     * @return
     */
    ResultVo getNodeInformation(String jsonData);

    /**
     * 收益明细
     * @param jsonData
     * @return
     */
    ResultVo nodeProfitDetailed(String jsonData);

    /**
     * 注册明细
     * @param jsonData
     * @return
     */
    ResultVo nodeRegisterDetailed(String jsonData);

    /**
     * 收支明细
     * @param jsonData
     * @return
     */
    ResultVo nodeBudgetDetailed(String jsonData);
}
