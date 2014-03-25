/**
 * 
 */
package com.tmhs.tmhri.enrichedChem.func.search;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.tmhs.tmhri.enrichedChem.ui.ImgShower;
import com.tmhs.yage.api.NIH.DTO.PubChemDrug;

/**
 * @author ya
 * 
 */
public class ImageShow {
	JLabel lblImg = new JLabel("img");
	JLabel lblName = new JLabel("name");

	ImgShower frame = new ImgShower();

	/**
	 * @param panel
	 * 		the parent panel for this image panel
	 */
	public ImageShow(JPanel panel) {
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							frame.setImg(lblImg.getToolTipText(),
									lblName.getText());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		lblImg.setBounds(new Rectangle(0, 6, 270, 270));

		lblName.setBounds(0, 0, 270, 16);

		panel.add(lblName);
		panel.add(lblImg);
	}

	/**
	 * @param drug
	 * 		set this panel's current compound
	 */
	public void setImg(PubChemDrug drug) {
		lblImg.setText("Loading...");
		lblImg.setText("<html><img width=182 height=182 src=\""
				+ drug.getStructure() + "\" /></html>");
		lblImg.setToolTipText(drug.getStructure());

		lblName.setText(drug.getSynos().get(0));
	}
}
