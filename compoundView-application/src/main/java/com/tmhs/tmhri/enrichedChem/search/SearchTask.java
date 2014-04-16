/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.TaskMonitor;
import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.database.main.PubChemWithStream;
import com.tmhs.tmhri.enrichedChem.config.EnrichParams;
import com.tmhs.tmhri.enrichedChem.config.InputParams;
import com.tmhs.tmhri.enrichedChem.config.InputParams.Params;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.fileparsers.CompoundLibReader;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;
import com.tmhs.tmhri.enrichedChem.task.ThreadManager;
import com.tmhs.tmhri.enrichedChem.task.ThreadRunner;
import com.tmhs.tmhri.enrichedChem.ui.ControlPanel;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author ya
 * 
 */
public class SearchTask extends EnAbstractTask {
	EnrichedNetwork network;
	Map<String, Long> cid2Id = new HashMap<String, Long>();
	List<String> cids = new ArrayList<String>();

	/**
	 * 
	 */
	public Map<CyNode, List<PubChemDrug>> searchResults = new HashMap<CyNode, List<PubChemDrug>>();
	private TaskMonitor taskMonitor;

	/**
	 * @param network
	 */
	public SearchTask(EnrichedNetwork network) {
		this.network = network;
	}

	/**
	 * @param param
	 * @throws Exception
	 * 
	 */
	public void processSmilesData(Params param) throws Exception {
		NoticeSystem.getInstance().info("adding smiles data...");
		List<CyNode> nodes = network.getNetwork().getNodeList();
		// process originId to cid
		if (param == Params.PUBCHEM_IMMIDIATE) {
			/*
			 * index is a cid
			 */
			for (CyNode node : nodes) {
				CyTable cyTable = network.getNetwork().getDefaultEdgeTable();
				String cid = (String) cyTable.getRow(node).get(
						EnrichParams.ORIGIN_ID.getString(),
						EnrichParams.ORIGIN_ID.getType());
				if (cid == null || cid.equals("")) {
					NoticeSystem.getInstance().err(node + "cid is null");
					continue;
				}
				cids.add(cid);
				network.setAttribute(node, EnrichParams.CID, cid);
				cid2Id.put(cid, node.getSUID());
			}
		} else if (param == Params.COMPOUND_COVER) {
			/*
			 * index needs to be cast to cid
			 */
			Map<String, String> coverMap = CompoundLibReader
					.read(ControlPanel.smileSourceFile.getText());
			for (CyNode node : nodes) {
				String index = (String) network
						.getNetwork()
						.getDefaultEdgeTable()
						.getRow(node)
						.get(EnrichParams.ORIGIN_ID.getString(),
								EnrichParams.ORIGIN_ID.getType());
				if (index == null || index.equals("")) {
					NoticeSystem.getInstance().err(node + " cid is null");
					continue;
				}
				String cid = coverMap.get(node.getSUID());
				if (cid == null || cid.equals("")) {
					NoticeSystem.getInstance().err(node + " cid is null");
					continue;
				}
				cids.add(cid);
				network.setAttribute(node, EnrichParams.CID, cid);
				cid2Id.put(cid, node.getSUID());
			}
			/**
			 * this section mainly used in a special environment. now be removed
			 */
			// } else if (param == Params.LOCALDATABASE_CID_COVER) {
			// try {
			// for (CyNode node : nodes) {
			// String index = (String) network
			// .getNetwork()
			// .getDefaultEdgeTable()
			// .getRow(node)
			// .get(EnrichParams.ORIGIN_ID.getString(),
			// EnrichParams.ORIGIN_ID.getType());
			// if (index == null || index.equals("")) {
			// NoticeSystem.getInstance().err(node + " cid is null");
			// continue;
			// }
			//
			// DrugDAO drugDao = DAOManager.getManager().getDAO(
			// DrugDAO.class);
			// Drug drug = drugDao.getDrugByIndex(index);
			// if (drug == null || drug.getCid() == null) {
			// NoticeSystem.getInstance().err(
			// "drug: " + index
			// + " not found from local database");
			// continue;
			// }
			// String cid = drug.getCid();
			//
			// if (cid == null || cid.equals("")) {
			// NoticeSystem.getInstance().err(node + " cid is null");
			// continue;
			// }
			// cids.add(cid);
			// network.setAttribute(node, EnrichParams.SHOW_NAME,
			// drug.getName());
			// network.setAttribute(node, EnrichParams.CID, cid);
			// cid2Id.put(cid, node.getSUID());
			// }
			// } catch (Exception e) {
			// NoticeSystem.getInstance().err(e.getMessage());
			// NoticeSystem
			// .getInstance()
			// .err("Can't connect to local database. Make sure you have one.");
			// return;
			// }
		} else if (param == Params.COVER_BY_NAME) {
			coverByName();
			return;
		}

		// CidManager.process(cid2Id);

	}

