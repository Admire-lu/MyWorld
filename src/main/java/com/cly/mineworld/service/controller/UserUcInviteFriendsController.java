package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcInviteFriendsService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-邀请好友-控制器
 * @author william
 *
 */
@RestController
public class UserUcInviteFriendsController {

    @Autowired
    private UserUcInviteFriendsService ucInviteFriendsService;


    /**
     * 邀请好友
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/inviteGoodFriends.html")
    public ResultVo inviteGoodFriends(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return ucInviteFriendsService.inviteGoodFriends(jsonData);
    }
}
