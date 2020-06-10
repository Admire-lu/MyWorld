package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.AuthorizationService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 授权控制器
 * @author william
 *
 */
@RestController
public class AuthorizationController {

	@Autowired
	private AuthorizationService authorizationService;
	
	/**
	 * 获取token
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/authorization/getTokenRequest.html")
	public ResultVo getTokenRequest(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return authorizationService.getToken(jsonData);
	}
}
