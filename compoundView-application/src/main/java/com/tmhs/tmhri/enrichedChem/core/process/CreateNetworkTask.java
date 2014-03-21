package com.tmhs.tmhri.enrichedChem.core.process;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

/**
 * @author ya
 *
 */
public class CreateNetworkTask extends AbstractTask {
	
	private final CyNetworkManager netMgr;
	private final CyNetworkFactory cnf;
	private final CyNetworkNaming namingUtil; 
	
	/**
	 * @param netMgr
	 * @param namingUtil
	 * @param cnf
	 */
	public CreateNetworkTask(final CyNetworkManager netMgr, final CyNetworkNaming namingUtil, final CyNetworkFactory cnf){
		this.netMgr = netMgr;
		this.cnf = cnf;
		this.namingUtil = namingUtil;
	}
	
	public void run(TaskMonitor monitor) {
		// Create an empty network
		CyNetwork myNet = cnf.createNetwork();
		myNet.getRow(myNet).set(CyNetwork.NAME,
				      namingUtil.getSuggestedNetworkTitle("My Network"));
				
		netMgr.addNetwork(myNet);
		
	}
}
