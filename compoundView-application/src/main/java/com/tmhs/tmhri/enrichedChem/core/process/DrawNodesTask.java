/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.process;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.TaskMonitor;
import org.tmhs.tool.yage.Info.NoticeSystem;
import org.tmhs.tool.yage.datasets.Table;
import org.tmhs.yage.api.fileFormat.CDTFile;
import org.tmhs.yage.api.fileFormat.ClusterTreeNode;
import org.tmhs.yage.api.fileFormat.PermutedCMAPResult;

import com.tmhs.tmhri.enrichedChem.EnrichConfig;
import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.config.EnrichParams;
import com.tmhs.tmhri.enrichedChem.config.FileType;
import com.tmhs.tmhri.enrichedChem.config.InputParams;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.fileparsers.CMAPFile;
import com.tmhs.tmhri.enrichedChem.task.EnAbstractTask;
import com.tmhs.tmhri.enrichedChem.ui.ControlPanel;

/**
 * @author TMHYXZ6
 * 
 */
public class DrawNodesTask extends EnAbstractTask {
	/**
	 * 
	 */
	private Table<Double> nodeData;
	/**
	 * 
	 */
	public EnrichedNetwork network = null;
	TaskMonitor monitor;
	private String filename;

	/**
	 * @param filename
	 * @param network
	 */
	public DrawNodesTask(String filename, EnrichedNetwork network) {
		this.filename = filename;
		this.network = network;
	}

	/**
	 * @throws IllegalArgumentException
	 */
	@Override
	public void run(TaskMonitor taskMonitor) {
		this.monitor = taskMonitor;
		try {
			List<ClusterTreeNode> clusters = null;
			if (InputParams.sourceFile_type == FileType.CDT) {
				// get cdt file data
				NoticeSystem.getInstance().info("reading cdt file...");
				CDTFile cdtFile = new CDTFile(new File(filename));
				nodeData = cdtFile.getData();
				ClusterTreeNode left = cdtFile.getLeftTree();
				if (left == null) {
					left = cdtFile.getUpperTree();
					if (left == null)
						return;
				}

				clusters = new ArrayList<ClusterTreeNode>();
				left.filteCluster(0.3, clusters);
			} else if (InputParams.sourceFile_type == FileType.CLU) {
				NoticeSystem.getInstance().info("reading cluster file");

				// ClusterFile clusterFile = new ClusterFile(new
				// File(filename));
				//
				// clusters = clusterFile.getClusters();
				// nodeData = clusterFile.getData();

				NoticeSystem.getInstance().warning(
						"this function is not usable");

			} else if (InputParams.sourceFile_type == FileType.CMAP_RESU) {
				NoticeSystem.getInstance().info("reading cmap result");
				PermutedCMAPResult table = PermutedCMAPResult
						.readSimpleTable(filename);
				clusters = new ArrayList<ClusterTreeNode>();
				nodeData = new Table<Double>();
				nodeData.setLength(1);
				nodeData.addColumTag(filename);

				int num = table.getHeight();
				for (int i = 0; i < num; i++) {
					List<String> line = table.getLine(i);
					ClusterTreeNode node = new ClusterTreeNode();
					node.setClusterName(line.get(PermutedCMAPResult.CMAPNAME));
					node.setName(line.get(PermutedCMAPResult.CMAPNAME).split(
							" - ")[0]);
					node.setLineNumber(i);
					clusters.add(node);

					nodeData.addValue(Double.parseDouble(line
							.get(PermutedCMAPResult.ENRICHMENT)));
					nodeData.addLineTag("");
				}
				// return;
			} else if (InputParams.sourceFile_type == FileType.CMAP_DETAIL_RESU) {
				CMAPFile cmapParser = new CMAPFile(new File(filename));

				clusters = cmapParser.getClusters();
				nodeData = cmapParser.getTable();
			}

			NoticeSystem.getInstance().info("drawing all nodes...");
			// draw parent node
			for (ClusterTreeNode node : clusters) {
				CyNode newNode = addNode(network, node);
				// node.getClusterName()

				network.setAttribute(newNode, EnrichParams.CLUSTER,
						node.getClusterName());

				// add to network
				// cyNetwork.addNode(newNode); -- did not add to network

				// draw parents children
				// draw pie chat and attach parameters at the same time
				if (node.left != null || node.right != null)
					drawChildren(node, network, newNode);
			}

			NoticeSystem.getInstance().info("showing network...");
		
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(EnrichedChemPlugin.getDesktop(),
					e1.getMessage(), "File read error",
					JOptionPane.ERROR_MESSAGE);
			ControlPanel.filename.setForeground(Color.RED);
		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(EnrichedChemPlugin.getDesktop(),
					e1.getMessage(), "IllegalArgument",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(EnrichedChemPlugin.getDesktop(),
					e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

		setFinish();
	}

	private void drawChildren(ClusterTreeNode node, EnrichedNetwork network,
			CyNode root) throws MalformedURLException {
		// if this is a leaf node, then add it to the network
		if (node.left == null && node.right == null) {

			// create a new node
			CyNode newNode = addNode(network, node);// Cytoscape.getCyNode(node.getClusterName(),
			// true);
			network.setAttribute(newNode, EnrichParams.CLUSTER,
					network.getAttrValue(root, EnrichParams.CLUSTER));
			network.setAttribute(newNode, EnrichParams.PARENT, root.getSUID());

			return;
		}

		if (node.left != null)
			drawChildren(node.left, network, root);
		if (node.right != null)
			drawChildren(node.right, network, root);
	}

	protected CyNode addNode(EnrichedNetwork network, ClusterTreeNode node) {
		CyNode newNode = network.getNetwork().addNode();// Cytoscape.getCyNode(node.getClusterName(),
		// true);

		// add all parameters
		CyTable table = network.getNetwork().getDefaultNodeTable();
		CyRow row = table.getRow(newNode.getSUID());
		List<String> keys = nodeData.getColumTag();
		List<Double> values = nodeData.getLine(node.getLineNumber());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Double value = values.get(i);
			if (table.getColumn(key) == null)
				table.createColumn(key, Double.class, false);
			row.set(key, value);
		}
		String keyString = EnrichConfig.pieChartPerfix + ":" + keys.get(1)
				+ "|" + keys.get(2);
		network.setAttribute(newNode, EnrichParams.IMG_KEY, keyString);
		network.setAttribute(newNode, EnrichParams.SIZE,
				100 * Math.abs(nodeData.getLine(node.getLineNumber()).get(0)));
		network.setAttribute(newNode, EnrichParams.ORIGIN_ID,
				nodeData.getLineTag(node.getLineNumber()));
		if (node.getName() != null)
			network.setAttribute(newNode, EnrichParams.COMP_NAME,
					node.getName());
		return newNode;
	}

}
