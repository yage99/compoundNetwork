/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Tocris;

/**
 * @author TMHYXZ6
 *
 */
public interface TocrisDAO {
	/**
	 * @param position
	 * @return a tocris drug
	 */
	public Tocris getItemByPosition(String position);
}
