/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

/**
 * @author ya
 * 
 */
public class StatusPanel extends JPanel {
	JPanel panel;
	int height = 0;

	/**
	 * 
	 */
	public StatusPanel() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(350, height));
		// panel.setMaximumSize(new Dimension(360, Short.MAX_VALUE));
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(107, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		setLayout(groupLayout);

	}

	/**
	 * @param msg
	 */
	public void addTextMsg(String msg) {
		//System.out.println(panel.getHeight());
		StatusEle newEle = new StatusEle(msg);
		newEle.setPreferredSize(new Dimension(340, 30));

		panel.add(newEle, 0);
		panel.setPreferredSize(new Dimension(350, height += 40));

		panel.updateUI();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6826728251981681950L;

}

class StatusEle extends JPanel implements FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4405552509692528775L;
	JLabel label;

	/**
	 * Create the panel.
	 * 
	 * @param msg
	 */
	public StatusEle(String msg) {
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(100, 100, 100)));
		label = new JLabel(msg);
		add(label);

		this.setBackground(Color.gray);

		this.addFocusListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		this.setBackground(new Color(100, 100, 100));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.setBackground(new Color(240, 240, 240));
	}

}
