/**
 * 
 */
package com.tmhs.tmhri.enrichedChem;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyNode;
import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.database.DTO.PubChemDrug;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.core.NotEnrichedException;
import com.tmhs.tmhri.enrichedChem.core.defines.EnrichParams;
import com.tmhs.tmhri.enrichedChem.core.defines.InputParams;
import com.tmhs.tmhri.enrichedChem.core.process.SearchTask;
import com.tmhs.tmhri.enrichedChem.ui.ImgShower;

import javax.swing.ScrollPaneConstants;

/**
 * @author ya
 * 
 */
public class SearchResultPanel extends JPanel implements CytoPanelComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8463786937219555503L;
	private static JTable table;
	static SearchResultTableModel tableModel = new SearchResultTableModel();
	static DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
	JList<String> list = new JList<String>();
	static ImageShow img;
	static EnrichedNetwork network;

	/**
	 * 
	 */
	public SearchResultPanel() {

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CyNode node = tableModel.getParent(table.getSelectedColumn());
				PubChemDrug drug = tableModel.getDrug(table.getSelectedRow(),
						node);
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
						PubChemDrug selected = columnList.remove(columnList
								.size() - cellNum - 1);
						columnList.add(selected);
					}

					SearchTask.setNodeDrug(network, node, drug);
					tableModel.fireTableDataChanged();
					table.repaint();

				}

			}
		});
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		table.setModel(tableModel);
		table.setColumnModel(columnModel);

		JPanel panel_2 = new JPanel();

		JScrollPane scrollPane_1 = new JScrollPane(list);

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane_1,
				GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addComponent(scrollPane_1,
				GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE));
		panel_2.setLayout(gl_panel_2);

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(10)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 287,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								505, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 213,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(10)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addGap(3)
																		.addComponent(
																				panel,
																				GroupLayout.DEFAULT_SIZE,
																				277,
																				Short.MAX_VALUE))
														.addComponent(
																scrollPane, 0,
																0,
																Short.MAX_VALUE)
														.addComponent(
																panel_2,
																GroupLayout.DEFAULT_SIZE,
																280,
																Short.MAX_VALUE))
										.addContainerGap()));

		panel.setLayout(null);

		setLayout(groupLayout);

		img = new ImageShow(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBounds(0, 13, 182, 82);
		panel.add(panel_1);

		JButton search = new JButton("search");
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							new SearchTask(EnrichedChemPlugin
									.getCurrentNetwork())
									.processSmilesData(InputParams.idCover);
						} catch (Exception e1) {
							NoticeSystem.getInstance().err(e1.getMessage());
						}
					}
				}).start();
			}
		});
		panel_1.add(search);

		JButton btnDrawedges = new JButton("drawEdges");
		btnDrawedges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					EnrichedChemPlugin.getCurrentNetwork().drawEdges();
				} catch (NotEnrichedException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnDrawedges);
	}

	/**
	 * @param nodes
	 * @param network
	 */
	public static void setTable(List<CyNode> nodes, EnrichedNetwork network) {

		SearchResultPanel.network = network;

		tableModel.refreshTable(network.getSearchResult(), nodes);
		tableModel.fireTableDataChanged();
		updateColumn(nodes, network);

		table.repaint();

	}

	// update column by node id
	private static void updateColumn(List<CyNode> nodes, EnrichedNetwork network) {
		columnModel = new DefaultTableColumnModel();

		for (CyNode node : nodes) {
			TableColumn column = new TableColumn();
			column.setHeaderValue(network.getAttrValue(node,
					EnrichParams.COMP_NAME));
			column.setIdentifier(node.getSUID());
			column.setModelIndex(nodes.indexOf(node));
			columnModel.addColumn(column);
		}
		table.setColumnModel(columnModel);

		if (nodes.size() > 0) {
			PubChemDrug drug = tableModel.getDrug(0, nodes.get(0));
			setImg(drug);
		}
	}

	/**
	 * @param drug
	 */
	public static void setImg(PubChemDrug drug) {
		img.setImg(drug);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.application.swing.CytoPanelComponent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.application.swing.CytoPanelComponent#getCytoPanelName()
	 */
	@Override
	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.SOUTH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.application.swing.CytoPanelComponent#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Search Result";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.application.swing.CytoPanelComponent#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return null;
	}
}

class SearchResultTableModel extends AbstractTableModel {

	Map<CyNode, List<PubChemDrug>> result;
	int row = 0;
	int column = 0;
	private List<CyNode> nodeList;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2370148716805867010L;

	public SearchResultTableModel() {

	}

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

	public CyNode getParent(int columnIndex) {
		return nodeList.get(columnIndex);
	}

	public List<PubChemDrug> getColumn(CyNode node) {
		return result.get(node);
	}

	public PubChemDrug getDrug(int rowIndex, CyNode node) {
		List<PubChemDrug> line = result.get(node);
		if (rowIndex < line.size())
			return line.get(line.size() - rowIndex - 1);
		return null;
	}
};

class ImageShow {

	JLabel lblImg = new JLabel("img");
	JLabel lblName = new JLabel("name");

	ImgShower frame = new ImgShower();

	public ImageShow(JPanel panel) {
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							frame.setImg(lblImg.getToolTipText(),
									lblName.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		lblImg.setBounds(new Rectangle(0, 6, 270, 270));

		lblName.setBounds(0, 0, 270, 16);

		panel.add(lblName);
		panel.add(lblImg);
	}

	void setImg(PubChemDrug drug) {
		lblImg.setText("Loading...");
		lblImg.setText("<html><img width=182 height=182 src=\""
				+ drug.getStructure() + "\" /></html>");
		lblImg.setToolTipText(drug.getStructure());

		lblName.setText(drug.getSynos().get(0));
	}
}