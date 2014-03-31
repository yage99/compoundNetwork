/**
 * 
 */
package com.tmhs.yage.api.NIH.DTO;

import java.util.List;

/**
 * @author TMHYXZ6
 * 
 */
public class PubChemDrug {
	private String id;
	private String cid;
	private List<String> synos;
	private String structure;
	private String smiles;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the synos
	 */
	public List<String> getSynos() {
		return synos;
	}

	/**
	 * @param synos
	 *            the synos to set
	 */
	public void setSynos(List<String> synos) {
		this.synos = synos;
	}

	/**
	 * @return the structure
	 */
	public String getStructure() {
		return structure;
	}

	/**
	 * @param structure
	 *            the structure to set
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}

	/**
	 * @return smiles string
	 */
	public String getSmiles() {
		return smiles;
	}

	/**
	 * @param smiles
	 */
	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}
}
