package com.tmhs.tmhri.enrichedChem.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;

class Slice {
	double value;
	String label;

	/**
	 * 
	 */
	public static double max = 1;
	/**
	 * 
	 */
	public static double min = -1;

	public Slice(String label, double value) {
		this.value = value;
		this.label = label;
	}

	public Color getColor(boolean highLight) {
		int red = (int) (value > 0 ? 255 : ((1 - value / min) * 255));
		red = red < 0 ? 0 : red;
		int blue = (int) (value < 0 ? 255 : ((1 - (value) / (max)) * 255));
		blue = blue < 0 ? 0 : blue;
		int green = (int) ((1 - Math.abs(value / max)) < 0 ? 0 : ((1 - Math
				.abs(value / max)) * 255));
		int light = 20;
		if (!highLight) {
			red = (red - light) < 0 ? 0 : (red - light);
			blue = (blue - light) < 0 ? 0 : (blue - light);
			blue = (blue - light) < 0 ? 0 : (blue - light);
		}

		return new Color(red, blue, green);
	}
}

/**
 * @author TMHYXZ6
 * 
 */
public class PieChart implements ImageCustomGraphicLayer {
	Graphics2D g;
	Rectangle area;

	/**
	 * @param g
	 * @param keys
	 * @param values
	 */
	public PieChart(Graphics2D g, List<String> keys, List<Double> values) {
		this.g = g;

		List<Slice> slices = new ArrayList<Slice>();

		for (int i = 0; i < values.size(); i++) {
			double realValue = values.get(i);

			slices.add(new Slice(keys.get(i), realValue));
		}
	}

	/**
	 * @param g
	 * @param area
	 * @param slices
	 */
	void drawGraph(List<Slice> slices, Boolean highLight) {
		double total = 0.0D;
		for (Slice slice : slices) {
			total += slice.value;
		}

		// double curValue = 0.0D;
		int startAngle = 0;
		for (Slice slice : slices) {
			// startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slice.value * 360 / total);

			g.setColor(slice.getColor(highLight));
			g.fillArc(area.x, area.y, area.width, area.height, startAngle,
					arcAngle);
			// curValue += slice.value;
			startAngle += arcAngle;
		}
	}

	/**
	 * @param highLight
	 * @return a drawn image
	 */
	public BufferedImage getBufferedImage(boolean highLight) {
		BufferedImage img = new BufferedImage(area.width, area.height,
				BufferedImage.TYPE_INT_ARGB);
		g.drawImage(img, null, 0, 0);
		return img;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer#getBounds2D
	 * ()
	 */
	@Override
	public Rectangle2D getBounds2D() {
		return area;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer
	 * #getPaint(java.awt.geom.Rectangle2D)
	 */
	@Override
	public TexturePaint getPaint(Rectangle2D bounds) {
		this.area = bounds.getBounds();
		return null;
	}
}