/**
 * 
 */
package com.tmhs.yage.api.NIH.test;

/**
 * @author TMHYXZ6
 * 
 */
public class ThreadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stat.runn();
		System.out.println("main end");
		return;
	}

}

class TH extends Thread {
	public void run() {
		try {
			new TH1().start();
			while (true) {
				System.out.println("thread alive.");
				sleep(1000);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

class TH1 extends Thread {
	public void run() {
		try {
			while (true) {
				System.out.println("thread alive.1");
				sleep(1000);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

class Stat {
	public static void runn() {
		Thread threadstart = new Thread() {
			public void run() {
				try {
					new TH().start();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		};
		threadstart.start();
		return;
	}
}