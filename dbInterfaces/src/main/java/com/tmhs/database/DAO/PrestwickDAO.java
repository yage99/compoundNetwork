package com.tmhs.database.DAO;

import com.tmhs.database.DTO.Prestwick;

/**
 * @author TMHYXZ6
 *
 */
public interface PrestwickDAO {
	/**
	 * @param position
	 * @return one prestwick drug
	 */
	public Prestwick getItemByPosition(String position);
}
