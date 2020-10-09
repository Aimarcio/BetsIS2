package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import businessLogic.BLFacade;
import domain.User;
import exceptions.ObjectNotFound;
import exceptions.PermisoDenegado;
import exceptions.UserAlreadyExists;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;

public class MiCuenta extends JFrame {

	private JPanel contentPane;
	private List<User> allUsers;
	private User usuarioAct;
	
	private BLFacade businessLogic = AdminGUI.getBusinessLogic();
	private DefaultListModel<User> usersModel = new DefaultListModel<User>();
	
	
	private final int numberOfEditables = 6;
	private JTextField textUsername;
	private JTextField textNombre;
	private JTextField textApe1;
	private JTextField textApe2;
	private JTextField textDNI;
	private JTextField textEmail;
	private JButton btnEditUsername;
	private JButton btnEditNombre;
	private JButton btnEditApe1;
	private JButton btnEditApe2;
	private JButton btnEditDNI;
	private JButton btnEditEmail;
	private JButton btnSaveUsername;
	private JButton btnSaveNombre;
	private JButton btnSaveApe1;
	private JButton btnSaveApe2;
	private JButton btnSaveDNI;
	private JButton btnSaveEmail;
	private JButton btnCancelUsername;
	private JButton btnCancelNombre;
	private JButton btnCancelApe1;
	private JButton btnCancelApe2;
	private JButton btnCancelDNI;
	private JButton btnCancelEmail;
	private JButton btnSaveAll;
	private JButton btnEditAll;
	private JButton btnCancelAll;
	private JButton btnResetPasswd;
	private JButton btnDeleteUser;
	private JButton btnApuestas;
	
	private JLabel lblUsername;
	private JLabel lblNombre;
	private JLabel lblApe1;
	private JLabel lblApe2;
	private JLabel lblDNI;
	private JLabel lblEmail;
	private JLabel lblUsernameAct;
	private JLabel lblNombreAct;
	private JLabel lblApe1Act;
	private JLabel lblApe2Act;
	private JLabel lblDNIAct;
	private JLabel lblEmailAct;
	private List<JButton> listEdits;
	private List<JButton> listSaves;
	private List<JButton> listCancels;
	private List<JTextField> listTexts;
	private List<JLabel> listLblActs;
	private JFrame ventanaAnt;
	private JFrame frame = this;

