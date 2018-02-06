package soar.assessment.Y3853992;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Color;

public class GraphicalClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private GridBagLayout gbl_basket_panel;

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
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel home_panel = new JPanel();
		contentPane.add(home_panel, "panel");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 40, 95, 65, 0, 40, -60, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		home_panel.setLayout(gbl_panel);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 1;
		home_panel.add(lblUsername, gbc_lblUsername);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		home_panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnCustomerLogin = new JButton("Customer login");
		btnCustomerLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Open customer logged in window
				// TODO: add password/username checks
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "tabbedPane");
			}
		});
		GridBagConstraints gbc_btnCustomerLogin = new GridBagConstraints();
		gbc_btnCustomerLogin.anchor = GridBagConstraints.WEST;
		gbc_btnCustomerLogin.gridheight = 2;
		gbc_btnCustomerLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomerLogin.gridx = 4;
		gbc_btnCustomerLogin.gridy = 1;
		home_panel.add(btnCustomerLogin, gbc_btnCustomerLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 2;
		home_panel.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 2;
		home_panel.add(passwordField, gbc_passwordField);
		
		JLabel lblUsername_1 = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername_1 = new GridBagConstraints();
		gbc_lblUsername_1.anchor = GridBagConstraints.EAST;
		gbc_lblUsername_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername_1.gridx = 2;
		gbc_lblUsername_1.gridy = 4;
		home_panel.add(lblUsername_1, gbc_lblUsername_1);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 4;
		home_panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JButton btnRestaurantLogin = new JButton("Restaurant Login");
		GridBagConstraints gbc_btnRestaurantLogin = new GridBagConstraints();
		gbc_btnRestaurantLogin.anchor = GridBagConstraints.WEST;
		gbc_btnRestaurantLogin.gridheight = 2;
		gbc_btnRestaurantLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestaurantLogin.gridx = 4;
		gbc_btnRestaurantLogin.gridy = 4;
		home_panel.add(btnRestaurantLogin, gbc_btnRestaurantLogin);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword_1 = new GridBagConstraints();
		gbc_lblPassword_1.anchor = GridBagConstraints.EAST;
		gbc_lblPassword_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword_1.gridx = 2;
		gbc_lblPassword_1.gridy = 5;
		home_panel.add(lblPassword_1, gbc_lblPassword_1);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 3;
		gbc_passwordField_1.gridy = 5;
		home_panel.add(passwordField_1, gbc_passwordField_1);
		
		JButton btnCustomerRegistration = new JButton("Customer Registration");
		GridBagConstraints gbc_btnCustomerRegistration = new GridBagConstraints();
		gbc_btnCustomerRegistration.gridwidth = 2;
		gbc_btnCustomerRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomerRegistration.gridx = 2;
		gbc_btnCustomerRegistration.gridy = 7;
		home_panel.add(btnCustomerRegistration, gbc_btnCustomerRegistration);
		
		JButton btnRestaurantRegistration = new JButton("Restaurant Registration");
		GridBagConstraints gbc_btnRestaurantRegistration = new GridBagConstraints();
		gbc_btnRestaurantRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestaurantRegistration.gridx = 4;
		gbc_btnRestaurantRegistration.gridy = 7;
		home_panel.add(btnRestaurantRegistration, gbc_btnRestaurantRegistration);
		
		JTabbedPane customer_panel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(customer_panel, "tabbedPane");
		
		JPanel customer_tab_1 = new JPanel();
		customer_panel.addTab("Search", null, customer_tab_1, null);
		customer_tab_1.setLayout(new CardLayout(0, 0));
		
		JPanel customer_tab_1_search = new JPanel();
		customer_tab_1.add(customer_tab_1_search, "name_136881280955797");
		GridBagLayout gbl_customer_tab_1_search = new GridBagLayout();
		gbl_customer_tab_1_search.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_search.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_search.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_customer_tab_1_search.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		customer_tab_1_search.setLayout(gbl_customer_tab_1_search);
		
		JLabel lblSearch = new JLabel("Search:");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch.gridx = 1;
		gbc_lblSearch.gridy = 1;
		customer_tab_1_search.add(lblSearch, gbc_lblSearch);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 3;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 2;
		customer_tab_1_search.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JButton btnGo = new JButton("Go");
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo.gridx = 3;
		gbc_btnGo.gridy = 3;
		customer_tab_1_search.add(btnGo, gbc_btnGo);
		
		JPanel customer_tab_1_results = new JPanel();
		customer_tab_1.add(customer_tab_1_results, "name_136915680923230");
		GridBagLayout gbl_customer_tab_1_results = new GridBagLayout();
		gbl_customer_tab_1_results.columnWidths = new int[]{0, 78, 60, 0, 0, 0, 0};
		gbl_customer_tab_1_results.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_results.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_customer_tab_1_results.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		customer_tab_1_results.setLayout(gbl_customer_tab_1_results);
		
		JLabel lblResults = new JLabel("Results:");
		GridBagConstraints gbc_lblResults = new GridBagConstraints();
		gbc_lblResults.anchor = GridBagConstraints.WEST;
		gbc_lblResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblResults.gridx = 4;
		gbc_lblResults.gridy = 1;
		customer_tab_1_results.add(lblResults, gbc_lblResults);
		
		JLabel lblSearch_1 = new JLabel("Search:");
		GridBagConstraints gbc_lblSearch_1 = new GridBagConstraints();
		gbc_lblSearch_1.anchor = GridBagConstraints.WEST;
		gbc_lblSearch_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch_1.gridx = 1;
		gbc_lblSearch_1.gridy = 2;
		customer_tab_1_results.add(lblSearch_1, gbc_lblSearch_1);
		
		JScrollPane customer_tab_1_results_pane = new JScrollPane();
		GridBagConstraints gbc_customer_tab_1_results_pane = new GridBagConstraints();
		gbc_customer_tab_1_results_pane.gridheight = 4;
		gbc_customer_tab_1_results_pane.insets = new Insets(0, 0, 5, 5);
		gbc_customer_tab_1_results_pane.fill = GridBagConstraints.BOTH;
		gbc_customer_tab_1_results_pane.gridx = 4;
		gbc_customer_tab_1_results_pane.gridy = 2;
		customer_tab_1_results.add(customer_tab_1_results_pane, gbc_customer_tab_1_results_pane);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.gridwidth = 2;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 3;
		customer_tab_1_results.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridheight = 5;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 1;
		customer_tab_1_results.add(separator, gbc_separator);
		
		JButton btnGo_1 = new JButton("Go");
		GridBagConstraints gbc_btnGo_1 = new GridBagConstraints();
		gbc_btnGo_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo_1.gridx = 2;
		gbc_btnGo_1.gridy = 4;
		customer_tab_1_results.add(btnGo_1, gbc_btnGo_1);
		
		JPanel customer_tab_1_place_order = new JPanel();
		customer_tab_1.add(customer_tab_1_place_order, "name_136936962547985");
		GridBagLayout gbl_customer_tab_1_place_order = new GridBagLayout();
		gbl_customer_tab_1_place_order.columnWidths = new int[]{0, 210, 0, 185, 0, 0};
		gbl_customer_tab_1_place_order.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_place_order.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_customer_tab_1_place_order.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		customer_tab_1_place_order.setLayout(gbl_customer_tab_1_place_order);
		
		JLabel customer_tab_1_place_order_restaurant_name_label = new JLabel("");
		GridBagConstraints gbc_customer_tab_1_place_order_restaurant_name_label = new GridBagConstraints();
		gbc_customer_tab_1_place_order_restaurant_name_label.anchor = GridBagConstraints.WEST;
		gbc_customer_tab_1_place_order_restaurant_name_label.insets = new Insets(0, 0, 5, 5);
		gbc_customer_tab_1_place_order_restaurant_name_label.gridx = 1;
		gbc_customer_tab_1_place_order_restaurant_name_label.gridy = 1;
		customer_tab_1_place_order.add(customer_tab_1_place_order_restaurant_name_label, gbc_customer_tab_1_place_order_restaurant_name_label);
		
		JLabel lblMenu = new JLabel("Menu:");
		GridBagConstraints gbc_lblMenu = new GridBagConstraints();
		gbc_lblMenu.anchor = GridBagConstraints.WEST;
		gbc_lblMenu.insets = new Insets(0, 0, 5, 5);
		gbc_lblMenu.gridx = 1;
		gbc_lblMenu.gridy = 2;
		customer_tab_1_place_order.add(lblMenu, gbc_lblMenu);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridheight = 6;
		gbc_separator_2.insets = new Insets(0, 0, 0, 5);
		gbc_separator_2.gridx = 2;
		gbc_separator_2.gridy = 1;
		customer_tab_1_place_order.add(separator_2, gbc_separator_2);
		
		JLabel lblBasket = new JLabel("Basket:");
		GridBagConstraints gbc_lblBasket = new GridBagConstraints();
		gbc_lblBasket.anchor = GridBagConstraints.WEST;
		gbc_lblBasket.insets = new Insets(0, 0, 5, 5);
		gbc_lblBasket.gridx = 3;
		gbc_lblBasket.gridy = 2;
		customer_tab_1_place_order.add(lblBasket, gbc_lblBasket);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		customer_tab_1_place_order.add(panel, gbc_panel);
		
		JPanel basket_panel = new JPanel();
		basket_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_basket_panel = new GridBagConstraints();
		gbc_basket_panel.gridheight = 2;
		gbc_basket_panel.insets = new Insets(0, 0, 5, 5);
		gbc_basket_panel.fill = GridBagConstraints.BOTH;
		gbc_basket_panel.gridx = 3;
		gbc_basket_panel.gridy = 3;
		customer_tab_1_place_order.add(basket_panel, gbc_basket_panel);
		GridBagLayout gbl_basket_panel;
		gbl_basket_panel = new GridBagLayout();
		gbl_basket_panel.columnWidths = new int[]{0, 0};
		gbl_basket_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_basket_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_basket_panel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		basket_panel.setLayout(gbl_basket_panel);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 1;
		basket_panel.add(separator_1, gbc_separator_1);
		
		JLabel lblOrderTotal = new JLabel("Order Total:");
		GridBagConstraints gbc_lblOrderTotal = new GridBagConstraints();
		gbc_lblOrderTotal.anchor = GridBagConstraints.WEST;
		gbc_lblOrderTotal.gridx = 0;
		gbc_lblOrderTotal.gridy = 2;
		basket_panel.add(lblOrderTotal, gbc_lblOrderTotal);
		
		JButton btnPlaceOrder = new JButton("Place Order");
		GridBagConstraints gbc_btnPlaceOrder = new GridBagConstraints();
		gbc_btnPlaceOrder.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlaceOrder.gridx = 3;
		gbc_btnPlaceOrder.gridy = 5;
		customer_tab_1_place_order.add(btnPlaceOrder, gbc_btnPlaceOrder);
		
		JPanel customer_tab_1_order_placed = new JPanel();
		customer_tab_1.add(customer_tab_1_order_placed, "name_136959918189771");
		GridBagLayout gbl_customer_tab_1_order_placed = new GridBagLayout();
		gbl_customer_tab_1_order_placed.columnWidths = new int[]{0, 0, 0, 0};
		gbl_customer_tab_1_order_placed.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_order_placed.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_customer_tab_1_order_placed.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		customer_tab_1_order_placed.setLayout(gbl_customer_tab_1_order_placed);
		
		JLabel lblThanksForYour = new JLabel("Thanks for your order!");
		GridBagConstraints gbc_lblThanksForYour = new GridBagConstraints();
		gbc_lblThanksForYour.insets = new Insets(0, 0, 5, 5);
		gbc_lblThanksForYour.gridx = 1;
		gbc_lblThanksForYour.gridy = 1;
		customer_tab_1_order_placed.add(lblThanksForYour, gbc_lblThanksForYour);
		
		JLabel lblPleaseCheckThe = new JLabel("Please check the order status tab for updates.");
		GridBagConstraints gbc_lblPleaseCheckThe = new GridBagConstraints();
		gbc_lblPleaseCheckThe.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseCheckThe.gridx = 1;
		gbc_lblPleaseCheckThe.gridy = 3;
		customer_tab_1_order_placed.add(lblPleaseCheckThe, gbc_lblPleaseCheckThe);
		
		JPanel customer_tab_2 = new JPanel();
		customer_panel.addTab("Order Status", null, customer_tab_2, null);
		GridBagLayout gbl_customer_tab_2 = new GridBagLayout();
		gbl_customer_tab_2.columnWidths = new int[]{0, 0, 0, 222, 0, 0};
		gbl_customer_tab_2.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_customer_tab_2.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_customer_tab_2.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		customer_tab_2.setLayout(gbl_customer_tab_2);
		
		JLabel lblYourOrders = new JLabel("Your orders:");
		GridBagConstraints gbc_lblYourOrders = new GridBagConstraints();
		gbc_lblYourOrders.anchor = GridBagConstraints.WEST;
		gbc_lblYourOrders.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourOrders.gridx = 1;
		gbc_lblYourOrders.gridy = 1;
		customer_tab_2.add(lblYourOrders, gbc_lblYourOrders);
		
		JButton btnRefresh = new JButton("Refresh");
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.anchor = GridBagConstraints.EAST;
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefresh.gridx = 3;
		gbc_btnRefresh.gridy = 1;
		customer_tab_2.add(btnRefresh, gbc_btnRefresh);
		
		JPanel customer_tab_2_orders_panel = new JPanel();
		customer_tab_2_orders_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_customer_tab_2_orders_panel = new GridBagConstraints();
		gbc_customer_tab_2_orders_panel.gridwidth = 3;
		gbc_customer_tab_2_orders_panel.insets = new Insets(0, 0, 5, 5);
		gbc_customer_tab_2_orders_panel.fill = GridBagConstraints.BOTH;
		gbc_customer_tab_2_orders_panel.gridx = 1;
		gbc_customer_tab_2_orders_panel.gridy = 2;
		customer_tab_2.add(customer_tab_2_orders_panel, gbc_customer_tab_2_orders_panel);
		
		JTabbedPane restaurant_panel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(restaurant_panel, "name_138999272006941");
		
		JPanel restaurant_panel_tab_1 = new JPanel();
		restaurant_panel.addTab("Orders", null, restaurant_panel_tab_1, null);
		
		JPanel restaurant_panel_tab_2 = new JPanel();
		restaurant_panel.addTab("Menu", null, restaurant_panel_tab_2, null);
	}

}
