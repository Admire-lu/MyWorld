package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心-邀请好友-调度服务
 * @author william
 *
 */
public interface UserUcInviteFriendsService {

    /**
     * 邀请好友
     * @param jsonData
     * @return
     * @throws Exception
     */
    ResultVo inviteGoodFriends(String jsonData);
}
