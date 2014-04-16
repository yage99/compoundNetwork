/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.search;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTable;

import org.cytoscape.model.CyNode;

import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author ya
 * 
 */
public class SearchResultAction extends MouseAdapter {
	private SearchResultTableModel tableModel;
	private JTable table;
	private JList<String> list;
	private ImageShow img;

	/**
	 * @param panel
	 *            search result panel
	 */
	public SearchResultAction(SearchResultPanel panel) {
		this.table = SearchResultPanel.getTable();
		this.tableModel = SearchResultPanel.getTableModel();
		this.list = SearchResultPanel.getSynoListPanel();
		this.img = SearchResultPanel.getImagePanel();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		EnrichedNetwork network = SearchResultPanel.network;

		CyNode node = tableModel.getParent(table.getSelectedColumn());
		PubChemDrug drug = tableModel.getDrug(table.getSelectedRow(), node);
		List<String> synos = drug.getSynos();
		if (synos == null)
			list.setListData(new String[] {});
		else {
			list.setListData(synos.toArray(new String[0]));
		}
		img.setImg(drug);
		if (e.getClickCount() == 2) {
			List<PubChemDrug> columnList = tableModel.getColumn(node);
			int cellNum = table.getSelectedRow();
			if (cellNum < columnList.size()) {
				PubChemDrug selected = columnList.remove(columnList.size()
						- cellNum - 1);
				columnList.add(selected);
			}

			SearchTask.setNodeDrug(network, node, drug);
			SearchResultPanel.refresh();

		}

	}
}
