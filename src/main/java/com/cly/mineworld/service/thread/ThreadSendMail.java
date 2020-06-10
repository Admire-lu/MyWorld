package com.cly.mineworld.service.thread;

import com.cly.mineworld.service.sendcloud.SendCloudSendMail;

/**
 * 发送邮件线程
 * @author william
 *
 */
public class ThreadSendMail implements Runnable {

	private String address;
	private String subject;
	private String content;
	
	public ThreadSendMail(String address,String subject,String content) {
		this.address = address;
		this.subject = subject;
		this.content = content;
	}
	
	@Override
	public void run() {
		//MailUtils.sendEmail(address,subject ,content);
		try {
			SendCloudSendMail.sendCode(address, subject, content);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
