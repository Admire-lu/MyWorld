package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心-我的团队-调度服务
 * @author william
 *
 */
public interface UserUcTeamService {

    /**
     * 根据用户strId查询用户信息
     * @param jsonData
     * @return
     */
    ResultVo findUserById(String jsonData);

    /**
     * 我的管理团队
     * @param jsonData
     * @return
     * @throws Exception
     */
    ResultVo findUserManagerTeam(String jsonData);

    /**
     * 我的等级
     * @param jsonData
     * @return
     */
    ResultVo myRank(String jsonData);

    /**
     * 我的团队
     * @param jsonData
     * @return
     */
    ResultVo findUserTeams(String jsonData);

    /**
     * 查询好友信息
     * @param jsonData
     * @return
     */
    ResultVo findUserFriend(String jsonData);
}
