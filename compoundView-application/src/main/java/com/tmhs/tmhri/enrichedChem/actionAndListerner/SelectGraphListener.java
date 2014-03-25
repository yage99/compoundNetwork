/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.actionAndListerner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.RowSetRecord;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.core.NotEnrichedException;
import com.tmhs.tmhri.enrichedChem.func.search.SearchResultPanel;

/**
 * @author ya
 * 
 */
public class SelectGraphListener implements RowsSetListener {
	// private List<CyNode> lastSelect = new ArrayList<CyNode>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.model.events.RowsSetListener#handleEvent(org.cytoscape.
	 * model.events.RowsSetEvent)
	 */
	@Override
	public void handleEvent(RowsSetEvent e) {
		if (!e.containsColumn(CyNetwork.SELECTED))
			return;
		Collection<RowSetRecord> records = e
				.getColumnRecords(CyNetwork.SELECTED);
		try {
			EnrichedNetwork network = EnrichedChemPlugin.getCurrentNetwork();
			// List<CyNode> nodes = new ArrayList<CyNode>();
			// for (RowSetRecord row : select) {
			// nodes.add(network.getNode(row.getRow().get(CyNetwork.SUID,
			// Long.class)));
			// }
			CyNetworkView nv = network.getNetworkView();
			List<CyNode> nodeSelect = new ArrayList<CyNode>();
			// for (CyNode node : lastSelect) {
			// EnrichedNetwork.highLight(nv.getNodeView(node), false);
			// }
			// List<CyNode> records = new ArrayList<CyNode>();
			for (RowSetRecord record : records) {
				long id = record.getRow().get("SUID", Long.class);
				CyNode node = network.getNode(id);
				if (node != null) {
					if ((boolean) record.getValue())
						nodeSelect.add(node);
					View<CyNode> currentNodeView = nv.getNodeView(node);
					// TODO highlight node has bug
					EnrichedNetwork.highLight(currentNodeView,
							(boolean) record.getValue());
					// if ((boolean) record.getValue())
					// nodeSelect.add(node);
				}
			}
			// for (CyNode node : nodeSelect) {
			// EnrichedNetwork.highLight(nv.getNodeView(node), true);
			// }
			SearchResultPanel.setTable(nodeSelect, network);
			network.updateView();

			// lastSelect = nodeSelect;
		} catch (NotEnrichedException e1) {
			e1.printStackTrace();
		}

	}
}
