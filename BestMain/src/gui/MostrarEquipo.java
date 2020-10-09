package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Equipo;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarEquipo extends JFrame {

	private JPanel contentPane;
	private Equipo equipo;
	private JLabel lblNombre = new JLabel("New label");
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> listJugador = new JList<String>();
	private JFrame ventanaAnt;
	private JFrame ventanaAct = this;
	private final JButton btnExit = new JButton("Exit");
	
	/**
	 * Create the frame.
	 */
	public MostrarEquipo(JFrame v, Equipo e) {
		setTitle("Equipo");
		equipo = e;
		ventanaAnt = v;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNombre.setText(equipo.getNombre());
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNombre.setBounds(10, 18, 664, 39);
		contentPane.add(lblNombre);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 62, 281, 338);
		contentPane.add(scrollPane);
		listJugador.setModel(listModel);
		listModel.addAll(equipo.getJugadores());
		scrollPane.setViewportView(listJugador);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaAct.dispose();
				ventanaAnt.setVisible(true);
				Header.actualizarMenu(ventanaAnt);
			}
		});
		btnExit.setBounds(585, 377, 89, 23);
		
		contentPane.add(btnExit);
	
	}
}
