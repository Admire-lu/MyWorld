package com.cly.mineworld.service.thread;

import java.io.IOException;
import com.cly.mineworld.service.sms.SmsUtils;

/**
 * 发送短信验证码线程
 * @author william
 *
 */
public class ThreadSendSmsCode implements Runnable {

	private String mobile;
	private String code;
	private String type;
	
	public ThreadSendSmsCode(String mobile,String code,String type) {
		this.mobile = mobile;
		this.code = code;
		this.type = type;
	}
	
	@Override
	public void run() {
		try {
			String resultStr = SmsUtils.sendSmsCode(this.mobile, this.code, this.type);
			System.out.println(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
