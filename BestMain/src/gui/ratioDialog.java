package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class ratioDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private double ratio = 0;
	private JLabel lblError = new JLabel("");
	private JDialog pan = this;

	/**
	 * Create the dialog.
	 */
	public ratioDialog() {

		super(null, ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Ratio");
		setBounds(100, 100, 441, 207);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(51, 59, 96, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		      char c = e.getKeyChar();
		      if (!((c >= '0') && (c <= '9') ||
		         (c == KeyEvent.VK_BACK_SPACE) ||
		         (c == KeyEvent.VK_DELETE)||(!textField.getText().contains(".") &&c == KeyEvent.VK_PERIOD))) {
		        getToolkit().beep();
		        e.consume();
		      }
		    }
		  });
		
		JLabel lblNewLabel = new JLabel("Selecciona el ratio de copia");
		lblNewLabel.setBounds(51, 40, 330, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>El ratio es la proporcion mediante la que se va a copiar al usuario (si el usa 100 y tienes un ratio 0.1, tu apuesta sera 10)</html>");
		lblNewLabel_1.setBounds(51, 77, 330, 44);
		contentPanel.add(lblNewLabel_1);
		
		
		lblError.setForeground(Color.RED);
		lblError.setBounds(174, 62, 207, 14);
		contentPanel.add(lblError);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textField.getText().equals("")) {
							lblError.setText("Escribe un ratio");
						} else {
						ratio = Double.parseDouble(textField.getText());
						pan.setVisible(false);
						pan.dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ratio = 0;
						pan.setVisible(false);
						pan.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public double showDialog() {
		setVisible(true);
		return ratio;
	}
}
