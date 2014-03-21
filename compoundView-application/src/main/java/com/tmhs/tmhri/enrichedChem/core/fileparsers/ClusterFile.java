/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.fileparsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tmhs.tool.yage.datasets.Table;
import org.tmhs.yage.api.fileFormat.ClusterTreeNode;

/**
 * @author ya
 * 
 *         a very simple cluster file. One file indicates a set of nodes in the
 *         same group. The group name is the same as filename.
 * 
 * @deprecated not fully implemented.
 */
@Deprecated
public class ClusterFile {
	private File file;

	private Table<Double> data = new Table<Double>();
	private List<ClusterTreeNode> cluster = new ArrayList<ClusterTreeNode>();

	/**
	 * @param file
	 * @throws IOException
	 */
	public ClusterFile(File file) throws IOException {
		this.file = file;
		read();
	}

	/**
	 * @return cluster list
	 */
	public List<ClusterTreeNode> getClusters() {
		return cluster;
	}

	/**
	 * @return data table
	 */
	public Table<Double> getData() {
		return data;
	}

	private void read() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line;
		line = reader.readLine();
		String[] values = line.split("\t");
		int length = values.length - 2;
		data.setLength(length);
		for (int i = 0; i < length; i++)
			data.addColumTag(values[i + 2]);

		int linenum = 0;
		while ((line = reader.readLine()) != null) {
			values = line.split("\t");
			ClusterTreeNode node = new ClusterTreeNode();
			node.setLineNumber(linenum++);
			node.setName(values[1]);
			data.addLineTag(values[0]);
			cluster.add(node);
			node.setClusterName(values[0]);
			for (int i = 0; i < length; i++) {
				data.addValue(Double.parseDouble(values[2 + i]));
			}
		}

		while (cluster.size() > 1) {
			ClusterTreeNode parent = new ClusterTreeNode();
			parent.setClusterName(file.getName());
			parent.left = cluster.remove(0);
			parent.right = cluster.remove(0);
			cluster.add(parent);
		}

		reader.close();

	}
}
