package com.tmhs.yage.api.NIH.xml;

import java.io.InputStream;

/**
 * @author TMHYXZ6
 * 
 */
public class ResourceManager {
	/**
	 * @param name
	 * @return stream
	 * 
	 */
	public static InputStream getXML(String name) {
		return Class.class.getResourceAsStream("/com/tmhs/yage/api/NIH/xml/"
				+ name);
	}
}
