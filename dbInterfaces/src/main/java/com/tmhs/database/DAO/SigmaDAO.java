package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Sigma;

/**
 * @author TMHYXZ6
 *
 */
public interface SigmaDAO {
	/**
	 * @param position
	 * @return a sigma drug
	 */
	public Sigma getDrugByPosition(String position);
}
