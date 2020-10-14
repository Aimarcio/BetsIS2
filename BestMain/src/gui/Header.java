package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import domain.User;

public class Header {
	static private JMenuBar menuBar;
	static private JMenu mnUserName;
	static private JMenu mnGuest;
	static private JButton puntos;
	static private User usuarioMenu;
	static private JFrame ventanaAct;
	static private JTextField search;
	static private JButton notificaciones;
	
	static public JMenuBar getMenuBar() {
		if (menuBar == null) {
			initialize();
		}
		return menuBar;
	}
	
	static public void initialize() {
		menuBar = new JMenuBar();
		usuarioMenu  = null;
		initializeMnUserName();
		initializeMnGuest();
		initializePuntos();
		initializeNotificaciones();
		search = new JTextField();
		search.setFont(new Font("Tahoma", Font.PLAIN, 14));
		search.setMaximumSize(new Dimension(200,20));
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(ventanaAct.getClass().equals(UserSearch.class)) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER) {
						((UserSearch) ventanaAct).cambiarBusqueda(search.getText());
					} else {
						((UserSearch) ventanaAct).updateText(search.getText());
					}
				}else {
					if(e.getKeyChar() == KeyEvent.VK_ENTER) {
						ventanaAct.setVisible(false);
						UserSearch frame = new UserSearch(ventanaAct);
						frame.setVisible(true);
						frame.cambiarBusqueda(search.getText());
					}
				}
			}
		});
		menuBar.add(mnGuest);
		menuBar.add(search);
	}
	static private void initializePuntos() {
		puntos = new JButton();
		puntos.setBorderPainted(false);
		//puntos.setFocusPainted(false);
		puntos.setContentAreaFilled(false);
		puntos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PacksDePuntos frame = new PacksDePuntos(ventanaAct);
				ventanaAct.setVisible(false);
				frame.setVisible(true);
			}
			
		});
	}
	static private void initializeMnUserName() {
		mnUserName = new JMenu(""); 

		JMenuItem mntmMiCuenta = new JMenuItem("Usuario"); 
		mntmMiCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MiCuenta frame = new MiCuenta(ventanaAct,usuarioMenu);
				frame.setVisible(true);
				ventanaAct.setVisible(false);
				
			}
		});
		mnUserName.add(mntmMiCuenta);
		
		JMenuItem mntmAjustes = new JMenuItem("Ajustes");
		mntmAjustes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AjustesGUI frame= new AjustesGUI(ventanaAct);
				frame.setVisible(true);
				ventanaAct.setVisible(false);
				
				
			}
		});
		mnUserName.add(mntmAjustes);

		JMenuItem mntmLogout = new JMenuItem("Logout"); 
		mntmLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainGUI frame = new MainGUI(null);
				LOGIN.setUsuarioAct(null);
				ventanaAct.dispose();
				Header.actualizarMenu(frame);
				frame.setVisible(true);
				
			}
		});
		mnUserName.add(mntmLogout);
	}
	
	static private void initializeMnGuest() {
		mnGuest = new JMenu("Guest"); 

		JMenuItem mntmMiCuenta = new JMenuItem("Register"); 
		
		mntmMiCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				REGISTER frame = new REGISTER(ventanaAct);
				frame.setVisible(true);
				ventanaAct.dispose();
			}
		});
		mnGuest.add(mntmMiCuenta);
		
		JMenuItem mntmAjustes = new JMenuItem("Ajustes");
		mnGuest.add(mntmAjustes);

		
		JMenuItem mntmLogin = new JMenuItem("Login"); 
		mntmLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LOGIN frame = new LOGIN(ventanaAct);
				ventanaAct.dispose();
				frame.setVisible(true);
				
				
			}
		});
		mnGuest.add(mntmLogin);
	}
	/**
	 * 
	 * @param usuarioAct debe ser !=null
	 */
	static private void actualizarUsername(User usuarioAct) {
		mnUserName.setText(usuarioAct.getUserVisibleName());
		puntos.setText(Integer.toString(usuarioAct.getPuntos()) );
	}
	
	static public void actualizarMenu(JFrame newFrame) {
		ventanaAct = newFrame;
		menuBar.setBounds(0, 0, ventanaAct.getWidth(), 21);
		User usuarioAct = LOGIN.getUsuarioAct();
		if (usuarioMenu != null & usuarioAct != null) {
			actualizarUsername(usuarioAct);
			
		}else if(usuarioMenu == null & usuarioAct != null) {
			menuBar.removeAll();
			actualizarUsername(usuarioAct);
			menuBar.add(mnUserName);
			menuBar.add(puntos);
			menuBar.add(search);
			menuBar.add(notificaciones);
			
		}else if(usuarioMenu != null & usuarioAct == null){
			menuBar.removeAll();
			menuBar.add(mnGuest);
			menuBar.add(search);
		}else if(usuarioMenu == null & usuarioAct == null) {
			
		}
		usuarioMenu = usuarioAct;
		ventanaAct.getContentPane().add(menuBar);
	}
	static public String getTextBusqueda() {
		return search.getText();
	}
	
	static private void initializeNotificaciones() {
		notificaciones = new JButton();
		ImageIcon imgIcon = new ImageIcon(MostrarUsuario.class.getResource("/images/inbox icon.png"));
		Image img = imgIcon.getImage();
		Image newImg = img.getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH);
		imgIcon.setImage(newImg);
		notificaciones.setIcon(imgIcon);
		notificaciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame not = new Notificaciones();
				not.setVisible(true);
			}
			
		});
		//notificaciones.setIcon(defaultIcon);
		
	}
}
