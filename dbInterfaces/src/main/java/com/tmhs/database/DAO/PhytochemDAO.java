/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Phytochem;

/**
 * @author TMHYXZ6
 *
 */
public interface PhytochemDAO {
	/**
	 * @param position
	 * @return item
	 */
	public Phytochem getItemByPostion(String position);
}
