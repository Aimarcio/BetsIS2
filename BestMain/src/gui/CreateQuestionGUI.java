package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.*;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class CreateQuestionGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	DefaultListModel<Pronostico> modelResult = new DefaultListModel<Pronostico>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Query"));
	private JLabel jLabelMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jTextFieldQuery = new JTextField();
	private JTextField jTextFieldPrice = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private final JLabel lblPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.lblPronostico.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private JTextField Pronostico;
	private final JTextField Cuota = new JTextField();
	private final JLabel lblCuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.lblCuota.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private Vector<Pronostico> vecPro = new Vector<Pronostico>();
	private final JLabel lblPro = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.lblPronostico.text")); //$NON-NLS-1$ //$NON-NLS-2$;
	
	public CreateQuestionGUI(Vector<domain.Event> v) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Cuota.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE)||(!Cuota.getText().contains(".") &&c == KeyEvent.VK_PERIOD))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		Cuota.setBounds(637, 397, 86, 20);
		Cuota.setColumns(10);
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(793, 542));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		final JList list = new JList(modelResult);
		list.setBounds(40, 296, 306, 126);
		getContentPane().add(list);

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(279, 49, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(40, 211, 75, 20));
		jTextFieldQuery.setBounds(new Rectangle(125, 211, 429, 20));
		jLabelMinBet.setBounds(new Rectangle(40, 240, 75, 20));
		
		jTextFieldPrice.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if (!((c >= '0') && (c <= '9') ||(c == KeyEvent.VK_BACK_SPACE) ||(c == KeyEvent.VK_DELETE))) {
			        getToolkit().beep();
			        e.consume();
			      }
			    }
			  });
		
		jButtonCreate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Pronostico.setText("");
				Cuota.setText("");
				jTextFieldQuery.setText("");
				jTextFieldPrice.setText("");
				lblPro.setText("");
				DefaultListModel listModel = (DefaultListModel) list.getModel();
		        listModel.removeAllElements();
		        vecPro.clear();
			}
		});
		
		
		jTextFieldPrice.setBounds(new Rectangle(125, 240, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(237, 462, 130, 30));
		jButtonCreate.setEnabled(false);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(424, 462, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jTextFieldPrice, null);

		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JButton CrearPronostico = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		//btnNewButton.setEnabled(false);
		CrearPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
				try{
				Pronostico p=new Pronostico(Pronostico.getText(),Float.parseFloat(Cuota.getText()));
				boolean esta=false;
				if(Pronostico.getText().length()>0 & Cuota.getText().length()>0){
				for(int i=0;i<modelResult.size();i++) {
					Pronostico j=modelResult.getElementAt(i);
					if(j.getPronostico().equals(p.getPronostico())) esta=true;
				}
				if(!esta) {
				modelResult.addElement(p);
				vecPro.add(p);
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "El pronostico no se ha guardado: el pronostico ya existe");
				}
				}else JOptionPane.showMessageDialog(getContentPane(), "El pronostico no se ha guardado: El pronostico o la cuota deben estar rellenados");

				}catch(Exception E) {
					JOptionPane.showMessageDialog(getContentPane(), "El pronostico no se ha guardado: El pronostico o la cuota deben estar rellenados");
					return;
					}
				}
		});
		CrearPronostico.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				lblPro.setText("");
			}});
		CrearPronostico.setBounds(366, 342, 124, 30);
		getContentPane().add(CrearPronostico);
		
		JButton EliminarPronostico = new JButton("Eliminar Pronostico");
		EliminarPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
				if(!list.isSelectionEmpty()){
					int x=list.getSelectedIndex();
					modelResult.removeElementAt(x);
					vecPro.removeElementAt(x);
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "no se ha podido eliminar: eliga un pronostico a eliminar");
				}
				}
		});
		EliminarPronostico.setBounds(366, 372, 124, 30);
		getContentPane().add(EliminarPronostico);
		
		
		JLabel lblPreguntasDelEvento = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.lblPreguntasDelEvento.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPreguntasDelEvento.setBounds(40, 271, 250, 14);
		getContentPane().add(lblPreguntasDelEvento);
		lblPronostico.setBounds(366, 297, 161, 14);
		
		getContentPane().add(lblPronostico);
		
		Pronostico = new JTextField();
		Pronostico.setBounds(366, 322, 399, 20);
		getContentPane().add(Pronostico);
		Pronostico.setColumns(10);
		
		getContentPane().add(Cuota);
		lblCuota.setBounds(581, 400, 46, 14);
		
		getContentPane().add(lblCuota);
		
		lblPro.setBounds(376, 353, 322, 14);
		getContentPane().add(lblPro);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					jCalendar.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					try {
						BLFacade facade = AdminGUI.getBusinessLogic();
						
						List<domain.Event> events = facade.getEvents(firstDay);
						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarMio.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {
					}

				}
				Pronostico.setText("");
				Cuota.setText("");
				jTextFieldQuery.setText("");
				jTextFieldPrice.setText("");
				lblPro.setText("");
				DefaultListModel listModel = (DefaultListModel) list.getModel();
		        listModel.removeAllElements();
				paintDaysWithEvents(jCalendar);
			}
		});
	}



