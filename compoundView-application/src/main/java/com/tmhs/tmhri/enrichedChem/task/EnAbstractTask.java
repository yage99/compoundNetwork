/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.task;

import org.cytoscape.work.AbstractTask;

/**
 * @author ya
 *
 */
public abstract class EnAbstractTask extends AbstractTask {

	private boolean finish = false;
	
	protected void setFinish() {
		finish = true;
	}
	
	/**
	 * @return define whether is finished
	 */
	public boolean isFinished() {
		return finish;
	}

}
