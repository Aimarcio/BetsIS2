package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.*;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class VerApuestas extends JFrame {

	private JPanel contentPane;
	private User usuarioAct;
	private JScrollPane scrollPane = new JScrollPane();
	private JPanel panel = new JPanel();
	private JFrame ventanaAnt;
	/**
	 * Create the frame.
	 */
	public VerApuestas(User u, JFrame ventanaAnterior) {
		ventanaAnt = ventanaAnterior;
		setTitle("Apuestas");
		usuarioAct=u;
		crearPaneles();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblApuestas = new JLabel("Usuario: "+usuarioAct.getUserVisibleName());
		contentPane.add(lblApuestas, BorderLayout.PAGE_START);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		

		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnNewButton, BorderLayout.PAGE_END);
		repaint();
	}
	
	private void crearPaneles() {
		List<Apuesta> la = AdminGUI.getBusinessLogic().getApuestas(usuarioAct.getUsername());
		for(Apuesta a:la) {
		PanelApuesta panelaux = new PanelApuesta(a,ventanaAnt, this);
		panelaux.setMaximumSize(new Dimension(Integer.MAX_VALUE,90));
		panelaux.setPreferredSize(new Dimension(400,90));
		panel.add(panelaux);
		}
	}
}
