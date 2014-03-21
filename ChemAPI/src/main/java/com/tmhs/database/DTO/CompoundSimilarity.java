/**
 * 
 */
package com.tmhs.database.DTO;

/**
 * @author ya
 * 
 */
public class CompoundSimilarity {
	private String id;

	private String smiles1;
	private String smiles2;

	private double value;

	/**
	 * 
	 */
	public CompoundSimilarity() {

	}

	/**
	 * @param smiles1
	 * @param smiles2
	 */
	public CompoundSimilarity(String smiles1, String smiles2) {
		this.setPair(smiles1, smiles2);
	}

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
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @param smiles1
	 * @param smiles2
	 * 
	 */
	public void setPair(String smiles1, String smiles2) {
		if (smiles1.compareToIgnoreCase(smiles2) >= 0) {
			this.smiles1 = smiles2;
			this.smiles2 = smiles1;
		} else {
			this.smiles1 = smiles1;
			this.smiles2 = smiles2;
		}
	}

	/**
	 * @return the cid1
	 */
	public String getCid1() {
		return smiles1;
	}

	/**
	 * @return the cid2
	 */
	public String getCid2() {
		return smiles2;
	}

}
