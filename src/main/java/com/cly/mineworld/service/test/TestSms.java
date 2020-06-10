package com.cly.mineworld.service.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.cly.mineworld.service.thread.ThreadSendSmsCode;

public class TestSms {

    public static final String GET_URL = "http://120.77.14.55:8888/v2sms.aspx";
//  final static String userid = "9559";
//  final static String account = "jiaoysuo";
//  final static String password = "jiaoysuo";

//  final static String userid = "11276";
//  final static String account = "coinbkb";
//  final static String password = "coinbkb";

  final static String userid = "11611";
  final static String account = "EVA";
  final static String password = "EVA123";
  final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddhhmmss");
  //验证码模版内容
//  final static String TEMEPLATE_1001 = "【CoinMars】您的验证码是：code,该验证码10分钟内有效。";
  //C2C商家买入挂单用户接单对商家提示
//  final static String TEMEPLATE_1002 = "【CoinMars】您的forderId号订单已经生成,对方资金已经锁定,请及时完成订单,超时(15分钟)自动取消。";
  //C2C用户确认付款对商家短信提示
//  final static String TEMEPLATE_1003 = "【CoinMars】您的forderId号卖单已经处于买家“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”，完成交易。";
  //C2C商家买入订单商家确认付款提示
//  final static String TEMEPLATE_1004 = "【CoinMars】您的forderId号订单已经处于“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”及时提交,完成交易。";

  //验证码模版内容
//  final static String TEMEPLATE_1001 = "【Coinbkb Global】您的验证码是：code,该验证码10分钟内有效。";
  //C2C商家买入挂单用户接单对商家提示
//  final static String TEMEPLATE_1002 = "【Coinbkb Global】您的forderId号订单已经生成,对方资金已经锁定,请及时完成订单,超时(15分钟)自动取消。";
  //C2C用户确认付款对商家短信提示
//  final static String TEMEPLATE_1003 = "【Coinbkb Global】您的forderId号卖单已经处于买家“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”，完成交易。";
  //C2C商家买入订单商家确认付款提示
//  final static String TEMEPLATE_1004 = "【Coinbkb Global】您的forderId号订单已经处于“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”及时提交,完成交易。";

  //验证码模版内容
  final static String TEMEPLATE_1001 = "【eva】您的验证码是：code,该验证码10分钟内有效。";
  //C2C商家买入挂单用户接单对商家提示
  final static String TEMEPLATE_1002 = "【bacmars】您的forderId号订单已经生成,对方资金已经锁定,请及时完成订单,超时(15分钟)自动取消。";
  //C2C用户确认付款对商家短信提示
  final static String TEMEPLATE_1003 = "【bacmars】您的forderId号卖单已经处于买家“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”，完成交易。";
  //C2C商家买入订单商家确认付款提示
  final static String TEMEPLATE_1004 = "【bacmars】您的forderId号订单已经处于“已付款”状态,请尽快查看收款账号，并在确认收到付款后点击“确认收款”及时提交,完成交易。";

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
//		ThreadSendSmsCode tsc = new ThreadSendSmsCode("13684911320","1234","0");
//		Thread t = new Thread(tsc);
//		t.start();
//	}

    // 发送短信
    public static String Send(String mobile,String code,String type) throws IOException {
        String dateStr = getDateStr();
        String requestUrl = GET_URL + "/v2sms.aspx?action=send&userid=" + userid
                + "&timestamp=" + dateStr
                + "&sign=" + getSign(dateStr)
                + "&mobile=" + mobile+"&content=";
        String content = "";
        if ("0".equals(type)){
            content = URLEncoder.encode(TEMEPLATE_1001.replace("code",code), "utf-8");
        }else if("1".equals(type)){
            content = URLEncoder.encode(TEMEPLATE_1002.replace("forderId",code), "utf-8");
        }else if("2".equals(type)){
            content = URLEncoder.encode(TEMEPLATE_1003.replace("forderId",code), "utf-8");
        }else if("3".equals(type)){
            content = URLEncoder.encode(TEMEPLATE_1004.replace("forderId",code), "utf-8");
        }else{
            return "";
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
