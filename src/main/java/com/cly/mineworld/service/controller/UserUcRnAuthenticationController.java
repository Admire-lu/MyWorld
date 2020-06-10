package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcRnAuthenticationService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-实名认证-控制器
 * @author william
 *
 */
@RestController
public class UserUcRnAuthenticationController {

    @Autowired
    private UserUcRnAuthenticationService ucRnAuthenticationService;

    /**
     * 校验是否已经实名认证
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/checkRealNameIsExist.html")
    public ResultVo checkRealNameIsExist(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return ucRnAuthenticationService.checkRealNameIsExist(jsonData);
    }

    /**
     * 实名认证
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/realNameAuthentication.html")
    public ResultVo realNameAuthentication(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return ucRnAuthenticationService.realNameAuthentication(jsonData);
    }

    /**
     * 实名信息详情
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/findRealNameInformation.html")
    public ResultVo findRealNameInformation(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return ucRnAuthenticationService.findRealNameInformation(jsonData);
    }
}
