package com.tmhs.database.DTO;


/**
 * @author TMHYXZ6
 * 
 * @deprecated use {@link PubChemDrug} instead.
 */
public class Drug {
	private String id;
	private String plateName;
	private String position;
	private String name;
	private String desc;
	private String cid;
	private String structure;
	private String libName;
	private String processMsg;
	private String img;
	private String otherRes;
	private String sysIndex;

	/**
	 * @return the plateName
	 */
	public String getPlateName() {
		return plateName;
	}

	/**
	 * @param plateName
	 *            the plateName to set
	 */
	public void setPlateName(String plateName) {
		this.plateName = plateName;
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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
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
	 * @return the libName
	 */
	public String getLibName() {
		return libName;
	}

	/**
	 * @param libName
	 *            the libName to set
	 */
	public void setLibName(String libName) {
		this.libName = libName;
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
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the synos
	 */
	public String getOtherRes() {
		return otherRes;
	}

	/**
	 * @param synos
	 *            the synos to set
	 */
	public void setOtherRes(String synos) {
		this.otherRes = synos;
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
}
