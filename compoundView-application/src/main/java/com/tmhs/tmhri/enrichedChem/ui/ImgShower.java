/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.ui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

/**
 * @author ya
 * 
 */
public class ImgShower extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8488311729775214225L;
	private JPanel contentPane;
	JLabel lblImg = new JLabel("img");
	JLabel lblName = new JLabel("New label");
	private final JPanel panel = new JPanel();

	/**
	 * 
	 */
	public ImgShower() {
		// setResizable(false);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (e.getID() == ComponentEvent.COMPONENT_RESIZED) {
					setImg(imgsrc, name);
				}
			}
		});
		panel.setLayout(null);

		panel.add(lblImg);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 233,
						Short.MAX_VALUE)
				.addComponent(lblName, GroupLayout.DEFAULT_SIZE, 233,
						Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(lblName)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 265,
								Short.MAX_VALUE).addGap(0)));
		contentPane.setLayout(gl_contentPane);

		this.setVisible(false);
	}

	/**
	 * Create the frame.
	 * 
	 * @param labelText
	 * @param name
	 */
	public ImgShower(String labelText, String name) {
		this();
		setImg(labelText, name);
	}

	private String imgsrc;
	private String name;

	/**
	 * @param labelText
	 * @param name
	 */
	public void setImg(String labelText, String name) {
		this.imgsrc = labelText;
		this.name = name;

		Dimension size = panel.getSize();
		int len = size.width < size.height ? size.width : size.height;

		lblImg.setBounds(0, 0, len, len);
		lblImg.setText("<html><img width=" + len + " height=" + len + " src=\""
				+ labelText + "\" /></html>");

		lblName.setText(name);
		this.setVisible(true);
	}

}
