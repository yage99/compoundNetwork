/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.visual;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;

import com.tmhs.tmhri.enrichedChem.EnrichConfig;

/**
 * @author TMHYXZ6
 * 
 */
public class PieChartFactory implements CyCustomGraphicsFactory<NodeImageLayer> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #getPrefix()
	 */
	@Override
	public String getPrefix() {
		return EnrichConfig.pieChartPerfix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #supportsMime(java.lang.String)
	 */
	@Override
	public boolean supportsMime(String mimeType) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #getInstance(java.net.URL)
	 */
	@Override
	public PieChartGraph getInstance(URL url) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #getInstance(java.lang.String)
	 */
	@Override
	public PieChartGraph getInstance(String input) {
		String[] keySplit = input.split("\\|");
		List<String> keys = Arrays.asList(keySplit);
		return new PieChartGraph(keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #parseSerializableString(java.lang.String)
	 */
	@Override
	public PieChartGraph parseSerializableString(String string) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory
	 * #getSupportedClass()
	 */
	@Override
	public Class<? extends CyCustomGraphics<NodeImageLayer>> getSupportedClass() {
		return PieChartGraph.class;
	}

}
