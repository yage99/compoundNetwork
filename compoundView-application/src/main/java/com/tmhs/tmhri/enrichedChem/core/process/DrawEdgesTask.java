/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.process;

import java.util.ArrayList;
import java.util.List;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.TaskMonitor;
import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.database.DAO.CompoundSimilarityDAO;
import com.tmhs.database.DTO.CompoundSimilarity;
import com.tmhs.database.frame.DAOManager;
import com.tmhs.tmhri.enrichedChem.config.EnrichParams;
import com.tmhs.tmhri.enrichedChem.config.InputParams;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;
import com.tmhs.tmhri.enrichedChem.task.ThreadManager;
import com.tmhs.tmhri.enrichedChem.task.ThreadRunner;
import com.tmhs.tmhri.enrichedChem.ui.LocalNoticeSys;
import com.tmhs.yage.api.NIH.PubChemCompound;

/**
 * @author ya
 * 
 */
public class DrawEdgesTask extends EnAbstractTask {
	/**
	 * 
	 */
	public static int mutix = 10;
	private EnrichedNetwork network;
	private TaskMonitor taskMonitor;
	/**
	 * @param network
	 */
	public DrawEdgesTask(EnrichedNetwork network) {
		this.network = network;
	}

	/**
	 * @param selected
	 *            selected node id list
	 */
	private void refreshEdges(List<CyNode> selected) {
		NoticeSystem.getInstance().info("start add edges");
		List<CyNode> allnodes = network.getNetwork().getNodeList();

		List<Position> taskPool = new ArrayList<Position>();
		for (CyNode i : selected) {
			allnodes.remove(i);
			for (CyNode j : allnodes) {
				Position p = new Position();
				p.i = i;
				p.j = j;
				taskPool.add(p);
			}
		}

		NoticeSystem.getInstance().debug("multi thread draw edges start!");
		new DrawEdgeTask(taskPool, network).run(taskMonitor);

		this.insertTasksAfterCurrentTask(new UpdateEdgeTask(network,
				InputParams.minEdge));
		// new DrawEdgeTask(taskPool, this).run();
	}

	/**
	 * 
	 */
	void addEdgeByPPI() {

		List<CyNode> select = network.getSelectedNodes();
		if (select == null || select.isEmpty()) {
			List<CyNode> allNodes = network.getNetwork().getNodeList();
			refreshEdges(allNodes);
		} else {
			refreshEdges(select);
		}
	}

	// void applyLayout() {
	// LayoutAlgorithmManager.applyLayout(nv);
	// nv.updateView();
	// ViewStyleManager.applyVisualStyle(nv);
	// // BioLayoutFRAlgorithm layout = (BioLayoutFRAlgorithm) CyLayouts
	// // .getLayout("fruchterman-rheingold");
	// // // nv.applyLayout(layout);
	// // // Create Task
	// // LayoutTask task = new LayoutTask(layout, nv);
	// //
	// // // Execute Task in New Thread; pop open JTask Dialog Box.
	// // TaskManager.executeTask(task, new TaskUIConfig());
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.work.AbstractTask#run(org.cytoscape.work.TaskMonitor)
	 */
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		this.taskMonitor = taskMonitor;
		this.addEdgeByPPI();
	}
}

class Position {
	public CyNode i;
	public CyNode j;
}

class DrawEdgeTask {
	private List<Position> tasksPool;
	private EnrichedNetwork network;
	private ThreadManager tm;

	public DrawEdgeTask(List<Position> tasksPool, EnrichedNetwork network) {
		this.tasksPool = tasksPool;
		this.network = network;
	}

	public void run(TaskMonitor taskMonitor) {
		tm = new ThreadManager();

		for (Position pos : tasksPool) {
			tm.addWork(new DrawEdgeRunner(pos.i, pos.j, network));
		}
		tm.startWork(taskMonitor);
		try {
			tm.waitForFinish();
		} catch (Exception e) {
			NoticeSystem.getInstance().err(e.getLocalizedMessage());
		}
	}
}

class DrawEdgeRunner extends ThreadRunner {

	private CyNode i;
	private CyNode j;
	private EnrichedNetwork network;
	private double ppi;

	public DrawEdgeRunner(CyNode i, CyNode j, EnrichedNetwork network) {
		this.i = i;
		this.j = j;
		this.network = network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		final CyNode node = i;
		final CyNode node1 = j;

		if (node == null || node1 == null) {
			this.setError();
			return;
		}

		String smile1 = (String) network.getAttrValue(node, EnrichParams.SMILE);
		String smile2 = (String) network
				.getAttrValue(node1, EnrichParams.SMILE);
		if (smile1 == null || smile2 == null)
			ppi = 0;
		else {
			try {
				ppi = getPPI(smile1, smile2) * DrawEdgesTask.mutix;
				// ppi = -ppi;
			} catch (Exception e1) {
				LocalNoticeSys.getInstance().err(
						"smiles: " + smile1 + "/" + smile2 + " calc error");
				e1.printStackTrace();
			}
		}

		network.addEdge(node, node1, false, ppi);

		// EdgeManager.updateEdgeView(nv, edge1);
	}

	/**
	 * @param smile1
	 * @param smile2
	 * @return a numeric similarity between two compounds
	 * @throws Exception
	 */
	private double getPPI(String smile1, String smile2) throws Exception {
		PubChemCompound comp = new PubChemCompound();
		if (InputParams.database_stream) {
			/*
			 * here is the stream model. values will first be find from
			 * database, otherwise calculated and insert into database
			 */
			CompoundSimilarity pair = new CompoundSimilarity(smile1, smile2);
			Double value;
			try {
				value = DAOManager.getManager()
						.getDAO(CompoundSimilarityDAO.class).getValue(pair);

				if (value.isNaN()) {
					value = comp.getCompoundSimilarity(smile1, smile2);
					pair.setValue(value);
					DAOManager.getManager().getDAO(CompoundSimilarityDAO.class)
							.insert(pair);
				}
				return pair.getValue();
			} catch (Exception e) {
				InputParams.database_stream = false;
				try {
					return comp.getCompoundSimilarity(smile1, smile2);
				} catch (Exception e1) {
					throw e1;
				}
			}
		}
		/*
		 * this is the non-stream model. Calculate directly
		 */
		return comp.getCompoundSimilarity(smile1, smile2);
	}

	/**
	 * @return the ppi
	 */
	public double getPPI() {
		return ppi;
	}

}