	/**
	 * coge una línea (username, email, DNI...) y pone tanto texto como botones preparado para editar o desahace editar (cancelar)
	 * @param indice línea que preparar (0 = Username, 1 = Nombre... , 4 = DNI, 5 = Email)
	 * @param mode si true: lo prepara para editar. Si false: cancel
	 */
	private void setupEdit(int indice, boolean edit) {
		listEdits.get(indice).setVisible(!edit);
		listSaves.get(indice).setVisible(edit);
		listCancels.get(indice).setVisible(edit);
		listTexts.get(indice).setVisible(edit);
		listLblActs.get(indice).setVisible(!edit);
		if(!edit) listTexts.get(indice).setText(listLblActs.get(indice).getText());
	}
	private void actualizarLbls() {
		
			lblUsernameAct.setText(usuarioAct.getUserVisibleName());
			lblNombreAct.setText(usuarioAct.getNombre());
			lblApe1Act.setText(usuarioAct.getApellido1());
			lblApe2Act.setText(usuarioAct.getApellido2());
			lblEmailAct.setText(usuarioAct.getEmail());
			lblDNIAct.setText(usuarioAct.getDNI());
		
	}
	
	
	public JFrame getVentanaAnt() {
		return ventanaAnt;
	}
	public void setVentanaAnt(JFrame ventanaAnt) {
		this.ventanaAnt = ventanaAnt;
	}
	private boolean areUnsavedChanges() {
		return (textUsername.isVisible() |textNombre.isVisible() |textApe1.isVisible() |textApe2.isVisible() |textDNI.isVisible() |textEmail.isVisible());
	}
	private int unsavedChangesDialog() {
		String[] options = {"continuar", "continuar y guardar", "cancelar"};
		int i = JOptionPane.showOptionDialog(contentPane, "Hay cambios sin guardar. Deseas continuar sin guardar?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "continuar");
		switch (i) {
			case 0://continue
				for(int j = 0; j<listTexts.size();j++)setupEdit(j,false);
				return 0;
			case 1://save and continue
				return saveAll();
			case 2://cancel
				return 2;
		}
		return -1;
	}
	private int saveAll() {
		if(textUsername.getText().equals("")) {
			JOptionPane.showMessageDialog(contentPane, "el nombre de usuario no puede estar vacio");
			return -1;
		}
			usuarioAct = businessLogic.updateOwnUser(usuarioAct, textUsername.getText(), textNombre.getText(), textApe1.getText(), textApe2.getText(), textDNI.getText(),textEmail.getText(), usuarioAct.getTarjeta());
			if(LOGIN.getUsuarioAct().getUsername().equals(usuarioAct))
			LOGIN.setUsuarioAct(usuarioAct);
			actualizarLbls();
			for(int j =0;j<numberOfEditables;j++)setupEdit(j,false);
			return 1;

	}
	private void saveRow(int row) {
		String[] parameters = new String[numberOfEditables];
		for (int i= 0; i<parameters.length;i++)
		parameters[i] = listLblActs.get(i).getText();
		parameters[row] = listTexts.get(row).getText();
		System.out.println(parameters[0]);
		if(parameters[0].equals("")) {
			JOptionPane.showMessageDialog(contentPane, "el nombre de usuario no puede estar vacio");
			return;
		}
			usuarioAct = businessLogic.updateOwnUser(usuarioAct, parameters[0], parameters[1], parameters[2], parameters[3], parameters[4], parameters[5], usuarioAct.getTarjeta());
			if(LOGIN.getUsuarioAct().getUsername().equals(usuarioAct))
			LOGIN.setUsuarioAct(usuarioAct);
			actualizarLbls();
			setupEdit(row,false);

		
	}
	
	

	/**
	 * Create the frame.
	 */
	public MiCuenta(JFrame ventanaAnt, User user) {
		this.usuarioAct = user;
		this.ventanaAnt=ventanaAnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(areUnsavedChanges()) {
					if(unsavedChangesDialog()== 2)return;
				}
				Header.actualizarMenu(getVentanaAnt());
				getVentanaAnt().setVisible(true);
				setVisible(false);
			}
		});

		btnExit.setBounds(518, 356, 89, 23);
		contentPane.add(btnExit);
		
		lblUsername = new JLabel("VisibleName:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(203, 44, 83, 14);
		contentPane.add(lblUsername);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(203, 69, 83, 14);
		contentPane.add(lblNombre);
		
		lblApe1 = new JLabel("Apellido1:");
		lblApe1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApe1.setBounds(203, 94, 83, 14);
		contentPane.add(lblApe1);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(203, 169, 83, 14);
		contentPane.add(lblEmail);
		
		lblApe2 = new JLabel("Apellido2:");
		lblApe2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApe2.setBounds(203, 119, 83, 14);
		contentPane.add(lblApe2);
		
		lblDNI = new JLabel("DNI:");
		lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNI.setBounds(203, 144, 83, 14);
		contentPane.add(lblDNI);
		
		lblUsernameAct = new JLabel("");
		lblUsernameAct.setVerticalAlignment(SwingConstants.TOP);
		lblUsernameAct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsernameAct.setBounds(296, 44, 192, 23);
		contentPane.add(lblUsernameAct);
		
		lblNombreAct = new JLabel("");
		lblNombreAct.setVerticalAlignment(SwingConstants.TOP);
		lblNombreAct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreAct.setBounds(296, 69, 192, 23);
		contentPane.add(lblNombreAct);
		
		lblApe1Act = new JLabel("");
		lblApe1Act.setVerticalAlignment(SwingConstants.TOP);
		lblApe1Act.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApe1Act.setBounds(296, 94, 192, 23);
		contentPane.add(lblApe1Act);
		
		lblEmailAct = new JLabel("");
		lblEmailAct.setVerticalAlignment(SwingConstants.TOP);
		lblEmailAct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmailAct.setBounds(296, 169, 192, 23);
		contentPane.add(lblEmailAct);
		
		lblApe2Act = new JLabel("");
		lblApe2Act.setVerticalAlignment(SwingConstants.TOP);
		lblApe2Act.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApe2Act.setBounds(296, 119, 192, 23);
		contentPane.add(lblApe2Act);
		
		lblDNIAct = new JLabel("");
		lblDNIAct.setVerticalAlignment(SwingConstants.TOP);
		lblDNIAct.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDNIAct.setBounds(296, 144, 192, 23);
		contentPane.add(lblDNIAct);
		
		textUsername = new JTextField();
		textUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textUsername.setBounds(296, 43, 192, 20);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		textNombre = new JTextField();
		textNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textNombre.setColumns(10);
		textNombre.setBounds(296, 68, 192, 20);
		contentPane.add(textNombre);
		
		textApe1 = new JTextField();
		textApe1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textApe1.setColumns(10);
		textApe1.setBounds(296, 93, 192, 20);
		contentPane.add(textApe1);
		
		textApe2 = new JTextField();
		textApe2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textApe2.setColumns(10);
		textApe2.setBounds(296, 118, 192, 20);
		contentPane.add(textApe2);
		
		textDNI = new JTextField();
		textDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textDNI.setColumns(10);
		textDNI.setBounds(296, 143, 192, 20);
		contentPane.add(textDNI);
		
		textEmail = new JTextField();
		textEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textEmail.setColumns(10);
		textEmail.setBounds(296, 168, 192, 20);
		contentPane.add(textEmail);
		
		
		btnEditUsername = new JButton("Edit");
		btnEditUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditUsername.setBounds(498, 42, 70, 23);
		contentPane.add(btnEditUsername);
		
		btnEditNombre = new JButton("Edit");
		btnEditNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditNombre.setBounds(498, 67, 70, 23);
		contentPane.add(btnEditNombre);
		
		btnEditApe1 = new JButton("Edit");
		btnEditApe1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditApe1.setBounds(498, 92, 70, 23);
		contentPane.add(btnEditApe1);
		
		btnEditApe2 = new JButton("Edit");
		btnEditApe2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditApe2.setBounds(498, 117, 70, 23);
		contentPane.add(btnEditApe2);
		
		btnEditDNI = new JButton("Edit");
		btnEditDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditDNI.setBounds(498, 142, 70, 23);
		contentPane.add(btnEditDNI);
		
		btnEditEmail = new JButton("Edit");
		btnEditEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditEmail.setBounds(498, 167, 70, 23);
		contentPane.add(btnEditEmail);
		
		btnSaveUsername = new JButton("Save");
		btnSaveUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveUsername.setBounds(498, 44, 70, 23);
		contentPane.add(btnSaveUsername);
		
		btnSaveNombre = new JButton("Save");
		btnSaveNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveNombre.setBounds(498, 69, 70, 23);
		contentPane.add(btnSaveNombre);
		
		btnSaveApe1 = new JButton("Save");
		btnSaveApe1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveApe1.setBounds(498, 94, 70, 23);
		contentPane.add(btnSaveApe1);
		
		btnSaveApe2 = new JButton("Save");
		btnSaveApe2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveApe2.setBounds(498, 119, 70, 23);
		contentPane.add(btnSaveApe2);
		
		btnSaveDNI = new JButton("Save");
		btnSaveDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveDNI.setBounds(498, 144, 70, 23);
		contentPane.add(btnSaveDNI);
		
		btnSaveEmail = new JButton("Save");
		btnSaveEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveEmail.setBounds(498, 169, 70, 23);
		contentPane.add(btnSaveEmail);
		
		btnCancelUsername = new JButton("Cancel");
		btnCancelUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelUsername.setBounds(578, 44, 70, 23);
		contentPane.add(btnCancelUsername);
		
		btnCancelNombre = new JButton("Cancel");
		btnCancelNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelNombre.setBounds(578, 69, 70, 23);
		contentPane.add(btnCancelNombre);
		
		btnCancelApe1 = new JButton("Cancel");
		btnCancelApe1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelApe1.setBounds(578, 94, 70, 23);
		contentPane.add(btnCancelApe1);
		
		btnCancelApe2 = new JButton("Cancel");
		btnCancelApe2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelApe2.setBounds(578, 119, 70, 23);
		contentPane.add(btnCancelApe2);
		
		btnCancelDNI = new JButton("Cancel");
		btnCancelDNI.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelDNI.setBounds(578, 144, 70, 23);
		contentPane.add(btnCancelDNI);
		
		btnCancelEmail = new JButton("Cancel");
		btnCancelEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancelEmail.setBounds(578, 169, 70, 23);
		contentPane.add(btnCancelEmail);
		
		btnSaveAll = new JButton("Save All");
		btnSaveAll.setBounds(430, 203, 89, 23);
		contentPane.add(btnSaveAll);
		
		btnEditAll = new JButton("Edit All");
		btnEditAll.setBounds(530, 203, 89, 23);
		contentPane.add(btnEditAll);
		
		btnCancelAll = new JButton("CancelAll");
		btnCancelAll.setBounds(530, 227, 89, 23);
		contentPane.add(btnCancelAll);
		
		btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.setBounds(203, 311, 192, 23);
		contentPane.add(btnDeleteUser);
		
		btnResetPasswd = new JButton("Change Password");
		btnResetPasswd.setBounds(405, 277, 192, 23);
		contentPane.add(btnResetPasswd);
		
		btnApuestas = new JButton("View Bets");
		btnApuestas.setBounds(203, 277, 192, 23);
		contentPane.add(btnApuestas);
		
		JButton btnNoSeMe = new JButton("No se me ocurre nada");
		btnNoSeMe.setBounds(405, 311, 192, 23);
		contentPane.add(btnNoSeMe);
		
		JButton btnSaveAllExit = new JButton("Save & Exit");
		btnSaveAllExit.setBounds(365, 356, 143, 23);
		contentPane.add(btnSaveAllExit);
		
		listEdits = Arrays.asList(btnEditUsername, btnEditNombre, btnEditApe1,btnEditApe2,btnEditDNI,btnEditEmail);
		listSaves = Arrays.asList(btnSaveUsername, btnSaveNombre, btnSaveApe1,btnSaveApe2,btnSaveDNI,btnSaveEmail);
		listCancels = Arrays.asList(btnCancelUsername, btnCancelNombre, btnCancelApe1,btnCancelApe2,btnCancelDNI,btnCancelEmail);
		listTexts = Arrays.asList(textUsername, textNombre, textApe1,textApe2,textDNI,textEmail);
		listLblActs = Arrays.asList(lblUsernameAct, lblNombreAct, lblApe1Act,lblApe2Act,lblDNIAct,lblEmailAct);
		actualizarLbls();
		for(int i =0; i <numberOfEditables;i++) setupEdit(i,false);
		
		
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					saveAll();
			}
		});
		btnEditAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0;i<numberOfEditables;i++)setupEdit(i,true);

			}
		});
		btnCancelAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0;i<numberOfEditables;i++)setupEdit(i,false);
			}
		});
		btnResetPasswd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(contentPane, "cambios no guardados:/n nombre de usuario en uso");
            	JLabel antigua=new JLabel("Introduzca la anterior cotraseña");
            	JLabel nueva=new JLabel("Introduzca la nueva cotraseña");
                JPasswordField pwd = new JPasswordField();
                JPasswordField pwd2 = new JPasswordField();
                 Object[] ob = {antigua,pwd,nueva,pwd2};
                int action = JOptionPane.showConfirmDialog(null, ob,"Enter Password",JOptionPane.OK_CANCEL_OPTION);
                if(action < 0 || JOptionPane.CANCEL_OPTION==action)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
                else {
                    String pwdAnt=String.valueOf(pwd.getPassword());
                    String pwdAct=String.valueOf(pwd2.getPassword());
                    int x=PassWordCahnge(pwdAct,pwdAnt);
                    switch(x) {
                    case 0:{
                    	JOptionPane.showMessageDialog(null,"Se ha guardado con exito:Su contraseña se ha cambiado con exito ");
                    	break;
                    }case 1:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado: No introduzcas la misma contraseña ");
                    	break;
                    }case 2:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado: Ha introduzido mal la contraseña anterior ");
                    	break;
                    }case 3:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado:La contraseña axctual esta vacia ");
                    	break;
                    }case 4:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado: La contraseña anterior esta vacia ");
                    	break;
                    }case 5:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado: No introduzca contraseñas vacias ");
                    	break;
                    }case 6:{
                    	JOptionPane.showMessageDialog(null,"No se ha guardado: Ha ocurrido algo inerperado ");
                    	break;
                    }
                    }
                }

            }

        });
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int confirm = JOptionPane.showConfirmDialog(contentPane, "¿Estas seguro de que quieres eliminar tu cuenta? \n Esta operacion no permitira recuperar ningún punto.");
				if(confirm == JOptionPane.YES_OPTION) {
				businessLogic.borrarUsuario(usuarioAct);
				LOGIN.setUsuarioAct(null);
				JFrame FindEventsGUI = new MainGUI(null);
				dispose();
				Header.actualizarMenu(FindEventsGUI);
				FindEventsGUI.setVisible(true);
				}
			}
		});
		btnApuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame panel = new VerApuestas(usuarioAct,frame);
				panel.setVisible(true);
			}
		});
		btnNoSeMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSaveAllExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAll();
				Header.actualizarMenu(getVentanaAnt());
				getVentanaAnt().setVisible(true);
				setVisible(false);
			}
		});
		
		btnEditUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(0,true);
			}
		});
		btnEditNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(1,true);
			}
		});
		btnEditApe1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(2,true);
			}
		});
		btnEditApe2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(3,true);
			}
		});
		btnEditDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(4,true);
			}
		});
		btnEditEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(5,true);
			}
		});
		btnSaveUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(0);
			}
		});
		btnSaveNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(1);
			}
		});
		btnSaveApe1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(2);
			}
		});
		btnSaveApe2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(3);
			}
		});
		btnSaveDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(4);
			}
		});
		btnSaveEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRow(5);
			}
		});
		btnCancelUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(0,false);
			}
		});
		btnCancelNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(1,false);
			}
		});
		btnCancelApe1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(2,false);
			}
		});
		btnCancelApe2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(3,false);
			}
		});
		btnCancelDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(4,false);
			}
		});
		btnCancelEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupEdit(5,false);
			}
		});
	}
	public int PassWordCahnge(String pwdAct,String pwdAnt) {
        if(pwdAct.length()>0 & pwdAnt.length()>0) {
            if (pwdAnt.equals(usuarioAct.getPw())) {
            if(!pwdAct.equals(pwdAnt)) {
                businessLogic.CambiarContraseña(usuarioAct, pwdAct);
                return 0;
            }else {
                return 1;
            }
            }else {
                return 2;
            }
        }else if(pwdAct.length()<=0 & pwdAnt.length()>0) {
            return 3;
        }else if(pwdAct.length()>0 & pwdAnt.length()<=0) {
            return 4;
        }else if(pwdAct.length()<=0 & pwdAnt.length()<=0) {
            return 5;
        }
        //no deberia llegar aqui
        return 6;
	}
}

