package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

public class CrearEquipo extends JFrame {

	private JPanel contentPane;
	private JFrame ventanaAnt;
	private JTextField textNombre;
	private JTextField textJugador;
	private JList lista;
	private JLabel lblError = new JLabel("");
	private DefaultListModel<String> modelo = new DefaultListModel<String>();
	BLFacade facade = AdminGUI.getBusinessLogic();

	public CrearEquipo(JFrame ventanaAnterior) {
		setTitle("Crear Equipo");
		ventanaAnt = ventanaAnterior;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaAnt.setVisible(true);
				dispose();
			}
		});
		btnExit.setBounds(335, 402, 89, 23);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel = new JLabel("Nombre del equipo:");
		lblNewLabel.setBounds(23, 11, 214, 14);
		contentPane.add(lblNewLabel);
		
		textNombre = new JTextField();
		textNombre.setBounds(23, 24, 270, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Jugador:");
		lblNewLabel_1.setBounds(23, 47, 214, 14);
		contentPane.add(lblNewLabel_1);
		
		textJugador = new JTextField();
		textJugador.setBounds(23, 64, 270, 20);
		contentPane.add(textJugador);
		textJugador.setColumns(10);
		
		JButton btnAnadir = new JButton("Anadir");
		btnAnadir.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblError.setText("");
			}
		});
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textJugador.getText().equals("")) {
					lblError.setText("Nombre vacio");
				} else {
					if (modelo.contains(textJugador.getText())) lblError.setText("Ya existe ese jugador");
					else modelo.addElement(textJugador.getText());
				}
			}
		});
		btnAnadir.setBounds(335, 63, 89, 23);
		contentPane.add(btnAnadir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 124, 270, 301);
		contentPane.add(scrollPane);
		
		JList<String> list = new JList<String>();
		lista = list;
		scrollPane.setViewportView(list);
		list.setModel(modelo);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblError.setText("");
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lista.getSelectedValue() == null) {
					lblError.setText("No hay un jugador seleccionado");
				} else {
					modelo.removeElement(lista.getSelectedValue());
				}
			}
		});
		btnBorrar.setBounds(335, 122, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblError.setText("");
			}
		});
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textNombre.getText().equals("") || modelo.isEmpty()) {
					lblError.setText("Hay campos vacios");
				} else {
					facade.crearEquipo(textNombre.getText(), parseLista(modelo));
					JOptionPane.showMessageDialog(contentPane, "El equipo se ha creado correctamente.");
					ventanaAnt.setVisible(true);
					dispose();
				}
			}
		});
		btnCrear.setBounds(335, 368, 89, 23);
		contentPane.add(btnCrear);
		
		
		lblError.setForeground(Color.RED);
		lblError.setBounds(23, 91, 270, 14);
		contentPane.add(lblError);
	}
	
	public List<String> parseLista(DefaultListModel<String> modelu){
		List<String> dab = new ArrayList<String>();
		while(!modelu.isEmpty()) {
			dab.add(modelu.remove(0));
		}
		return dab;
	}
	
}
