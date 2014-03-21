/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.task;

/**
 * @author TMHYXZ6
 * 
 */
public enum RunStatus {
	/**
	 * task ends succeed
	 */
	SUCCEED,
	/**
	 * some error
	 */
	ERROR,
	/**
	 * task runs too long
	 */
	TIMEOUT;
}