	/**
	 * @param cyNetwork
	 */
	void coverByName() {
		searchResults = new HashMap<CyNode, List<PubChemDrug>>();

		// ControlPanel.progressBar.setIndeterminate(false);
		final List<CyNode> nodes = network.getSelectedNodes();// cyNetwork.getNodeIndicesArray();

		new SearchByNameTask(nodes, network, searchResults).run(taskMonitor);

	}

	/**
	 * @param network
	 * @param node
	 * @param drug
	 */
	public static void setNodeDrug(EnrichedNetwork network, CyNode node,
			PubChemDrug drug) {
		network.setAttribute(node, EnrichParams.CID, drug.getCid());
		network.setAttribute(node, EnrichParams.SEARCH_RESULT, drug.getSynos()
				.toString());
		network.setAttribute(node, EnrichParams.SMILE, drug.getSmiles());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.work.AbstractTask#run(org.cytoscape.work.TaskMonitor)
	 */
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		this.taskMonitor = taskMonitor;
		this.processSmilesData(InputParams.idCover);
	}
}

class CompoundSearch extends ThreadRunner {
	String name;
	List<PubChemDrug> comps;
	CyNode target;

	CompoundSearch(String name, CyNode target) {
		this.name = name;
		this.target = target;
	}

	public List<PubChemDrug> getComps() {
		return comps;
	}

	public CyNode getTargetNode() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			comps = PubChemWithStream.searchCompound(name);
		} catch (Exception e) {
			this.setError();
			NoticeSystem.getInstance().warning(e.getMessage());
		}
	}
}

class SearchByNameTask {

	List<CyNode> nodes;
	EnrichedNetwork network;
	Map<CyNode, List<PubChemDrug>> searchResults;

	public SearchByNameTask(List<CyNode> nodes, EnrichedNetwork network,
			Map<CyNode, List<PubChemDrug>> searchResults) {
		this.nodes = nodes;
		this.network = network;
		this.searchResults = searchResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run(TaskMonitor taskMonitor) {

		ThreadManager tm = new ThreadManager();
		for (CyNode node : nodes) {
			String name = (String) network.getAttrValue(node,
					EnrichParams.COMP_NAME);
			if (name == null || name.equals("")) {
				NoticeSystem.getInstance().err(node + " name is null");
				continue;
			}
			tm.addWork(new CompoundSearch(name, node));
		}
		tm.startWork(taskMonitor);
		List<ThreadRunner> results;
		try {
			results = tm.waitForFinish();

			for (Runnable ash : results) {
				CompoundSearch compsearch = (CompoundSearch) ash;
				List<PubChemDrug> comps = compsearch.getComps();
				if (comps == null || comps.size() == 0) {
					NoticeSystem.getInstance().warning(
							"can't find compound in pubchem: "
									+ compsearch.name);
				}

				searchResults.put(compsearch.getTargetNode()/*
															 * cyNetwork.getNode(
															 * id
															 * ).getIdentifier()
															 */, comps);
			}

			network.registSearchResult(searchResults);
			// EnrichedChemPlugin.registeSearchResult(network, searchResults);

			// CidManager.processResult(
			// EnrichedChemPlugin.getSearchResult(network), network);
		} catch (Exception e) {
			NoticeSystem.getInstance().err(e.getLocalizedMessage());
		}

	}

}