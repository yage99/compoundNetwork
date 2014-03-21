/**
 * 
 */
package com.tmhs.cytoscape.vizMapGraph.internal;

import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;

/**
 * @author TMHYXZ6
 * 
 */
public class ChartLayer implements CustomGraphicLayer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer#getBounds2D
	 * ()
	 */
	@Override
	public Rectangle2D getBounds2D() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer#getPaint
	 * (java.awt.geom.Rectangle2D)
	 */
	@Override
	public Paint getPaint(Rectangle2D bounds) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer#transform
	 * (java.awt.geom.AffineTransform)
	 */
	@Override
	public CustomGraphicLayer transform(AffineTransform xform) {
		return null;
	}

}
