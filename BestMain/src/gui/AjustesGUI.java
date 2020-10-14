package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Ajustes;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AjustesGUI extends JFrame {

	private JPanel contentPane;
	private JCheckBox chckbxApuestasPrivadas = new JCheckBox("Apuestas privadas");
	private JCheckBox chckbxApuesta = new JCheckBox("Apuesta");
	private JCheckBox chckbxPublicaciones = new JCheckBox("Publicaciones");
	private JCheckBox chckbxPerfilPrivado = new JCheckBox("Perfil privado");
	private Ajustes ajustesIni; 
	private JFrame ventanaAnt;

	public AjustesGUI(JFrame ventanaAct) {
		ajustesIni = LOGIN.getUsuarioAct().getSettings();
		setTitle("Ajustes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ventanaAnt=ventanaAct;
		
		chckbxApuesta.setSelected(ajustesIni.isNotificacionesApuesta());
		chckbxPublicaciones.setSelected(ajustesIni.isNotificacionesPublicaciones());
		chckbxPerfilPrivado.setSelected(ajustesIni.isPerfil());
		chckbxApuestasPrivadas.setSelected(ajustesIni.isApuestasprivadas());
		
		chckbxApuesta.setBounds(59, 63, 284, 23);
		contentPane.add(chckbxApuesta);
		
		chckbxPublicaciones.setBounds(59, 89, 284, 23);
		contentPane.add(chckbxPublicaciones);
		
		chckbxPerfilPrivado.setBounds(59, 149, 284, 23);
		contentPane.add(chckbxPerfilPrivado);
		
		chckbxApuestasPrivadas.setBounds(59, 175, 284, 23);
		contentPane.add(chckbxApuestasPrivadas);
		
		JLabel lblNotificaciones = new JLabel("Notificaciones:");
		lblNotificaciones.setBounds(10, 40, 77, 14);
		contentPane.add(lblNotificaciones);
		
		JLabel lblPrivacidad = new JLabel("Privacidad:");
		lblPrivacidad.setBounds(10, 124, 77, 14);
		contentPane.add(lblPrivacidad);
		
		JButton btnGuardarSalir = new JButton("Guardar y Salir");
		btnGuardarSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ajustes ajustes = new Ajustes(chckbxPublicaciones.isSelected(),chckbxApuesta.isSelected(),chckbxPerfilPrivado.isSelected(),chckbxApuestasPrivadas.isSelected());
				AdminGUI.getBusinessLogic().updateAjustes(LOGIN.getUsuarioAct().getUsername(), ajustes);
				LOGIN.getUsuarioAct().setSettings(ajustes);
				salir();
			}
		});
		btnGuardarSalir.setBounds(92, 227, 152, 23);
		contentPane.add(btnGuardarSalir);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ajustes ajustes = new Ajustes(chckbxPublicaciones.isSelected(),chckbxApuesta.isSelected(),chckbxPerfilPrivado.isSelected(),chckbxApuestasPrivadas.isSelected());
				if(!ajustes.equals(ajustesIni)) {
					String[] options = {"continuar", "continuar y guardar", "cancelar"};
					int i = JOptionPane.showOptionDialog(contentPane, "Hay cambios sin guardar. Deseas continuar sin guardar?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "continuar");
					switch (i) {

					case 1://save and continue
						AdminGUI.getBusinessLogic().updateAjustes(LOGIN.getUsuarioAct().getUsername(), ajustes);
						LOGIN.getUsuarioAct().setSettings(ajustes);
					case 0://continue
						salir();
						break;
					case 2://cancel
						break;
				}
				}else salir();
			}
		});
		btnSalir.setBounds(254, 227, 89, 23);
		contentPane.add(btnSalir);
	}
	public void salir() {
		ventanaAnt.setVisible(true);
		this.dispose();
	}
}
