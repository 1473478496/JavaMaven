package cn.sunline.ws.message.thread;

import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import cn.sunline.ws.message.common.ContextUtil;
import cn.sunline.ws.message.service.impl.MessageServiceImpl;


public class MessageThreadTask<V> implements Callable<V> {
	
	private static Logger logger = Logger.getLogger(MessageThreadTask.class);
	
	private static MessageServiceImpl messageService; 
	
	static{
		messageService = (MessageServiceImpl) ContextUtil.getBean("messageService");
	}

	// 随机数标志
	private String random;

	public MessageThreadTask(){
		
	}
	// 构造函数
	public MessageThreadTask(String random){
		this.random = random;
	}
	@Override
	public V call() throws Exception {
		dealWithMessage(random);
		return null;
	}

	public void dealWithMessage(String random) throws Exception {
		logger.info("线程"+random+"开始！" + new Date());
		try {
			messageService.getMessageContent(random);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("线程"+random+"结束！"  + new Date());
	}
	
}