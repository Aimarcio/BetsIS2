package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import businessLogic.BLFacade;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import domain.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import exceptions.PreguntaYaResuelta;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelApuesta extends JPanel {
	JButton btnCancel;
	Apuesta apuesta;
	JPanel panel = this;
	JFrame ventanaAnt;
	JFrame ventana;
	JLabel lblEvento;
	JLabel lblPregunta;
	JLabel lblPronostico;
	JLabel lblCantidad;
	JLabel lblGanancia;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	/**
	 * Create the panel.
	 */
	public PanelApuesta(Apuesta a, JFrame ventanaAnterior, JFrame v) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				handleResize();
			}
		});
		
		apuesta = a;
		ventana = v;
		ventanaAnt=ventanaAnterior;
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new LineBorder(new Color(0, 0, 0), 2)));
				int	result = 0;
				infoApuesta inf = new infoApuesta(apuesta);
				result = inf.showDialog();
				System.out.println(result);
				switch(result) {
				case 0:
				break;
				case 1:
					BLFacade businessLogic = AdminGUI.getBusinessLogic();
					try {
						User u = businessLogic.cancelBet(apuesta);
						if(LOGIN.getUsuarioAct().getUsername().equals(u.getUsername())){
							LOGIN.setUsuarioAct(u);
						}
						panel.setVisible(false);
					}catch(PreguntaYaResuelta exc) {
						JOptionPane.showMessageDialog(panel, "Error al cancelar la apuesta: "+exc.getMessage());
					}
				break;
				case 2:
					MainGUI gui = new MainGUI(ventanaAnt);
					gui.setCurrentEvent(apuesta.getPronostico().getQuestion().getEvent());
					gui.setCurrentQuestion(apuesta.getPronostico().getQuestion());
					gui.setupShowQuestions();
					gui.setupShowPronosticos();
					Header.actualizarMenu(gui);
					ventanaAnt.setVisible(false);
					ventana.setVisible(false);
					gui.setVisible(true);
				break;
				default:
				break;
				} 
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				setBorder(new LineBorder(new Color(0, 0, 0), 2));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		setLayout(null);
		
		lblEvento = new JLabel(apuesta.getPronostico().getQuestion().getEvent().toString());
		lblEvento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEvento.setBounds(10, 7, 207, 25);
		add(lblEvento);
		
		lblPregunta = new JLabel(apuesta.getPronostico().getQuestion().getQuestion());
		lblPregunta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPregunta.setBounds(10, 32, 223, 25);
		add(lblPregunta);
		
		lblPronostico = new JLabel(apuesta.getPronostico().getPronostico());
		lblPronostico.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPronostico.setBounds(10, 61, 207, 14);
		add(lblPronostico);
		
		lblCantidad = new JLabel(Integer.toString(apuesta.getCantidad()));
		lblCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCantidad.setBounds(280, 11, 81, 14);
		add(lblCantidad);
		
		lblGanancia = new JLabel(Integer.toString((int)(apuesta.getCantidad()*apuesta.getRatio())));
		lblGanancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGanancia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGanancia.setBounds(286, 61, 75, 14);
		add(lblGanancia);
		
		lblNewLabel = new JLabel("Cantidad:");
		lblNewLabel.setBounds(243, 12, 75, 14);
		add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Ganancia:");
		lblNewLabel_1.setBounds(243, 63, 75, 14);
		add(lblNewLabel_1);
		if(a.getPronostico().getQuestion().getResult() != null) {
			if(a.getPronostico().equals(a.getPronostico().getQuestion().getResult())) {
				setBackground(Color.GREEN);
			}else {
				setBackground(Color.RED);
			}
		}
		

	}
	private void handleResize() {
		int width = this.getWidth();
				lblEvento.setBounds(10, 7, width-197, 25);
				lblPregunta.setBounds(10, 32,  width-177, 25);
				lblPronostico.setBounds(10, 61,  width-193, 14);
				lblCantidad.setBounds( width-120, 11, 81, 14);
				lblGanancia.setBounds( width-114, 61, 75, 14);
				lblNewLabel.setBounds( width-157, 12, 75, 14);
				lblNewLabel_1.setBounds( width-157, 63, 75, 14);
	}
}
