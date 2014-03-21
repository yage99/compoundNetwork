/**
 * 
 */
package com.tmhs.tmhri.enrichedChem;

import java.io.IOException;
import java.util.Properties;

/**
 * @author ya
 *
 */
public class Props {
	static Properties prop = new Properties();
	
	static {
		try {
			prop.load(Props.class.getResourceAsStream("prop.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static String PLUGIN_VERSION = "version";
	
	/**
	 * @param str
	 * @return propertiy string
	 */
	public static String getProp(String str) {
		return prop.getProperty(str);
	}
}
