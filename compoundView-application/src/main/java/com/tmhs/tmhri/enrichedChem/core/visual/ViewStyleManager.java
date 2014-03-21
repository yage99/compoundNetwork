/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.visual;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import com.tmhs.tmhri.enrichedChem.core.defines.EnrichParams;

/**
 * @author ya
 * 
 */
public class ViewStyleManager {
	/**
	 * 
	 */
	public static VisualMappingManager visualMapManager;
	/**
	 * 
	 */
	public static VisualMappingFunctionFactory vmfFactoryP;
	/**
	 * 
	 */
	public static VisualMappingFunctionFactory vmfFactoryC;
	/**
	 * 
	 */
	public static VisualMappingFunctionFactory vmfFactoryD;

	static VisualStyle vs = null;

	/**
	 * 
	 */
	static void createViewStyle() {
		vs = visualMapManager.getDefaultVisualStyle();
		vs.setTitle("Enriched Style");
		visualMapManager.addVisualStyle(vs);

		// create a new visual style
		// vs = new VisualStyle(visualMapManager.getVisualStyle(),
		// "EnrichedStyle");

		// add node graphic passthrough to the visual style

		VisualMappingFunction<String, ?> mapping = vmfFactoryP
				.createVisualMappingFunction(EnrichParams.IMG_KEY.getString(),
						String.class, MoreVisualLexicon.NODE_CUSTOMGRAPHICS_1);
		vs.addVisualMappingFunction(mapping);
		// add size
		vs.addVisualMappingFunction(vmfFactoryC.createVisualMappingFunction(
				EnrichParams.SIZE.getString(), EnrichParams.SIZE.getType(),
				BasicVisualLexicon.NODE_SIZE));
		// add edge width to visual style
		vs.addVisualMappingFunction(vmfFactoryC.createVisualMappingFunction(
				EnrichParams.EDGE_WIDTH.getString(), Float.class,
				BasicVisualLexicon.EDGE_WIDTH));

		// set nodes and edges graphic visual style
		// Cytoscape.getVisualMappingManager().getCalculatorCatalog()
		// .removeVisualStyle("EnrichedStyle");
		// Cytoscape.getVisualMappingManager().getCalculatorCatalog()
		// .addVisualStyle(vs);
		// Cytoscape.getVisualMappingManager().setVisualStyle("EnrichedStyle");

		visualMapManager.setCurrentVisualStyle(vs);

	}

	/**
	 * @param networkView
	 */
	public static void applyVisualStyle(CyNetworkView networkView) {
		if (vs == null)
			createViewStyle();
		vs.apply(networkView);
	}
}
