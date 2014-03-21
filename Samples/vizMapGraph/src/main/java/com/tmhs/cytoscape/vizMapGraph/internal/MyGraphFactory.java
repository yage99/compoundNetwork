/**
 * 
 */
package com.tmhs.cytoscape.vizMapGraph.internal;

import java.net.URL;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;

/**
 * @author TMHYXZ6
 * 
 */
public class MyGraphFactory implements CyCustomGraphicsFactory<ChartLayer> {

	@Override
	public String getPrefix() {
		return "layer";
	}

	@Override
	public boolean supportsMime(String mimeType) {
		return false;
	}

	@Override
	public CyCustomGraphics<ChartLayer> getInstance(URL url) {
		return null;
	}

	@Override
	public CyCustomGraphics<ChartLayer> getInstance(String input) {
		return null;
	}

	@Override
	public CyCustomGraphics<ChartLayer> parseSerializableString(String string) {
		return null;
	}

	@Override
	public Class<? extends CyCustomGraphics<?>> getSupportedClass() {
		return Graphic.class;
	}

}
