package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.List;
import domain.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PonerResultadoGUI extends JFrame {
	
	private JFrame ventanaAnterior;
	

	private JPanel contentPane;
    private DefaultListModel<Event> listaEventModel= new DefaultListModel<Event>();
	private JList<Event> listEvento = new JList<Event>();
	private DefaultListModel<Question> listaQuestionModel= new DefaultListModel<Question>();
	private JList<Question> listPregunta = new JList<Question>();
	private DefaultListModel<Pronostico> listaPronosticoModel= new DefaultListModel<Pronostico>();
	private JList<Pronostico> listPronostico = new JList<Pronostico>();
	private JButton btnRespuesta = new JButton("Validar respuesta");
	
	public PonerResultadoGUI(JFrame ventanaAnt) {
		ventanaAnterior = ventanaAnt;
		setTitle("Resolver pronostico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEvento = new JLabel("Evento");
		lblEvento.setBounds(39, 29, 85, 14);
		contentPane.add(lblEvento);
		
		JScrollPane scrollPaneEvento = new JScrollPane();
		scrollPaneEvento.setBounds(39, 54, 213, 275);
		contentPane.add(scrollPaneEvento);
		
		btnRespuesta.setEnabled(false);
		
		listEvento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEvento.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				
				domain.Event ev = (Event) listEvento.getSelectedValue();
				listaQuestionModel.clear();
				listaPronosticoModel.clear();
				if(ev==null)return;
				//solo añadir los que no tengan resultado
				for (Question q: ev.getQuestions()) 
					if(q.getResult() == null) listaQuestionModel.addElement(q);
				
				//listaQuestionModel.addAll(ev.getQuestions());
				btnRespuesta.setEnabled(false);
				
			}
		});
		scrollPaneEvento.setViewportView(listEvento);
		
		JScrollPane scrollPanePregunta = new JScrollPane();
		scrollPanePregunta.setBounds(289, 54, 213, 275);
		contentPane.add(scrollPanePregunta);
		
		
		listPregunta.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				
				domain.Question q =  (Question) listPregunta.getSelectedValue();
				listaPronosticoModel.clear();
				if(q==null)return;
				listaPronosticoModel.addAll(q.getPronosticos());
				btnRespuesta.setEnabled(false);
			}
		});
		listPregunta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePregunta.setViewportView(listPregunta);
		
		JScrollPane scrollPanePronostico = new JScrollPane();
		scrollPanePronostico.setBounds(538, 54, 213, 275);
		contentPane.add(scrollPanePronostico);
		
		
		listPronostico.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				
				domain.Pronostico p =  (Pronostico) listPronostico.getSelectedValue();
				if(p==null) {
					btnRespuesta.setEnabled(false);
					return;
				}
				btnRespuesta.setEnabled(true);
				
			}
		});
		listPronostico.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePronostico.setViewportView(listPronostico);
		
		JLabel lblPregunta = new JLabel("Pregunta");
		lblPregunta.setBounds(289, 29, 85, 14);
		contentPane.add(lblPregunta);
		
		JLabel lblPronostico = new JLabel("Pronostico");
		lblPronostico.setBounds(538, 29, 115, 14);
		contentPane.add(lblPronostico);
		
		
		btnRespuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain.Pronostico p =  (Pronostico) listPronostico.getSelectedValue();
				if(p==null) {
					
				}else {
					domain.Question q =  (Question) listPregunta.getSelectedValue();
					listaPronosticoModel.clear();
					listaQuestionModel.removeElement(q);
					if(listaQuestionModel.isEmpty())listaEventModel.removeElement(listEvento.getSelectedValue());
					if(q==null)return;
					AdminGUI.getBusinessLogic().resolverPregunta(p);
				}
			}
		});
		btnRespuesta.setBounds(603, 363, 148, 23);
		contentPane.add(btnRespuesta);
		
		List<domain.Event> listEvent = AdminGUI.getBusinessLogic().getUnresolvedEvents();
		listaEventModel.addAll(listEvent);
		listEvento.setModel(listaEventModel);
		listPregunta.setModel(listaQuestionModel);
		listPronostico.setModel(listaPronosticoModel);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		btnAtras.setBounds(21, 363, 89, 23);
		contentPane.add(btnAtras);
		
		
		
	}
	private void salir() {
		ventanaAnterior.setVisible(true);
		this.dispose();
	}
}
