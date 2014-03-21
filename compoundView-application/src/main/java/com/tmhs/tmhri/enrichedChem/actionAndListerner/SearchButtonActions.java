/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.actionAndListerner;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.core.defines.InputParams;
import com.tmhs.tmhri.enrichedChem.core.process.SearchTask;

/**
 * @author TMHYXZ6
 * 
 */
public class SearchButtonActions extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new SearchTask(EnrichedChemPlugin
							.getCurrentNetwork())
							.processSmilesData(InputParams.idCover);
				} catch (Exception e1) {
					NoticeSystem.getInstance().err(e1.getMessage());
				}
			}
		}).start();
	}
}