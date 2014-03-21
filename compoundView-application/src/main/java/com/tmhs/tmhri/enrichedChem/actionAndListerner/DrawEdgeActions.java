/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.actionAndListerner;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;
import com.tmhs.tmhri.enrichedChem.core.NotEnrichedException;

/**
 * @author TMHYXZ6
 * 
 */
public class DrawEdgeActions extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			EnrichedChemPlugin.getCurrentNetwork().drawEdges();
		} catch (NotEnrichedException e1) {
			e1.printStackTrace();
		}

	}
}
