/**
 * 
 */
package com.tmhs.database.DAO;

import java.io.InputStream;

/**
 * @author TMHYXZ6
 * 
 */
public interface FileManagerDAO {
	/**
	 * @param is
	 * @param fileName
	 * @param close
	 * @return position
	 */
	public String saveFile(InputStream is, String fileName, boolean close);

	/**
	 * @param fileName
	 * @return fileid
	 */
	public String findFile(String fileName);

	/**
	 * @param fileName
	 * @return file input stream
	 */
	public InputStream getFile(String fileName);
}
