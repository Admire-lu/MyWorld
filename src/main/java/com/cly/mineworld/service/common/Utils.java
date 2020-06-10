package com.cly.mineworld.service.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class Utils {
	
	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}
	
    
	/**
	 * 获取当前时间，精确到微秒
	 * @return
	 */
	public static String getNowYear(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//
		return df.format(new Date());
	}
	
	/**
	 * 获取当前年份（2017）
	 * @return
	 */
	public static String getNowTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//
		return df.format(new Date());
	}
	
	/**
	 * 获取当前时间，精确到秒
	 * @return
	 */
	public static String getNowTimeForSeconds(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		return df.format(new Date());
	}
	
	/**
	 * 获取当前时间字符串
	 * @return
	 */
	public static String getNowTimeStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//
		return df.format(new Date());
	}
	
	/**
	 * 获取当前时间字符串，精确到秒
	 * @return
	 */
	public static String getNowTimeStrForSeconds(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//
		return df.format(new Date());
	}
	
	/**
	 * 获取当前日期字符串
	 * @return
	 */
	public static String getNowDateStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//
		return df.format(new Date());
	}
	/**
	 * 获取当前日期前几个月字符串
	 * @return
	 * @throws ParseException 
	 */
	public static String getNowMothNextStr(int monthSum) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		String curDate=sdf.format(calendar.getTime());
		calendar.setTime(sdf.parse(curDate));
		//取得现在时间
		//System.out.println(sdf.format(curDate));
		//取得上一个时间
		calendar.set(Calendar.MONTH,calendar.get(calendar.MONTH)+monthSum);
		
		//System.out.println(sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获取当前日期前几个月字符串
	 * @return
	 * @throws ParseException 
	 */
	public static String getNowMothNextStr1(int monthSum) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		String curDate=sdf.format(calendar.getTime());
		calendar.setTime(sdf.parse(curDate));
		//取得现在时间
		//System.out.println(sdf.format(curDate));
		//取得上一个时间
		calendar.set(Calendar.MONTH,calendar.get(calendar.MONTH)+monthSum);
		
		//System.out.println(sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获取当前日期字符串2
	 * @return
	 */
	public static String getNowDateStr2(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		return df.format(new Date());
	}
	/**
	 * 获取当前日期字符串3
	 * @return
	 */
	public static String getNowDateStr3(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//
		return df.format(new Date());
	}
	/**
	 * 日期格式化
	 * @param str
	 * @return yyyyMMdd
	 */
	public static String dateFormat(String str){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//
		Date d = null;
		try {
			d = df.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(d);
	}


	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static long getTimestamp() {
		long timstamp = 0;
		try {
			timstamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").parse(getNowTime()).getTime()/1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timstamp;
	}

	public static long getTimestamp11111(String time) {
		long timstamp = 0;

		try {
			timstamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").parse(time).getTime()/1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timstamp;
	}

	/*public static void main(String[] args) {
		String nowTime = getNowTime();
		System.out.println(nowTime);
		long timestamp11111 = getTimestamp11111("2019-09-05 11:39:01:591");
		System.out.println(timestamp11111);
	}*/

	/**
	 * 将时间戳转为时间格式,只要年月日
	 * @return
	 */
	public static String timeStampToDate(Long timestamp) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp * 1000));
		return date;
	}

    
    /**
     * 是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
    	  for (int i = str.length();--i>=0;){   
    	   if (!Character.isDigit(str.charAt(i))){
    	    return false;
    	   }
    	  }
    	  return true;
    }
    
    /**
     * MD5
     * @param source
     * @return
     */
	public static String md5(String source) {  
        String encode = source;
        StringBuilder stringbuilder = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            md5.update(encode.getBytes());  
            byte[] str_encoded = md5.digest();  
            for (int i = 0; i < str_encoded.length; i++) {  
                if ((str_encoded[i] & 0xff) < 0x10) {  
                    stringbuilder.append("0");  
                }  
                stringbuilder.append(Long.toString(str_encoded[i] & 0xff, 16));  
               } 
		} catch (Exception e) {
			e.printStackTrace();
		}
       return stringbuilder.toString();     
	}
	
	/**
	 * 格式化double 为小数两位数
	 * @param sum
	 * @return
	 */
	public static String formatDouble(Double sum){
    	DecimalFormat df = new DecimalFormat("0.00");
    	return df.format(sum);
	}
	
	/**
	 * 格式化double 为小数两位数，返回double
	 * @param sum
	 * @return
	 */
	public static Double formatDoubleForDouble(Double sum){
    	DecimalFormat df = new DecimalFormat("0.00");
    	return Double.parseDouble(df.format(sum));
	}
	
	/**
	 * 格式化数字，不四舍五入
	 * @param sum
	 * @return
	 */
	public static Double formatDoubleNotFourFive(double sum){
		BigDecimal b = new BigDecimal(sum);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
	/**
	 * 格式化数字，不四舍五入,不返回小数
	 * @param sum
	 * @return
	 */
	public static Double formatDoubleNotFourFive2(double sum){
		BigDecimal b = new BigDecimal(sum);
		double f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}
	/**
	 * 获取从今天开始到下几个月的下一天
	 * @param monthSum 取下几个月
	 * @return
	 */
	public static String getNextMonthNextDay(int monthSum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date curDate = new Date();
		calendar.setTime(curDate);
		//取得现在时间
		//System.out.println(sdf.format(curDate));
		//取得上一个时间
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) + monthSum);
		//取得下一个月的下一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		//System.out.println(sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取某天的下个月的下一天
	 * @param monthSum
	 * @return
	 */
	public static String getDayNextMonthNextDay(String date,int monthSum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
		//Date curDate = new Date();
		calendar.setTime(sdf.parse(date));
		//取得现在时间
		//System.out.println(sdf.format(curDate));
		//取得上一个时间
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) + monthSum);
		//取得下一个月的下一天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		//System.out.println(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取某天的下几个月的某天
	 * @param date
	 * @param monthSum
	 * @return
	 */
	public static String getNextMonthCurrentDay(String date,int monthSum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
		//Date curDate = new Date();
		calendar.setTime(sdf.parse(date));
		//取得现在时间
		//System.out.println(sdf.format(curDate));
		//取得上一个时间
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) + monthSum);
		//取得下一个月的当天
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
		//System.out.println(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取从某天开始到下几天
	 * @param daySum
	 * @return
	 */
	public static String getDayNextDay(String date,int daySum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			//Date curDate = new Date();
			calendar.setTime(sdf.parse(date));
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + daySum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取从今天开始到下一天
	 * @param monthSum 取下几天
	 * @return
	 */
	public static String getNextDay(int daySum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date curDate = new Date();
		calendar.setTime(curDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + daySum);
		return sdf.format(calendar.getTime());
	}
	
	public static String addPoint(String str){
		String a = str;
		if(str.substring(str.lastIndexOf(".")+1, str.length()).length()==1){
			a = str + "0";
		}
		return a;
	}
	
	/**
	 * 获取两日期的相隔天数
	 * @param start yyyy-MM-dd
	 * @param end yyyy-MM-dd
	 * @return
	 */
	public static Long getDaysBetween(String start, String end){
		long betweenDate = 0;
		try {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");   
			java.util.Date startDate = df.parse(start);   
			java.util.Date endDate = df.parse(end);   
			betweenDate = (endDate.getTime()-startDate.getTime())/(24*60*60*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return betweenDate;
	}


	
	/**
	 * 获取两日期相隔月数
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getDiffer(String begin, String end) {
		int difMonth = 0;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date beginDate = df.parse(begin);
			Date endDate = df.parse(end);
			int beginYear = beginDate.getYear();
			int beginMonth = beginDate.getMonth();
			int endYear = endDate.getYear();
			int endMonth = endDate.getMonth();
			difMonth = (endYear-beginYear)*12+(endMonth-beginMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return difMonth; 
	}
	
	/**
	 * 获取某日期的最后一天日期
	 * @param invoiceMonth  格式：201108
	 * @return
	 */
	public static String  getInvoiceMonth(String invoiceMonth){//invoiceMonth 的格式为 201108
		String str = "";
		try {
		  String year = invoiceMonth.substring(0, 4);
		  String month = invoiceMonth.substring(4, invoiceMonth.length());
		  Calendar date = Calendar.getInstance(); 
		  int yeari = Integer.parseInt(year); 
		  int monthi = Integer.parseInt(month); 
		  //if(monthi==1){
			//  monthi=12;
		  //}
		  date.set(yeari,monthi-1,1); 
		  int maxDayOfMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		  str = year+"-"+monthi+"-"+maxDayOfMonth;
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return str;
	}
	
    /**
     * 当月第一天
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        //StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return day_first;
    }
    
    /**
     * 当月最后一天
     * @return
     */
	public static String lastDayOfMonth(String da) {
		String d = null;
		try {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(da);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		cal.roll(Calendar.DAY_OF_MONTH, -1); 
		//d = cal.getTime();
		d = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	} 
    
    /**
     * 两日期时间相差的秒数
     * @param start
     * @param end
     * @return
     */
	public static Long getDateBetweenSecond(String start,String end){
		long betweenDate = 0;
		try {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
			java.util.Date startDate = df.parse(start);   
			java.util.Date endDate = df.parse(end);   
			betweenDate = (endDate.getTime()-startDate.getTime())/1000;
		} catch (Exception e) {
			//logger.info("**** ERROR 两日期时间相差的秒数 Utils.getDateBetweenSecond " + e.getMessage());
			e.printStackTrace();
		}
		return betweenDate;
	}
    
    /**
     * 生成唯一码
     * @return
     */
    public static String  getUUID(){  
        return  java.util.UUID.randomUUID().toString().replaceAll("-", "");  
    }
    
	/**
	 * 下载
	 * @param path
	 * @param response
	 * @return
	 */
    public static HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
        	//logger.info("**** ERROR 下载 Utils.download " + ex.getMessage());
            ex.printStackTrace();
        }
        return response;
    }
    
    /**
     * 获取6位随机数字
     * @return
     */
    public static String getSixRamdomSum(){
		 String sum = Double.toString(Math.random() * 900000 + 100000);
		 return sum.substring(0,sum.indexOf("."));
    }
    
    /**
     * 获取4位随机数字
     * @return
     */
    public static String getFourRamdomSum(){
		 String sum = Double.toString(Math.random() * 9000 + 1000);
		 return sum.substring(0,sum.indexOf("."));
    }
    
    /**
     * 随机20位数字字符串
     * @return
     */
    public static String getRamdomTwentySumStr(){
    	return getNowTimeStr() + getSixRamdomSum();
    }
    
    /**
     * 获取3位随机数字
     * @return
     */
    public static String getThreeRamdomSum(){
		 String sum = Double.toString(Math.random() * 900 + 100);
		 return sum.substring(0,sum.indexOf("."));
    }
    
    /** 
     * 获取过去多少个月的月份名称
     */  
    public static String[] getPreMonths(int monthSum){  
        String[] nextMonths = new String[monthSum];  
        for(int i=1;i<monthSum+1;i++){
	        Calendar cal = Calendar.getInstance();  
	        cal.add(Calendar.MONTH, -i);
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	        String date = format.format(cal.getTime());
	        nextMonths[i-1] = date;
        }
        return nextMonths;  
    }
    
    /**
     * 获取今天星期几
     * @return
     */
    public static String getTodayWeek(){
		Calendar c = Calendar.getInstance();
		  c.setTime(new Date(System.currentTimeMillis()));
		  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		  return Integer.toString(dayOfWeek);
    }
    
    /**
     * 某月最后一天
     * @return
     */
	public static String getLastDayOfMonth(String da) {
		String d = null;
		try {
		Date date = new SimpleDateFormat("yyyy-MM").parse(da);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		cal.roll(Calendar.DAY_OF_MONTH, -1); 
		//d = cal.getTime();
		d = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 获取本周第一天日期
	 * @return
	 */
	public static String getWeekFirstDay() {
		Calendar cal =Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
		return df.format(cal.getTime());
	 }
	
	/**
	 * 获取本周最后一天日期
	 * @return
	 */
	public static String getWeekLastDay() {
		Calendar cal =Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return df.format(cal.getTime());
	 }
	
    /**
     * 某月第一天
     * @return
     */
	public static String getFirstDayOfMonth(String da) {
		String d = null;
		try {
		Date date = new SimpleDateFormat("yyyy-MM").parse(da);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		//d = cal.getTime();
		d = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(String birthday) {
		int age = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(birthday);
			Calendar cal = Calendar.getInstance();
			if (cal.before(birthday)) {
				throw new IllegalArgumentException(
						"The birthDay is before Now.It's unbelievable!");
			}
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

			cal.setTime(date);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			age = yearNow - yearBirth;

			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth 
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					// monthNow>monthBirth 
					age--;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return age;
	}

	/**
	 * 用户名是否符合规范（[a-zA-Z\d]{6,20}）
	 * @return
	 */
	public static boolean isNickName(String nickName) {
		if (null == nickName) {
			return false;
		}
		Pattern pattern = Pattern.compile("[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{2,16}");
		Matcher matcher = pattern.matcher(nickName);
		return matcher.matches();
	}


	/**
	 * 密码是否符合规范（[a-zA-Z\d]{6,20}）
	 * @return
	 */
	public static boolean isValidPassword(String password) {
		if (null == password) {
			return false;
		}
	    return password.matches("^([^\\s'‘’]{6,20})$");
	}


	/**
	 * 是否有效手机号码
	 * @param mobileNum
	 * @return
	 */
	public static boolean isMobileNum(String mobileNum) {
		if (null == mobileNum) {
			return false;
		}
		/*return mobileNum.matches("^((13[0-9])|(14[4,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))(\\d{8})$");*/
		return mobileNum.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");
	}


	/**
	 * 是否有效邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email) {
			return false;
		}
		return email.matches("^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
	}
	
	public static long stringToTimeStamp(String str){
		long ts = 0;
		try {
			SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     Date date=simpleDateFormat .parse(str);
		     ts = date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;

	}
	
	/**
	 * POST请求
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }
    
	/**
	 * Get请求
	 * @param url
	 * @param param
	 * @return
	 */
    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建参数列表
            if (param != null) {
            	url += "?";
                for (String key : param.keySet()) {
                	url += key + "=" + param.get(key) + "&";
                }
            }
            url = url.substring(0, url.length() - 1);
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    
    /**
     *	 获取一个随机数
     * @param sum 少于等于这个数的随机数
     * @return
     */
    public static int getRandomSum(int sum) {
		Random r = new Random(1);
		int ran = r.nextInt(sum);
		return ran;
    }
    
    /**
     * 带head的POST
     * @param url
     * @param param
     * @return
     */
    public static String doPostHead(String url, Map<String, String> param,Map<String,String> headParam,StringEntity postingString,boolean isSSL) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = null;
        if(isSSL) {
        	httpClient = HttpClientUtil.createSSLClientDefault();
        }else {
        	httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //StringEntity postingString = new StringEntity("{\"method\":\"getnewaddress\", \"params\":[\"1\"], \"id\": 1}");
            if(null != postingString) {
            	httpPost.setEntity(postingString);
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            if(null != headParam) {
                for (String key : headParam.keySet()) {
                    httpPost.setHeader(key, headParam.get(key));
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            System.out.println(response.getEntity().toString());
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
