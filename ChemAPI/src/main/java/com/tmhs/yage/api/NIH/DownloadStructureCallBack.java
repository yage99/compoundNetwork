/**
 * 
 */
package com.tmhs.yage.api.NIH;

import java.util.Map;

/**
 * @author ya
 * 
 */
public interface DownloadStructureCallBack {
	/**
	 * @param data
	 */
	public void finish(Map<String, String> data);
}
