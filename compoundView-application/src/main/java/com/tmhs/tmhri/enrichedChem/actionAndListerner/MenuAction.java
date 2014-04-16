/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.actionAndListerner;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.config.FileType;
import com.tmhs.tmhri.enrichedChem.search.SearchResultPanel;
import com.tmhs.tmhri.enrichedChem.ui.ControlPanel;

/**
 * @author TMHYXZ6
 * 
 */
public class MenuAction extends AbstractCyAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6170204177943900212L;
	private CySwingApplication desktopApp;
	private final CytoPanel cytoPanelWest;
	private final CytoPanel cytoPanelSouth;
	private ControlPanel controlPanel;
	private SearchResultPanel searchPanel;

	/**
	 * @param desktopApp
	 * @param controlPanel
	 * @param searchPanel
	 */
	public MenuAction(CySwingApplication desktopApp, ControlPanel controlPanel,
			SearchResultPanel searchPanel) {
		// Add a menu item -- Apps->sample02
		super("EnrichedChemPlugin");
		setPreferredMenu("Apps");
		this.desktopApp = desktopApp;

		// Note: myCytoPanel is bean we defined and registered as a service
		this.cytoPanelWest = this.desktopApp.getCytoPanel(CytoPanelName.WEST);
		this.cytoPanelSouth = this.desktopApp.getCytoPanel(CytoPanelName.SOUTH);
		this.controlPanel = controlPanel;
		this.searchPanel = searchPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cytoscape.util.CytoscapeAction#actionPerformed(java.awt.event.ActionEvent
	 * )
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (EnrichedChemPlugin.isInit)
			return;
		EnrichedChemPlugin.isInit = true;
		// If the state of the cytoPanelWest is HIDE, show it
		if (cytoPanelWest.getState() == CytoPanelState.HIDE) {
			cytoPanelWest.setState(CytoPanelState.DOCK);
		}
		// Select my panel
		int index = cytoPanelWest.indexOfComponent(controlPanel);
		if (index == -1) {
			return;
		}
		cytoPanelWest.setSelectedIndex(index);

		index = cytoPanelSouth.indexOfComponent(searchPanel);
		if (index == -1)
			return;
		cytoPanelSouth.setSelectedIndex(index);

		// CytoPanelImp ctrlPanel = (CytoPanelImp) Cytoscape.getDesktop()
		// .getCytoPanel(SwingConstants.WEST);
		// ctrlPanel.add("EnrichedChem", new ControlPanel());
		// CytoPanelImp searchPanel = (CytoPanelImp) Cytoscape.getDesktop()
		// .getCytoPanel(SwingConstants.SOUTH);
		// searchPanel.add("search result", new SearchResultPanel());

		init();
	}

	private void init() {
		FileType.registeFileType(FileType.CMAP_DETAIL_RESU);
		// FileType.registeFileType(FileType.CDT);
	}

}
