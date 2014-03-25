/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.fileparsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tmhs.tool.yage.Info.NoticeSystem;
import org.tmhs.tool.yage.datasets.Table;
import org.tmhs.yage.api.fileFormat.ClusterTreeNode;
import org.tmhs.yage.api.fileFormat.DetailCMAPResult;

/**
 * @author TMHYXZ6
 * 
 */
public class CMAPFile {
	private File file;

	private List<ClusterTreeNode> clusters;
	private Table<Double> nodeData;

	private int count = 50;

	/**
	 * @param file
	 * @throws IOException
	 */
	public CMAPFile(File file) throws IOException {
		this.file = file;
		readFile();
	}

	private void readFile() throws IOException {
		NoticeSystem.getInstance().info("reading cmap result");
		DetailCMAPResult table = DetailCMAPResult.readSimpleTable(file
				.getAbsolutePath());
		clusters = new ArrayList<ClusterTreeNode>();
		nodeData = new Table<Double>();
		nodeData.setLength(3);
		nodeData.addColumTag("score");
		nodeData.addColumTag("up");
		nodeData.addColumTag("down");

		int num = table.getHeight();
		for (int i = 0; i < num && i < count; i++) {
			List<String> line = table.getLine(i);
			ClusterTreeNode node = new ClusterTreeNode();
			node.setClusterName(line.get(DetailCMAPResult.ID));
			node.setName(line.get(DetailCMAPResult.CMAPNAME));
			node.setLineNumber(i);
			clusters.add(node);

			nodeData.addValue(Double.parseDouble(line
					.get(DetailCMAPResult.ENRICHMENT)));
			nodeData.addValue(Double.parseDouble(line.get(6)));
			nodeData.addValue(-Double.parseDouble(line.get(7)));
			nodeData.addLineTag("");
		}
		for (int i = num - count; i < num && i >= count; i++) {
			List<String> line = table.getLine(i);
			ClusterTreeNode node = new ClusterTreeNode();
			node.setClusterName(line.get(DetailCMAPResult.ID));
			node.setName(line.get(DetailCMAPResult.CMAPNAME));
			node.setLineNumber(i + 2 * count - num);
			clusters.add(node);

			nodeData.addValue(Double.parseDouble(line
					.get(DetailCMAPResult.ENRICHMENT)));
			nodeData.addValue(Double.parseDouble(line.get(6)));
			nodeData.addValue(-Double.parseDouble(line.get(7)));
			nodeData.addLineTag("");
		}

	}

	/**
	 * @return a list contains clusters node
	 */
	public List<ClusterTreeNode> getClusters() {
		return clusters;
	}

	/**
	 * @return data table
	 */
	public Table<Double> getTable() {
		return nodeData;
	}

}
