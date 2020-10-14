package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;

import businessLogic.BLFacade;
import domain.*;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MostrarUsuario extends JFrame {
	private JPanel contentPane;
	private final JScrollPane scrollPane = new JScrollPane();
	private JFrame ventanaAnterior;
	private BLFacade bl = AdminGUI.getBusinessLogic();
	private boolean followingUser = false;
	private User userLogeado;
	private User userMostrado;
	private JButton btnFollow;
	private JPanel panelPublicaciones;
	private JTextArea textPublish;
	private JFrame panel = this;
	private JToggleButton btnMoreOptions;
	private JPopupMenu menu;
	private JMenuItem copyUser = new JMenuItem("Copiar usuario");
	/**
	 * Create the frame.
	 */
	public MostrarUsuario(JFrame ventanaAnt, User user) {

		userLogeado = LOGIN.getUsuarioAct();
		userMostrado = user;
		ventanaAnterior = ventanaAnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setLayout(null);
		scrollPane.setBounds(0, 175, 540, 241);
		contentPane.add(scrollPane);
		
		panelPublicaciones = new JPanel();
		mostrarPublicaciones();
		scrollPane.setViewportView(panelPublicaciones);
		panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
		
		btnFollow = new JButton("Follow");
		btnFollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userLogeado == null) abrirLogin();
				else if(followingUser) {
					try{
						bl.unfollow(userLogeado.getUsername(), userMostrado.getUsername());
						btnFollow.setBackground(null);
						btnFollow.setText("Follow");
						followingUser = false;
					}catch ( Exception exc) {
						JOptionPane.showMessageDialog(contentPane, "Ha ocurrido un error al intentar dejar de hacer follow");
					}
				}else {
					try{
						bl.follow(userLogeado.getUsername(), userMostrado.getUsername());
						btnFollow.setText("Unfollow");
						btnFollow.setBackground(new Color(255,80,80));
						followingUser = true;
					}catch ( Exception exc) {
						JOptionPane.showMessageDialog(contentPane, "Ha ocurrido un error al intentar hacer follow");
					}
				}
				
				
			}
		});
		btnFollow.setBounds(423, 48, 89, 23);
		contentPane.add(btnFollow);
		
		JLabel lblVisibleName = new JLabel("Solo quiero ver el tama\u00F1o");
		lblVisibleName.setBounds(57, 52, 204, 14);
		lblVisibleName.setText(userMostrado.getUserVisibleName());
		contentPane.add(lblVisibleName);
		
		JButton btnEdit = new JButton("");
		
		ImageIcon editIcon = new ImageIcon(MostrarUsuario.class.getResource("/images/edit icon.png"));
		Image img = editIcon.getImage();
		Image newImg = img.getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH);
		editIcon.setImage(newImg);
		btnEdit.setIcon(editIcon);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCuenta();
			}
		});
		btnEdit.setBounds(10, 48, 23, 23);
		contentPane.add(btnEdit);
		
		JButton btnVerApuestas = new JButton("Ver Apuestas");
		btnVerApuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new VerApuestas(userMostrado,panel);
				frame.setVisible(true);
			}
		});
		btnVerApuestas.setBounds(290, 48, 123, 23);
		contentPane.add(btnVerApuestas);
		
		JButton btnPublish = new JButton("Publish");
		btnPublish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgPublicacion =textPublish.getText();
				msgPublicacion = msgPublicacion.replaceAll("(?!\\r)\\n", "<br>");
				AdminGUI.getBusinessLogic().publicar(userLogeado.getUsername(), msgPublicacion);
				textPublish.setText("");
			}
		});
		btnPublish.setBounds(451, 82, 89, 69);
		contentPane.add(btnPublish);
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atras();
			}
		});
		textPublish = new JTextArea();
		
		textPublish.setLineWrap(true);
		textPublish.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane (textPublish);
		
		scroll.setBounds(0, 82, 445, 82);
		contentPane.add(scroll);
		
		btnAtras.setBounds(451, 427, 89, 23);
		contentPane.add(btnAtras);
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPublicaciones();
			}
		});
		btnRefresh.setBounds(517, 152, 23, 23);
		ImageIcon refreshIcon = new ImageIcon(MostrarUsuario.class.getResource("/images/refresh.jpg"));
		Image rfimage = refreshIcon.getImage(); // transform it 
		Image rfnewimg = rfimage.getScaledInstance(23, 23,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		refreshIcon = new ImageIcon(rfnewimg);  // transform it back
		btnRefresh.setIcon(refreshIcon);
		contentPane.add(btnRefresh);
		
		
		btnMoreOptions = new JToggleButton("");
		btnMoreOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JToggleButton b = btnMoreOptions;
                 if (b.isSelected()) {
                     menu.show(b, 0, b.getBounds().height);
                 } else {
                     menu.setVisible(false);
                 }
			}
		});
		menu = new JPopupMenu();
        menu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            	btnMoreOptions.setSelected(false);
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
		
		
		if(userLogeado!=null) {
			for(Copia c: userMostrado.getCopiando()) {
				if(c.getUserCopiando().equals(userLogeado)) {
					copyUser.setText("Dejar de copiar");
					break;
				}
			}
		}
		copyUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(copyUser.getText().equals("Dejar de copiar")) {
					bl.updateUsuarioCopiado(userLogeado, userMostrado, 0);
					copyUser.setText("Copiar usuario");
				} else {
					ratioDialog ratioD = new ratioDialog();
					double ratio = ratioD.showDialog();
					if(ratio <=0) {
						return;
					}
					bl.updateUsuarioCopiado(userLogeado, userMostrado, ratio);
					copyUser.setText("Dejar de copiar");
				}
			}
		});
		menu.add(copyUser);
		ImageIcon moreOptionsIcon = new ImageIcon(MostrarUsuario.class.getResource("/images/more options icon.png"));
		Image image = moreOptionsIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(23, 23,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		moreOptionsIcon = new ImageIcon(newimg);  // transform it back
		btnMoreOptions.setIcon(moreOptionsIcon);
		btnMoreOptions.setBounds(516, 48, 23, 23);
		btnMoreOptions.setContentAreaFilled(false);
		contentPane.add(btnMoreOptions);
		
	
		if(userLogeado != null && user.getUsername().equals(userLogeado.getUsername())) {
			//estas viendo tu propio perfil, todo esta activo menos seguirte
			btnFollow.setEnabled(false);
			btnMoreOptions.setEnabled(false);
		}else {
			
				scrollPane.setBounds(0, 100, 540, 314);
				btnRefresh.setBounds(517, 77, 23, 23);
				scroll.setVisible(false);//scroll de textPublish
				btnPublish.setVisible(false);
				if(userLogeado !=null && !userLogeado.isAdmin())
				btnEdit.setVisible(false);
				if(userLogeado != null) {
					List<User> listFollowing = bl.getFollowing(userLogeado.getUsername());
					for(User u : listFollowing)
						if(u.getUsername().equals(user.getUsername())){
							btnFollow.setText("Unfollow");
							btnFollow.setBackground(new Color(255,80,80));
							followingUser = true;
							break;
						}
				} else {
					btnEdit.setVisible(false);
					btnMoreOptions.setVisible(false);
				}
			
		}
		if(userMostrado.getSettings().isApuestasprivadas()) {
			btnVerApuestas.setVisible(false);
		}
		setContentPane(contentPane);
		}
	
	private void abrirLogin() {
		if (userLogeado == null) {
			LOGIN frame = new LOGIN(this);
			this.setVisible(false);
			frame.setVisible(true);
		}
	}
	private void editarCuenta() {
		JFrame frame = new MiCuenta(this,userMostrado);
		this.setVisible(false);
		frame.setVisible(true);
	}
	private void atras() {
		this.setVisible(false);
		Header.actualizarMenu(ventanaAnterior);
		ventanaAnterior.setVisible(true);
	}
	private void mostrarPublicaciones() {
		panelPublicaciones.removeAll();
		List<Publication> listPublications = AdminGUI.getBusinessLogic().getPublications(userMostrado.getUsername());
		for(Publication p:listPublications) 
			panelPublicaciones.add(new PanelPublicacion(p));
		
	}
}
