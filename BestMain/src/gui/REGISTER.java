package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import businessLogic.*;

public class REGISTER extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel;
	private JFrame ventanaAnterior;
    private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * Create the frame.
	 */
	public REGISTER(JFrame ventanaAnt) {
		ventanaAnterior = ventanaAnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(44, 41, 101, 14);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(44, 72, 101, 14);
		
		JLabel lblPasswordAgane = new JLabel("Password Agane");
		lblPasswordAgane.setBounds(44, 103, 101, 14);
		
		JLabel lblEmail = new JLabel("e-mail");
		lblEmail.setBounds(44, 134, 80, 14);
		
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(textField.getText().length()!=0 && textField_3.getText().length()!=0 && passwordField_1.getPassword().length!=0 && String.valueOf(passwordField_1.getPassword()).equals(String.valueOf(passwordField.getPassword())) ) {
					//hay que acceder a la bd para crear un nuevo cliente nuevo(user,email,password)
					BLFacadeImplementation facade=(BLFacadeImplementation) AdminGUI.getBusinessLogic();
					
					try{
						facade.register(textField.getText(),String.valueOf(passwordField_1.getPassword()),textField_3.getText());
						LOGIN frame = new LOGIN(ventanaAnterior);
						frame.setVisible(true);
						dispose();
						}
					catch (Exception UserAlreadyExists) {
						lblNewLabel.setText("°Este usuario ya existe!");
						
					}
					
				} else if(passwordField_1.getPassword().length!=0 && !String.valueOf(passwordField_1.getPassword()).equals(String.valueOf(passwordField.getPassword()))){
					lblNewLabel.setText("La contrase√±a no coincide");
				}else {
					lblNewLabel.setText("Falta algun elemento");
				}
				
			}
		});
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(95, 179, 243, 32);
		contentPane.add(lblNewLabel);
		
		btnRegister.setBounds(95, 215, 243, 51);
		
		textField = new JTextField();
		textField.setBounds(142, 38, 196, 20);
		textField.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(142, 131, 196, 20);
		textField_3.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(142, 100, 196, 20);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(142, 69, 196, 20);
		contentPane.setLayout(null);
		contentPane.add(lblUsername);
		contentPane.add(lblPassword);
		contentPane.add(lblPasswordAgane);
		contentPane.add(lblEmail);
		contentPane.add(btnRegister);
		contentPane.add(textField);
		contentPane.add(textField_3);
		contentPane.add(passwordField);
		contentPane.add(passwordField_1);
		
		JButton btnChangeToLog = new JButton("LOG IN");
		btnChangeToLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGIN frame = new LOGIN(ventanaAnterior);
				frame.setVisible(true);
				dispose();
			}
		});
		btnChangeToLog.setBounds(215, 4, 101, 23);
		contentPane.add(btnChangeToLog);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ventanaAnterior.setVisible(true);
			}
		});
		btnAtras.setBounds(335, 4, 89, 23);
		contentPane.add(btnAtras);
		
		
		
	}


}
