package com.cly.mineworld.service.thread;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ThreadUserLog implements Runnable {

	private String jsonStr;
	public ThreadUserLog(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	@Override
	public void run() {
		sendLog(this.jsonStr);
	}

	private void sendLog(String sendStr) {
		Properties properties = new Properties();
	    properties.put(PropertyKeyConst.GROUP_ID, "GID-evarocketmq");
	    properties.put(PropertyKeyConst.AccessKey,"LTAI1Hdh9H2DLrON");
	    properties.put(PropertyKeyConst.SecretKey, "C7hcR9046GIZ0Rux2rNc5Yx8YlCFh8");
	    properties.put(PropertyKeyConst.NAMESRV_ADDR,"http://MQ_INST_1357347188445129_Bbb285nc.mq-internet-access.mq-internet.aliyuncs.com:80");
	    Producer producer = ONSFactory.createProducer(properties);
	    producer.start();
        //Message msg = new Message("eva-user-log-topic","TagA",sendStr.getBytes());
        Message msg = new Message("eva-work-user-log-topic","TagA",sendStr.getBytes());//生产
	    msg.setKey("ORDERID_100");
	    SendResult sendResult = producer.send(msg);
	    System.out.println("Send Message success. Message ID is: " + sendResult.getMessageId());
	    producer.shutdown();
	}
}
