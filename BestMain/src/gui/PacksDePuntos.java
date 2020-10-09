package gui;

import domain.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import exceptions.*;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
public class PacksDePuntos extends JFrame {

	private JPanel contentPane;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JRadioButton rdbtnNewRadioButton_3;
	private JRadioButton rdbtnNewRadioButton_4;
	private ButtonGroup bg = new ButtonGroup();
	private BLFacade businessLogic = AdminGUI.getBusinessLogic();
	private JFrame ventanaAnterior;
	
	/**
	 * Create the frame.
	 */
	public PacksDePuntos(JFrame ventanaAnt) {
		setTitle("Comprar puntos");
		if (ventanaAnt != null) {
			ventanaAnterior = ventanaAnt;//poner ventanaAnt a null significa que no quieres cambiar la ventanaAnt que tenia, no que no tenga ventanaAnt.
			System.out.println("cambiando ventana anterior");
		}
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rdbtnNewRadioButton = new JRadioButton("1000 Puntos (10 \u20AC)");
		rdbtnNewRadioButton.setBounds(45, 40, 154, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("2050 Puntos (20 \u20AC)");
		rdbtnNewRadioButton_1.setBounds(45, 80, 154, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("5300 Puntos (50 \u20AC)");
		rdbtnNewRadioButton_2.setBounds(45, 120, 154, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		
		rdbtnNewRadioButton_3 = new JRadioButton("10800 Puntos (100 \u20AC)");
		rdbtnNewRadioButton_3.setBounds(45, 160, 154, 23);
		contentPane.add(rdbtnNewRadioButton_3);
		
		rdbtnNewRadioButton_4 = new JRadioButton("55000 Puntos (500 \u20AC)");
		rdbtnNewRadioButton_4.setBounds(45, 200, 154, 23);
		contentPane.add(rdbtnNewRadioButton_4);
		
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);
		bg.add(rdbtnNewRadioButton_3);
		bg.add(rdbtnNewRadioButton_4);
		JButton BuyButton = new JButton("COMPRAR");
		
		BuyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					//Boton 1
					if (rdbtnNewRadioButton.isSelected()) {
						comprar(1000);
					}
					
					//Boton 2
					if (rdbtnNewRadioButton_1.isSelected()) {
						comprar(2050);
					}
					
					//Boton 3
					if (rdbtnNewRadioButton_2.isSelected()) {
						comprar(5300);
					}
					
					//Boton 4
					if (rdbtnNewRadioButton_3.isSelected()) {
						comprar(10800);
					}
					
					//Boton 5
					if (rdbtnNewRadioButton_4.isSelected()) {
						comprar(55000);
					}
			}
			
		});
		BuyButton.setBounds(237, 100, 187, 63);
		contentPane.add(BuyButton);
		
		JButton CloseButton = new JButton("Close");
		CloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Header.actualizarMenu(ventanaAnterior);
				TarjetaTemp.Tarjeta="";
				ventanaAnterior.setVisible(true);
			}
				
			

		});
		CloseButton.setBounds(335, 227, 89, 23);
		contentPane.add(CloseButton);
		
		JLabel lblNewLabel = new JLabel("OFERTAS DISPONIBLES:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(45, 11, 263, 22);
		contentPane.add(lblNewLabel);
		initDataBindings();
	}
	protected void initDataBindings() {
		BeanProperty<JRadioButton, Boolean> jRadioButtonBeanProperty = BeanProperty.create("selected");
		BeanProperty<JRadioButton, Boolean> jRadioButtonBeanProperty_1 = BeanProperty.create("selected");
		AutoBinding<JRadioButton, Boolean, JRadioButton, Boolean> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, rdbtnNewRadioButton_1, jRadioButtonBeanProperty, rdbtnNewRadioButton, jRadioButtonBeanProperty_1);
		autoBinding.bind();
	}
	
	public void comprar(int points) {
		User usuarioAct = LOGIN.getUsuarioAct();
		String Tarjeta;
		if(usuarioAct.getTarjeta()!=null && !usuarioAct.getTarjeta().equals("")) {
			Tarjeta=usuarioAct.getTarjeta();
		} else {
			Tarjeta=TarjetaTemp.Tarjeta;
		}
		try {
			usuarioAct = businessLogic.sumarPuntos(usuarioAct, points, Tarjeta);
			LOGIN.setUsuarioAct(usuarioAct);
			JOptionPane.showMessageDialog(contentPane, points+" Puntos comprados con la tarjeta "+usuarioAct.getTarjeta());
			} catch (NoPaymentMethod exp) {
				TarjetaTemp frame = new TarjetaTemp(this);
				frame.setVisible(true);
				setVisible(false);
			} catch (UserIncomplete exu) {
				RellenarInfo frame= new RellenarInfo(this);
				frame.setVisible(true);
				setVisible(false);
			} 
	}

}
