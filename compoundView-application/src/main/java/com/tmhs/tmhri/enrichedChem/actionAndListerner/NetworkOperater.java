/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.actionAndListerner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.RowSetRecord;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;
import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.search.SearchResultPanel;

/**
 * @author TMHYXZ6
 * 
 */
public class NetworkOperater implements RowsSetListener {
	/*
	 * show the selected nodes on search panel
	 * 
	 * @see
	 * org.cytoscape.model.events.RowsSetListener#handleEvent(org.cytoscape.
	 * model.events.RowsSetEvent)
	 */
	@Override
	public void handleEvent(RowsSetEvent e) {
		if (e.containsColumn(CyNetwork.SELECTED)) {
			Collection<RowSetRecord> records = e
					.getColumnRecords(CyNetwork.SELECTED);

			EnrichedNetwork network;
			try {
				network = EnrichedChemPlugin.getCurrentNetwork();
				List<CyNode> selectNodes = new ArrayList<CyNode>();
				for (RowSetRecord record : records) {
					if ((Boolean) record.getValue()) {
						Long nodeId = record.getRow().get(CyIdentifiable.SUID,
								Long.class);
						CyNode node = network.getNetwork().getNode(nodeId);
						selectNodes.add(node);
					}
				}
				SearchResultPanel.setTable(selectNodes, network);
			} catch (Exception e1) {
				e1.printStackTrace();
				NoticeSystem.getInstance().warning(e1.getLocalizedMessage());
			}
		}

		/*
		 * if (event.getTargetType() == SelectEvent.SINGLE_NODE) { CyNode node =
		 * (CyNode) event.getTarget(); List<PubChemDrug> drugs =
		 * EnrichedChemPlugin.getSearchResult(
		 * cyNetwork).get(node.getRootGraphIndex()); if (drugs != null &&
		 * !drugs.isEmpty()) { SearchResultPanel.setImg(drugs.get(drugs.size() -
		 * 1)); } }
		 */
	}

}
