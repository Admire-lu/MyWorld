package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserRegisterService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户端-用户注册控制器
 * @author william
 *
 */
@RestController
public class UserRegisterController {

	@Autowired
	private UserRegisterService userRegisterService;


	/**
	 * 校验手机或者邮箱是否存在
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/register/checkMobileOrEmailIsExist.html")
	public ResultVo checkMobileOrEmailIsExist(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userRegisterService.checkMobileOrEmailIsExist(jsonData);
	}

	/**
	 * 发送验证码
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/register/sendVerificationCode.html")
	public ResultVo sendVerificationCode(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userRegisterService.sendVerificationCode(jsonData);
	}
	
	/**
	 * 用户注册
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/register/userRegister.html")
	public ResultVo userRegister(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userRegisterService.userRegister(jsonData);
	}

	/**
	 * 注册下级
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/register/registeredSubordinate.html")
	public ResultVo registeredSubordinate(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userRegisterService.registeredSubordinate(jsonData);
	}
}
