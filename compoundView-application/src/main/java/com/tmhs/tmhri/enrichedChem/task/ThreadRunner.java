/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.task;

/**
 * @author TMHYXZ6
 * 
 */
public abstract class ThreadRunner implements Runnable {

	private RunStatus status = RunStatus.SUCCEED;
	private int tryNum = 0;

	/**
	 * @return running status
	 */
	public RunStatus getStatus() {
		return status;
	}

	/**
	 * When this task needs rerun, set it to error.
	 */
	public void setError() {
		status = RunStatus.ERROR;
		tryNum++;
	}

	/**
	 * @return tried number
	 */
	public int getTryNum() {
		return tryNum;
	}

	/**
	 * reset this task to initial state
	 */
	public void reSet() {
		status = RunStatus.SUCCEED;
	}

}
