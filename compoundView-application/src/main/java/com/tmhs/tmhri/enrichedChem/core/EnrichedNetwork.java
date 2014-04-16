/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyEdge.Type;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.config.EnrichParams;
import com.tmhs.tmhri.enrichedChem.config.FileType;
import com.tmhs.tmhri.enrichedChem.core.process.DrawEdgesTask;
import com.tmhs.tmhri.enrichedChem.core.process.DrawNodesTask;
import com.tmhs.tmhri.enrichedChem.core.process.UpdateEdgeTask;
import com.tmhs.tmhri.enrichedChem.core.process.ViewRedrawTask;
import com.tmhs.tmhri.enrichedChem.search.SearchResultPanel;
import com.tmhs.tmhri.enrichedChem.search.SearchTask;
import com.tmhs.tmhri.enrichedChem.visual.MoreVisualLexicon;
import com.tmhs.tmhri.enrichedChem.visual.PieChartGraph;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author TMHYXZ6
 * 
 */
public class EnrichedNetwork {
	private CyNetwork cyNetwork;
	private CyNetworkView nv;
	Map<CyNode, List<PubChemDrug>> searchResult;
	private String filename;

	/**
	 * @param filename
	 * @param fileType
	 */
	public EnrichedNetwork(String filename, FileType fileType) {
		cyNetwork = EnrichedChemPlugin.createNetwork();
		this.filename = filename;
		// new CyNetworkView();

		init();
	}

	/**
	 * @param cyNetwork
	 * @param name
	 */
	public EnrichedNetwork(CyNetwork cyNetwork, String name) {
		this.cyNetwork = cyNetwork;
		nv = EnrichedChemPlugin.createNetworkView(cyNetwork, name);
		init();

	}

	/**
	 * @param nv
	 */
	public EnrichedNetwork(CyNetworkView nv) {
		this.nv = nv;
		this.cyNetwork = nv.getModel();

		init();
	}

	/**
	 * @param source
	 * @param target
	 * @param isDirected
	 * @param width 
	 * @return the new edge
	 */
	public synchronized CyEdge addEdge(CyNode source, CyNode target,
			boolean isDirected, Double width) {
		if (cyNetwork.containsEdge(source, target))
			cyNetwork.removeEdges(cyNetwork.getConnectingEdgeList(source,
					target, Type.UNDIRECTED));

		CyEdge edge =  cyNetwork.addEdge(source, target, isDirected);
		this.setAttribute(edge, EnrichParams.EDGE_WIDTH, width);
		return edge;
	}

	/**
	 * @param edge0
	 * @param set_remove
	 * 
	 * @deprecated recommend to remove all edges one time
	 */
	@Deprecated
	public synchronized void removeEdge(CyEdge edge0, boolean set_remove) {
		ArrayList<CyEdge> list = new ArrayList<CyEdge>();
		list.add(edge0);
		cyNetwork.removeEdges(list);
	}

	/**
	 * @param index
	 * @return node
	 */
	public CyNode getNode(long index) {
		return cyNetwork.getNode(index);
	}

	/**
	 * @return get the network view
	 */
	public CyNetworkView getNetworkView() {
		return nv;
	}

	/**
	 * @return system unique id
	 */
	public Long getId() {
		return nv.getSUID();
	}

	/**
	 * @param value
	 * @return update network minimum edge without recalculate values
	 */
	public boolean updateEdges(double value) {
		TaskIterator ti = new TaskIterator();
		ti.append(new UpdateEdgeTask(this, value));
		EnrichedChemPlugin.excuteTasks(ti);

		return true;
	}

	private void init() {

		registeListener(cyNetwork);
		new DrawEdgesTask(this);

		TaskIterator ti = new TaskIterator();
		ti.append(new DrawNodesTask(filename, this));
		ti.append(new SearchTask(this));
		ti.append(new DrawEdgesTask(this));
		EnrichedChemPlugin.excuteTasks(ti);

	}

	private void registeListener(CyNetwork cyNetwork) {
		// cyNetwork.addSelectEventListener(AttributeBrowserPlugin
		// .getAttributeBrowser(DataObjectType.NODES).getAttributeTable());
		// cyNetwork.addSelectEventListener(AttributeBrowserPlugin
		// .getAttributeBrowser(DataObjectType.EDGES).getAttributeTable());
		// cyNetwork.addSelectEventListener(new SelectGraphListener());

	}

	/**
	 * @return network
	 */
	public CyNetwork getNetwork() {
		return cyNetwork;
	}

	/**
	 * @param edge
	 * @deprecated useless
	 */
	public void hideEdge(CyEdge edge) {
		CyTable table = cyNetwork.getDefaultEdgeTable();
		table.getRow(edge.getSUID()).set(CyNetwork.HIDDEN_ATTRS, true);
		// CyEventHelper;
		if (nv != null)
			nv.updateView();
	}

