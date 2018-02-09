package soar.assessment.Y3853992;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class GraphicalClient extends JFrame {

	private JPanel contentPane;
	private JTextField clogin_username;
	private JTextField rlogin_username;
	private JPasswordField clogin_password;
	private JPasswordField rlogin_password;
	private JTextField textField_1;
	private JTextField c_search;
	private JTextField c_search_2;
	private GridBagLayout gbl_basket_panel;
	private JTextField textField_5;
	private JTextField textField_6;
	private JPasswordField creg_password;
	private JTextField creg_email;
	private JTextField creg_fullname;
	private JTextField creg_username;
	private JTextField rreg_username;
	private JTextField rreg_restaurantname;
	private JTextField rreg_address;
	private JTextField rreg_email;
	private JPasswordField rreg_password;
	private JList<Restaurant> c_search_list;
	private JPanel c_menu_panel;
	private JList<Item> basket_list;
	private DefaultListModel<Item> basket_contents;
	private JLabel order_total_label;
	private JList<Order> c_orders_list;
	
	private int restaurantID = -1;
	private int customerID = -1;
	
	private EngineConfiguration restaurantsConfig;		
	private RestaurantsSoapBindingStub restaurants;
	EngineConfiguration customersConfig;
	CustomersSoapBindingStub customers;
	private JTextField card_number;
	private JTextField delivery_address;

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
		// Setup stubs
		try {
			restaurantsConfig = new FileProvider(ClientApp.class.getResourceAsStream("restaurantclient.wsdd"));
			restaurants = (RestaurantsSoapBindingStub) new RestaurantsServiceLocator(restaurantsConfig).getRestaurants();
			customersConfig = new FileProvider(ClientApp.class.getResourceAsStream("customerclient.wsdd"));
			customers = (CustomersSoapBindingStub) new CustomersServiceLocator(customersConfig).getCustomers();
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel home_panel = new JPanel();
		contentPane.add(home_panel, "home_panel");
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
		
		clogin_username = new JTextField();
		GridBagConstraints gbc_clogin_username = new GridBagConstraints();
		gbc_clogin_username.insets = new Insets(0, 0, 5, 5);
		gbc_clogin_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_clogin_username.gridx = 3;
		gbc_clogin_username.gridy = 1;
		home_panel.add(clogin_username, gbc_clogin_username);
		clogin_username.setColumns(10);
		
		JButton btnCustomerLogin = new JButton("Customer login");
		btnCustomerLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = clogin_username.getText();
				String password = clogin_password.getText();

				try {
					customers.setUsername(username);
					customers.setPassword(password);
					customerID = customers.getCustomerID(username);
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "customer_panel");
				} catch(NoValidEntryException ex) {
					JOptionPane.showMessageDialog(home_panel, "Could not get ID for given username.");
				} catch (Exception ex) {
					if(ex instanceof AxisFault) {
						JOptionPane.showMessageDialog(home_panel, "Incorrect username/password.");
					} else {
						JOptionPane.showMessageDialog(home_panel, "An error occurred. Please try again.");
					}
				}
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
		
		clogin_password = new JPasswordField();
		GridBagConstraints gbc_clogin_password = new GridBagConstraints();
		gbc_clogin_password.insets = new Insets(0, 0, 5, 5);
		gbc_clogin_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_clogin_password.gridx = 3;
		gbc_clogin_password.gridy = 2;
		home_panel.add(clogin_password, gbc_clogin_password);
		
		JLabel lblUsername_1 = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername_1 = new GridBagConstraints();
		gbc_lblUsername_1.anchor = GridBagConstraints.EAST;
		gbc_lblUsername_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername_1.gridx = 2;
		gbc_lblUsername_1.gridy = 4;
		home_panel.add(lblUsername_1, gbc_lblUsername_1);
		
		rlogin_username = new JTextField();
		GridBagConstraints gbc_rlogin_username = new GridBagConstraints();
		gbc_rlogin_username.insets = new Insets(0, 0, 5, 5);
		gbc_rlogin_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_rlogin_username.gridx = 3;
		gbc_rlogin_username.gridy = 4;
		home_panel.add(rlogin_username, gbc_rlogin_username);
		rlogin_username.setColumns(10);
		
		JButton btnRestaurantLogin = new JButton("Restaurant Login");
		btnRestaurantLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = rlogin_username.getText();
				String password = rlogin_password.getText();
				try {
					restaurants.setUsername(username);
					restaurants.setPassword(password);
					restaurantID = restaurants.getRestaurantID(username);
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "restaurant_panel");
				} catch(NoValidEntryException ex) {
					JOptionPane.showMessageDialog(home_panel, "Could not get ID for given username.");
				} catch (Exception ex) {
					if(ex instanceof AxisFault) {
						JOptionPane.showMessageDialog(home_panel, "Incorrect username/password.");
					} else {
						JOptionPane.showMessageDialog(home_panel, "An error occurred. Please try again.");
					}
				}
			}
		});
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
		
		rlogin_password = new JPasswordField();
		GridBagConstraints gbc_rlogin_password = new GridBagConstraints();
		gbc_rlogin_password.insets = new Insets(0, 0, 5, 5);
		gbc_rlogin_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_rlogin_password.gridx = 3;
		gbc_rlogin_password.gridy = 5;
		home_panel.add(rlogin_password, gbc_rlogin_password);
		
		JButton btnCustomerRegistration = new JButton("Customer Registration");
		btnCustomerRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "customer_reg_panel");
			}
		});
		GridBagConstraints gbc_btnCustomerRegistration = new GridBagConstraints();
		gbc_btnCustomerRegistration.gridwidth = 2;
		gbc_btnCustomerRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnCustomerRegistration.gridx = 2;
		gbc_btnCustomerRegistration.gridy = 7;
		home_panel.add(btnCustomerRegistration, gbc_btnCustomerRegistration);
		
		JButton btnRestaurantRegistration = new JButton("Restaurant Registration");
		btnRestaurantRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "restaurant_reg_panel");
			}
		});
		GridBagConstraints gbc_btnRestaurantRegistration = new GridBagConstraints();
		gbc_btnRestaurantRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestaurantRegistration.gridx = 4;
		gbc_btnRestaurantRegistration.gridy = 7;
		home_panel.add(btnRestaurantRegistration, gbc_btnRestaurantRegistration);
		
		JTabbedPane customer_panel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(customer_panel, "customer_panel");
		
		JPanel customer_panel_search_tab = new JPanel();
		customer_panel.addTab("Search", null, customer_panel_search_tab, null);
		customer_panel_search_tab.setLayout(new CardLayout(0, 0));
		
		JPanel customer_tab_1_search = new JPanel();
		customer_panel_search_tab.add(customer_tab_1_search, "customer_tab_1_search");
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
		
		c_search = new JTextField();
		GridBagConstraints gbc_c_search = new GridBagConstraints();
		gbc_c_search.gridwidth = 3;
		gbc_c_search.insets = new Insets(0, 0, 5, 5);
		gbc_c_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_c_search.gridx = 1;
		gbc_c_search.gridy = 2;
		customer_tab_1_search.add(c_search, gbc_c_search);
		c_search.setColumns(10);
		
		JButton btnGo = new JButton("Go");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String searchTerm = c_search.getText();
				try {
					Restaurant[] searchResults = customers.searchForRestaurants(searchTerm);
					
					// Display each of the search results in the results panel
					DefaultListModel<Restaurant> dlm = new DefaultListModel<Restaurant>();
					for(int i = 0; i < searchResults.length; i++) {
						dlm.addElement(searchResults[i]);
					}
					
					c_search_list.setModel(dlm);
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_results");
				} catch(NoResultsException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "No results for given search term. Please try again.");
				} catch (Exception ex) {
					if(ex instanceof AxisFault) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "Incorrect username/password.");
					} else {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
					}
				}
			}
		});
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo.gridx = 3;
		gbc_btnGo.gridy = 3;
		customer_tab_1_search.add(btnGo, gbc_btnGo);
		
		JPanel customer_tab_1_results = new JPanel();
		customer_panel_search_tab.add(customer_tab_1_results, "customer_tab_1_results");
		GridBagLayout gbl_customer_tab_1_results = new GridBagLayout();
		gbl_customer_tab_1_results.columnWidths = new int[]{0, 78, 60, 0, 0, 0, 0};
		gbl_customer_tab_1_results.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_results.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_customer_tab_1_results.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		customer_tab_1_results.setLayout(gbl_customer_tab_1_results);
		
		JLabel lblResults = new JLabel("Results:");
		GridBagConstraints gbc_lblResults = new GridBagConstraints();
		gbc_lblResults.anchor = GridBagConstraints.WEST;
		gbc_lblResults.insets = new Insets(0, 0, 5, 5);
		gbc_lblResults.gridx = 4;
		gbc_lblResults.gridy = 1;
		customer_tab_1_results.add(lblResults, gbc_lblResults);
		
		c_search_list = new JList<Restaurant>();
		c_search_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c_search_list.setCellRenderer(new ListCellRenderer<Restaurant>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Restaurant> list, Restaurant value, int index,
					boolean isSelected, boolean cellHasFocus) {
				String text = "<html>" + value.getRestaurantName() + "<br>" + value.getAddress() + "</html>";
				JLabel component = new JLabel();
				component.setText(text);
				component.setOpaque(true);
				if (isSelected) {
				    component.setBackground(list.getSelectionBackground());
				    component.setForeground(list.getSelectionForeground());
				} else {
				    component.setBackground(list.getBackground());
				    component.setForeground(list.getForeground());
				}
				return component;
			}
			
		});
	    ListSelectionModel listSelectionModel = c_search_list.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if(!lsm.getValueIsAdjusting()) {
					int indexA = e.getFirstIndex();
					int indexB = e.getLastIndex();
					int restaurantId;
					if(lsm.isSelectedIndex(indexA)) {
						restaurantId = c_search_list.getModel().getElementAt(indexA).getRestaurantID();
					} else {
						restaurantId = c_search_list.getModel().getElementAt(indexB).getRestaurantID();
					}
					try {
						Item[] items = customers.getMenu(restaurantId);
						
						for(int i = 0; i < items.length; i++) {
							Item currentItem = items[i];
							JLabel item_label = new JLabel(currentItem.getTitle() + ": £" + currentItem.getPrice());
							GridBagConstraints gbc_item_label = new GridBagConstraints();
							gbc_item_label.anchor = GridBagConstraints.WEST;
							gbc_item_label.insets = new Insets(0, 0, 5, 5);
							gbc_item_label.gridx = 0;
							gbc_item_label.gridy = i+1;
							c_menu_panel.add(item_label, gbc_item_label);
							
							JButton add_to_basket = new JButton("+");
							add_to_basket.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									// Add title to basket
									if(basket_contents == null) {
										basket_contents = new DefaultListModel<Item>();
									}
									basket_contents.addElement(currentItem);
									basket_list.setModel(basket_contents);
									
									// Update order total
									float total = 0;
									for(int x = 0; x < basket_contents.getSize(); x++) {
										total += basket_contents.getElementAt(x).getPrice();
									}
									order_total_label.setText("Order total: £" + total);
								}
							});
							GridBagConstraints gbc_add_to_basket = new GridBagConstraints();
							gbc_add_to_basket.anchor = GridBagConstraints.WEST;
							gbc_add_to_basket.insets = new Insets(0, 0, 5, 5);
							gbc_add_to_basket.gridx = 1;
							gbc_add_to_basket.gridy = i+1;
							c_menu_panel.add(add_to_basket, gbc_add_to_basket);
						}	
						
						CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
						cardLayout.show(customer_panel_search_tab, "customer_tab_1_place_order");
					} catch (NoResultsException ex) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "No menu for given restaurant. Please try again.");
					} catch (RemoteException ex) {
						if(ex instanceof AxisFault) {
							JOptionPane.showMessageDialog(customer_panel_search_tab, "Incorrect username/password.");
						} else {
							JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
						}
					}
					
				}
			}
	    	
	    });
	    JScrollPane scroll_search_list = new JScrollPane(c_search_list);
		GridBagConstraints gbc_scroll_search_list = new GridBagConstraints();
		gbc_scroll_search_list.gridheight = 5;
		gbc_scroll_search_list.insets = new Insets(0, 0, 5, 5);
		gbc_scroll_search_list.fill = GridBagConstraints.BOTH;
		gbc_scroll_search_list.gridx = 4;
		gbc_scroll_search_list.gridy = 2;
		customer_tab_1_results.add(scroll_search_list, gbc_scroll_search_list);
		
		JLabel lblSearch_1 = new JLabel("Search:");
		GridBagConstraints gbc_lblSearch_1 = new GridBagConstraints();
		gbc_lblSearch_1.anchor = GridBagConstraints.WEST;
		gbc_lblSearch_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch_1.gridx = 1;
		gbc_lblSearch_1.gridy = 3;
		customer_tab_1_results.add(lblSearch_1, gbc_lblSearch_1);
		
		c_search_2 = new JTextField();
		GridBagConstraints gbc_c_search_2 = new GridBagConstraints();
		gbc_c_search_2.gridwidth = 2;
		gbc_c_search_2.insets = new Insets(0, 0, 5, 5);
		gbc_c_search_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_c_search_2.gridx = 1;
		gbc_c_search_2.gridy = 4;
		customer_tab_1_results.add(c_search_2, gbc_c_search_2);
		c_search_2.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridheight = 6;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 1;
		customer_tab_1_results.add(separator, gbc_separator);
		
		JButton btnGo_1 = new JButton("Go");
		btnGo_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String searchTerm = c_search_2.getText();
				try {
					Restaurant[] searchResults = customers.searchForRestaurants(searchTerm);
					
					// Display each of the search results in the results panel
					DefaultListModel<Restaurant> dlm = new DefaultListModel<Restaurant>();
					for(int i = 0; i < searchResults.length; i++) {
						dlm.addElement(searchResults[i]);
					}
					
					c_search_list.setModel(dlm);
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_results");
				} catch(NoResultsException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "No results for given search term. Please try again.");
				} catch (Exception ex) {
					if(ex instanceof AxisFault) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "Incorrect username/password.");
					} else {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
					}
				}
			}
		});
		GridBagConstraints gbc_btnGo_1 = new GridBagConstraints();
		gbc_btnGo_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo_1.gridx = 2;
		gbc_btnGo_1.gridy = 5;
		customer_tab_1_results.add(btnGo_1, gbc_btnGo_1);
		
		JPanel customer_tab_1_place_order = new JPanel();
		customer_panel_search_tab.add(customer_tab_1_place_order, "customer_tab_1_place_order");
		GridBagLayout gbl_customer_tab_1_place_order = new GridBagLayout();
		gbl_customer_tab_1_place_order.columnWidths = new int[]{0, 210, 0, 185, 0, 0};
		gbl_customer_tab_1_place_order.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_place_order.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_customer_tab_1_place_order.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_separator_2.gridheight = 7;
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
		
		c_menu_panel = new JPanel();
		c_menu_panel.setBackground(Color.WHITE);
		GridBagLayout gbl_c_menu_panel = new GridBagLayout();
		gbl_c_menu_panel.columnWidths = new int[]{0};
		gbl_c_menu_panel.rowHeights = new int[]{0};
		gbl_c_menu_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_c_menu_panel.rowWeights = new double[]{Double.MIN_VALUE};
		c_menu_panel.setLayout(gbl_c_menu_panel);
		
		JScrollPane menu_scroll_pane = new JScrollPane(c_menu_panel);
		GridBagConstraints gbc_menu_scroll_pane = new GridBagConstraints();
		gbc_menu_scroll_pane.gridheight = 4;
		gbc_menu_scroll_pane.insets = new Insets(0, 0, 5, 5);
		gbc_menu_scroll_pane.fill = GridBagConstraints.BOTH;
		gbc_menu_scroll_pane.gridx = 1;
		gbc_menu_scroll_pane.gridy = 3;
		customer_tab_1_place_order.add(menu_scroll_pane, gbc_menu_scroll_pane);
		
		JScrollPane basket_scroll_pane = new JScrollPane();
		GridBagConstraints gbc_basket_scroll_pane = new GridBagConstraints();
		gbc_basket_scroll_pane.gridheight = 2;
		gbc_basket_scroll_pane.insets = new Insets(0, 0, 5, 5);
		gbc_basket_scroll_pane.fill = GridBagConstraints.BOTH;
		gbc_basket_scroll_pane.gridx = 3;
		gbc_basket_scroll_pane.gridy = 3;
		customer_tab_1_place_order.add(basket_scroll_pane, gbc_basket_scroll_pane);
		
		basket_list = new JList<Item>();
		basket_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		basket_scroll_pane.setViewportView(basket_list);
		basket_list.setCellRenderer(new ListCellRenderer<Item>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value.getTitle());
			}
			
		});
		
		order_total_label = new JLabel("Order Total:");
		GridBagConstraints gbc_order_total_label = new GridBagConstraints();
		gbc_order_total_label.anchor = GridBagConstraints.WEST;
		gbc_order_total_label.insets = new Insets(0, 0, 5, 5);
		gbc_order_total_label.gridx = 3;
		gbc_order_total_label.gridy = 5;
		customer_tab_1_place_order.add(order_total_label, gbc_order_total_label);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
				cardLayout.show(customer_panel_search_tab, "customer_tab_1_details");
			}
		});
		GridBagConstraints gbc_btnCheckout = new GridBagConstraints();
		gbc_btnCheckout.insets = new Insets(0, 0, 5, 5);
		gbc_btnCheckout.gridx = 3;
		gbc_btnCheckout.gridy = 6;
		customer_tab_1_place_order.add(btnCheckout, gbc_btnCheckout);
		
		JPanel customer_tab_1_order_placed = new JPanel();
		customer_panel_search_tab.add(customer_tab_1_order_placed, "customer_tab_1_order_placed");
		GridBagLayout gbl_customer_tab_1_order_placed = new GridBagLayout();
		gbl_customer_tab_1_order_placed.columnWidths = new int[]{0, 0, 0, 0};
		gbl_customer_tab_1_order_placed.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_order_placed.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_customer_tab_1_order_placed.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		JPanel customer_tab_1_details = new JPanel();
		customer_panel_search_tab.add(customer_tab_1_details, "customer_tab_1_details");
		GridBagLayout gbl_customer_tab_1_details = new GridBagLayout();
		gbl_customer_tab_1_details.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_customer_tab_1_details.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_customer_tab_1_details.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_customer_tab_1_details.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		customer_tab_1_details.setLayout(gbl_customer_tab_1_details);
		
		JLabel lblCardNumber = new JLabel("Card Number:");
		GridBagConstraints gbc_lblCardNumber = new GridBagConstraints();
		gbc_lblCardNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblCardNumber.gridx = 1;
		gbc_lblCardNumber.gridy = 1;
		customer_tab_1_details.add(lblCardNumber, gbc_lblCardNumber);
		
		card_number = new JTextField();
		GridBagConstraints gbc_card_number = new GridBagConstraints();
		gbc_card_number.insets = new Insets(0, 0, 5, 5);
		gbc_card_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_card_number.gridx = 2;
		gbc_card_number.gridy = 1;
		customer_tab_1_details.add(card_number, gbc_card_number);
		card_number.setColumns(10);
		
		JLabel lblDeliveryAddress = new JLabel("Delivery Address:");
		GridBagConstraints gbc_lblDeliveryAddress = new GridBagConstraints();
		gbc_lblDeliveryAddress.anchor = GridBagConstraints.EAST;
		gbc_lblDeliveryAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeliveryAddress.gridx = 1;
		gbc_lblDeliveryAddress.gridy = 2;
		customer_tab_1_details.add(lblDeliveryAddress, gbc_lblDeliveryAddress);
		
		delivery_address = new JTextField();
		GridBagConstraints gbc_delivery_address = new GridBagConstraints();
		gbc_delivery_address.insets = new Insets(0, 0, 5, 5);
		gbc_delivery_address.fill = GridBagConstraints.HORIZONTAL;
		gbc_delivery_address.gridx = 2;
		gbc_delivery_address.gridy = 2;
		customer_tab_1_details.add(delivery_address, gbc_delivery_address);
		delivery_address.setColumns(10);
		
		JButton place_order = new JButton("Place Order");
		place_order.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Item> basket = new ArrayList<Item>();
				DefaultListModel m = (DefaultListModel) basket_list.getModel();
				for(int i = 0; i < m.getSize(); i++) {
					basket.add((Item) m.getElementAt(i));
				}
				Item[] basketArr = new Item[basket.size()];
				basketArr = basket.toArray(basketArr);
				try {
					customers.placeOrder(customerID, basketArr, card_number.getText(), delivery_address.getText());
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_order_placed");
				} catch(NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_tab_1_details, "Please fill in all required fields.");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(customer_tab_1_details, "An error has occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_place_order = new GridBagConstraints();
		gbc_place_order.insets = new Insets(0, 0, 0, 5);
		gbc_place_order.gridx = 2;
		gbc_place_order.gridy = 4;
		customer_tab_1_details.add(place_order, gbc_place_order);
		
		JPanel customer_panel_order_status_tab = new JPanel();
		customer_panel.addTab("Order Status", null, customer_panel_order_status_tab, null);
		GridBagLayout gbl_customer_panel_order_status_tab = new GridBagLayout();
		gbl_customer_panel_order_status_tab.columnWidths = new int[]{0, 0, 0, 222, 0, 0};
		gbl_customer_panel_order_status_tab.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_customer_panel_order_status_tab.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_customer_panel_order_status_tab.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		customer_panel_order_status_tab.setLayout(gbl_customer_panel_order_status_tab);
		
		JLabel lblYourOrders = new JLabel("Your orders:");
		GridBagConstraints gbc_lblYourOrders = new GridBagConstraints();
		gbc_lblYourOrders.anchor = GridBagConstraints.WEST;
		gbc_lblYourOrders.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourOrders.gridx = 1;
		gbc_lblYourOrders.gridy = 1;
		customer_panel_order_status_tab.add(lblYourOrders, gbc_lblYourOrders);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO: get customer's orders and populate c_orders_list using getOrders service
				try {
					Order[] orders = customers.getOrders(customerID);
					DefaultListModel<Order> dlm = new DefaultListModel<Order>();
					for(int i = 0; i < orders.length; i++) {
						dlm.addElement(orders[i]);
					}
					
					c_orders_list.setModel(dlm);
					
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(customer_panel_order_status_tab, "An error has occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.anchor = GridBagConstraints.EAST;
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefresh.gridx = 3;
		gbc_btnRefresh.gridy = 1;
		customer_panel_order_status_tab.add(btnRefresh, gbc_btnRefresh);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		customer_panel_order_status_tab.add(scrollPane, gbc_scrollPane);
		
		c_orders_list = new JList();
		c_orders_list.setCellRenderer(new ListCellRenderer<Order>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Order> list, Order value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				JLabel orderLabel = new JLabel("<html>OrderID: " + value.getOrderID() 
													+"<br>Status: " + value.getStatus()
													+"<br>Total Cost: £" + value.getTotalPrice()
													+"<br>Delivery Estimate: " + value.getDeliveryTime());
				panel.add(orderLabel);
				// TODO: add quantity info to Item class
				JScrollPane itemSummaryPane = new JScrollPane();
				JList<Item> itemSummary = new JList<Item>();
				itemSummaryPane.setViewportView(itemSummary);
				itemSummary.setCellRenderer(new ListCellRenderer<Item>() {

					@Override
					public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
							boolean isSelected, boolean cellHasFocus) {
						return new JLabel(value.getTitle() + ": £" + value.getPrice());
					}
					
				});
				
				DefaultListModel dlm = new DefaultListModel();
				Item[] items = value.getItems();
				for(int i = 0; i < items.length; i++) {
					dlm.addElement(items[i]);
				}
				itemSummary.setModel(dlm);
				
				panel.add(itemSummaryPane);
				return panel;
			}
			
		});
		scrollPane.setViewportView(c_orders_list);
		
		JTabbedPane restaurant_panel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(restaurant_panel, "restaurant_panel");
		
		JPanel restaurant_panel_orders_tab = new JPanel();
		restaurant_panel.addTab("Orders", null, restaurant_panel_orders_tab, null);
		GridBagLayout gbl_restaurant_panel_orders_tab = new GridBagLayout();
		gbl_restaurant_panel_orders_tab.columnWidths = new int[]{0, 0, 0, 0};
		gbl_restaurant_panel_orders_tab.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_restaurant_panel_orders_tab.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_restaurant_panel_orders_tab.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		restaurant_panel_orders_tab.setLayout(gbl_restaurant_panel_orders_tab);
		
		JLabel lblQueued = new JLabel("Queued:");
		GridBagConstraints gbc_lblQueued = new GridBagConstraints();
		gbc_lblQueued.anchor = GridBagConstraints.WEST;
		gbc_lblQueued.insets = new Insets(0, 0, 5, 5);
		gbc_lblQueued.gridx = 1;
		gbc_lblQueued.gridy = 1;
		restaurant_panel_orders_tab.add(lblQueued, gbc_lblQueued);
		
		JPanel rp_ot_queued_panel = new JPanel();
		rp_ot_queued_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_rp_ot_queued_panel = new GridBagConstraints();
		gbc_rp_ot_queued_panel.insets = new Insets(0, 0, 5, 5);
		gbc_rp_ot_queued_panel.fill = GridBagConstraints.BOTH;
		gbc_rp_ot_queued_panel.gridx = 1;
		gbc_rp_ot_queued_panel.gridy = 2;
		restaurant_panel_orders_tab.add(rp_ot_queued_panel, gbc_rp_ot_queued_panel);
		
		JSeparator separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.gridwidth = 3;
		gbc_separator_3.insets = new Insets(0, 0, 5, 0);
		gbc_separator_3.gridx = 0;
		gbc_separator_3.gridy = 3;
		restaurant_panel_orders_tab.add(separator_3, gbc_separator_3);
		
		JLabel lblAccepted = new JLabel("Accepted:");
		GridBagConstraints gbc_lblAccepted = new GridBagConstraints();
		gbc_lblAccepted.anchor = GridBagConstraints.WEST;
		gbc_lblAccepted.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccepted.gridx = 1;
		gbc_lblAccepted.gridy = 4;
		restaurant_panel_orders_tab.add(lblAccepted, gbc_lblAccepted);
		
		JPanel rp_ot_accepted_panel = new JPanel();
		rp_ot_accepted_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_rp_ot_accepted_panel = new GridBagConstraints();
		gbc_rp_ot_accepted_panel.insets = new Insets(0, 0, 5, 5);
		gbc_rp_ot_accepted_panel.fill = GridBagConstraints.BOTH;
		gbc_rp_ot_accepted_panel.gridx = 1;
		gbc_rp_ot_accepted_panel.gridy = 5;
		restaurant_panel_orders_tab.add(rp_ot_accepted_panel, gbc_rp_ot_accepted_panel);
		
		JPanel restaurant_panel_menu_tab = new JPanel();
		restaurant_panel.addTab("Menu", null, restaurant_panel_menu_tab, null);
		restaurant_panel_menu_tab.setLayout(new CardLayout(0, 0));
		
		JPanel rp_mt_current_menu = new JPanel();
		restaurant_panel_menu_tab.add(rp_mt_current_menu, "name_139444151858240");
		GridBagLayout gbl_rp_mt_current_menu = new GridBagLayout();
		gbl_rp_mt_current_menu.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_rp_mt_current_menu.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_rp_mt_current_menu.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rp_mt_current_menu.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		rp_mt_current_menu.setLayout(gbl_rp_mt_current_menu);
		
		JLabel lblMenu_1 = new JLabel("Menu:");
		GridBagConstraints gbc_lblMenu_1 = new GridBagConstraints();
		gbc_lblMenu_1.anchor = GridBagConstraints.WEST;
		gbc_lblMenu_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMenu_1.gridx = 1;
		gbc_lblMenu_1.gridy = 1;
		rp_mt_current_menu.add(lblMenu_1, gbc_lblMenu_1);
		
		JPanel current_menu_panel = new JPanel();
		current_menu_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_current_menu_panel = new GridBagConstraints();
		gbc_current_menu_panel.insets = new Insets(0, 0, 5, 5);
		gbc_current_menu_panel.fill = GridBagConstraints.BOTH;
		gbc_current_menu_panel.gridx = 1;
		gbc_current_menu_panel.gridy = 2;
		rp_mt_current_menu.add(current_menu_panel, gbc_current_menu_panel);
		
		JButton btnUpdateMenu = new JButton("Update menu");
		GridBagConstraints gbc_btnUpdateMenu = new GridBagConstraints();
		gbc_btnUpdateMenu.anchor = GridBagConstraints.WEST;
		gbc_btnUpdateMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateMenu.gridx = 2;
		gbc_btnUpdateMenu.gridy = 2;
		rp_mt_current_menu.add(btnUpdateMenu, gbc_btnUpdateMenu);
		
		JPanel rp_mt_update_menu = new JPanel();
		restaurant_panel_menu_tab.add(rp_mt_update_menu, "name_139473809524786");
		GridBagLayout gbl_rp_mt_update_menu = new GridBagLayout();
		gbl_rp_mt_update_menu.columnWidths = new int[]{0, 49, 0, 0, 0, 60, 0, 0, 0};
		gbl_rp_mt_update_menu.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_rp_mt_update_menu.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rp_mt_update_menu.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rp_mt_update_menu.setLayout(gbl_rp_mt_update_menu);
		
		JLabel lblSummary = new JLabel("Summary:");
		GridBagConstraints gbc_lblSummary = new GridBagConstraints();
		gbc_lblSummary.anchor = GridBagConstraints.WEST;
		gbc_lblSummary.insets = new Insets(0, 0, 5, 5);
		gbc_lblSummary.gridx = 1;
		gbc_lblSummary.gridy = 1;
		rp_mt_update_menu.add(lblSummary, gbc_lblSummary);
		
		JPanel menu_summary_panel = new JPanel();
		menu_summary_panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_menu_summary_panel = new GridBagConstraints();
		gbc_menu_summary_panel.gridwidth = 5;
		gbc_menu_summary_panel.insets = new Insets(0, 0, 5, 5);
		gbc_menu_summary_panel.fill = GridBagConstraints.BOTH;
		gbc_menu_summary_panel.gridx = 1;
		gbc_menu_summary_panel.gridy = 2;
		rp_mt_update_menu.add(menu_summary_panel, gbc_menu_summary_panel);
		
		JButton btnUpdate = new JButton("Update");
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdate.gridx = 6;
		gbc_btnUpdate.gridy = 2;
		rp_mt_update_menu.add(btnUpdate, gbc_btnUpdate);
		
		JLabel lblAddItem = new JLabel("Add Item:");
		GridBagConstraints gbc_lblAddItem = new GridBagConstraints();
		gbc_lblAddItem.anchor = GridBagConstraints.EAST;
		gbc_lblAddItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddItem.gridx = 1;
		gbc_lblAddItem.gridy = 3;
		rp_mt_update_menu.add(lblAddItem, gbc_lblAddItem);
		
		JLabel lblTitle = new JLabel("Title:");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 4;
		rp_mt_update_menu.add(lblTitle, gbc_lblTitle);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 2;
		gbc_textField_5.gridy = 4;
		rp_mt_update_menu.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price:");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.EAST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 3;
		gbc_lblPrice.gridy = 4;
		rp_mt_update_menu.add(lblPrice, gbc_lblPrice);
		
		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 4;
		gbc_textField_6.gridy = 4;
		rp_mt_update_menu.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JButton button = new JButton("+");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 5;
		gbc_button.gridy = 4;
		rp_mt_update_menu.add(button, gbc_button);
		
		JPanel customer_reg_panel = new JPanel();
		contentPane.add(customer_reg_panel, "customer_reg_panel");
		GridBagLayout gbl_customer_reg_panel = new GridBagLayout();
		gbl_customer_reg_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_customer_reg_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_customer_reg_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_customer_reg_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		customer_reg_panel.setLayout(gbl_customer_reg_panel);
		
		JLabel lblCustomerRegistration = new JLabel("Customer registration:");
		GridBagConstraints gbc_lblCustomerRegistration = new GridBagConstraints();
		gbc_lblCustomerRegistration.anchor = GridBagConstraints.WEST;
		gbc_lblCustomerRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerRegistration.gridx = 1;
		gbc_lblCustomerRegistration.gridy = 1;
		customer_reg_panel.add(lblCustomerRegistration, gbc_lblCustomerRegistration);
		
		JLabel lblUsername_2 = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername_2 = new GridBagConstraints();
		gbc_lblUsername_2.anchor = GridBagConstraints.EAST;
		gbc_lblUsername_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername_2.gridx = 1;
		gbc_lblUsername_2.gridy = 2;
		customer_reg_panel.add(lblUsername_2, gbc_lblUsername_2);
		
		creg_username = new JTextField();
		GridBagConstraints gbc_creg_username = new GridBagConstraints();
		gbc_creg_username.insets = new Insets(0, 0, 5, 5);
		gbc_creg_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_creg_username.gridx = 2;
		gbc_creg_username.gridy = 2;
		customer_reg_panel.add(creg_username, gbc_creg_username);
		creg_username.setColumns(10);
		
		JLabel lblFullName = new JLabel("Full Name:");
		GridBagConstraints gbc_lblFullName = new GridBagConstraints();
		gbc_lblFullName.anchor = GridBagConstraints.EAST;
		gbc_lblFullName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFullName.gridx = 1;
		gbc_lblFullName.gridy = 3;
		customer_reg_panel.add(lblFullName, gbc_lblFullName);
		
		creg_fullname = new JTextField();
		GridBagConstraints gbc_creg_fullname = new GridBagConstraints();
		gbc_creg_fullname.insets = new Insets(0, 0, 5, 5);
		gbc_creg_fullname.fill = GridBagConstraints.HORIZONTAL;
		gbc_creg_fullname.gridx = 2;
		gbc_creg_fullname.gridy = 3;
		customer_reg_panel.add(creg_fullname, gbc_creg_fullname);
		creg_fullname.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 4;
		customer_reg_panel.add(lblEmail, gbc_lblEmail);
		
		creg_email = new JTextField();
		GridBagConstraints gbc_creg_email = new GridBagConstraints();
		gbc_creg_email.insets = new Insets(0, 0, 5, 5);
		gbc_creg_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_creg_email.gridx = 2;
		gbc_creg_email.gridy = 4;
		customer_reg_panel.add(creg_email, gbc_creg_email);
		creg_email.setColumns(10);
		
		JLabel lblPassword_2 = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword_2 = new GridBagConstraints();
		gbc_lblPassword_2.anchor = GridBagConstraints.EAST;
		gbc_lblPassword_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword_2.gridx = 1;
		gbc_lblPassword_2.gridy = 5;
		customer_reg_panel.add(lblPassword_2, gbc_lblPassword_2);
		
		creg_password = new JPasswordField();
		GridBagConstraints gbc_creg_password = new GridBagConstraints();
		gbc_creg_password.insets = new Insets(0, 0, 5, 5);
		gbc_creg_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_creg_password.gridx = 2;
		gbc_creg_password.gridy = 5;
		customer_reg_panel.add(creg_password, gbc_creg_password);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Customer customer = new Customer();
				customer.setUsername(creg_username.getText());
				customer.setFullname(creg_fullname.getText());
				customer.setEmail(creg_email.getText());
				customer.setPassword(creg_password.getText());
				
				try {
					Registration registration = new RegistrationServiceLocator().getRegistration();
					registration.registerCustomer(customer);
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "home_panel");
					JOptionPane.showMessageDialog(home_panel, "Customer registration successful! Please login.");
					
					// Clear text
					creg_username.setText("");
					creg_fullname.setText("");
					creg_email.setText("");
					creg_password.setText("");
				} catch (NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "Please fill in all required fields.");
				} catch (UsernameAlreadyTakenException ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "That username is already taken. Please pick a new username.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "An exception has occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.EAST;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 2;
		gbc_btnSubmit.gridy = 6;
		customer_reg_panel.add(btnSubmit, gbc_btnSubmit);
		
		JPanel restaurant_reg_panel = new JPanel();
		contentPane.add(restaurant_reg_panel, "restaurant_reg_panel");
		GridBagLayout gbl_restaurant_reg_panel = new GridBagLayout();
		gbl_restaurant_reg_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_restaurant_reg_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_restaurant_reg_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_restaurant_reg_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		restaurant_reg_panel.setLayout(gbl_restaurant_reg_panel);
		
		JLabel label = new JLabel("Restaurant registration:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		restaurant_reg_panel.add(label, gbc_label);
		
		JLabel label_1 = new JLabel("Username:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		restaurant_reg_panel.add(label_1, gbc_label_1);
		
		rreg_username = new JTextField();
		rreg_username.setColumns(10);
		GridBagConstraints gbc_rreg_username = new GridBagConstraints();
		gbc_rreg_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_rreg_username.insets = new Insets(0, 0, 5, 5);
		gbc_rreg_username.gridx = 2;
		gbc_rreg_username.gridy = 2;
		restaurant_reg_panel.add(rreg_username, gbc_rreg_username);
		
		JLabel lblRestaurantName = new JLabel("Restaurant Name:");
		GridBagConstraints gbc_lblRestaurantName = new GridBagConstraints();
		gbc_lblRestaurantName.anchor = GridBagConstraints.EAST;
		gbc_lblRestaurantName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRestaurantName.gridx = 1;
		gbc_lblRestaurantName.gridy = 3;
		restaurant_reg_panel.add(lblRestaurantName, gbc_lblRestaurantName);
		
		rreg_restaurantname = new JTextField();
		rreg_restaurantname.setColumns(10);
		GridBagConstraints gbc_rreg_restaurantname = new GridBagConstraints();
		gbc_rreg_restaurantname.fill = GridBagConstraints.HORIZONTAL;
		gbc_rreg_restaurantname.insets = new Insets(0, 0, 5, 5);
		gbc_rreg_restaurantname.gridx = 2;
		gbc_rreg_restaurantname.gridy = 3;
		restaurant_reg_panel.add(rreg_restaurantname, gbc_rreg_restaurantname);
		
		JLabel lblRestaurantAddress = new JLabel("Restaurant Address:");
		GridBagConstraints gbc_lblRestaurantAddress = new GridBagConstraints();
		gbc_lblRestaurantAddress.anchor = GridBagConstraints.EAST;
		gbc_lblRestaurantAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblRestaurantAddress.gridx = 1;
		gbc_lblRestaurantAddress.gridy = 4;
		restaurant_reg_panel.add(lblRestaurantAddress, gbc_lblRestaurantAddress);
		
		rreg_address = new JTextField();
		rreg_address.setColumns(10);
		GridBagConstraints gbc_rreg_address = new GridBagConstraints();
		gbc_rreg_address.fill = GridBagConstraints.HORIZONTAL;
		gbc_rreg_address.insets = new Insets(0, 0, 5, 5);
		gbc_rreg_address.gridx = 2;
		gbc_rreg_address.gridy = 4;
		restaurant_reg_panel.add(rreg_address, gbc_rreg_address);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail_1 = new GridBagConstraints();
		gbc_lblEmail_1.anchor = GridBagConstraints.EAST;
		gbc_lblEmail_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail_1.gridx = 1;
		gbc_lblEmail_1.gridy = 5;
		restaurant_reg_panel.add(lblEmail_1, gbc_lblEmail_1);
		
		rreg_email = new JTextField();
		rreg_email.setColumns(10);
		GridBagConstraints gbc_rreg_email = new GridBagConstraints();
		gbc_rreg_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_rreg_email.insets = new Insets(0, 0, 5, 5);
		gbc_rreg_email.gridx = 2;
		gbc_rreg_email.gridy = 5;
		restaurant_reg_panel.add(rreg_email, gbc_rreg_email);
		
		JLabel label_6 = new JLabel("Password:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 1;
		gbc_label_6.gridy = 6;
		restaurant_reg_panel.add(label_6, gbc_label_6);
		
		rreg_password = new JPasswordField();
		GridBagConstraints gbc_rreg_password = new GridBagConstraints();
		gbc_rreg_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_rreg_password.insets = new Insets(0, 0, 5, 5);
		gbc_rreg_password.gridx = 2;
		gbc_rreg_password.gridy = 6;
		restaurant_reg_panel.add(rreg_password, gbc_rreg_password);
		
		JButton button_1 = new JButton("Submit");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Restaurant restaurant = new Restaurant();
				restaurant.setUsername(rreg_username.getText());
				restaurant.setRestaurantName(rreg_restaurantname.getText());
				restaurant.setEmail(rreg_email.getText());
				restaurant.setAddress(rreg_address.getText());
				restaurant.setPassword(rreg_password.getText());
				
				try {
					Registration registration = new RegistrationServiceLocator().getRegistration();
					registration.registerRestaurant(restaurant);
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "home_panel");
					JOptionPane.showMessageDialog(home_panel, "Restaurant registration successful! Please login.");
					
					// Clear text
					rreg_username.setText("");
					rreg_restaurantname.setText("");
					rreg_email.setText("");
					rreg_address.setText("");
					rreg_password.setText("");
				} catch (NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "Please fill in all required fields.");
				} catch (UsernameAlreadyTakenException ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "That username is already taken. Please pick a new username.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(customer_reg_panel, "An exception has occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.EAST;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 7;
		restaurant_reg_panel.add(button_1, gbc_button_1);
	}

}
