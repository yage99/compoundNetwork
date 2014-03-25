/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.view.model.CyNetworkView;
import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.Props;
import com.tmhs.tmhri.enrichedChem.actionAndListerner.DrawEdgeActions;
import com.tmhs.tmhri.enrichedChem.actionAndListerner.SearchButtonActions;
import com.tmhs.tmhri.enrichedChem.config.FileType;
import com.tmhs.tmhri.enrichedChem.config.InputParams;
import com.tmhs.tmhri.enrichedChem.config.InputParams.Params;
import com.tmhs.tmhri.enrichedChem.core.EnrichedNetwork;
import com.tmhs.tmhri.enrichedChem.core.NotEnrichedException;

/**
 * @author TMHYXZ6
 * 
 */
public class ControlPanel extends JPanel implements CytoPanelComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * source file name
	 */
	public static JTextField filename = new JTextField();;
	/**
	 * smileSource file used to cover id to cid
	 */
	public static JTextField smileSourceFile = new JTextField();
	/**
	 * current running status
	 */
	public static JLabel status = new JLabel("");
	/**
	 * 
	 */
	public static JProgressBar progressBar = new JProgressBar();

	private static JTextField t_edgeWidthCutOff = new JTextField();;
	private final ButtonGroup CompoundSource = new ButtonGroup();
	JRadioButton rdbtnFile = new JRadioButton("File:");
	JLabel lblStatus = new JLabel("Status:");
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	final JButton btnStopall = new JButton("Stop");
	JPanel panel = new JPanel();
	final JComboBox<FileType> mainFileType = new JComboBox<FileType>();
	JLabel text1 = new JLabel("File:");
	JButton start = new JButton("File...");
	JButton btnRun = new JButton("Load...");
	final JSlider slider = new JSlider();

	static boolean stop = true;
	private final JRadioButton rdbtnPubchem = new JRadioButton("PubChem");
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JCheckBox chckbxLocalstream = new JCheckBox("LocalStream");
	private final JLabel label_2 = new JLabel("");
	private final JLabel label_3 = new JLabel("");
	private final JPanel panel_5 = new JPanel();
	private JScrollPane scrollPane_1;
	private final JPanel panel_6 = new JPanel();
	private final static StatusPanel panel_7 = new StatusPanel();
	private final JButton button = new JButton("search");
	private final JButton button_2 = new JButton("drawEdges");

	static DefaultComboBoxModel<FileType> model = new DefaultComboBoxModel<FileType>();
	/**
	 * 
	 */
	public static ControlPanel self;

	/**
	 * @return status panel
	 */
	public static StatusPanel getStatusPanel() {
		return panel_7;
	}

	/**
	 * @param types
	 */
	public static void updateFile(List<FileType> types) {
		model.removeAllElements();
		for (FileType type : types)
			model.addElement(type);
	}

	/**
	 * Create the panel.
	 */
	public ControlPanel() {

		ControlPanel.self = this;

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		panel_2.setBounds(0, 0, 355, 89);

		panel_2.setBorder(new TitledBorder(null, "File", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		filename.setBounds(42, 50, 204, 21);
		panel_2.add(filename);

		filename.setColumns(30);
		panel_2.setLayout(null);
		mainFileType.setBounds(11, 23, 104, 21);
		panel_2.add(mainFileType);
		mainFileType.setBorder(new LineBorder(new Color(171, 173, 179)));
		panel.setBounds(0, 638, 355, 105);
		panel_5.add(panel);

		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "SmilesSource",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		CompoundSource.add(rdbtnFile);
		panel.setLayout(null);
		smileSourceFile.setBounds(67, 48, 186, 21);
		panel.add(smileSourceFile);
		smileSourceFile.setEnabled(false);
		smileSourceFile.setColumns(30);
		label_2.setBounds(426, 33, 0, 0);

		panel.add(label_2);
		label_3.setBounds(431, 33, 0, 0);

		panel.add(label_3);
		rdbtnPubchem.setBounds(6, 22, 67, 23);
		panel.add(rdbtnPubchem);
		CompoundSource.add(rdbtnPubchem);
		slider.setBounds(113, 23, 157, 36);
		slider.setMaximum(1000);
		slider.setValue(700);

		JLabel lblMinedgewidth = new JLabel("EdgeWidthCutOff");
		lblMinedgewidth.setBounds(11, 33, 97, 16);
		slider.setPaintTicks(true);
		panel_1.setBounds(0, 205, 355, 65);
		panel_1.setBorder(new TitledBorder(null, "ResultControl",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		t_edgeWidthCutOff.setBounds(282, 30, 61, 22);

		t_edgeWidthCutOff.setColumns(5);

		t_edgeWidthCutOff.setText("7.00");
		panel_1.setLayout(null);
		panel_1.add(lblMinedgewidth);
		panel_1.add(slider);
		panel_1.add(t_edgeWidthCutOff);
		panel_4.setVisible(false);
		panel_4.setBounds(0, 576, 355, 57);
		panel_4.setBorder(new TitledBorder(null, "SystemConfig",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		chckbxLocalstream.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chckbxLocalstream.isSelected())
					InputParams.database_stream = true;
				else
					InputParams.database_stream = false;
			}
		});
		chckbxLocalstream.setSelected(true);

		panel_4.add(chckbxLocalstream);
		progressBar.setVisible(false);
		progressBar.setBounds(0, 0, 355, 37);

		progressBar.setStringPainted(true);

		JLabel lblreportABug = new JLabel(
				"<html>Report a bug: <a href=\"mailto:yzhang2@tmhs.org\">email: zhangya998@gmail.com</a> version: "
						+ Props.getProp(Props.PLUGIN_VERSION) + "</html>",
				SwingConstants.RIGHT);
		lblreportABug.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblreportABug.setRequestFocusEnabled(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																426,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblStatus)
																		.addGap(10)
																		.addComponent(
																				status,
																				GroupLayout.DEFAULT_SIZE,
																				375,
																				Short.MAX_VALUE))
														.addComponent(
																lblreportABug,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																426,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE,
								1027, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(status,
												GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblStatus,
												GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblreportABug).addContainerGap()));
		lblStatus.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		setLayout(groupLayout);

		layoutPos();

	}

	void layoutPos() {

		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				double value = slider.getValue() / 100.0;
				t_edgeWidthCutOff.setText(String.valueOf(value));
				InputParams.minEdge = value;
				try {
					EnrichedChemPlugin.getCurrentNetwork().updateEdges(value);
				} catch (NotEnrichedException e) {
					e.printStackTrace();
					NoticeSystem.getInstance().err("no selected network");
				}
			}
		});
		panel_3.setBounds(0, 274, 355, 300);
		panel_3.setLayout(null);
		panel_3.add(progressBar);
		btnStopall.setVisible(false);
		btnStopall.setBounds(0, 186, 69, 37);
		panel_3.add(btnStopall);

		btnStopall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnStopall.setEnabled(false);
				if (stop) {
					NoticeSystem.getInstance().notice("All stoped");
					btnStopall.setText("Resume");
					stop = false;
				} else {
					NoticeSystem.getInstance().notice("Task go on");
					btnStopall.setText("stopAll");
					stop = true;
				}
				btnStopall.setEnabled(true);
			}
		});
		scrollPane_1.setViewportView(panel_6);
		panel_5.setMinimumSize(new Dimension(388, 680));
		panel_5.setLayout(null);
		updateFile(FileType.getRegisteredFileType());
		mainFileType.setModel(model);
		text1.setBounds(11, 54, 30, 15);
		panel_2.add(text1);
		start.setBounds(125, 22, 90, 23);
		panel_2.add(start);

		start.setAction(new SwingAction("File...", "", filename, model));
		panel_5.add(panel_2);
		btnRun.setBounds(253, 51, 90, 21);
		panel_2.add(btnRun);

		JButton btnEnrich = new JButton("Enrich");
		btnEnrich.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CyNetworkView nv;
				try {
					nv = EnrichedChemPlugin.getCurrentNetwork()
							.getNetworkView();
					if (!EnrichedChemPlugin.initNetwork(nv, false)) {
						int reply = JOptionPane
								.showConfirmDialog(
										ControlPanel.self,
										"The network is alreadly an enriched network,\nwould you like to reinit?",
										"confirm", JOptionPane.YES_NO_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							EnrichedChemPlugin.initNetwork(nv, true);
						}
					}
				} catch (NotEnrichedException e) {
					e.printStackTrace();
					NoticeSystem.getInstance().err("not an enriched network!");
				}

			}
		});
		btnEnrich.setBounds(221, 21, 97, 25);
		panel_2.add(btnEnrich);

		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NoticeSystem.getInstance().notice("start parsing file...");
				File file = new File(filename.getText());
				if (file.exists() && file.isFile())
					// Read file
					new EnrichedNetwork(filename.getText(), (FileType) model
							.getSelectedItem());
				else {
					JOptionPane.showMessageDialog(ControlPanel.self,
							"Not a valid file");
				}
				// allManager.addTask(new DrawNodes());
				// progressBar.setIndeterminate(true);
			}
		});
		panel_5.add(panel_1);
		panel_5.add(panel_4);
		panel_5.add(panel_3);
		panel_7.setBounds(0, 0, 355, 300);

		panel_3.add(panel_7);

		rdbtnPubchem.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (rdbtnPubchem.isSelected())
					InputParams.idCover = InputParams.Params.PUBCHEM_IMMIDIATE;
			}
		});

		rdbtnFile.setBounds(6, 47, 55, 23);
		panel.add(rdbtnFile);

		rdbtnFile.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (rdbtnFile.isSelected()) {
					smileSourceFile.setEnabled(true);
					InputParams.idCover = InputParams.Params.COMPOUND_COVER;
				} else {
					smileSourceFile.setEnabled(false);
				}
			}
		});

		final JRadioButton rdbtnPubchemdatabase = new JRadioButton(
				"pubChem(search name)");
		rdbtnPubchemdatabase.setSelected(true);
		rdbtnPubchemdatabase.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (rdbtnPubchemdatabase.isSelected()) {
					InputParams.idCover = Params.COVER_BY_NAME;
				}
			}
		});
		CompoundSource.add(rdbtnPubchemdatabase);
		rdbtnPubchemdatabase.setBounds(75, 22, 151, 23);
		panel.add(rdbtnPubchemdatabase);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Functions",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setBounds(0, 90, 355, 113);
		panel_5.add(panel_8);
		button.addMouseListener(new SearchButtonActions());

		panel_8.add(button);
		button_2.addMouseListener(new DrawEdgeActions());

		panel_8.add(button_2);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(gl_panel_6.createParallelGroup(
				Alignment.LEADING).addComponent(panel_5,
				GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE));
		gl_panel_6.setVerticalGroup(gl_panel_6.createParallelGroup(
				Alignment.LEADING).addComponent(panel_5,
				GroupLayout.PREFERRED_SIZE, 746, GroupLayout.PREFERRED_SIZE));
		panel_6.setLayout(gl_panel_6);

		mainFileType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				InputParams.sourceFile_type = (FileType) mainFileType
						.getModel().getSelectedItem();
			}
		});

		t_edgeWidthCutOff.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				InputParams.minEdge = Double.parseDouble(t_edgeWidthCutOff
						.getText());
				slider.setValue((int) (InputParams.minEdge * 100));
				try {
					EnrichedChemPlugin.getCurrentNetwork().updateEdges(
							InputParams.minEdge);
				} catch (NotEnrichedException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private class SwingAction extends AbstractAction {
		JTextField target;
		/**
		 * 
		 */
		private static final long serialVersionUID = -454355775150231960L;

		public SwingAction(String title, String desc, JTextField target,
				ComboBoxModel<FileType> comboBoxModel) {
			putValue(NAME, title);
			putValue(SHORT_DESCRIPTION, desc);
			this.target = target;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Create a file chooser
			final JFileChooser fc = new JFileChooser();
			// In response to a button click:
			int returnVal = fc.showOpenDialog(ControlPanel.self);
			// FileType fileType = (FileType) model.getSelectedItem();

			// Create FileFilter
			// CyFileFilter filter = new BasicCyFileFilter(
			// new String[] { fileType.getExt() }, null,
			// fileType.getDesc(), DataCategory.UNSPECIFIED,
			// EnrichedChemPlugin.getSwingAdapter().getStreamUtil());

			// Get the file name

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				if (file != null) {
					target.setForeground(Color.BLACK);
					target.setText(file.getAbsolutePath());
					target.setToolTipText(file.getAbsolutePath());
				}
			} else {
			}

		}
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
		return CytoPanelName.WEST;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.application.swing.CytoPanelComponent#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Enriched Chem Plugin";
	}
}
