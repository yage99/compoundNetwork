/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.PubChemDrug;

/**
 * @author TMHYXZ6
 * 
 */
public interface PubChemDrugDAO {
	/**
	 * @param drug
	 * @return id
	 */
	public String insert(PubChemDrug drug);

	/**
	 * @param cid
	 * @return a pubchem drug
	 */
	public PubChemDrug find(String cid);

	/**
	 * @param drug
	 * @return update status
	 */
	public boolean update(PubChemDrug drug);
}
