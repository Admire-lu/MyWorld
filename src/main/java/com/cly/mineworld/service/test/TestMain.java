package com.cly.mineworld.service.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.entity.StringEntity;

import com.cly.mineworld.service.common.Utils;

import net.sf.json.JSONObject;

public class TestMain {

	public static void main(String[] args) {
		double amount = 82003.2345;
		int count = 1000;
		double startAmount = 80.00;
//		for(int i=0;i<count;i++) {
//			double a = (amount - startAmount) / count;
//			if(amount - a > 0) {
//			System.out.println(a);
//			startAmount += a;
//			amount -= a;
//			
//			}
//		}
//		System.out.println("amount:"+amount);
		double a = amount / count;
		for(int i=0;i<count;i++) {
		
		Random rand = new Random();
		int num = (new Double(a)).intValue();
		System.out.println(rand.nextInt(num) + 1);
		}
		
	}
	
	
//	public static void test() {
//		Random r = new Random(1);
//		for(int i=0 ; i<5 ;  i++)
//		{
//			int ran1 = r.nextInt(10);
//			System.out.println(ran1);
//		}
//		try {
//			withdrawToken("0xf83d1F27ccFdA30da9Ee2F46DbB40246D1c97e08",10.0);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  Random rand = new Random();
//		  for(int i=0; i<100; i++) {
//		   System.out.println(rand.nextInt(500) + 10);
//		  }
	//}

	/**
	 * 提币
	 * @param childAddress
	 * @param tokenCount
	 * @throws Exception
	 */
	public static void withdrawToken(String childAddress,double tokenCount) throws Exception {
		walletpassphrase();
		sendToAddress(childAddress,tokenCount);
	}
	
	/**
	 * 解锁主账户
	 * @throws Exception
	 */
	public static void walletpassphrase() throws Exception {
		/*解锁主账户*/
		String url = "http://47.244.130.245:10013/walletpassphrase";
		long timestamp = System.currentTimeMillis() * 1000;
		Map<String,String> headParam = new HashMap<String,String>();
		headParam.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary("VAD:vadpassword".getBytes()));
		headParam.put("Content-Type", "[{\"key\":\"Content-Type\",\"value\":\"application/x-www-form-urlencoded\",\"description\":\"\",\"type\":\"text\",\"enabled\":true}]");
		headParam.put("json-Rpc-Tonce", Long.toString(timestamp));
		StringEntity postingString = new StringEntity("{\"method\":\"walletpassphrase\", \"params\":[\"127830yf\",30], \"id\": 1}");
		String str = Utils.doPostHead(url, null,headParam,postingString,false);
		System.out.println(str);
		JSONObject jsoResult = JSONObject.fromObject(str);
		if(null == jsoResult.get("result") || "".equals(jsoResult.getString("result"))) {
			throw new Exception("walletpassphrase Result is null");
		}
		if(!"null".equals(jsoResult.getString("error"))) {
			throw new Exception("walletpassphrase ERROR");
		}
	}
	
	/**
	 * 提币到子账户
	 * @throws Exception
	 */
	public static void sendToAddress(String childAddress,double tokenCount) throws Exception {
		/*提币*/
		String url = "http://47.244.130.245:10013/sendtoaddress";
		long timestamp = System.currentTimeMillis() * 1000;
		Map<String,String> headParam = new HashMap<String,String>();
		headParam.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary("VAD:vadpassword".getBytes()));
		headParam.put("Content-Type", "application/x-www-form-urlencoded");
		headParam.put("json-Rpc-Tonce", Long.toString(timestamp));
		StringEntity postingString = new StringEntity("{\"method\":\"sendtoaddress\", \"params\":[\""+childAddress+"\",\""+tokenCount+"\",\"1\"], \"id\": 1}");
		String str = Utils.doPostHead(url, null,headParam,postingString,false);
		System.out.println(str);
		JSONObject jsoResult = JSONObject.fromObject(str);
		if(null == jsoResult.get("result") || "".equals(jsoResult.getString("result"))) {
			throw new Exception("sendToAddress Result is null");
		}
		if(!"null".equals(jsoResult.getString("error"))) {
			throw new Exception("sendToAddress ERROR");
		}
	}
	
//	public static void walletpassphrase() throws Exception {
//		/*解锁主账户*/
//		String url = "http://47.244.130.245:10013/walletpassphrase";
//		long timestamp = System.currentTimeMillis() * 1000;
//		Map<String,String> headParam = new HashMap<String,String>();
//		headParam.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary("ETH:ethpassword".getBytes()));
//		headParam.put("Content-Type", "[{\"key\":\"Content-Type\",\"value\":\"application/x-www-form-urlencoded\",\"description\":\"\",\"type\":\"text\",\"enabled\":true}]");
//		headParam.put("json-Rpc-Tonce", Long.toString(timestamp));
//		StringEntity postingString = new StringEntity("{\"method\":\"walletpassphrase\", \"params\":[\"127830yf\",30], \"id\": 1}");
//		String str = Utils.doPostHead(url, null,headParam,postingString,false);
//		System.out.println(str);
//		JSONObject jsoResult = JSONObject.fromObject(str);
//		if(null == jsoResult.get("result") || "".equals(jsoResult.getString("result"))) {
//			throw new Exception("walletpassphrase Result is null");
//		}
//		if(!"null".equals(jsoResult.getString("error"))) {
//			throw new Exception("walletpassphrase ERROR");
//		}
//	}
//	
//	public static void sendToAddress() throws Exception {
//		/*提币*/
//		String url = "http://47.244.130.245:10013/sendtoaddress";
//		long timestamp = System.currentTimeMillis() * 1000;
//		Map<String,String> headParam = new HashMap<String,String>();
//		headParam.put("Authorization", "Basic " + DatatypeConverter.printBase64Binary("ETH:ethpassword".getBytes()));
//		headParam.put("Content-Type", "application/x-www-form-urlencoded");
//		headParam.put("json-Rpc-Tonce", Long.toString(timestamp));
//		StringEntity postingString = new StringEntity("{\"method\":\"sendtoaddress\", \"params\":[\"0xf83d1f27ccfda30da9ee2f46dbb40246d1c97e08\",\"0.1\",\"1\"], \"id\": 1}");
//		String str = Utils.doPostHead(url, null,headParam,postingString,false);
//		System.out.println(str);
//		JSONObject jsoResult = JSONObject.fromObject(str);
//		if(null == jsoResult.get("result") || "".equals(jsoResult.getString("result"))) {
//			throw new Exception("sendToAddress Result is null");
//		}
//		if(!"null".equals(jsoResult.getString("error"))) {
//			throw new Exception("sendToAddress ERROR");
//		}
//	}
}
