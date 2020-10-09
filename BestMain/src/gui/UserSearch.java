package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.naming.directory.SearchResult;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import domain.Equipo;
import domain.User;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserSearch extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JList<User> list;
	private JList<Equipo> listE;
	private DefaultListModel<User> listModel;
	private DefaultListModel<Equipo> listModelE;
	private JFrame ventanaAnterior;
	private JToggleButton btnEquipo = new JToggleButton("Buscar equipos");
	private JToggleButton btnUser = new JToggleButton("Buscar usuarios");
	private JTextField textBusqueda = new JTextField();
	private JFrame panel = this;
	private final JButton btnMore = new JButton("Mostrar mas");
	private String busquedaAct;
	private int index;
	
	/**
	 * Create the frame.
	 */
	public UserSearch(JFrame ventanaAnt) {
		setTitle("Buscador");
		ventanaAnterior = ventanaAnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 435, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 399, 403);
		contentPane.add(scrollPane);
		
		list = new JList<User>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())return;
				handleSelection();
				
			}
		});
		
		listModel = new DefaultListModel<User>();
		list.setModel(listModel);
		listE = new JList<Equipo>();
		listE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listE.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())return;
				handleSelectionE();	
			}
		});
		listModelE = new DefaultListModel<Equipo>();
		listE.setModel(listModelE);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.dispose();
				ventanaAnterior.setVisible(true);
				Header.actualizarMenu(ventanaAnterior);
			}
		});
		btnExit.setBounds(320, 507, 89, 23);
		contentPane.add(btnExit);
		textBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					cambiarBusqueda(textBusqueda.getText());
				}
			}
		});
		
		
		textBusqueda.setBounds(10, 35, 399, 20);
		contentPane.add(textBusqueda);
		textBusqueda.setColumns(10);
		textBusqueda.setText(Header.getTextBusqueda());

		btnUser.setBounds(10, 59, 175, 23);
		btnUser.setSelected(true);
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					btnUser.setSelected(true);
					btnEquipo.setSelected(false);
					index = 0;
					ampliarBusqueda();
			}
		});
		contentPane.add(btnUser);

		btnEquipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					btnUser.setSelected(false);
					btnEquipo.setSelected(true);
					index = 0;
					ampliarBusqueda();
			}
		});
		btnEquipo.setBounds(234, 59, 175, 23);
		contentPane.add(btnEquipo);
		btnMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ampliarBusqueda();
			}
		});
		btnMore.setBounds(150, 507, 133, 23);
		
		contentPane.add(btnMore);
		Header.actualizarMenu(this);
	}
	private void ampliarBusqueda() {
			BLFacade bl= AdminGUI.getBusinessLogic();
			if(index == 0) {
				listModel.removeAllElements();
				listModelE.removeAllElements();
			}
			if(btnUser.isSelected()) {
				scrollPane.setViewportView(list);
				List<User> searchResult = bl.search100Users(busquedaAct, index);
				for(int i = searchResult.size()-1;i>-1;i--) {
					listModel.addElement(searchResult.get(i));
				}
				if(searchResult.size()<100) {
					btnMore.setEnabled(false);
				}
			} else {
				scrollPane.setViewportView(listE);
				List<Equipo> searchResult = bl.search100Equipos(busquedaAct, index);
				for(int i = searchResult.size()-1;i>-1;i--) {
					listModelE.addElement(searchResult.get(i));
				}
				if(searchResult.size()<100) {
					btnMore.setEnabled(false);
				}
			}
			index = index + 100;
	}
	public void cambiarBusqueda(String text) {
		listModel.removeAllElements();
		listModelE.removeAllElements();
		index = 0;
		btnMore.setEnabled(true);
		busquedaAct=text;
		ampliarBusqueda();
	}
	private void handleSelection() {
		User u = list.getSelectedValue();
		if(u!=null) {
			list.clearSelection();
			JFrame frame = new MostrarUsuario(this,u);
			Header.actualizarMenu(frame);
			this.setVisible(false);
			frame.setVisible(true);
		}
		
	}
	private void handleSelectionE() {
		Equipo u = listE.getSelectedValue();
		if(u!=null) {
			listE.clearSelection();
			JFrame frame = new MostrarEquipo(this,u);
			Header.actualizarMenu(frame);
			this.setVisible(false);
			frame.setVisible(true);
		}
		
	}
	
	public void updateText(String s) {
		textBusqueda.setText(s);
	}
	
}
