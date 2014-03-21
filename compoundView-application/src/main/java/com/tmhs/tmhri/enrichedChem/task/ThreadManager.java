/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.task;

import java.util.LinkedList;
import java.util.List;

import org.cytoscape.work.TaskMonitor;
import org.tmhs.tool.yage.Info.NoticeSystem;

/**
 * @author TMHYXZ6
 * 
 */
public final class ThreadManager {
	private static int worker_num = 5;
	private WorkThread[] workThreads;
	private volatile int finished_task = 0;
	private List<ThreadRunner> taskQueue = new LinkedList<ThreadRunner>();
	private List<ThreadRunner> processedQueue = new LinkedList<ThreadRunner>();
	private ThreadManager threadPool;
	private int errorCount = 0;

	TaskMonitor tm;

	/**
	 * 
	 */
	public ThreadManager() {
		this(8);
	}

	/**
	 * @param worker_num
	 */
	public ThreadManager(int worker_num) {
		ThreadManager.worker_num = worker_num;
	}

	/**
	 * @param tm
	 * 
	 */
	public void startWork(TaskMonitor tm) {
		this.tm = tm;
		finished_task = 0;
		processedQueue = new LinkedList<ThreadRunner>();

		workThreads = new WorkThread[worker_num];
		for (int i = 0; i < worker_num; i++) {
			workThreads[i] = new WorkThread();
			workThreads[i].start();
		}
	}

	/**
	 * @return the results
	 * @throws Exception
	 * 
	 */
	public List<ThreadRunner> waitForFinish() throws Exception {
		for (WorkThread wk : workThreads) {
			while (wk.isAlive()) {
				try {
					if (taskQueue.isEmpty()) {
						halt();
						return this.processedQueue;
					}
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (!taskQueue.isEmpty())
			throw new Exception("Task Running Error");

		return this.processedQueue;
	}

	/**
	 * @return a new thread manager
	 * @deprecated
	 */
	@Deprecated
	public ThreadManager getThreadManager() {
		return getThreadManager(ThreadManager.worker_num);
	}

	/**
	 * @param worker_num1
	 * @return a new thread manager
	 * @deprecated
	 */
	@Deprecated
	public ThreadManager getThreadManager(int worker_num1) {
		if (worker_num1 <= 0)
			worker_num1 = ThreadManager.worker_num;
		if (threadPool == null)
			threadPool = new ThreadManager(worker_num1);
		return threadPool;
	}

	/**
	 * @param task
	 */
	public void addWork(ThreadRunner task) {
		synchronized (taskQueue) {
			taskQueue.add(task);
			taskQueue.notify();
		}
	}

	/**
	 * @param task
	 */
	public void addWork(ThreadRunner[] task) {
		synchronized (taskQueue) {
			for (ThreadRunner t : task)
				taskQueue.add(t);
			taskQueue.notify();
		}
	}

	/**
	 * force stop all tasks
	 */
	public void forceStop() {
		for (int i = 0; i < worker_num; i++) {
			if (workThreads[i] == null)
				continue;
			workThreads[i].interrupt();
			workThreads[i] = null;
		}
		threadPool = null;
		taskQueue.clear();
	}

	/**
	 * @param task
	 */
	public void addWork(List<ThreadRunner> task) {
		synchronized (taskQueue) {
			for (ThreadRunner t : task)
				taskQueue.add(t);
			taskQueue.notify();
		}
	}

	/**
	 * 
	 */
	public void halt() {
		for (int i = 0; i < worker_num; i++) {
			if (workThreads[i].isAlive())
				workThreads[i].stopWorker();
			workThreads[i] = null;
		}
		threadPool = null;
		taskQueue.clear();
	}

	/**
	 * @return current work thread number
	 */
	public int getWorkThreadNumber() {
		return worker_num;
	}

	/**
	 * @return quantity of finished tasks
	 */
	public int getFinishedTasknumber() {
		return finished_task;
	}

	/**
	 * @return quantity of waiting tasks
	 */
	public int getWaitTasknumber() {
		return taskQueue.size();
	}

	@Override
	public String toString() {
		return "WorkThread number:" + worker_num + "  finished task number:"
				+ finished_task + "  wait task number:" + getWaitTasknumber();
	}

	private class WorkThread extends Thread {
		private volatile boolean isRunning = true;

		private volatile boolean stillRun = true;

		@Override
		public void run() {
			ThreadRunner r = null;
			while (isRunning) {
				synchronized (taskQueue) {
					if (taskQueue.isEmpty()) {
						try {
							taskQueue.wait(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!taskQueue.isEmpty())
						r = taskQueue.remove(0);
				}
				if (r != null) {
					try {
						r.run();
						if (r.getStatus() == RunStatus.ERROR) {
							throw new Exception("task error");
						} else {
							finished_task++;
							if (tm != null) {
								int finished = getFinishedTasknumber();
								int wait = getWaitTasknumber();
								tm.setProgress(((float) finished)
										/ (wait + finished + getWorkThreadNumber()));
							}
							processedQueue.add(r);
						}
					} catch (Exception e) {
						errorCount++;
						if (r.getTryNum() < 3) {
							r.reSet();
							taskQueue.add(r);
						} else {
							NoticeSystem.getInstance()
									.err("task running error");
						}
					}

				}
				if (errorCount >= 10) {
					isRunning = false;
				}
				r = null;
			}
			stillRun = false;
		}

		public void stopWorker() {
			isRunning = false;
			while (stillRun) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}