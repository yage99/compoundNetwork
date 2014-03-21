/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.ui;

import org.tmhs.tool.yage.Info.NoticeSystem;

import com.tmhs.tmhri.enrichedChem.ControlPanel;

/**
 * @author ya
 * 
 */
public class LocalNoticeSys extends NoticeSystem {

	@Override
	public void notice(String msg) {
		ControlPanel.status.setText(msg);
		// System.out.println(msg);
	}

	@Override
	public void err(String msg) {
		ControlPanel.getStatusPanel().addTextMsg(html(msg, "red"));
		notice(html(msg, "#f00"));
	}

	@Override
	public void info(String msg) {
		ControlPanel.getStatusPanel().addTextMsg(html(msg, "black"));
		notice(msg);
	}

	private String html(String msg, String color) {
		return "<html><p style=\"color:" + color + ";\">" + msg + "</p></html>";
	}

	@Override
	public void warning(String msg) {
		ControlPanel.getStatusPanel().addTextMsg(html(msg, "yellow"));
	}
}
