package com.cly.mineworld.service.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	/**
	 * 发送邮件
	 */
	public static void sendEmail(String address,String subject,String content) {
		try {
	        // 配置发送邮件的环境属性
	        final Properties props = new Properties();
	        // 表示SMTP发送邮件，需要进行身份验证
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.qq.com");
	        //props.put("mail.smtp.port", "25");   
	        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置, 
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.port", "465");
	        // 发件人的账号
	        props.put("mail.user", "110100869@qq.com");
	        // 访问SMTP服务时需要提供的密码
	        //props.put("mail.password", "lam123456");
	        //110100869@qq.com    wzamfljoztsvbjch
	        //1415358181@qq.com   okbkfdbtgqxpjjcc
	        props.put("mail.password", "wzamfljoztsvbjch");//wzamfljoztsvbjch
	        //oyixuichbbzihfhj
	        // 构建授权信息，用于进行SMTP进行身份验证
	        Authenticator authenticator = new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                // 用户名、密码
	                String userName = props.getProperty("mail.user");
	                String password = props.getProperty("mail.password");
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	        // 使用环境属性和授权信息，创建邮件会话
	        Session mailSession = Session.getInstance(props, authenticator);
	        // 创建邮件消息
	        MimeMessage message = new MimeMessage(mailSession);
	        // 设置发件人
	        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
	        message.setFrom(form);
	        // 设置收件人
	        InternetAddress to = new InternetAddress(address);
	        message.setRecipient(MimeMessage.RecipientType.TO, to);
	        // 设置邮件标题
	        message.setSubject(subject);
	        // 设置邮件的内容体
	        message.setContent(content, "text/html;charset=UTF-8");
	        // 发送邮件
	        Transport.send(message);
	        System.out.println("Email send success...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
