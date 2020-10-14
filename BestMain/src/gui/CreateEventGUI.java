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

public class CreateEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	private DefaultComboBoxModel<Equipo> modelEquipos1 = new DefaultComboBoxModel<Equipo>();
	private DefaultComboBoxModel<Equipo> modelEquipos2 = new DefaultComboBoxModel<Equipo>();
	private JComboBox<Equipo> cbEquipo1;
	private JComboBox<Equipo> cbEquipo2;
	
	
	
	private DefaultListModel<Pronostico> modelResult = new DefaultListModel<Pronostico>();
	private JLabel jLabelDesc = new JLabel("Description");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jTextFieldDesc = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton("Create Event");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();
	private Date firstDay;
	
	public CreateEventGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEventGUI.this.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(600, 501));
		jLabelDesc.setBounds(new Rectangle(59, 367, 75, 20));
		
		jTextFieldDesc.setBounds(new Rectangle(161, 367, 245, 20));

		jCalendar.setBounds(new Rectangle(103, 66, 376, 240));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				jLabelError.setText("");
			}
		});

		jButtonCreate.setBounds(new Rectangle(126, 409, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(308, 409, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBounds(100, 0, 93, 91);
		addPopup(getContentPane(), popupMenu);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(126, 378, 312, 20));
		jLabelError.setForeground(Color.red);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldDesc, null);
		this.getContentPane().add(jLabelDesc, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(103, 33, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		List<Equipo> listEquipos = AdminGUI.getBusinessLogic().getAllEquipos();
		modelEquipos1.addAll(listEquipos);
		modelEquipos2.addAll(listEquipos);
		
		cbEquipo1 = new JComboBox<Equipo>();
		cbEquipo1.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent event) {
				 
			     if (event.getStateChange() == ItemEvent.SELECTED) {
			         modelEquipos2.removeElement((Equipo) event.getItem());
			     }else {
			    	 modelEquipos2.addElement((Equipo) event.getItem());
			     }
			 }
		});
		cbEquipo1.setBounds(59, 319, 152, 22);
		cbEquipo1.setModel(modelEquipos1);
		getContentPane().add(cbEquipo1);
		
		cbEquipo2 = new JComboBox<Equipo>();
		cbEquipo2.addItemListener(new ItemListener() {
			 public void itemStateChanged(ItemEvent event) {
				 
			     if (event.getStateChange() == ItemEvent.SELECTED) {
			         modelEquipos1.removeElement((Equipo) event.getItem());
			     }else {
			    	 modelEquipos1.addElement((Equipo) event.getItem());
			     }
			 }
		});
		cbEquipo2.setBounds(339, 317, 152, 22);
		cbEquipo2.setModel(modelEquipos2);
		getContentPane().add(cbEquipo2);

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
					firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					System.out.println(firstDay);
				}
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
        String s = jTextFieldDesc.getText();
        if (s.length()==0) jLabelError.setText("Escribe una descripcion");
        else if (firstDay == null) jLabelError.setText("Seleccione una fecha");
        else if(cbEquipo1.getSelectedItem() == null || cbEquipo2.getSelectedItem() == null) jLabelError.setText("Seleccione ambos equipos");
        else {
            BLFacade facade = AdminGUI.getBusinessLogic();
            boolean repeated = facade.repeatedEvent(s, firstDay);
            if (repeated) jLabelError.setText("Este evento ya existe");
            else {

                try {
                    facade.createEvent(s, firstDay,(Equipo) cbEquipo1.getSelectedItem(), (Equipo) cbEquipo2.getSelectedItem());
                    jLabelError.setText("Evento creado");
                    jTextFieldDesc.setText("");
                } catch (EventFinished e1) {
                    jLabelError.setText("Elige una fecha valida");
                }
            }
        }

    }

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		AdminGUI frame = new AdminGUI();
		Header.actualizarMenu(frame);
		frame.setVisible(true);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}