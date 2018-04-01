package cn.sunline.ws.message.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
@Service("msgThread")
public class MessageThread {

	private static Logger logger = Logger.getLogger(MessageThread.class);

	private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	public void dealMsg(List<Callable<String>> list) {
		try {
			executorService.invokeAll(list);
			Thread.sleep(250);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
}