package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;
import exceptions.PronosticoAlreadyExist;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ModificarPreguntas extends JFrame {

	BLFacade facade = AdminGUI.getBusinessLogic();
	private JPanel contentPane;
	private JTextField textFieldPregunta;
	private JTextField textFieldMinbet;
	private JTextField textFieldEditPronostico;
	private JTextField textFieldEditCuota;
	private JTextField textFieldCreatePronostico;
	private JTextField textFieldCreateCuota;
	private Question currentQuestion;
	private DefaultListModel<Pronostico> modelPronosticos = new DefaultListModel<Pronostico>();
	private Pronostico currentPronostico;
	private JList<Pronostico> list; 	
	private JLabel lblErrores = new JLabel("");
	private JLabel lblErroresG = new JLabel("");
	private JLabel lblPregunta = new JLabel("");
	private String pregunta;
	private String minimo;
	private JFrame frame = this;
	/**
	 * Create the frame.
	 */
	public ModificarPreguntas(Question q) {
		currentQuestion = q;
		pregunta = q.getQuestion();
		minimo = Integer.toString(q.getBetMinimum());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldPregunta = new JTextField();
		textFieldPregunta.setBounds(10, 30, 200, 20);
		contentPane.add(textFieldPregunta);
		textFieldPregunta.setColumns(10);
		textFieldPregunta.setText(currentQuestion.getQuestion());
		
		textFieldMinbet = new JTextField();
		textFieldMinbet.setBounds(334, 30, 96, 20);
		contentPane.add(textFieldMinbet);
		textFieldMinbet.setColumns(10);
		textFieldMinbet.setText(Integer.toString(currentQuestion.getBetMinimum()));
		textFieldMinbet.addKeyListener(new KeyAdapter() {
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
		
		JLabel lblNewLabel = new JLabel("Pregunta actual:");
		lblNewLabel.setBounds(10, 11, 174, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Min Bet:");
		lblNewLabel_1.setBounds(334, 11, 96, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Pronosticos:");
		lblNewLabel_2.setBounds(10, 57, 73, 14);
		contentPane.add(lblNewLabel_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(220, 204, 324, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_3 = new JLabel("Editar:");
		lblNewLabel_3.setBounds(220, 83, 48, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Crear:");
		lblNewLabel_4.setBounds(220, 211, 48, 14);
		contentPane.add(lblNewLabel_4);
		
		textFieldEditPronostico = new JTextField();
		textFieldEditPronostico.setBounds(220, 108, 179, 20);
		contentPane.add(textFieldEditPronostico);
		textFieldEditPronostico.setColumns(10);
		
		textFieldEditCuota = new JTextField();
		textFieldEditCuota.setBounds(220, 139, 179, 20);
		contentPane.add(textFieldEditCuota);
		textFieldEditCuota.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE)||(!textFieldEditCuota.getText().contains(".") &&c == KeyEvent.VK_PERIOD))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldEditCuota.setColumns(10);
		
		JButton btnEditPronostico = new JButton("Guardar");
		btnEditPronostico.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblErroresG.setText("");
			}
		});
		btnEditPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentPronostico==null) {
					lblErroresG.setText("Elige un pronostico");
					return;
				}
				if(textFieldEditPronostico.getText().equals("") || textFieldEditCuota.getText().equals("")) {
					lblErroresG.setText("Escribe un pronostico y una cuota");
					return;
				}
				currentPronostico.setCuota(Integer.parseInt(textFieldEditCuota.getText()));
				currentPronostico.setPronostico(textFieldEditPronostico.getText());
				list.repaint();
			}
		});
		btnEditPronostico.setBounds(455, 107, 89, 23);
		contentPane.add(btnEditPronostico);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblErroresG.setText("");
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentPronostico==null) {
					lblErroresG.setText("Elige un pronostico");
					return;
				}
				currentQuestion.removePronostico(currentPronostico);
				modelPronosticos.removeElement(currentPronostico);
				currentPronostico= null;
				list.repaint();
			}
		});
		btnBorrar.setBounds(455, 172, 89, 23);
		contentPane.add(btnBorrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 200, 268);
		contentPane.add(scrollPane);
		
		list = new JList<Pronostico>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		modelPronosticos.addAll(currentQuestion.getPronosticos());
		list.setModel(modelPronosticos);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					return;
				}
				currentPronostico=list.getSelectedValue();
				if(currentPronostico!=null) {
					textFieldEditPronostico.setText(currentPronostico.getPronostico());
					textFieldEditCuota.setText(Double.toString(currentPronostico.getCuota()));
				}
				else {
					textFieldEditPronostico.setText("");
					textFieldEditCuota.setText("");
				}
			}
		});

		
		
		textFieldCreatePronostico = new JTextField();
		textFieldCreatePronostico.setBounds(220, 236, 179, 20);
		contentPane.add(textFieldCreatePronostico);
		textFieldCreatePronostico.setColumns(10);
		
		textFieldCreateCuota = new JTextField();
		textFieldCreateCuota.setBounds(220, 267, 179, 20);
		contentPane.add(textFieldCreateCuota);
		textFieldCreateCuota.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE)||(!textFieldCreateCuota.getText().contains(".") &&c == KeyEvent.VK_PERIOD))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		textFieldCreateCuota.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Pronostico");
		lblNewLabel_5.setBounds(455, 239, 89, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Cuota");
		lblNewLabel_6.setBounds(455, 270, 89, 14);
		contentPane.add(lblNewLabel_6);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblErrores.setText("");
			}
		});
		btnCrear.setBounds(455, 295, 89, 23);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldCreatePronostico.getText().equals("")) {
					lblErrores.setText("El texto de pronostico esta vacio");
					return;
				}
				if(textFieldCreateCuota.getText().equals("")) {
					lblErrores.setText("El texto de cuota esta vacio");
					return;
				}
				Pronostico x = new Pronostico(textFieldCreatePronostico.getText(), Double.parseDouble(textFieldCreateCuota.getText()));
				x.setQuestion(currentQuestion);
				modelPronosticos.addElement(x);
				list.repaint();
			}
		});
		contentPane.add(btnCrear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(455, 329, 89, 23);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnExit);
		
		lblErrores.setForeground(Color.RED);
		lblErrores.setBounds(220, 299, 179, 14);
		contentPane.add(lblErrores);
		
		lblErroresG.setForeground(Color.RED);
		lblErroresG.setBounds(220, 170, 179, 14);
		contentPane.add(lblErroresG);
		

		lblPregunta.setBounds(95, 11, 214, 14);
		contentPane.add(lblPregunta);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Pronostico> lu = new Vector<Pronostico>();
				while(!modelPronosticos.isEmpty()) {
					lu.add(modelPronosticos.remove(0));
				}
				if(lu.isEmpty() || textFieldPregunta.getText().equals("")|textFieldMinbet.getText().equals("")) {
					lblErrores.setText("Introduce un valor en todos los campos necesarios (Pregunta, minimo y pronosticos)");
				}else {
					try {

						facade.editarPregunta(currentQuestion.getQuestionNumber(), textFieldPregunta.getText(),Integer.parseInt(textFieldMinbet.getText()), lu);

						frame.dispose();
					}catch(Exception exc) {
						JOptionPane.showMessageDialog(ModificarPreguntas.this,"La pregunta no ha sido guardada: "+ exc.getMessage());
					}
				}
			}
		});
		btnSave.setBounds(356, 329, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnNewButton = new JButton("X");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldPregunta.setText(pregunta);
			}
		});
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setToolTipText("X");
		btnNewButton.setBounds(220, 29, 39, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("X");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMinbet.setText(minimo);
			}
		});
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setToolTipText("X");
		btnNewButton_1.setBounds(440, 29, 39, 23);
		contentPane.add(btnNewButton_1);
	}
}
