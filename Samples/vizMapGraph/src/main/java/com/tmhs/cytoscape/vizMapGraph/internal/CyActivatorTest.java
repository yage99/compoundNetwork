package com.tmhs.cytoscape.vizMapGraph.internal;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.RenderingEngine;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.osgi.framework.BundleContext;

/**
 * @author TMHYXZ6
 *
 */
/**
 * @author TMHYXZ6
 *
 */
public class CyActivatorTest extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		CyApplicationManager cyApplicationManager = getService(context,
				CyApplicationManager.class);

		Properties properties = new Properties();

		RenderingEngine<CyNetwork> render = cyApplicationManager
				.getCurrentRenderingEngine();
		MyGraphFactory myFac = new MyGraphFactory();

		VisualLexicon vl = render.getVisualLexicon();
		VisualProperty<?> vp = vl.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");

		registerService(context, myFac, CyCustomGraphicsFactory.class,
				new Properties());

		VisualMappingManager vmmServiceRef = getService(context,
				VisualMappingManager.class);
		VisualMappingFunctionFactory vmfFactoryP = getService(context,
				VisualMappingFunctionFactory.class,
				"(mapping.type=passthrough)");
		VisualMappingFunction<?, ?> mapping = vmfFactoryP
				.createVisualMappingFunction("layer", String.class, vp);

		// VisualMappingFunction<String, Paint> mapping = vmfFactoryP
		// .createVisualMappingFunction("graph", String.class,
		// new PaintVisualProperty(new Color(120, 120, 120), null,
		// "NODE_CUSTOMGRAPHICS_2", "Node Paint2",
		// CyNode.class));

		vmmServiceRef.getCurrentVisualStyle().addVisualMappingFunction(mapping);

		MenuAction action = new MenuAction(cyApplicationManager, vmmServiceRef,
				"Hello World App");
		registerAllServices(context, action, properties);

	}
}
