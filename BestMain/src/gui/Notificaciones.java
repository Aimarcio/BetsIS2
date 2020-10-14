package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Notification;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Notificaciones extends JFrame {

	private JPanel contentPane;
	private BLFacade facade = AdminGUI.getBusinessLogic();
	private JPanel panel = new JPanel();
	private JLabel lblTitulo = new JLabel("");
	private JTextArea textMensaje = new JTextArea();
	private JScrollPane scrollPane_1 = new JScrollPane();
	/**
	 * Create the frame.
	 */
	public Notificaciones() {
		setTitle("Notificaciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 300, 439);
		contentPane.add(scrollPane);
		
		
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		
		lblTitulo.setBounds(330, 11, 644, 28);
		contentPane.add(lblTitulo);
		
		
		scrollPane_1.setBounds(330, 62, 644, 388);
		scrollPane_1.setAutoscrolls(false);
		contentPane.add(scrollPane_1);
		
		
		textMensaje.setLineWrap(true);
		textMensaje.setWrapStyleWord(true);
		textMensaje.setEditable(false);
		scrollPane_1.setViewportView(textMensaje);
		initializeButtons();
	}
	
	public class BotonNot extends JButton {
		private Notification notificacion;

		public BotonNot(Notification not) {
			super();
			this.notificacion = not;
			this.setText(not.getTitulo());
			if(!notificacion.isRead()) {
				this.setFont(new Font("Tahoma", Font.BOLD, 12));
			} else {
				this.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					lblTitulo.setText(notificacion.getTitulo());
					textMensaje.setText(notificacion.getMessage().replaceAll("<br>", System.lineSeparator()));
					
					if(!notificacion.isRead()) {
						update();
					}
				}
				
			});
			
		}
		public void update() {
			facade.setRead(notificacion.getId());
			this.setFont(new Font("Tahoma", Font.PLAIN, 12));
			notificacion.setRead(true);
		}

		public Notification getNotificacion() {
			return notificacion;
		}

		public void setNotificacion(Notification notificacion) {
			this.notificacion = notificacion;
		}
		
	}
	
	public void initializeButtons() {
		List<Notification> lista = facade.getNotificaciones(LOGIN.getUsuarioAct().getUsername());
		for(Notification p:lista) {
			BotonNot aux = new BotonNot(p);
			aux.setMaximumSize(new Dimension(Integer.MAX_VALUE, aux.getMinimumSize().height));
			panel.add(aux);
		}
	}
}
