package gui;

import javax.swing.JPanel;

import domain.Publication;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class PanelPublicacion extends JPanel {

	/**
	 * Create the panel.
	 * 
	 */
	private JTextArea textPublication;
	private Publication publication;
	private JLabel lblUsernameDate;
	public PanelPublicacion(Publication publ) {
		this.publication = publ;
		this.setSize(540, 114);
		setLayout(new BorderLayout(0, 0));
		
		lblUsernameDate = new JLabel(publication.getUser().getUserVisibleName());
		add(lblUsernameDate, BorderLayout.NORTH);


		textPublication = new JTextArea();
		textPublication.setSize(540, 16);
		textPublication.setEditable(false);
		textPublication.setLineWrap(true);
		textPublication.setWrapStyleWord(true);
		textPublication.setText(publication.getMessage().replaceAll("<br>", System.lineSeparator()));
		System.out.println(textPublication.getPreferredSize());
		textPublication.setSize(textPublication.getPreferredSize());
		
		add(textPublication, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension (520, textPublication.getPreferredSize().height+16));
		this.setMaximumSize(new Dimension (Integer.MAX_VALUE, textPublication.getPreferredSize().height+16));
		this.setMinimumSize(new Dimension (520, textPublication.getPreferredSize().height+16));
		
		
	}
}