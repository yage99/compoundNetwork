/**
 * 
 */
package com.tmhs.database.DAO;

import java.util.List;

import com.tmhs.database.DTO.ProcedExperiment;

/**
 * @author TMHYXZ6
 * 
 */
public interface ProcedExperimentsDAO {
	/**
	 * @param exp
	 * @return id
	 */
	public String insert(ProcedExperiment exp);

	/**
	 * @param libName
	 * @param cell
	 * @param formatPosition
	 * @param expNum
	 * @param model
	 * @return result
	 */
	public ProcedExperiment getData(String libName, String cell,
			String formatPosition, int expNum, String model);

	/**
	 * @param start
	 * @param limit
	 * @return result list
	 */
	public List<ProcedExperiment> getAll(int start, int limit);

	/**
	 * @param lib
	 * @param start
	 * @param limit
	 * @return list of the lib
	 */
	public List<ProcedExperiment> getLib(String lib, int start, int limit);

	/**
	 * @param exp
	 * @return true if succeed
	 */
	public boolean update(ProcedExperiment exp);

	/**
	 * @param libName
	 * @param cell
	 * @param formatPosition
	 * @return list
	 */
	public List<ProcedExperiment> getDatas(String libName, String cell,
			String formatPosition);
}
