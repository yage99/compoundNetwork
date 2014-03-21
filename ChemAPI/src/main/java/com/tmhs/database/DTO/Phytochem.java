/**
 * 
 */
package com.tmhs.database.DTO;

/**
 * @author TMHYXZ6
 * 
 */
public class Phytochem {
	private String id;
	private String position;
	private String PrestwNumber;
	private String name;
	private String fmlas;
	private String molweight;
	private String CASNo;
	private String smiles;
	private String precautions;

	/**
	 * @return the precautions
	 */
	public String getPrecautions() {
		return precautions;
	}

	/**
	 * @param precautions
	 *            the precautions to set
	 */
	public void setPrecautions(String precautions) {
		this.precautions = precautions;
	}

	/**
	 * @return the smiles
	 */
	public String getSmiles() {
		return smiles;
	}

	/**
	 * @param smiles
	 *            the smiles to set
	 */
	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}

	/**
	 * @return the cASNo
	 */
	public String getCASNo() {
		return CASNo;
	}

	/**
	 * @param cASNo
	 *            the cASNo to set
	 */
	public void setCASNo(String cASNo) {
		CASNo = cASNo;
	}

	/**
	 * @return the molweight
	 */
	public String getMolweight() {
		return molweight;
	}

	/**
	 * @param molweight
	 *            the molweight to set
	 */
	public void setMolweight(String molweight) {
		this.molweight = molweight;
	}

	/**
	 * @return the fmlas
	 */
	public String getFmlas() {
		return fmlas;
	}

	/**
	 * @param fmlas
	 *            the fmlas to set
	 */
	public void setFmlas(String fmlas) {
		this.fmlas = fmlas;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the prestwNumber
	 */
	public String getPrestwNumber() {
		return PrestwNumber;
	}

	/**
	 * @param prestwNumber
	 *            the prestwNumber to set
	 */
	public void setPrestwNumber(String prestwNumber) {
		PrestwNumber = prestwNumber;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
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

}
