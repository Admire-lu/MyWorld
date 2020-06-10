package com.cly.mineworld.service.service;

import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户个人中心--实名认证-调度服务
 * @author william
 *
 */
public interface UserUcRnAuthenticationService {

    /**
     *实名认证
     * @param jsonData
     * @return
     */
    ResultVo realNameAuthentication(String jsonData);

    /**
     * 实名信息详情
     * @param jsonData
     * @return
     */
    ResultVo findRealNameInformation(String jsonData);

    /**
     * 校验是否已经实名认证
     * @param jsonData
     * @return
     */
    ResultVo checkRealNameIsExist(String jsonData);
}
