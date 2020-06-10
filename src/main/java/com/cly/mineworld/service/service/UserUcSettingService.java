package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心-设置-调度服务
 * @author william
 *
 */
public interface UserUcSettingService {

    /**
     * 增加意见反馈
     * @param jsonData
     * @return
     */
    ResultVo addFeedback(String jsonData);
}
