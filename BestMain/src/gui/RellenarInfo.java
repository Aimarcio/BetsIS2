package gui;

import java.awt.BorderLayout;
import domain.*;
import exceptions.PermisoDenegado;
import exceptions.UserAlreadyExists;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RellenarInfo extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private User usuarioAct;
	private BLFacade businessLogic = AdminGUI.getBusinessLogic();
	private JFrame ventanaAnterior;
	/**
	 * Create the frame.
	 */
	public RellenarInfo(JFrame ventanaAnt) {
		ventanaAnterior = ventanaAnt;
		this.usuarioAct = LOGIN.getUsuarioAct();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Por favor, rellena los siguientes campos antes de apostar");
		lblNewLabel.setBounds(68, 11, 294, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(31, 49, 93, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setBounds(31, 74, 93, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Apellido");
		lblNewLabel_3.setBounds(31, 99, 93, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Segundo Apellido");
		lblNewLabel_4.setBounds(31, 124, 93, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Dni");
		lblNewLabel_5.setBounds(31, 149, 93, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Tarjeta de Credito");
		lblNewLabel_6.setBounds(31, 174, 93, 14);
		contentPane.add(lblNewLabel_6);
		
		textField_1 = new JTextField();
		textField_1.setBounds(170, 71, 96, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		if (usuarioAct.getNombre()!=null & !usuarioAct.getNombre().equals("")) {
			textField_1.setText(usuarioAct.getNombre());
			textField_1.setEnabled(false);
		}
		
		textField_2 = new JTextField();
		textField_2.setBounds(170, 96, 96, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		if (usuarioAct.getApellido1()!=null & !usuarioAct.getApellido1().equals("")) {
			textField_2.setText(usuarioAct.getApellido1());
			textField_2.setEnabled(false);
		}
		
		textField_3 = new JTextField();
		textField_3.setBounds(170, 121, 96, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		if (usuarioAct.getApellido2()!=null & !usuarioAct.getApellido2().equals("")) {
			textField_3.setText(usuarioAct.getApellido2());
			textField_3.setEnabled(false);
		}
		
		textField_4 = new JTextField();
		textField_4.setBounds(170, 146, 96, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		if (usuarioAct.getDNI()!=null & !usuarioAct.getDNI().equals("")) {
			textField_4.setText(usuarioAct.getDNI());
			textField_4.setEnabled(false);
		}
		
		textField_5 = new JTextField();
		textField_5.setBounds(170, 171, 96, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		if (usuarioAct.getTarjeta()!=null & !usuarioAct.getTarjeta().equals("")) {
			textField_5.setText(usuarioAct.getTarjeta());
			textField_5.setEnabled(false);
		}
		
		JLabel lblNewLabel_7 = new JLabel(usuarioAct.getUsername());
		lblNewLabel_7.setBounds(170, 49, 96, 14);
		contentPane.add(lblNewLabel_7);
		
		JButton CloseButton = new JButton("Close");
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				ventanaAnterior.setVisible(true);
			}
		});
		CloseButton.setBounds(335, 227, 89, 23);
		contentPane.add(CloseButton);
		
		JButton ReadyButton = new JButton("Terminado");
		ReadyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					User x = businessLogic.updateOwnUser(usuarioAct, usuarioAct.getUserVisibleName(), textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(), usuarioAct.getEmail(), textField_5.getText());
					LOGIN.getUsuarioAct().copy(x);;
					usuarioAct=x;
				setVisible(false);
				ventanaAnterior.setVisible(true);
			}
		});
		ReadyButton.setBounds(31, 227, 121, 23);
		contentPane.add(ReadyButton);
		
		JLabel lblNewLabel_8 = new JLabel("Esta operacion no realizara la compra");
		lblNewLabel_8.setBounds(31, 199, 361, 14);
		contentPane.add(lblNewLabel_8);
	}
}