	/**
	 * @param edge
	 * @deprecated useless
	 */
	public void restoreEdge(CyEdge edge) {
		CyTable table = cyNetwork.getDefaultEdgeTable();
		table.getRow(edge).set(CyNetwork.HIDDEN_ATTRS, false);

		if (nv != null)
			nv.updateView();
	}

	/**
	 * @param node
	 * @param param
	 * @param value
	 */
	public void setAttribute(CyNode node, EnrichParams param, Object value) {
		if (cyNetwork.getDefaultNodeTable().getColumn(param.getString()) == null) {
			CyTable table = cyNetwork.getDefaultNodeTable();
			if (param.getType().equals(List.class))
				table.createListColumn(param.getString(), param.getListType(),
						false);
			else
				table.createColumn(param.getString(), param.getType(), false);
		}
		CyRow row = cyNetwork.getRow(node);
		row.set(param.getString(), value);
	}

	/**
	 * @param edge1
	 * @param param
	 * @param value
	 */
	public void setAttribute(CyEdge edge1, EnrichParams param, Object value) {
		if (cyNetwork.getDefaultEdgeTable().getColumn(param.getString()) == null) {
			CyTable table = cyNetwork.getDefaultEdgeTable();
			if (param.getType().equals(List.class))
				table.createListColumn(param.getString(), param.getListType(),
						false);
			else
				table.createColumn(param.getString(), param.getType(), false);
		}
		CyRow row = cyNetwork.getRow(edge1);
		row.set(param.getString(), value);
	}

	/**
	 * @param node
	 * @param param
	 * @return attribute value
	 */
	public Object getAttrValue(CyNode node, EnrichParams param) {
		CyRow row = cyNetwork.getDefaultNodeTable().getRow(node.getSUID());
		return row.get(param.getString(), param.getType());
	}

	/**
	 * @param edge
	 * @param param
	 * @return attribute value
	 */
	public Object getAttrValue(CyEdge edge, EnrichParams param) {
		CyRow row = cyNetwork.getDefaultEdgeTable().getRow(edge.getSUID());
		return row.get(param.getString(), param.getType());
	}

	/**
	 * @return a list of selected nodes of this network
	 */
	public List<CyNode> getSelectedNodes() {
		List<CyNode> selected = new ArrayList<CyNode>();

		CyTable table = cyNetwork.getDefaultEdgeTable();
		Collection<CyRow> allRows = table.getMatchingRows(CyNetwork.SELECTED,
				"true");
		// table.getAllRows();
		for (CyRow row : allRows) {
			// if (row.get(CyNetwork.SELECTED, Boolean.class))
			selected.add(cyNetwork.getNode(row.get(CyIdentifiable.SUID,
					Long.class)));
		}

		// selected.addAll(allNodes);
		if (selected.size() == 0)
			selected = cyNetwork.getNodeList();
		return selected;
	}

	/**
	 * @param searchResult
	 */
	public void registSearchResult(Map<CyNode, List<PubChemDrug>> searchResult) {
		this.searchResult = searchResult;
		SearchResultPanel.setTable(getSelectedNodes(), this);

		for (CyNode node : searchResult.keySet()) {
			List<PubChemDrug> result = searchResult.get(node);
			int size = result.size();
			if (size == 0)
				continue;
			PubChemDrug drug = result.get(size - 1);
			SearchTask.setNodeDrug(this, node, drug);
		}
	}

	/**
	 * @param currentNodeView
	 * @param highLight
	 */
	public static void highLight(View<CyNode> currentNodeView, boolean highLight) {
		PieChartGraph graph = (PieChartGraph) currentNodeView
				.getVisualProperty(MoreVisualLexicon.NODE_CUSTOMGRAPHICS_1);
		graph.highLight(highLight);
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void drawEdges() {
		EnrichedChemPlugin.executeTask(new DrawEdgesTask(this));
	}

	/**
	 * @return searchResult
	 */
	public Map<CyNode, List<PubChemDrug>> getSearchResult() {
		return searchResult;
	}

	/**
	 * 
	 */
	public void createView() {
		nv = EnrichedChemPlugin.createNetworkView(cyNetwork, "clusters");
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void redrawView() {
		EnrichedChemPlugin.executeTask(new ViewRedrawTask(this));
	}

	/**
	 * 
	 */
	public void updateView() {
		nv.updateView();
	}

	/**
	 * @return node view currently been selected
	 */
	public List<CyNode> getSelectedNodeView() {
		List<CyNode> select = new ArrayList<CyNode>();

		CyTable table = cyNetwork.getDefaultNodeTable();
		Collection<CyRow> selectRow = table.getMatchingRows(CyNetwork.SELECTED,
				true);
		for (CyRow row : selectRow) {
			select.add(cyNetwork.getNode(row.get(CyNetwork.SUID, Long.class)));
		}
		return select;
	}
}
