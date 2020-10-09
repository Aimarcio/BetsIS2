package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import domain.User;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnRAdmin;
	private JFrame contentPane;
	private JButton btnCreateTeam;
	



	/**
	 * This is the default constructor
	 */
	public AdminGUI() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = this;
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(498, 446);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getPanel());
			
			JButton btnCreateEvent = new JButton();
			btnCreateEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreateEventGUI frame = new CreateEventGUI();
					Header.actualizarMenu(frame);
					frame.setVisible(true);
					setVisible(false);
				}
			});
			btnCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnCreateEvent.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnCreateEvent.setBounds(0, 177, 238, 62);
			jContentPane.add(btnCreateEvent);
			
			JButton btnFindUsers = new JButton();
			btnFindUsers.setText("Find Users"); //$NON-NLS-1$ //$NON-NLS-2$
			btnFindUsers.setBounds(241, 53, 238, 62);
			jContentPane.add(btnFindUsers);
			jContentPane.add(getBtnRAdmin());
			jContentPane.add(getBtnCreateTeam());
			
			JButton btnNotificacionGeneral = new JButton("Notificacion General");
			btnNotificacionGeneral.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new CreateNotificacionGeneral();
					frame.setVisible(true);
				}
			});
			btnNotificacionGeneral.setBounds(0, 239, 238, 62);
			jContentPane.add(btnNotificacionGeneral);
			
			JButton btnPonerRespuesta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.btnPonerRespuesta.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnPonerRespuesta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new PonerResultadoGUI(contentPane);
					frame.setVisible(true);
					contentPane.setVisible(false);
				}
			});
			btnPonerRespuesta.setBounds(241, 239, 238, 62);
			jContentPane.add(btnPonerRespuesta);
			btnFindUsers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FindUsers frame = new FindUsers();
					frame.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jContentPane;
	}


	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(0, 115, 238, 62);
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BLFacade facade=AdminGUI.getBusinessLogic();
					//Vector<Event> events=facade.getAllEvents();
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					Header.actualizarMenu(a);
					a.setVisible(true);
					setVisible(false);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(0, 53, 238, 62);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actionPerformedBoton3(e);
				}
			});
		}
		return jButtonQueryQueries;
	}
	private void actionPerformedBoton3(java.awt.event.ActionEvent e) {
		JFrame a = new MainGUI(this);

		Header.actualizarMenu(a);
		a.setVisible(true);
		setVisible(false);
	}
	

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 479, 62);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 334, 479, 62);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	private JButton getBtnRAdmin() {
		if (btnRAdmin == null) {
			btnRAdmin = new JButton(); 
			btnRAdmin.setText("Register Admin");
			btnRAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame Rpanel = new REGISTERAdmin(contentPane);
					Rpanel.setVisible(true);
					setVisible(false);
				}
			});
			btnRAdmin.setBounds(241, 115, 238, 62);
		}
		return btnRAdmin;
	}
	
	private JButton getBtnCreateTeam() {
		if (btnCreateTeam == null) {
			btnCreateTeam = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnCreateTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame eq = new CrearEquipo(contentPane);
					contentPane.setVisible(false);
					eq.setVisible(true);
				}
			});
			btnCreateTeam.setBounds(241, 177, 238, 62);
		}
		return btnCreateTeam;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