/* Less eficient version: too many calls to business logic 
	  
	 
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		Calendar calendar = jCalendar.getCalendar();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		int offset = calendar.get(Calendar.DAY_OF_WEEK);
		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		int month = calendar.get(Calendar.MONTH);
		while (month == calendar.get(Calendar.MONTH)) {
			Vector<domain.Event> events = facade.getEvents(calendar.getTime());
			if (events.size() > 0) {
				// Obtain the component of the day in the panel of the DayChooser of the
				// JCalendar.
				// The component is located after the decorator buttons of "Sun", "Mon",... or
				// "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days previous to each day.
				// That number of components is calculated with "offset" and is different in
				// English and Spanish
//				    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
				Component o = (Component) jCalendar.getDayChooser().getDayPanel()
						.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
				o.setBackground(Color.CYAN);
			}
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		calendar.set(Calendar.MONTH, month);
	}

*/
	
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = AdminGUI.getBusinessLogic();

		List<Date> dates=facade.getEventsMonth(jCalendar.getDate());
			
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:dates){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
	 		calendar.set(Calendar.DAY_OF_MONTH, 1);
	 		calendar.set(Calendar.MONTH, month);
	 	
	}
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
		
		try {

			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();

			if (inputQuery.length() > 0) {

				// It could be to trigger an exception if the introduced string is not a number
				int inputPrice = Integer.parseInt(jTextFieldPrice.getText());

				if (inputPrice <= 0)
					JOptionPane.showMessageDialog(getContentPane(), "No se ha guardadola pregunta: Debe introducir un numero positivo");
				else {

					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = AdminGUI.getBusinessLogic();
					if(vecPro.size()<=0) {
						JOptionPane.showMessageDialog(getContentPane(), "No se ha guardadola pregunta: Debe añadir al menos un pronostico");
						//jLabelMsg.setText("Error, introduce pronosticos");
					}else {
					facade.createQuestion(event, inputQuery, inputPrice, vecPro);
					JOptionPane.showMessageDialog(getContentPane(), "Se ha guardado la pregunta correctamente");
					}
				}
			} else {JOptionPane.showMessageDialog(getContentPane(), "No se ha guardadola pregunta: La pregunta no debe estar vacia");
				//jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuery"));
			}
		} catch (EventFinished e1) {
			JOptionPane.showMessageDialog(getContentPane(), "El evento ya ha terminado");
			
		} catch (QuestionAlreadyExist e1) {
			JOptionPane.showMessageDialog(getContentPane(), "No se ha guardadola pregunta: La pregunta ya existe");
			//jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (java.lang.NumberFormatException e1) {
			JOptionPane.showMessageDialog(getContentPane(), "No se ha guardadola pregunta: Debe introducir algun numero en minBet");
			//jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		AdminGUI frame = new AdminGUI();
		Header.actualizarMenu(frame);
		frame.setVisible(true);
	}
}