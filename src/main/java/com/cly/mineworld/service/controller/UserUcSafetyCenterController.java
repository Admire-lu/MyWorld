package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcSafetyCenterService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-安全中心-控制器
 * @author william
 *
 */
@RestController
public class UserUcSafetyCenterController {

    @Autowired
    private UserUcSafetyCenterService userUcSafetyCenterService;


    /**
     * 根据用户StrId修改图片
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updateHeadImage.html")
    public ResultVo updateHeadImage(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updateHeadImage(jsonData);
    }

    /**
     * 根据用户StrId昵称
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updateNickName.html")
    public ResultVo updateNickName(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updateNickName(jsonData);
    }

    /**
     * 根据用户StrId修改邮箱号
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updateEmail.html")
    public ResultVo updateEmail(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updateEmail(jsonData);
    }

    /**
     * 根据用户StrId修改手机号
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updateMobile.html")
    public ResultVo updateMobile(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updateMobile(jsonData);
    }

    /**
     * 根据用户StrId修改登陆密码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updatePassword.html")
    public ResultVo updatePassword(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updatePassword(jsonData);
    }

    /**
     * 根据用户StrId修改交易密码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/updateTransactionPsw.html")
    public ResultVo updateTransactionPsw(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.updateTransactionPsw(jsonData);
    }

    /**
     * 根据用户StrId设置交易密码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/setTransactionPsw.html")
    public ResultVo setTransactionPsw(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.setTransactionPsw(jsonData);
    }

    /**
     * 发送手机验证码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/sendMobileCode.html")
    public ResultVo sendMobileCode(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.sendMobileCode(jsonData);
    }

    /**
     * 发送邮件验证码
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/sendEmailCode.html")
    public ResultVo sendEmailCode(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return userUcSafetyCenterService.sendEmailCode(jsonData);
    }
}
