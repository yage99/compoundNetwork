package com.tmhs.database.DTO;

/**
 * @author TMHYXZ6
 * 
 */
public class Experiment {
	private String id;
	private String libName;
	private String plateNum;
	private String position;
	private String result1;
	private double z1;
	private double z2;
	private String result2;
	private String cell;
	private String name;
	private String desc;
	private String title;
	private int expNum = 0;
	
	private String model;
	private String sourceFileName;

	/**
	 * @return string
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the plateNum
	 */
	public String getPlateNum() {
		return plateNum;
	}

	/**
	 * @param plateNum
	 *            the plateNum to set
	 */
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
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
	 * @return the result1
	 */
	public String getResult1() {
		return result1;
	}

	/**
	 * @param result1
	 *            the result1 to set
	 */
	public void setResult1(String result1) {
		this.result1 = result1;
	}

	/**
	 * @return the result2
	 */
	public String getResult2() {
		return result2;
	}

	/**
	 * @param result2
	 *            the result2 to set
	 */
	public void setResult2(String result2) {
		this.result2 = result2;
	}

	/**
	 * @return the cell
	 */
	public String getCell() {
		return cell;
	}

	/**
	 * @param cell
	 *            the cell to set
	 */
	public void setCell(String cell) {
		this.cell = cell;
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
	 * @return the z1
	 */
	public double getZ1() {
		return z1;
	}

	/**
	 * @param z1 the z1 to set
	 */
	public void setZ1(double z1) {
		this.z1 = z1;
	}

	/**
	 * @return the z2
	 */
	public double getZ2() {
		return z2;
	}

	/**
	 * @param z2 the z2 to set
	 */
	public void setZ2(double z2) {
		this.z2 = z2;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the sourceFileName
	 */
	public String getSourceFileName() {
		return sourceFileName;
	}

	/**
	 * @param sourceFileName the sourceFileName to set
	 */
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	/**
	 * @return the expNum
	 */
	public int getExpNum() {
		return expNum;
	}

	/**
	 * @param expNum the expNum to set
	 */
	public void setExpNum(int expNum) {
		this.expNum = expNum;
	}
}
