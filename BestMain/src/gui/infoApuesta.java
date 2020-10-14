package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import domain.*;
import exceptions.PreguntaYaResuelta;
public class infoApuesta extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	private Apuesta apuesta;
	public int result;
	public infoApuesta(Apuesta a) {
		super(null, ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		apuesta = a;
		setTitle("Info");
		setBounds(100, 100, 308, 199);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Apuesta: " +apuesta.getPronostico().getQuestion().getQuestion());
		lblNewLabel.setBounds(67, 31, 316, 14);
		contentPanel.add(lblNewLabel);
		
		JButton btnInfo = new JButton("Mas informacion");
		btnInfo.setBounds(67, 56, 171, 23);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = 2;
				setVisible(false);
				dispose();
			}
		});
		contentPanel.add(btnInfo);
		
		JButton btnCancel = new JButton("Cancelar apuesta");
		btnCancel.setBounds(67, 90, 171, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = 1;
				setVisible(false);
				dispose();
			}
		});
		if(LOGIN.getUsuarioAct()==null) {
		} else if(apuesta.getUser().equals(LOGIN.getUsuarioAct())|LOGIN.getUsuarioAct().isAdmin()) {	
			contentPanel.add(btnCancel);
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JButton btnExit = new JButton("Exit");
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					result=0;
						setVisible(false);
					dispose();
				}
			});
			buttonPane.add(btnExit);		
	}
	 int showDialog() {
	    setVisible(true);
	    return result;
	}
}
