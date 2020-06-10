package com.cly.mineworld.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cly.mineworld.service.service.UserCommonService;
import com.cly.mineworld.service.vo.common.ResultVo;

/**
 * 用户公共控制器
 * @author william
 *
 */
@RestController
public class UserCommonController {

	@Autowired
	private UserCommonService userCommonService;
	
	/**
	 * 获取应用版本信息
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/common/getAppVersion.html")
	public ResultVo getAppVersion(
			@RequestParam(value = "jsonData") String jsonData
			) throws Exception {
		return userCommonService.getAppVersion(jsonData);
	}
}
