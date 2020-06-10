package com.cly.mineworld.service.controller;


import com.cly.mineworld.service.service.UserNoticeService;
import com.cly.mineworld.service.vo.common.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台-公告控制器
 * @author william
 *
 */
@RestController
public class UserNoticeController {

	@Autowired
	private UserNoticeService userNoticeService;


	/**
	 * 获取公告详情
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/notice/getNoticeDetails.html")
	public ResultVo getNoticeDetails(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userNoticeService.getNoticeDetails(jsonData);
	}
	
	/**
	 * 获取公告
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/notice/getNotices.html")
	public ResultVo getNotices(
			@RequestParam(value = "jsonData") String jsonData
	) throws Exception {
		return userNoticeService.getNotices(jsonData);
	}
}
