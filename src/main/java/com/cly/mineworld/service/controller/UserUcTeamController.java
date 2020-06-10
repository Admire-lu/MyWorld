package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcTeamService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-我的团队-控制器
 * @author william
 *
 */
@RestController
public class UserUcTeamController {

    @Autowired
    private UserUcTeamService userUcTeamService;


    /**
     * 我的等级
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/myRank.html")
    public ResultVo myRank(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcTeamService.myRank(jsonData);
    }

    /**
     * 我的管理团队
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/findUserManagerTeam.html")
    public ResultVo findUserManagerTeam(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcTeamService.findUserManagerTeam(jsonData);
    }

    /**
     * 我的团队
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/findUserTeams.html")
    public ResultVo findUserTeams(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcTeamService.findUserTeams(jsonData);
    }

    /**
     * 查询好友信息
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/findUserFriend.html")
    public ResultVo findUserFriend(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcTeamService.findUserFriend(jsonData);
    }


    /**
     * 根据用户StrId查询
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/findUserById.html")
    public ResultVo findUserById(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcTeamService.findUserById(jsonData);
    }
}
