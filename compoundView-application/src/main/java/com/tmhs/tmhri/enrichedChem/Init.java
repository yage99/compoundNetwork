/**
 * 
 */
package com.tmhs.tmhri.enrichedChem;

import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.database.frame.DAOManager;
import com.tmhs.tmhri.enrichedChem.ui.LocalNoticeSys;

/**
 * @author ya
 * 
 */
public class Init {
	/**
	 * 
	 */
	public static void inti() {
		NoticeSystem.plugSystem(new LocalNoticeSys());
		DAOManager.init("com.tmhs.database.mongodb.DAOImpl", "drugPlate");
	}
}
