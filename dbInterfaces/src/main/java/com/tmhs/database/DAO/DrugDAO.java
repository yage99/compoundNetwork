package com.tmhs.database.DAO;

import java.util.List;

import com.tmhs.database.DTO.Drug;

/**
 * @author TMHYXZ6
 * 
 * @deprecated don't use this
 */
public interface DrugDAO {
	/**
	 * 
	 * @param drug
	 * @return new drug id
	 */
	public String insert(Drug drug);

	/**
	 * @param start
	 * @param end
	 * @return a list of drugs
	 */
	public List<Drug> getAll(int start, int end);

	/**
	 * @param libName
	 * @param position
	 * @return drug
	 */
	public Drug getDrugByPosition(String libName, String position);

	/**
	 * @param id
	 * @return drug
	 * 
	 *         get drug by the db system _id. Don't use it if you don't know
	 *         what is a system _id
	 */
	public Drug getDrugById(String id);

	/**
	 * @param drug
	 * @param upsert
	 * @return true
	 */
	public boolean update(Drug drug, boolean upsert);

	/**
	 * @param index
	 * @return a drug
	 */
	public Drug getDrugByIndex(String index);
}
