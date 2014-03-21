/**
 * 
 */
package com.tmhs.cytoscape.vizMapGraph.internal;

import java.awt.Image;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;

/**
 * @author TMHYXZ6
 * 
 */
public class Graphic implements CyCustomGraphics<ChartLayer> {

	@Override
	public Long getIdentifier() {
		return null;
	}

	@Override
	public void setIdentifier(Long id) {

	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public void setDisplayName(String displayName) {

	}

	@Override
	public String toSerializableString() {
		return null;
	}

	@Override
	public List<ChartLayer> getLayers(CyNetworkView networkView,
			View<? extends CyIdentifiable> grView) {
		return null;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public void setWidth(int width) {

	}

	@Override
	public void setHeight(int height) {

	}

	@Override
	public float getFitRatio() {
		return 0;
	}

	@Override
	public void setFitRatio(float ratio) {

	}

	@Override
	public Image getRenderedImage() {
		return null;
	}

}
