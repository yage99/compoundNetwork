/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.visual;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.NullDataType;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.tmhs.tmhri.enrichedChem.EnrichedChemPlugin;

/**
 * @author TMHYXZ6
 * 
 */
public class MoreVisualLexicon extends BasicVisualLexicon {
	static VisualLexicon vl = EnrichedChemPlugin.getVisualLexicon();

	/**
	 * @param rootVisualProperty
	 */
	public MoreVisualLexicon(VisualProperty<NullDataType> rootVisualProperty) {
		super(rootVisualProperty);
	}

	/**
	 * 
	 */
	public static VisualProperty<?> NODE_CUSTOMGRAPHICS_1 = vl.lookup(
			CyNode.class, "NODE_CUSTOMGRAPHICS_1");
}
