/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Spectrum;

/**
 * @author TMHYXZ6
 *
 */
public interface SpectrumDAO {
	/**
	 * @param position
	 * @return a spectrum drug
	 */
	public Spectrum getItemByPosition(String position);
}
