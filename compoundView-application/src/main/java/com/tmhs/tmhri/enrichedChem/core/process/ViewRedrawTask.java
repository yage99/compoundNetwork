/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.process;

import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;
import com.tmhs.tmhri.enrichedChem.visual.LayoutAlgorithmManager;
import com.tmhs.tmhri.enrichedChem.visual.ViewStyleManager;

/**
 * @author ya
 *
 */
public class ViewRedrawTask extends EnAbstractTask {
	private EnrichedNetwork network;
	private CyNetworkView nv;
	
	/**
	 * @param network
	 */
	public ViewRedrawTask(EnrichedNetwork network) {
		this.network = network;
	}

	/* (non-Javadoc)
	 * @see org.cytoscape.work.AbstractTask#run(org.cytoscape.work.TaskMonitor)
	 */
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if(network.getNetworkView() == null) network.createView();
		this.nv = network.getNetworkView();
		addChartToNodes();

		EnrichedChemPlugin.registNetwork(network);
	}
	
	private boolean addChartToNodes() {
		// List<CyNode> allNodes = cyNetwork.getNodeList();

		ViewStyleManager.applyVisualStyle(nv);
		
		CyLayoutAlgorithm layout = LayoutAlgorithmManager.getDefaultAlgorithm();
		this.insertTasksAfterCurrentTask(layout.createTaskIterator(nv,
				layout.createLayoutContext(), CyLayoutAlgorithm.ALL_NODE_VIEWS, null));
		/**
		 * for (CyNode node : allNodes) {
		 * //PieChartGraph.highLight(nv.getNodeView(node));
		 * 
		 * //nv.applyVizMap(node); }
		 **/
		nv.updateView();
		return true;
	}


}
