package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.*;
import domain.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.math.*;



public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	private JFrame ventanaAnt;
	private JFrame estaVentana = this;
	
	BLFacade facade = AdminGUI.getBusinessLogic();
	List<domain.Event> listProximosEventos = facade.getProximosEventos();
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;

	private DefaultTableModel tableModelEvents = new DefaultTableModel();
	private DefaultListModel<domain.Event> modelEvents = new DefaultListModel<domain.Event>();
	private DefaultListModel<Question> modelQuestions = new DefaultListModel<Question>();
	private DefaultListModel<Pronostico> modelPronosticos = new DefaultListModel<Pronostico>();
	private JButton btnCurrentEvent;
	private JButton btnCurrentQuestion;
	private JButton btnCurrentPronostico;
	private JScrollPane scrollPaneEvents;
	private JScrollPane scrollPaneQues;
	private JScrollPane scrollPanePron;
	private JScrollPane scrollPanePE;
	private JList <domain.Event>listEvents;
	private JList <Question>listQuestions;
	private JList <Pronostico>listPronosticos;
	private JTable tableProximosEventos;
	private JTextField textSearch;
	private JPanel panelApuesta;
	private JLabel lblMinBet;
	private JLabel lblCantidadAGanar;
	
	private JTextField textCantidad;
	private domain.Event currentEvent;
	private Question currentQuestion;
	private Pronostico currentPronostico;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
	private JButton btnModificarPregunta;
	private JButton btnEliminarPregunta;
	private JLabel lblError = new JLabel("");
	
	
	
	
	private void setupShowEvents(Collection<domain.Event> events) {
		if (events != null) {
			modelEvents.clear();
			
			modelEvents.addAll(events);
		}
		btnEliminarPregunta.setVisible(false);
		btnModificarPregunta.setVisible(false);
		scrollPaneQues.setVisible(false);
		scrollPanePron.setVisible(false);
		scrollPaneEvents.setVisible(true);
		btnCurrentEvent.setVisible(false);
		btnCurrentQuestion.setVisible(false);
		btnCurrentPronostico.setVisible(false);
		panelApuesta.setVisible(false);
		
	}
	public void setupShowQuestions() {
		if (currentEvent != null) {
			btnCurrentEvent.setText(currentEvent.toString());
			modelQuestions.clear();
			modelQuestions.addAll(currentEvent.getQuestions());
		}
		btnEliminarPregunta.setVisible(false);
		btnModificarPregunta.setVisible(false);
		scrollPaneQues.setVisible(true);
		scrollPanePron.setVisible(false);
		scrollPaneEvents.setVisible(false);
		btnCurrentEvent.setVisible(true);
		btnCurrentQuestion.setVisible(false);
		btnCurrentPronostico.setVisible(false);
		panelApuesta.setVisible(false);
		}
	public void setCurrentEvent(Event e) {
		currentEvent = e;
	}
	public void setupShowPronosticos() {
		if ( currentQuestion != null) {
			btnCurrentQuestion.setText(currentQuestion.getQuestion());
			modelPronosticos.clear();
			modelPronosticos.addAll(currentQuestion.getPronosticos());
		}
		if(LOGIN.getUsuarioAct() != null && LOGIN.getUsuarioAct().isAdmin()) {
			btnEliminarPregunta.setVisible(true);
			btnModificarPregunta.setVisible(true);
			scrollPanePron.setBounds(432, 111, 224, 275);
		}else {
			scrollPanePron.setBounds(432, 111, 224, 297);
		}
		scrollPaneQues.setVisible(false);
		scrollPanePron.setVisible(true);
		scrollPaneEvents.setVisible(false);
		btnCurrentEvent.setVisible(true);
		btnCurrentQuestion.setVisible(true);
		btnCurrentPronostico.setVisible(false);
		panelApuesta.setVisible(false);
	}
	public void setCurrentQuestion(Question q) {
		currentQuestion = q;
	}
	private void setupShowApuesta(){
		if (currentPronostico != null) {
			btnCurrentPronostico.setText(currentPronostico.toString());
			lblMinBet.setText("Min. Bet: "+currentQuestion.getBetMinimum());
			lblCantidadAGanar.setText("Ganarías: 0.0");
			textCantidad.setText("");
		}
		btnEliminarPregunta.setVisible(false);
		btnModificarPregunta.setVisible(false);
		scrollPaneQues.setVisible(false);
		scrollPanePron.setVisible(false);
		scrollPaneEvents.setVisible(false);
		btnCurrentEvent.setVisible(true);
		btnCurrentQuestion.setVisible(true);
		btnCurrentPronostico.setVisible(true);
		panelApuesta.setVisible(true);
	}
	private void handleTableEventsSelection(ListSelectionEvent e){
			if(e.getValueIsAdjusting())return;
			if(tableProximosEventos.getSelectedRow() == -1) return;
	        int row = tableProximosEventos.getSelectedRow();
	        domain.Event ev = (domain.Event)tableModelEvents.getValueAt(row, 0);
	        currentEvent = ev;
	        setupShowQuestions();
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		if (ventanaAnt == null) System.exit(0);
		else {
			Header.actualizarMenu(ventanaAnt);
			ventanaAnt.setVisible(true);
		}
		
		
	}
	
	
	public MainGUI(JFrame ventanaAnt)
	{
		this.ventanaAnt = ventanaAnt;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonClose.setBounds(526, 420, 130, 30);

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButtonClose_actionPerformed(e);
			}
		});
		getContentPane().setLayout(null);

		this.getContentPane().add(jButtonClose);
		jCalendar1.setBounds(0, 23, 413, 261);


		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					try {
						BLFacade facade = AdminGUI.getBusinessLogic();
						
						List<domain.Event> events = facade.getEvents(firstDay);
						setupShowEvents(events);
					}catch(Exception e) {
					}
				}

				CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
			}
		});
		getContentPane().add(jCalendar1);
		
		btnCurrentEvent = new JButton();
		btnCurrentEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listEvents.clearSelection();
				setupShowEvents(null);
			}
		});
		btnCurrentEvent.setBounds(432, 41, 223, 23);
		getContentPane().add(btnCurrentEvent);
		
		btnCurrentQuestion = new JButton();
		btnCurrentQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listQuestions.clearSelection();
				setupShowQuestions();
			}
		});
		btnCurrentQuestion.setBounds(432, 75, 223, 23);
		getContentPane().add(btnCurrentQuestion);
		
		btnCurrentPronostico = new JButton();
		btnCurrentPronostico.setBounds(432, 109, 223, 23);
		btnCurrentPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listPronosticos.clearSelection();
				setupShowPronosticos();
			}
		});
		getContentPane().add(btnCurrentPronostico);
		
		scrollPanePE = new JScrollPane();
		scrollPanePE.setBounds(10, 315, 405, 135);
		getContentPane().add(scrollPanePE);
		
		
		
		
		tableProximosEventos = new JTable();
		tableProximosEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableProximosEventos.setModel(tableModelEvents);
		
		Collections.sort(listProximosEventos); //ordenarlos por fecha
		Vector<String> columnIdentifiers = new Vector<String>(); columnIdentifiers.add("Event");columnIdentifiers.add("Date");
		tableModelEvents.setColumnIdentifiers(columnIdentifiers);
		tableModelEvents.setColumnCount(2);
		for (int i= 0; i<listProximosEventos.size();i++) {
			Object[] row = {listProximosEventos.get(i), sdf.format(listProximosEventos.get(i).getEventDate())};
			tableModelEvents.insertRow(i, row);
		}
		tableProximosEventos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
		        handleTableEventsSelection(e);
		    }
		});
	   
		
		
		scrollPanePE.setViewportView(tableProximosEventos);
		
		scrollPanePron = new JScrollPane();
		
		scrollPanePron.setBounds(432, 111, 224, 297);
		getContentPane().add(scrollPanePron);
		
		listPronosticos = new JList<Pronostico>();
		listPronosticos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPronosticos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())return;
				Pronostico p = listPronosticos.getSelectedValue();
				if (p == null) return;
				currentPronostico = p;
				setupShowApuesta();
			}
		});
		listPronosticos.setModel(modelPronosticos);
		scrollPanePron.setViewportView(listPronosticos);
		
		scrollPaneQues = new JScrollPane();
		scrollPaneQues.setBounds(432, 76, 224, 332);
		getContentPane().add(scrollPaneQues);
		
		listQuestions = new JList<Question>();
		listQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listQuestions.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())return;
				Question q = listQuestions.getSelectedValue();
				if (q == null) return;
				currentQuestion = q;
				setupShowPronosticos();
			}
		});
		listQuestions.setModel(modelQuestions);
		scrollPaneQues.setViewportView(listQuestions);
		
		scrollPaneEvents = new JScrollPane();
		scrollPaneEvents.setBounds(432, 41, 224, 367);
		getContentPane().add(scrollPaneEvents);
		
		listEvents = new JList<domain.Event>();
		listEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEvents.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())return;
				Event ev = listEvents.getSelectedValue();
				if (ev == null) return;
				currentEvent = ev;
				setupShowQuestions();
				
			}
		});
		listEvents.setModel(modelEvents);
		scrollPaneEvents.setViewportView(listEvents);
		
		panelApuesta = new JPanel();
		panelApuesta.setBounds(433, 156, 223, 254);
		getContentPane().add(panelApuesta);
		panelApuesta.setLayout(null);
		
		JButton btnApostar = new JButton("Apostar");
		btnApostar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblError.setText("");
			}
		});
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (LOGIN.getUsuarioAct() == null) {
					LOGIN frame = new LOGIN(estaVentana);
					estaVentana.setVisible(false);
					frame.setVisible(true);
					return;
				}
				if(textCantidad.getText().equals("")) {
					lblError.setText("Introduce una cantidad a apostar");
					return;
				}
				if(currentPronostico == null) {
					System.out.println("el pronostico es nulo");return;
				}
				int cantidad = (int) (Integer.parseInt(textCantidad.getText()));
				try {
					facade.apostar(LOGIN.getUsuarioAct(), currentPronostico, cantidad);
					LOGIN.getUsuarioAct().restarPuntos(cantidad);
					Header.actualizarMenu(estaVentana);
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(estaVentana, exc.getMessage());
				}
				
			}
		});
		btnApostar.setBounds(64, 161, 89, 23);
		panelApuesta.add(btnApostar);
		
		textCantidad = new JTextField();
		textCantidad.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textCantidad.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textCantidad.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  doSomething();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  doSomething();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    doSomething();
			  }

			  public void doSomething() {
				 String text = textCantidad.getText();
			     if(text.length() == 0) lblCantidadAGanar.setText("Ganarías: 0");
			     else {
			    	 try {
			    		 lblCantidadAGanar.setText("Ganarías: "+(int)(Integer.parseInt(text) *currentPronostico.getCuota()));
			    	 }catch(Exception e) {
			    		 System.out.println("problema al sacar el int");
			    	 }
			     }
			  }
			});
		 
		textCantidad.setBounds(37, 97, 143, 20);
		panelApuesta.add(textCantidad);
		
		
		
		textSearch = new JTextField();
		textSearch.setBounds(10, 290, 121, 23);
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				tableProximosEventos.clearSelection();
				//limpiar contenido actual de la tabla
				tableModelEvents.setRowCount(0);
				//añadir los que coinciden con la busqueda
				int indexRow = 0;
				for (domain.Event ev : listProximosEventos) {
					if(ev.toString().toLowerCase().contains(textSearch.getText().toLowerCase())) {
						Object[] row = {ev, sdf.format(ev.getEventDate())};
						tableModelEvents.insertRow(indexRow, row);
						indexRow++;
					}
					
				}
					
				
			}
		});
		getContentPane().add(textSearch);
		
		lblMinBet = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblMinBet.setBounds(38, 72, 100, 14);
		panelApuesta.add(lblMinBet);
		
		lblCantidadAGanar = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblCantidadAGanar.setForeground(Color.GRAY);
		lblCantidadAGanar.setBounds(47, 128, 100, 14);
		panelApuesta.add(lblCantidadAGanar);
		

		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 195, 203, 14);
		panelApuesta.add(lblError);
		
		btnEliminarPregunta = new JButton("Eliminar"); //$NON-NLS-1$ //$NON-NLS-2$
		btnEliminarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.borrarPregunta(currentQuestion);
				currentEvent.removeQuestion(currentQuestion);
				setupShowQuestions();
			}
		});
		btnEliminarPregunta.setBounds(449, 386, 89, 23);
		getContentPane().add(btnEliminarPregunta);
		btnModificarPregunta = new JButton("Modificar"); //$NON-NLS-1$ //$NON-NLS-2$
		btnModificarPregunta.setBounds(548, 386, 89, 23);
		btnModificarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ModificarPreguntas = new ModificarPreguntas(currentQuestion); 
				ModificarPreguntas.setVisible(true);
			}
		});
		getContentPane().add(btnModificarPregunta);
		
		
		
		setupShowEvents(null);
	}
}
