/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.core.visual;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;

/**
 * @author TMHYXZ6
 * 
 */
public class PieChartGraph implements CyCustomGraphics<NodeImageLayer> {
	private Long id;
	private String displayName;
	private int width = 100;
	private int height = 100;
	private float fitRatio = 1.3f;

	// now only has one layer
	private NodeImageLayer layer;

	private List<String> columnKeys;

	/**
	 * @param keys
	 */
	public PieChartGraph(List<String> keys) {
		this.columnKeys = keys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#getIdentifier
	 * ()
	 */
	@Override
	public Long getIdentifier() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#setIdentifier
	 * (java.lang.Long)
	 */
	@Override
	public void setIdentifier(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#
	 * getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#
	 * setDisplayName(java.lang.String)
	 */
	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#
	 * toSerializableString()
	 */
	@Override
	public String toSerializableString() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#getLayers
	 * (org.cytoscape.view.model.CyNetworkView, org.cytoscape.view.model.View)
	 */
	@Override
	public List<NodeImageLayer> getLayers(CyNetworkView networkView,
			View<? extends CyIdentifiable> grView) {
		CyTable table = networkView.getModel().getDefaultNodeTable();
		CyRow row = table.getRow(grView.getModel().getSUID());
		if (layer == null) {
			List<Double> values = new ArrayList<Double>();
			for (String key : columnKeys) {
				values.add(row.get(key, Double.class));
			}
			layer = new NodeImageLayer(columnKeys, values, width, height);
		}
		List<NodeImageLayer> result = new ArrayList<NodeImageLayer>();
		result.add(layer);
		//layer.highLight(row.get(CyNetwork.SELECTED, Boolean.class));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#getWidth
	 * ()
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#getHeight
	 * ()
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#setWidth
	 * (int)
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#setHeight
	 * (int)
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#getFitRatio
	 * ()
	 */
	@Override
	public float getFitRatio() {
		return this.fitRatio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#setFitRatio
	 * (float)
	 */
	@Override
	public void setFitRatio(float ratio) {
		this.fitRatio = ratio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cytoscape.view.presentation.customgraphics.CyCustomGraphics#
	 * getRenderedImage()
	 */
	@Override
	public Image getRenderedImage() {
		// return layer.getImage(width, height);
		return null;
	}

	/**
	 * @param highLight
	 * 
	 */
	public void highLight(boolean highLight) {
		layer.highLight(highLight);
	}
}
