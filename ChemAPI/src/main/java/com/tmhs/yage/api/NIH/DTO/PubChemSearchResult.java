/**
 * 
 */
package com.tmhs.yage.api.NIH.DTO;

import java.util.List;

import org.tmhs.tool.yage.Info.RunResultStatus;

/**
 * @author TMHYXZ6
 * 
 */
public class PubChemSearchResult {
	private RunResultStatus status;
	private int num;
	private List<String> cids;

	/**
	 * @return the status
	 */
	public RunResultStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(RunResultStatus status) {
		this.status = status;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the cids
	 */
	public List<String> getCids() {
		return cids;
	}

	/**
	 * @param cids
	 *            the cids to set
	 */
	public void setCids(List<String> cids) {
		this.cids = cids;
	}

}
