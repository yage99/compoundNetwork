/**
 * 
 */
package com.tmhs.database.DTO;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author TMHYXZ6
 * 
 */
public class ProcedExperiment extends Experiment {
	private double p;
	private String drug;
	private String processMsg;
	private String sysIndex;

	/**
	 * @param exp
	 */
	public ProcedExperiment(Experiment exp) {
		super();
		try {
			BeanUtils.copyProperties(this, exp);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		setFormatPosition(super.getPlateNum() + super.getPosition());
	}

	/**
	 * @deprecated please create this from a Experiment Object
	 */
	public ProcedExperiment() {
		super();
	}

	/**
	 * @return the p
	 */
	public double getP() {
		return p;
	}

	/**
	 * @param p
	 *            the p to set
	 */
	public void setP(double p) {
		this.p = p;
	}

	/**
	 * @return the processMsg
	 */
	public String getProcessMsg() {
		return processMsg;
	}

	/**
	 * @param processMsg
	 *            the processMsg to set
	 */
	public void setProcessMsg(String processMsg) {
		this.processMsg = processMsg;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the drug
	 */
	public String getDrug() {
		return drug;
	}

	/**
	 * @param drug
	 *            the drug to set
	 */
	public void setDrug(String drug) {
		this.drug = drug;
	}

	/**
	 * @return the formatPosition
	 */
	public String getFormatPosition() {
		return formatPosition;
	}

	/**
	 * @param formatPosition
	 *            the formatPosition to set
	 */
	public void setFormatPosition(String formatPosition) {
		this.formatPosition = formatPosition;
	}

	/**
	 * @return the sysIndex
	 */
	public String getSysIndex() {
		return sysIndex;
	}

	/**
	 * @param sysIndex
	 *            the sysIndex to set
	 */
	public void setSysIndex(String sysIndex) {
		this.sysIndex = sysIndex;
	}

	private String cid;
	private String formatPosition;

}
