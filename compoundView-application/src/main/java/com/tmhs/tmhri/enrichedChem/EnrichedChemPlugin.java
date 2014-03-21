/**
 * 
 */
package com.tmhs.tmhri.enrichedChem;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.TaskIterator;
import org.osgi.framework.BundleContext;

import com.tmhs.tmhri.enrichedChem.actionAndListerner.MenuAction;
import com.tmhs.tmhri.enrichedChem.actionAndListerner.SelectGraphListener;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.core.NotEnrichedException;
import com.tmhs.tmhri.enrichedChem.core.visual.LayoutAlgorithmManager;
import com.tmhs.tmhri.enrichedChem.core.visual.PieChartFactory;
import com.tmhs.tmhri.enrichedChem.core.visual.ViewStyleManager;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;
import com.tmhs.tmhri.enrichedChem.task.ThreadManager;

/**
 * @author TMHYXZ6
 * 
 */
public class EnrichedChemPlugin extends AbstractCyActivator {
	private static ThreadManager handler;
	private static Map<Long, EnrichedNetwork> registerMap = new HashMap<Long, EnrichedNetwork>();

	private static CyNetworkViewFactory networkViewFactory;
	private static CyNetworkViewManager networkViewManager;
	private static CyApplicationManager cyApplicationManagerServiceRef;

	/**
	 * 
	 */
	public static boolean isInit = false;
	private static CyAppAdapter appAdapter;
	private static RenderingEngineManager renderEngine;

	/**
	 * @return appAdapter
	 */
	public static CyAppAdapter getSwingAdapter() {
		return appAdapter;
	}

