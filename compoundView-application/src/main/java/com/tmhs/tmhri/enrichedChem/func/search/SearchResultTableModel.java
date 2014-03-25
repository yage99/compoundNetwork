/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.func.search;

import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.cytoscape.model.CyNode;

import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author ya
 * 
 */
public class SearchResultTableModel extends AbstractTableModel {

	Map<CyNode, List<PubChemDrug>> result;
	int row = 0;
	int column = 0;
	private List<CyNode> nodeList;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2370148716805867010L;

	/**
	 * 
	 */
	public SearchResultTableModel() {

	}

	/**
	 * set table with given dataset
	 * 
	 * @param map
	 * @param nodeList
	 */
	public void refreshTable(Map<CyNode, List<PubChemDrug>> map,
			List<CyNode> nodeList) {
		this.result = map;
		this.nodeList = nodeList;

		updateTable(map);
	}

	private void updateTable(Map<CyNode, List<PubChemDrug>> map) {
		column = 0;
		row = 0;
		for (CyNode key : map.keySet()) {
			List<PubChemDrug> list = map.get(key);
			column++;
			if (list != null) {
				if (row < list.size())
					row = list.size();
			}
		}

	}

	@Override
	public int getRowCount() {
		return row;
	}

	@Override
	public int getColumnCount() {
		return column;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<PubChemDrug> line = result.get(nodeList.get(columnIndex));
		if (line == null)
			return null;
		if (rowIndex < line.size()) {
			List<String> synos = line.get(line.size() - rowIndex - 1)
					.getSynos();
			if (synos.size() == 0)
				return "[]";
			else
				return synos.get(0);
		}
		return null;
	}

	/**
	 * @param columnIndex
	 * @return the node of this column
	 */
	public CyNode getParent(int columnIndex) {
		return nodeList.get(columnIndex);
	}

	/**
	 * @param node
	 * @return get column according to node
	 */
	public List<PubChemDrug> getColumn(CyNode node) {
		return result.get(node);
	}

	/**
	 * @param rowIndex
	 * @param node
	 * @return get one drug in the node in rowIndex
	 */
	public PubChemDrug getDrug(int rowIndex, CyNode node) {
		List<PubChemDrug> line = result.get(node);
		if (rowIndex < line.size())
			return line.get(line.size() - rowIndex - 1);
		return null;
	}

}
