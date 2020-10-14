package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacadeImplementation;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import domain.*;

public class LOGIN extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton btnChangeToRegister;
	private JLabel lblNewLabel;
	static private User usuarioAct;
	private JFrame ventanaAnterior;
	private JButton btnAtras;

	
	static public User getUsuarioAct() {
		return usuarioAct;
	}
	static public void setUsuarioAct(User usuarioAct) {
		if(usuarioAct==null) {
			LOGIN.usuarioAct=null;
		}else {
			LOGIN.usuarioAct.copy(usuarioAct);
		}
		
	}
	/**
	 * Create the frame.
	 */
	public LOGIN(JFrame ventanaAnt) {
		
		ventanaAnterior = ventanaAnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(79, 211, 294, 39);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Check(textField,passwordField); 
				//check si user existe y si password encaja
				
				login();
			}
		});
		
		usernameField = new JTextField();
		usernameField.setBounds(149, 78, 224, 20);
		usernameField.setColumns(10);
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) login();
			}
		});
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 116, 224, 20);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) login();
			}
		});
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(79, 81, 74, 14);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(79, 122, 74, 14);
		contentPane.setLayout(null);
		contentPane.add(lblUsername);
		contentPane.add(lblPassword);
		contentPane.add(usernameField);
		contentPane.add(passwordField);
		contentPane.add(btnLogIn);
		
		btnChangeToRegister = new JButton("REGISTER");
		btnChangeToRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				REGISTER frame = new REGISTER(ventanaAnterior);
				frame.setVisible(true);
				dispose();
			}
		});
		btnChangeToRegister.setBounds(214, 23, 114, 23);
		contentPane.add(btnChangeToRegister);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(79, 186, 294, 14);
		contentPane.add(lblNewLabel);
		
		btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ventanaAnterior.setVisible(true);
			}
		});
		btnAtras.setBounds(338, 23, 89, 23);
		contentPane.add(btnAtras);
	}
	private void login() {
		BLFacadeImplementation facade=(BLFacadeImplementation) AdminGUI.getBusinessLogic();
		String myPass=String.valueOf(passwordField.getPassword());
		String username = usernameField.getText();
		if(myPass.length() == 0 | username.length() == 0) {
			lblNewLabel.setText("Usuario o contraseña son incorrectas");
			return;
		}
		usuarioAct = facade.login(username, myPass);
		
		if (usuarioAct!=null) {
			
				if(usuarioAct.isAdmin()) {
					AdminGUI frame = new AdminGUI();
					Header.actualizarMenu(frame);
					frame.setVisible(true);
					dispose();
				} else {
					Header.actualizarMenu(ventanaAnterior);
					ventanaAnterior.setVisible(true);
					dispose();
				}
			
		}else {
			lblNewLabel.setText("Usuario o contraseña son incorrectas");
		}
	}
}
