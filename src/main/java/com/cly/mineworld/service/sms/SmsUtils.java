package com.cly.mineworld.service.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsUtils {

    public static final String GET_URL = "http://120.77.14.55:8888/v2sms.aspx";
    final static String userid = "11611";
    final static String account = "EVA";
    final static String password = "EVA123";
    final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddhhmmss");
    //final static String TEMEPLATE_1001 = "【eva】您的验证码是：code,该验证码10分钟内有效。";
    final static String TEMEPLATE_1001 = "【我的世界】您的验证码是：code,该验证码2分钟内有效。";
	
	 //MD5加密,账号+密码+时间戳
	 public static String MD5(String str) {
	      try {
	          MessageDigest digest =MessageDigest.getInstance("MD5");
	          byte[] bs = digest.digest(str.getBytes("utf-8"));
	          StringBuffer zm =new StringBuffer();
	          for (byte b : bs) {
	              int i=b&0xff;
	              String hexString = Integer.toHexString(i);
	              if (hexString.length()<2) {
	                  hexString="0"+hexString;
	              }
	              zm.append(hexString);
	          }
	          return zm.toString();
	      }
	      catch (Exception e) {
	          return null;
	      }
	  }
		
	//	public static void main(String[] args) throws Exception {
	//		sendSmsCode("15012630328","1234","0");
	//}

	    // 发送短信验证码
	    public static String sendSmsCode(String mobile,String code,String type) throws IOException {
	        String dateStr = getDateStr();
	        String requestUrl = GET_URL + "/v2sms.aspx?action=send&userid=" + userid
	                + "&timestamp=" + dateStr
	                + "&sign=" + getSign(dateStr)
	                + "&mobile=" + mobile+"&content=";
	        String content = "";
	        if ("0".equals(type)){
	            content = URLEncoder.encode(TEMEPLATE_1001.replace("code",code), "utf-8");
	        }
	        requestUrl = requestUrl+content+"&sendTime=&extno=";
	        URL getUrl = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	        connection.connect();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
	        String lines=null;
	        String line;
	        while ((line = reader.readLine()) != null) {
	            lines += line;
	        }
	        reader.close();
	        // 断开连接
	        connection.disconnect();
	        return lines;
	    }
	    
	    public static String getDateStr(){
	        return SDF.format(new Date());
	    }

	    public static String getSign(String dateStr){
	        return MD5(account+password+dateStr);
	    }
}
