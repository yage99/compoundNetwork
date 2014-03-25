/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.visual;

import java.awt.Color;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;
import org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author ya
 * 
 */
public class NodeImageLayer implements ImageCustomGraphicLayer {
	private BufferedImage originalImage;
	private BufferedImage highLightImage;
	private TexturePaint texture;
	private TexturePaint highlightTexture;

	private TexturePaint result;

	private List<String> keyList;
	private List<Double> valueList;

	Rectangle2D defaultBound;
	private int width;
	private int height;
	private boolean highLight = false;
	/**
	 * 
	 */
	public static double max = 1;
	/**
	 * 
	 */
	public static double min = -1;

	/**
	 * @param name
	 * @throws IOException
	 */
	// public MyURLImage(String name) throws IOException {
	//
	// // fitRatio = myFitRatio;
	// }

	/**
	 * @param keyList
	 * @param valueList
	 * @param width
	 * @param height
	 * 
	 *            cytoscape will call this method
	 */
	public NodeImageLayer(List<String> keyList, List<Double> valueList,
			int width, int height) {
		this.keyList = keyList;
		this.valueList = valueList;
		this.width = width;
		this.height = height;

		defaultBound = null;

		defaultBound = new Rectangle2D.Double(-width / 2, -height / 2, width,
				height);

		originalImage = drawChart(false);
		highLightImage = drawChart(true);
		reDrawImage(defaultBound);
	}

	/**
	 * @param highLight
	 */
	private BufferedImage drawChart(boolean highLight) {
		Map<String, Double> values = new LinkedHashMap<String, Double>();
		for (int i = 0; i < keyList.size(); i++) {
			// Double number = 0.0;
			// try {
			// number = Double.parseDouble(valueListString.get(i));
			// } catch (NumberFormatException e) {
			// number = 0.0;
			// NoticeSystem.getInstance().warning(
			// "pie chart: number exception!");
			// }
			values.put(keyList.get(i), valueList.get(i));
		}

		DefaultPieDataset dataSet = new DefaultPieDataset();
		int i = 0;

		Iterator<String> keys = values.keySet().iterator();
		for (i = 0; i < values.size(); i++) {
			String key = keys.next();
			dataSet.insertValue(i, key, 1);
		}
		JFreeChart chart = ChartFactory.createPieChart("", dataSet, false,
				false, false);

		// draw different part with valued color
		PiePlot plots = (PiePlot) chart.getPlot();
		for (i = 0; i < values.size(); i++) {
			double realValue = values.get(dataSet.getKey(i));

			int red = (int) (realValue > 0 ? 255
					: ((1 - realValue / min) * 255));
			red = red < 0 ? 0 : red;
			int blue = (int) (realValue < 0 ? 255
					: ((1 - (realValue) / (max)) * 255));
			blue = blue < 0 ? 0 : blue;
			int green = (int) ((1 - Math.abs(realValue / max)) < 0 ? 0
					: ((1 - Math.abs(realValue / max)) * 255));
			// int red = (int) (((realValue - min) < 0 ? 0
			// : ((realValue - min) / (max - min))) * 255);
			// red = red > 255 ? 255 : red;
			// int blue = (int) (((max - realValue) < 0 ? 0
			// : ((max - realValue) / (max - min))) * 255);
			// blue = blue > 255 ? 255 : blue;
			int light = 20;
			if (!highLight) {
				red = (red - light) < 0 ? 0 : (red - light);
				blue = (blue - light) < 0 ? 0 : (blue - light);
				blue = (blue - light) < 0 ? 0 : (blue - light);
			}
			plots.setSectionPaint(dataSet.getKey(i),
					new Color(red, blue, green));
		}
		chart.setBackgroundPaint(null);
		chart.setBackgroundImageAlpha(0);
		chart.getTitle().setVisible(false);
		chart.getPlot().setBackgroundPaint(Color.green);
		chart.getPlot().setBackgroundAlpha(0);
		((PiePlot) chart.getPlot()).setShadowPaint(null);
		chart.setBorderVisible(false);
		chart.getPlot().setOutlineVisible(false);

		return chart.createBufferedImage(500, 500);
		// createLayer();
		// resizeImage(width, height);
	}

	private void buildCustomGraphics() {
		// layers.clear();

		defaultBound = null;

		defaultBound = new Rectangle2D.Double(-width / 2, -height / 2, width,
				height);
		// final PaintFactory paintFactory = new TexturePaintFactory(targetImg);

		// cg = new CustomGraphic(bound, paintFactory);

		// This object is always one layer, so simply add without sorting.
		// DLayer layer = new DLayer(cg, 1);
		// layers.add(layer);
	}

	/**
	 * @param width
	 * @param height
	 */
	private void reDrawImage(Rectangle2D bound) {
		int width = bound.getBounds().width;
		int height = bound.getBounds().height;

		BufferedImage scaledImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		final Image img = originalImage.getScaledInstance(width, height,
				Image.SCALE_AREA_AVERAGING);
		scaledImage.getGraphics().drawImage(img, 0, 0, null);
		texture = new TexturePaint(scaledImage, bound);

		BufferedImage scaledHighLightImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		final Image highLightImg = highLightImage.getScaledInstance(width,
				height, Image.SCALE_AREA_AVERAGING);
		scaledHighLightImage.getGraphics().drawImage(highLightImg, 0, 0, null);
		highlightTexture = new TexturePaint(scaledHighLightImage, bound);
		// scaledImage = ImageUtil.toBufferedImage(img);
		
		buildCustomGraphics();
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
		buildCustomGraphics();
		return defaultBound;
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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cytoscape.view.presentation.customgraphics.ImageCustomGraphicLayer
	 * #getPaint(java.awt.geom.Rectangle2D)
	 * 
	 * this method will be called every time when refresh network view
	 */
	@Override
	public TexturePaint getPaint(Rectangle2D bounds) {
		width = bounds.getBounds().width;
		height = bounds.getBounds().height;

		if (texture == null || highlightTexture == null) {
			reDrawImage(bounds);
		} else if (defaultBound.getWidth() != width
				|| defaultBound.getHeight() != height) {
			reDrawImage(bounds);
		}

		if (highLight)
			result = highlightTexture;
		else
			result = texture;
		return result;
	}

	/**
	 * @param highLight
	 */
	public void highLight(boolean highLight) {
		this.highLight = highLight;
	}

}
