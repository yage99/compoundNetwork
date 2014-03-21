package com.tmhs.cytoscape.vizMapGraph.internal;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.vizmap.VisualMappingManager;

/**
 * Creates a new menu item under Apps menu section.
 * 
 */
public class MenuAction extends AbstractCyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6179456990268798504L;
	private CyApplicationManager cyApplicationManager;
	private VisualMappingManager vmmServiceRef;

	/**
	 * @param cyApplicationManager
	 * @param vmmServiceRef
	 * @param menuTitle
	 */
	public MenuAction(CyApplicationManager cyApplicationManager,
			VisualMappingManager vmmServiceRef, final String menuTitle) {

		super(menuTitle, cyApplicationManager, null, null);
		this.cyApplicationManager = cyApplicationManager;
		this.vmmServiceRef = vmmServiceRef;
		setPreferredMenu("Apps");

	}

	public void actionPerformed(ActionEvent e) {
		vmmServiceRef.getCurrentVisualStyle().apply(
				cyApplicationManager.getCurrentNetworkView());
		cyApplicationManager.getCurrentNetworkView().updateView();
		// Write your own function here.
		JOptionPane.showMessageDialog(null, "Hello Cytoscape World!");

	}
}
