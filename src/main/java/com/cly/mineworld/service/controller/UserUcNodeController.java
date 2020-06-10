package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcNodeService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-节点-控制器
 * @author william
 *
 */
@RestController
public class UserUcNodeController {

    @Autowired
    private UserUcNodeService userUcNodeService;

    /**
     * 查询节点信息
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/getNodeInformation.html")
    public ResultVo getNodeInformation(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.getNodeInformation(jsonData);
    }

    /**
     * 激活码是否有效
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/activationOrNot.html")
    public ResultVo activationOrNot(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.activationOrNot(jsonData);
    }

    /**
     * 添加节点
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/applicationNode.html")
    public ResultVo applicationNode(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.applicationNode(jsonData);
    }

    /**
     * 收支明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/nodeBudgetDetailed.html")
    public ResultVo nodeBudgetDetailed(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.nodeBudgetDetailed(jsonData);
    }

    /**
     * 注册明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/nodeRegisterDetailed.html")
    public ResultVo nodeRegisterDetailed(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.nodeRegisterDetailed(jsonData);
    }

    /**
     * 收益明细
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/nodeProfitDetailed.html")
    public ResultVo nodeProfitDetailed(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcNodeService.nodeProfitDetailed(jsonData);
    }

}
