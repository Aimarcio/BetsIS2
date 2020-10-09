package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import domain.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TarjetaTemp extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JCheckBox chckbxNewCheckBox;
	private BLFacade businessLogic = AdminGUI.getBusinessLogic();
	private User usuarioAct;
	private JFrame ventanaAnterior;
	public static String Tarjeta;

	/**
	 * Create the dialog.
	 */
	public TarjetaTemp(JFrame ventanaAnt) {
		ventanaAnterior = ventanaAnt;
		usuarioAct = LOGIN.getUsuarioAct();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Por favor introduce una tarjeta para esta sesion");
		lblNewLabel.setBounds(10, 11, 334, 14);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 189, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		chckbxNewCheckBox = new JCheckBox("Guardar en la cuenta");
		chckbxNewCheckBox.setBounds(225, 35, 189, 23);
		contentPanel.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_1 = new JLabel("Esta operacion no realizara la compra");
		lblNewLabel_1.setBounds(10, 115, 334, 14);
		contentPanel.add(lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (textField.getText().length()>0) {	
							if (chckbxNewCheckBox.isSelected()) {
								try {
									User u = businessLogic.updateOwnUser(usuarioAct, usuarioAct.getUserVisibleName(), usuarioAct.getNombre(), usuarioAct.getApellido1(), usuarioAct.getApellido2(), usuarioAct.getDNI(), usuarioAct.getEmail(), textField.getText());
									LOGIN.setUsuarioAct(u);
								} catch(Exception exc) {
									System.out.println("Ha habido un error");
								}
							} else {
								TarjetaTemp.Tarjeta = textField.getText();
							}
							ventanaAnterior.setVisible(true);
							setVisible(false);
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ventanaAnterior.setVisible(true);
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
