package com.im.base.common.task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author XiaHui
 * @date 2014年9月12日 下午2:17:36
 */
@Component
public class QueueTaskThread extends Thread {
	protected final Logger logger = LogManager.getLogger(getClass());
	private ConcurrentLinkedQueue<ExecuteTask> executeTaskQueue = new ConcurrentLinkedQueue<ExecuteTask>();// 任务队列
	private long sleepTime = 200;// 线程休眠时间
	private ExecutorService pool = null;;

	public QueueTaskThread() {
		this(10);
	}

	public QueueTaskThread(int poolCount) {
		pool = Executors.newFixedThreadPool(poolCount);
		this.start();
	}

	/**
	 * 添加任务
	 * 
	 * @param executeTask
	 */
	public void add(ExecuteTask executeTask) {
		executeTaskQueue.add(executeTask);
	}

	@Override
	public void run() {
		while (true) {
			handleTask();// 处理任务
			threadSleep(sleepTime);
		}
	}

	private void threadSleep(long time) {
		try {
			sleep(time);
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

	/**
	 * 处理任务队列，检查其中是否有任务
	 */
	private void handleTask() {
		ExecuteTask executeTask;
		while (executeTaskQueue.peek() != null) {
			executeTask = executeTaskQueue.poll();
			try {
				handleTask(executeTask);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 执行任务操作
	 * 
	 * @param executeTask
	 */
	private void handleTask(ExecuteTask executeTask) {
		pool.execute(new ExecuteRunnable(executeTask));
	}

	class ExecuteRunnable implements Runnable {
		ExecuteTask executeTask;

		ExecuteRunnable(ExecuteTask executeTask) {
			this.executeTask = executeTask;
		}

		public void run() {
			try {
				executeTask.execute();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
}
