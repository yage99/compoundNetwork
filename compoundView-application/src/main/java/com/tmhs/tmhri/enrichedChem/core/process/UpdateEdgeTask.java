/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.TaskMonitor;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.core.defines.EnrichParams;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;

/**
 * @author ya
 * 
 */
public class UpdateEdgeTask extends EnAbstractTask {
	private EnrichedNetwork network;
	private Double minEdge;
	private static Map<CyEdge, Double> filtered = new HashMap<CyEdge, Double>();

	/**
	 * @param network
	 * @param minEdge
	 */
	public UpdateEdgeTask(EnrichedNetwork network, Double minEdge) {
		this.network = network;
		this.minEdge = minEdge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.work.AbstractTask#run(org.cytoscape.work.TaskMonitor)
	 */
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		List<CyEdge> hidden = new ArrayList<CyEdge>();

		hidden.addAll(filtered.keySet());
		List<CyEdge> edges = network.getNetwork().getEdgeList();
		int process_num = hidden.size() + edges.size();
		int num = 0;
		// process all hidden edges
		for (CyEdge edge : hidden) {
			// if this edge width beyond minimum width, add back to network
			Double edgeWidth = (Double) filtered.get(edge);
			if (edgeWidth != null && edgeWidth >= minEdge) {
				// add this edge to network
				network.addEdge(edge.getSource(), edge.getTarget(),
						edge.isDirected(), edgeWidth);
				filtered.remove(edge);
			}
			taskMonitor.setProgress(++num / process_num);
		}
		Map<CyEdge, Double> tobeHid = new HashMap<CyEdge, Double>();
		for (CyEdge edge : edges) {
			Double edgeVal = (Double) network.getAttrValue(edge,
					EnrichParams.EDGE_WIDTH);
			if (edgeVal == null || edgeVal < minEdge) {
				tobeHid.put(edge, (Double) network.getAttrValue(edge,
						EnrichParams.EDGE_WIDTH));
			}
			taskMonitor.setProgress(++num / process_num);
			// test.equals(test);
		}
		network.getNetwork().removeEdges(tobeHid.keySet());
		filtered.putAll(tobeHid);

		this.insertTasksAfterCurrentTask(new ViewRedrawTask(network));
	}
}