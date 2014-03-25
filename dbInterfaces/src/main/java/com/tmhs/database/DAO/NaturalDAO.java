/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Natural;

/**
 * @author TMHYXZ6
 * 
 */
public interface NaturalDAO {
	/**
	 * @param position
	 * @return nature
	 */
	public Natural getItemByPosition(String position);
}
