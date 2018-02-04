package soar.assessment.Y3853992;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

public class GraphicalClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalClient frame = new GraphicalClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphicalClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 40, 95, 65, 0, 40, -60, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 1;
		panel.add(lblUsername, gbc_lblUsername);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnCustomerLogin = new JButton("Customer login");
		GridBagConstraints gbc_btnCustomerLogin = new GridBagConstraints();
		gbc_btnCustomerLogin.anchor = GridBagConstraints.WEST;
		gbc_btnCustomerLogin.gridheight = 2;
		gbc_btnCustomerLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomerLogin.gridx = 4;
		gbc_btnCustomerLogin.gridy = 1;
		panel.add(btnCustomerLogin, gbc_btnCustomerLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 2;
		panel.add(lblPassword, gbc_lblPassword);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUsername_1 = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername_1 = new GridBagConstraints();
		gbc_lblUsername_1.anchor = GridBagConstraints.EAST;
		gbc_lblUsername_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername_1.gridx = 2;
		gbc_lblUsername_1.gridy = 4;
		panel.add(lblUsername_1, gbc_lblUsername_1);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 4;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JButton btnRestaurantLogin = new JButton("Restaurant Login");
		GridBagConstraints gbc_btnRestaurantLogin = new GridBagConstraints();
		gbc_btnRestaurantLogin.anchor = GridBagConstraints.WEST;
		gbc_btnRestaurantLogin.gridheight = 2;
		gbc_btnRestaurantLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestaurantLogin.gridx = 4;
		gbc_btnRestaurantLogin.gridy = 4;
		panel.add(btnRestaurantLogin, gbc_btnRestaurantLogin);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword_1 = new GridBagConstraints();
		gbc_lblPassword_1.anchor = GridBagConstraints.EAST;
		gbc_lblPassword_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword_1.gridx = 2;
		gbc_lblPassword_1.gridy = 5;
		panel.add(lblPassword_1, gbc_lblPassword_1);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 5;
		panel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JButton btnCustomerRegistration = new JButton("Customer Registration");
		GridBagConstraints gbc_btnCustomerRegistration = new GridBagConstraints();
		gbc_btnCustomerRegistration.gridwidth = 2;
		gbc_btnCustomerRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomerRegistration.gridx = 2;
		gbc_btnCustomerRegistration.gridy = 7;
		panel.add(btnCustomerRegistration, gbc_btnCustomerRegistration);
		
		JButton btnRestaurantRegistration = new JButton("Restaurant Registration");
		GridBagConstraints gbc_btnRestaurantRegistration = new GridBagConstraints();
		gbc_btnRestaurantRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestaurantRegistration.gridx = 4;
		gbc_btnRestaurantRegistration.gridy = 7;
		panel.add(btnRestaurantRegistration, gbc_btnRestaurantRegistration);
	}

}
