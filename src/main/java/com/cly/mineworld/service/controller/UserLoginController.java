package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserLoginService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-登录控制器
 * @author william
 *
 */
@RestController
public class UserLoginController {

	@Autowired
	private UserLoginService userLoginService;


	/**
	 * 用户登录
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/login/userLogin.html")
	public ResultVo userLogin(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userLoginService.userLogin(jsonData);
	}

}
