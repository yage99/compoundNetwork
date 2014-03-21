/**
 * 
 */
package com.tmhs.database.DAO;

import com.tmhs.database.DTO.CompoundSimilarity;

/**
 * @author ya
 * 
 */
public interface CompoundSimilarityDAO {
	/**
	 * @param cs
	 * @return value
	 */
	public double getValue(CompoundSimilarity cs);

	/**
	 * @param cs
	 * @return succeed
	 * 
	 */
	public boolean insert(CompoundSimilarity cs);
}
