/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.visual;

import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;

/**
 * @author TMHYXZ6
 * 
 */
public class LayoutAlgorithmManager {

	/**
	 * 
	 */
	public static CyLayoutAlgorithmManager layoutManager;

	private static CyLayoutAlgorithm layout;

	/**
	 * @param nv
	 */
	public static void applyLayout(CyNetworkView nv) {
		
		EnrichedChemPlugin.excuteTasks(layout.createTaskIterator(nv,
				layout.createLayoutContext(), CyLayoutAlgorithm.ALL_NODE_VIEWS, null));
	}
	
	/**
	 * @return default algorithm
	 */
	public static CyLayoutAlgorithm getDefaultAlgorithm() {
		if (layout == null)
			layout = layoutManager.getLayout("force-directed");
		
		return layout;
	}
}
