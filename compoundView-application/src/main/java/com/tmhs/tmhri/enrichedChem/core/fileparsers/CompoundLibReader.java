/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.fileparsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ya
 * 
 */
public class CompoundLibReader {
	/**
	 * @param filename
	 * @return cover map
	 * @throws IOException
	 */
	public static Map<String, String> read(String filename) throws IOException {
		Map<String, String> cover = new HashMap<String, String>();
		File file = new File(filename);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;
		String[] sep;
		while ((line = reader.readLine()) != null) {
			sep = line.split("\t");
			cover.put(sep[0], sep[1]);
		}
		reader.close();

		return cover;
	}
}
