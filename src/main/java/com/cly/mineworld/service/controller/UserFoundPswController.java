package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserFoundPswService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 找回密码控制器
 * @author william
 *
 */
@RestController
public class UserFoundPswController {

    @Autowired
    private UserFoundPswService userFoundPswService;


    /**
     * 根据手机号/邮箱 修改密码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/found/foundPsw.html")
    public ResultVo foundPsw (
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userFoundPswService.foundPsw(jsonData);
    }

    //校验手机或者邮箱是否存在
    @ResponseBody
    @RequestMapping(value = "/user/found/checkMobileOrEmailIsExist.html")
    public ResultVo checkMobileOrEmailIsExist(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userFoundPswService.checkMobileOrEmailIsExist(jsonData);
    }

    /**
     * 发送验证码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/found/sendVerificationCode.html")
    public ResultVo sendVerificationCode(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userFoundPswService.sendVerificationCode(jsonData);
    }
}