	/**
	 * @param task
	 * @deprecated don't use it since 2.2.0
	 */
	public static void executeTask(EnAbstractTask task) {
		excuteTasks(new TaskIterator(task));
//		while(!task.isFinished()) {
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * @param ti
	 */
	public static void excuteTasks(TaskIterator ti) {
		appAdapter.getTaskManager().execute(ti);
	}

	private static CyNetworkManager cyNetworkManager;
	private static CyNetworkNaming cyNetworkNaming;
	private static CyNetworkFactory cyNetworkFactory;

	private void extractMongo() {
		// XXX to be continued
	}

	/**
	 * @param enrichedNetwork
	 * @return result
//	 */
//	public static Map<CyNode, List<PubChemDrug>> getSearchResult(
//			EnrichedNetwork enrichedNetwork) {
//		return searchResultList.get(enrichedNetwork.getNetwork().getSUID());
//	}

	/**
	 * @param network
	 */
	public static void registNetwork(EnrichedNetwork network) {
		registerMap.put(network.getNetworkView().getSUID(), network);
		network.getNetworkView().setVisualProperty(
				BasicVisualLexicon.NETWORK_BACKGROUND_PAINT,
				new Color(230, 230, 230));
		network.getNetworkView().updateView();
	}

	/**
	 * @return current Handler
	 */
	public static ThreadManager getHandler() {
		return handler;
	}

	/**
	 * @return a new network
	 */
	public static CyNetwork createNetwork() {
		CyNetwork network = cyNetworkFactory.createNetwork();
		network.getRow(network).set(CyNetwork.NAME,
			      cyNetworkNaming.getSuggestedNetworkTitle("cluster"));
		cyNetworkManager.addNetwork(network);
		// edgeManagers.put(network.getIdentifier(), new EdgeManager());

		return network;
	}

	/**
	 * @param network
	 * @param title
	 * @return create Network View for a network
	 * 
	 */
	public static CyNetworkView createNetworkView(CyNetwork network,
			String title) {
		CyNetworkView nv = networkViewFactory.createNetworkView(network);
		networkViewManager.addNetworkView(nv);

		// edgeManagers.get(network.getIdentifier()).setNetworkView(nv);

		return nv;
	}

	/**
	 * @param network
	 * @return a list of selected node root graph id. If nothing selected,
	 *         return all nodes' id
	 */
	public static List<CyNode> getSelectedNodes(EnrichedNetwork network) {

		List<CyNode> selected = new ArrayList<CyNode>();

		CyTable table = network.getNetwork().getDefaultEdgeTable();
		Collection<CyRow> allNodes = table.getMatchingRows(CyNetwork.SELECTED,
				true);
		// table.getAllRows();
		for (CyRow row : allNodes) {
			// if (row.get(CyNetwork.SELECTED, Boolean.class))
			selected.add(network.getNode(row.get(CyIdentifiable.SUID, Long.class)));
		}

		// selected.addAll(allNodes);
		return selected;

		/*
		 * Set<?> select = nv.getModel().getSelectedNodes(); if (select == null
		 * || select.isEmpty()) { Long[] ids =
		 * nv.getNetwork().getNodeIndicesArray(); List<CyNode> indc = new
		 * ArrayList<CyNode>(); for (int i = 0; i < ids.length; i++) {
		 * indc.add(ids[i]); } return indc; } else { Object[] nodes =
		 * select.toArray(); List<CyNode> ids = new ArrayList<CyNode>(); for
		 * (int i = 0; i < nodes.length; i++) { ids.add(((CyNode)
		 * nodes[i]).getSUID()); }
		 * 
		 * return ids; }
		 */
	}

	/**
	 * 
	 * @return current EnrichedNetwork
	 * @throws NotEnrichedException
	 * 
	 */
	public static EnrichedNetwork getCurrentNetwork()
			throws NotEnrichedException {
		CyNetworkView currentNv = cyApplicationManagerServiceRef
				.getCurrentNetworkView();
		if (registerMap.containsKey(currentNv.getSUID()))
			return registerMap.get(currentNv.getSUID());
		throw new NotEnrichedException("not an Enriched Network");
	}

	/**
	 * @param nv
	 * @param force
	 * @return if inited
	 */
	public static boolean initNetwork(CyNetworkView nv, boolean force) {
		if (registerMap.containsKey(nv.getSUID()))
			return false;

		new EnrichedNetwork(nv);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext bc) throws Exception {
		cyApplicationManagerServiceRef = getService(bc,
				CyApplicationManager.class);
		networkViewFactory = getService(bc, CyNetworkViewFactory.class);
		networkViewManager = getService(bc, CyNetworkViewManager.class);
		renderEngine = getService(bc, RenderingEngineManager.class);
		appAdapter = getService(bc, CyAppAdapter.class);
		getService(bc, CySwingAppAdapter.class);

		cyNetworkManager = getService(bc,CyNetworkManager.class);
		cyNetworkNaming = getService(bc,CyNetworkNaming.class);
		cyNetworkFactory = getService(bc,CyNetworkFactory.class);
		
		ViewStyleManager.visualMapManager = getService(bc,
				VisualMappingManager.class);
		ViewStyleManager.vmfFactoryP = getService(bc,
				VisualMappingFunctionFactory.class,
				"(mapping.type=passthrough)");
		ViewStyleManager.vmfFactoryC = getService(bc,
				VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		ViewStyleManager.vmfFactoryD = getService(bc,
				VisualMappingFunctionFactory.class, "(mapping.type=discrete)");

		LayoutAlgorithmManager.layoutManager = getService(bc,
				CyLayoutAlgorithmManager.class);

		extractMongo();
		Init.inti();

		handler = new ThreadManager(1);
		handler.startWork(null);

		ControlPanel controlPanel = new ControlPanel();
		registerService(bc, controlPanel, CytoPanelComponent.class,
				new Properties());
		SearchResultPanel searchPanel = new SearchResultPanel();
		registerService(bc, searchPanel, CytoPanelComponent.class,
				new Properties());
		registerService(bc,
				new MenuAction(getService(bc, CySwingApplication.class),
						null, searchPanel), CyAction.class,
				new Properties());
		registerService(bc, new SelectGraphListener(), RowsSetListener.class,
				new Properties());

		registerService(bc, new PieChartFactory(),
				CyCustomGraphicsFactory.class, new Properties());
	}

	/**
	 * @return desktop
	 */
	public static Component getDesktop() {
		return null;
	}

	/**
	 * @return visual lexicon
	 */
	public static VisualLexicon getVisualLexicon() {
		return renderEngine.getDefaultVisualLexicon();
	}
}
