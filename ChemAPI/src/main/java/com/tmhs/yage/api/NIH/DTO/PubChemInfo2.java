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
public class PubChemInfo2 {
	private RunResultStatus status;
	private List<String> synonyms;
	private String canonicalSmiles;

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
	 * @return the synonyms
	 */
	public List<String> getSynonyms() {
		return synonyms;
	}

	/**
	 * @param synonyms
	 *            the synonyms to set
	 */
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	/**
	 * @return the canonicalSmiles
	 */
	public String getCanonicalSmiles() {
		return canonicalSmiles;
	}

	/**
	 * @param canonicalSmiles
	 *            the canonicalSmiles to set
	 */
	public void setCanonicalSmiles(String canonicalSmiles) {
		this.canonicalSmiles = canonicalSmiles;
	}

}
