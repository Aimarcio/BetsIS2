package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollPane;

public class CreateNotificacionGeneral extends JFrame {

	private JPanel contentPane;
	private JTextField textTitulo;
	private JLabel lblErrorMsg;
	private JLabel lblErrorTitulo;
	private JTextArea textMensaje;


	/**
	 * Create the frame.
	 */
	public CreateNotificacionGeneral() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo:");
		lblTitulo.setBounds(10, 11, 48, 14);
		contentPane.add(lblTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setBounds(10, 32, 414, 20);
		contentPane.add(textTitulo);
		textTitulo.setColumns(10);
		
		JLabel lblMensaje = new JLabel("Mensaje");
		lblMensaje.setBounds(10, 63, 48, 14);
		contentPane.add(lblMensaje);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(40, 394, 113, 36);
		contentPane.add(btnCancel);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblErrorTitulo.setText("");
				lblErrorMsg.setText("");
			}
		});
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean sendable = true;
				String titulo = textTitulo.getText();
				if(titulo.equals("")) {
					sendable = false;
					lblErrorTitulo.setText("Titulo Vacio");
				}
				
				String mensaje = textMensaje.getText().replaceAll("(?!\\r)\\n", "<br>");;
				if(mensaje.equals("")) {
					sendable = false;
					lblErrorMsg.setText("Mensaje Vacio");
				}
				if(sendable) {
					AdminGUI.getBusinessLogic().notificacionGeneral(titulo, mensaje);
					JOptionPane.showMessageDialog(contentPane, "Notificacion Enviada");
					dispose();
				}
				
				
			}
		});
		btnEnviar.setBounds(277, 394, 113, 36);
		contentPane.add(btnEnviar);
		
		lblErrorMsg = new JLabel("");
		lblErrorMsg.setForeground(Color.RED);
		lblErrorMsg.setBounds(92, 63, 197, 14);
		contentPane.add(lblErrorMsg);
		
		lblErrorTitulo = new JLabel("");
		lblErrorTitulo.setForeground(Color.RED);
		lblErrorTitulo.setBounds(68, 11, 153, 14);
		contentPane.add(lblErrorTitulo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 414, 295);
		contentPane.add(scrollPane);
		
		textMensaje = new JTextArea();
		textMensaje.setLineWrap(true);
		textMensaje.setWrapStyleWord(true);
		scrollPane.setViewportView(textMensaje);
	}
}
