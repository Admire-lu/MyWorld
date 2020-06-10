package com.cly.mineworld.service.controller;

import com.cly.mineworld.service.service.UserUcSettingService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端-用户个人中心-设置-控制器
 * @author william
 *
 */
@RestController
public class UserUcSettingController {

    @Autowired
    private UserUcSettingService ucSettingService;


    /**
     * 意见反馈
     * @param jsonData
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/user/userCentre/feedback.html")
    public ResultVo addFeedback(
            @RequestParam(value = "jsonData") String jsonData
    ) throws Exception {
        return ucSettingService.addFeedback(jsonData);
    }
}
