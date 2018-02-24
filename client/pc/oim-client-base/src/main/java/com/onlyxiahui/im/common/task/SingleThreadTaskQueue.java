package com.onlyxiahui.im.common.task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlyxiahui.app.base.task.ExecuteTask;

/**
 * @author XiaHui
 * @date 2017-12-27 11:26:40
 */
public class SingleThreadTaskQueue extends Thread {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ConcurrentLinkedQueue<ExecuteTask> executeTaskQueue = new ConcurrentLinkedQueue<ExecuteTask>();
	private ExecutorService pool = Executors.newSingleThreadExecutor();
	/**
	 * 线程休眠时间
	 */
	private long sleepTime = 200;

	public SingleThreadTaskQueue() {
		pool.execute(this);
	}

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
		ExecuteTask task;
		while (executeTaskQueue.peek() != null) {
			try {
				task = executeTaskQueue.poll();
				handleTask(task);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 执行任务操作
	 * 
	 * @param executeTask
	 */
	private void handleTask(final ExecuteTask executeTask) {
		try {
			executeTask.execute();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
