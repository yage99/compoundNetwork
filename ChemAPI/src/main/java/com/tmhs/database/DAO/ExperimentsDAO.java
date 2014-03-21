package com.tmhs.database.DAO;

import java.util.List;

import com.tmhs.database.DTO.Experiment;

/**
 * @author TMHYXZ6
 * 
 */
public interface ExperimentsDAO {
	/**
	 * @param start
	 * @param num
	 * @return list of experiments
	 */
	public List<Experiment> get(int start, int num);

	/**
	 * @param libName
	 * @param plateNum
	 * @param position
	 * @param cell 
	 * @return one experiment of this position
	 */
	public Experiment getByPosition(String libName, String plateNum,
			String position, String cell);
}
