package cn.sunline.ws.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import cn.sunline.ws.message.thread.MessageThread;
import cn.sunline.ws.message.thread.MessageThreadTask;
//@PropertySource("classpath:sysConfig.properties")
@Service("messageTask")
public class MessageTask {
	private static final Logger logger = Logger.getLogger(MessageTask.class);
	// 线程
	@Autowired
	private MessageThread msgThread;
	
	//@Scheduled(cron = "${jobs.cron}")
	public void excuteTask(){
		logger.info("定时任务开始执行" + new Date());
		List<Callable<String>> list = new ArrayList<Callable<String>>();
		for(int i = 0 ; i <  5 ; i++){
			MessageThreadTask<String> task = new MessageThreadTask<String>(i + "");
			list.add(task);
		}
		// 执行多线程
		this.msgThread.dealMsg(list);
		logger.info("定时任务执行结束" + new Date());
		
	}
	
}
