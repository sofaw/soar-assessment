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
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class GraphicalClient extends JFrame {

	private JPanel contentPane;
	private JPanel home_panel;
	private JTextField clogin_username;
	private JTextField rlogin_username;
	private JPasswordField clogin_password;
	private JPasswordField rlogin_password;
	private JTextField textField_1;
	private JTextField c_search;
	private JTextField c_search_2;
	private GridBagLayout gbl_basket_panel;
	private JTextField r_menu_title_text;
	private JTextField r_menu_price_text;
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
	private JList<Order> r_queued_list;
	private JList<Order> r_accepted_list;
	private JList<Item> menu_list;
	private JList<Item> summary_menu_list;
	
	private int restaurantID = -1;
	private int customerID = -1;
	
	private EngineConfiguration restaurantsConfig;		
	private RestaurantsSoapBindingStub restaurants;
	EngineConfiguration customersConfig;
	CustomersSoapBindingStub customers;
	private JTextField card_number;
	private JTextField delivery_address;
	private JTextField r_time_text;
	
	// Push notifications
	private Topic restaurantTopic; // For msgs corresponding to restaurant order updates
	private Topic customerTopic; // For msgs corresponding to a customer placing an order
	private Session session;

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
	 * @throws JMSException 
	 */
	public GraphicalClient() throws JMSException {
		// Setup stubs
		try {
			restaurantsConfig = new FileProvider(GraphicalClient.class.getResourceAsStream("restaurantclient.wsdd"));
			restaurants = (RestaurantsSoapBindingStub) new RestaurantsServiceLocator(restaurantsConfig).getRestaurants();
			customersConfig = new FileProvider(GraphicalClient.class.getResourceAsStream("customerclient.wsdd"));
			customers = (CustomersSoapBindingStub) new CustomersServiceLocator(customersConfig).getCustomers();
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
		
		// Setup push notifications
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		ListCellRenderer<Order> orderListCellRenderer = new ListCellRenderer<Order>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Order> list, Order value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				String deliveryEstimate;
				if(value.getDeliveryTime() < 0) {
					deliveryEstimate = "N/A"; // No estimate provided
				} else {
					int hours = value.getDeliveryTime() / 60;
					int minutes = value.getDeliveryTime() % 60;
					String hoursString = "" + hours;
					String minutesString = "" + minutes;
					if(hoursString.length() == 1) {
						hoursString = "0" + hoursString;
					}
					if(minutesString.length() == 1) {
						minutesString = "0" + minutesString;
					}
					deliveryEstimate = hoursString + ":" + minutesString;
				}
				
				JLabel orderLabel = new JLabel("<html>OrderID: " + value.getOrderID() 
													+ "<br>Status: " + value.getStatus()
													+ "<br>Total Cost: £" + value.getTotalPrice()
													+ "<br>Delivery Estimate: " + deliveryEstimate
													+ "</html>");
				panel.add(orderLabel);
				JScrollPane itemSummaryPane = new JScrollPane();
				JList<Item> itemSummary = new JList<Item>();
				itemSummaryPane.setViewportView(itemSummary);
				itemSummary.setCellRenderer(new ListCellRenderer<Item>() {

					@Override
					public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
							boolean isSelected, boolean cellHasFocus) {
						return new JLabel("(x" + value.getQuantity() + ") " 
							+ value.getTitle() + ": £" + value.getPrice());
					}
					
				});
				
				DefaultListModel dlm = new DefaultListModel();
				Item[] items = value.getItems();
				for(int i = 0; i < items.length; i++) {
					dlm.addElement(items[i]);
				}
				
				itemSummary.setModel(dlm);
				
				panel.add(itemSummaryPane);
				
				panel.setOpaque(true);
				if (isSelected) {
				    panel.setBackground(list.getSelectionBackground());
				    panel.setForeground(list.getSelectionForeground());
				} else {
				    panel.setBackground(list.getBackground());
				    panel.setForeground(list.getForeground());
				}
				
				return panel;
			}
		};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		home_panel = new JPanel();
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
					
					// Setup push notifications
					customerTopic = session.createTopic("customerTopic");
					
					restaurantTopic = session.createTopic("restaurantTopic");
					MessageConsumer consumer = session.createConsumer(restaurantTopic, 
							"CustomerID = " + customerID);
					CustomerListener listener = new CustomerListener();
					consumer.setMessageListener(listener);
					
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "customer_panel");
				} catch(InvalidUsernameException ex) {
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
					
					// Setup push notifications
					restaurantTopic = session.createTopic("restaurantTopic");
					customerTopic = session.createTopic("customerTopic");
					MessageConsumer consumer = session.createConsumer(customerTopic, 
							"RestaurantID = " + restaurantID);
					RestaurantListener listener = new RestaurantListener();
					consumer.setMessageListener(listener);
					
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "restaurant_panel");
				} catch(InvalidUsernameException ex) {
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
					Search search = new SearchServiceLocator().getSearch();
					Restaurant[] searchResults = search.searchForRestaurants(searchTerm);
					
					// Display each of the search results in the results panel
					DefaultListModel<Restaurant> dlm = new DefaultListModel<Restaurant>();
					for(int i = 0; i < searchResults.length; i++) {
						dlm.addElement(searchResults[i]);
					}
					
					c_search_list.setModel(dlm);
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_results");
				} catch(NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "Please provide a non-empty search term.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "Incorrect username/password.");
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
						Search search = new SearchServiceLocator().getSearch();
						Item[] items = search.getMenu(restaurantId);
						
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
									
									int basketIndex = -1;
									for(int i = 0; i < basket_contents.getSize(); i++) {
										if(basket_contents.getElementAt(i).getItemID() == currentItem.getItemID()) {
											basketIndex = i;
										}
									}
									
									if(basketIndex == -1) {
										currentItem.setQuantity(1);
										basket_contents.addElement(currentItem);
									} else {
										Item currItem = basket_contents.getElementAt(basketIndex);
										int currQuantity = currItem.getQuantity();
										currItem.setQuantity(currQuantity + 1);
										basket_contents.setElementAt(currItem, basketIndex);
									}
									
									basket_list.setModel(basket_contents);
									
									// Update order total
									float total = 0;
									for(int x = 0; x < basket_contents.getSize(); x++) {
										total += (basket_contents.getElementAt(x).getPrice() 
												* basket_contents.getElementAt(x).getQuantity());
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
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "Restaurant has not provided a menu. Please try a different restaurant.");
					} catch (InvalidIDException ex) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "Invalid restaurant ID. Please try again.");
					} catch (RemoteException ex) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
						ex.printStackTrace();
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
					Search search = new SearchServiceLocator().getSearch();
					Restaurant[] searchResults = search.searchForRestaurants(searchTerm);
					
					// Display each of the search results in the results panel
					DefaultListModel<Restaurant> dlm = new DefaultListModel<Restaurant>();
					for(int i = 0; i < searchResults.length; i++) {
						dlm.addElement(searchResults[i]);
					}
					
					c_search_list.setModel(dlm);
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_results");
				} catch(NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "Please provide a non-empty search term.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
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
				return new JLabel("(x" + value.getQuantity() + ") " + value.getTitle() + ": £" + value.getPrice());
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
					Order order = new Order();
					order.setCustomerID(customerID);
					order.setItems(basketArr);
					order.setCardNumber(card_number.getText());
					order.setDeliveryAddress(delivery_address.getText());
					customers.placeOrder(order);
					
					sendCustomerMessage("placing order", basketArr[0].getRestaurantID());
					
					CardLayout cardLayout = (CardLayout) customer_panel_search_tab.getLayout();
					cardLayout.show(customer_panel_search_tab, "customer_tab_1_order_placed");
				} catch(NullFieldException ex) {
					JOptionPane.showMessageDialog(customer_tab_1_details, "Please fill in all required fields.");
				} catch(InvalidPaymentException ex) {
					JOptionPane.showMessageDialog(customer_tab_1_details, "Please provide 16 digit card number.");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(customer_tab_1_details, "An error has occurred. Please try again.");
				} catch (JMSException ex) {
					ex.printStackTrace();
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
		
		JButton c_order_refresh_button = new JButton("Refresh");
		c_order_refresh_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Order[] orders = customers.getOrders(customerID);
					DefaultListModel<Order> dlm = new DefaultListModel<Order>();
					for(int i = 0; i < orders.length; i++) {
						dlm.addElement(orders[i]);
					}
					
					c_orders_list.setModel(dlm);
					
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(customer_panel_order_status_tab, "No orders to display.");
				} catch (InvalidIDException ex) {
					JOptionPane.showMessageDialog(customer_panel_order_status_tab, "Invalid customer ID. Please try again.");
				} catch (RemoteException ex) {
					JOptionPane.showMessageDialog(customer_panel_order_status_tab, "An error has occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_c_order_refresh_button = new GridBagConstraints();
		gbc_c_order_refresh_button.anchor = GridBagConstraints.EAST;
		gbc_c_order_refresh_button.insets = new Insets(0, 0, 5, 5);
		gbc_c_order_refresh_button.gridx = 3;
		gbc_c_order_refresh_button.gridy = 1;
		customer_panel_order_status_tab.add(c_order_refresh_button, gbc_c_order_refresh_button);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		customer_panel_order_status_tab.add(scrollPane, gbc_scrollPane);
		
		c_orders_list = new JList<Order>();
		c_orders_list.setCellRenderer(new ListCellRenderer<Order>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Order> list, Order value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				String deliveryEstimate;
				if(value.getDeliveryTime() < 0) {
					deliveryEstimate = "N/A"; // No estimate provided
				} else {
					int hours = value.getDeliveryTime() / 60;
					int minutes = value.getDeliveryTime() % 60;
					String hoursString = "" + hours;
					String minutesString = "" + minutes;
					if(hoursString.length() == 1) {
						hoursString = "0" + hoursString;
					}
					if(minutesString.length() == 1) {
						minutesString = "0" + minutesString;
					}
					deliveryEstimate = hoursString + ":" + minutesString;
				}
				
				JLabel orderLabel = new JLabel("<html>OrderID: " + value.getOrderID() 
													+ "<br>Status: " + value.getStatus()
													+ "<br>Total Cost: £" + value.getTotalPrice()
													+ "<br>Delivery Estimate: " + deliveryEstimate
													+ "</html>");
				panel.add(orderLabel);
				JScrollPane itemSummaryPane = new JScrollPane();
				JList<Item> itemSummary = new JList<Item>();
				itemSummaryPane.setViewportView(itemSummary);
				itemSummary.setCellRenderer(new ListCellRenderer<Item>() {

					@Override
					public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
							boolean isSelected, boolean cellHasFocus) {
						return new JLabel("(x" + value.getQuantity() + ") " 
							+ value.getTitle() + ": £" + value.getPrice());
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
		gbl_restaurant_panel_orders_tab.columnWidths = new int[]{0, 0, 0, 124, 0, 0};
		gbl_restaurant_panel_orders_tab.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_restaurant_panel_orders_tab.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_restaurant_panel_orders_tab.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		restaurant_panel_orders_tab.setLayout(gbl_restaurant_panel_orders_tab);
		
		JLabel lblQueued = new JLabel("Queued:");
		GridBagConstraints gbc_lblQueued = new GridBagConstraints();
		gbc_lblQueued.anchor = GridBagConstraints.WEST;
		gbc_lblQueued.insets = new Insets(0, 0, 5, 5);
		gbc_lblQueued.gridx = 1;
		gbc_lblQueued.gridy = 1;
		restaurant_panel_orders_tab.add(lblQueued, gbc_lblQueued);
		
		JButton r_order_refresh_button = new JButton("Refresh");
		r_order_refresh_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Order[] orders = restaurants.getOrders(restaurantID);
					
					DefaultListModel<Order> q_dlm = new DefaultListModel<Order>();
					DefaultListModel<Order> a_dlm = new DefaultListModel<Order>();
					for(int i = 0; i < orders.length; i++) {
						Order order = orders[i];
						if(order.getStatus().equals("QUEUED")) {
							q_dlm.addElement(order);
						} else if(!order.getStatus().contentEquals("REJECTED")) {
							a_dlm.addElement(order);
						}
					}
					r_queued_list.setModel(q_dlm);
					r_accepted_list.setModel(a_dlm);
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "No orders to display.");
				} catch (InvalidIDException ex) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "Invalid restaurant ID. Please try again.");
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(customer_panel_search_tab, "An error occurred. Please try again.");
				}
			}
		});
		GridBagConstraints gbc_order_refresh_button = new GridBagConstraints();
		gbc_order_refresh_button.insets = new Insets(0, 0, 5, 5);
		gbc_order_refresh_button.gridx = 3;
		gbc_order_refresh_button.gridy = 1;
		restaurant_panel_orders_tab.add(r_order_refresh_button, gbc_order_refresh_button);
		
		JScrollPane r_queued_scroll = new JScrollPane();
		GridBagConstraints gbc_r_queued_scroll = new GridBagConstraints();
		gbc_r_queued_scroll.gridheight = 3;
		gbc_r_queued_scroll.insets = new Insets(0, 0, 5, 5);
		gbc_r_queued_scroll.fill = GridBagConstraints.BOTH;
		gbc_r_queued_scroll.gridx = 1;
		gbc_r_queued_scroll.gridy = 2;
		restaurant_panel_orders_tab.add(r_queued_scroll, gbc_r_queued_scroll);
		
		r_queued_list = new JList<Order>();
		r_queued_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		r_queued_list.setCellRenderer(orderListCellRenderer);
		r_queued_scroll.setViewportView(r_queued_list);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Order selected = r_queued_list.getSelectedValue();
				if(selected != null) {
					try {
						restaurants.changeOrderStatus(restaurantID, selected.getOrderID(), "ACCEPTED", -1);
						sendRestaurantMessage("accepting order", selected.getCustomerID());
					} catch (InvalidIDException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Order or restaurant ID does not exist.");
					} catch (NullFieldException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Make sure all fields are provided.");
					} catch (InvalidStatusException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Invalid status. Please try again.");
					} catch (UnauthorizedException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "You are not authorized to change the status of this order.");
					} catch (RemoteException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "An error occurred. Please try again.");
					} catch (JMSException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.insets = new Insets(0, 0, 5, 5);
		gbc_btnAccept.gridx = 2;
		gbc_btnAccept.gridy = 2;
		restaurant_panel_orders_tab.add(btnAccept, gbc_btnAccept);
		
		JButton btnReject = new JButton("Reject");
		btnReject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Order selected = r_queued_list.getSelectedValue();
				if(selected != null) {
					try {
						restaurants.changeOrderStatus(restaurantID, selected.getOrderID(), "REJECTED", -1);
						sendRestaurantMessage("rejecting order", selected.getCustomerID());
					} catch (InvalidIDException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Order or restaurant ID does not exist.");
					} catch (NullFieldException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Make sure all fields are provided.");
					} catch (InvalidStatusException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Invalid status. Please try again.");
					} catch (UnauthorizedException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "You are not authorized to change the status of this order.");
					} catch (RemoteException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "An error occurred. Please try again.");
					} catch (JMSException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_btnReject = new GridBagConstraints();
		gbc_btnReject.anchor = GridBagConstraints.NORTH;
		gbc_btnReject.insets = new Insets(0, 0, 5, 5);
		gbc_btnReject.gridx = 2;
		gbc_btnReject.gridy = 3;
		restaurant_panel_orders_tab.add(btnReject, gbc_btnReject);
		
		JLabel lblAccepted = new JLabel("Accepted:");
		GridBagConstraints gbc_lblAccepted = new GridBagConstraints();
		gbc_lblAccepted.anchor = GridBagConstraints.WEST;
		gbc_lblAccepted.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccepted.gridx = 1;
		gbc_lblAccepted.gridy = 5;
		restaurant_panel_orders_tab.add(lblAccepted, gbc_lblAccepted);
		
		JLabel lblNewLabel = new JLabel("New Status:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 6;
		restaurant_panel_orders_tab.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox r_status_combo = new JComboBox();
		r_status_combo.setModel(new DefaultComboBoxModel(new String[] {"UNDER PREPARATION", "ON THE WAY", "DELIVERED"}));
		GridBagConstraints gbc_r_status_combo = new GridBagConstraints();
		gbc_r_status_combo.insets = new Insets(0, 0, 5, 5);
		gbc_r_status_combo.fill = GridBagConstraints.HORIZONTAL;
		gbc_r_status_combo.gridx = 3;
		gbc_r_status_combo.gridy = 6;
		restaurant_panel_orders_tab.add(r_status_combo, gbc_r_status_combo);
		
		JLabel lblDeliveryTime = new JLabel("<html><p>Delivery Time:</p><p>hh:mm</p></html>");
		GridBagConstraints gbc_lblDeliveryTime = new GridBagConstraints();
		gbc_lblDeliveryTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeliveryTime.anchor = GridBagConstraints.EAST;
		gbc_lblDeliveryTime.gridx = 2;
		gbc_lblDeliveryTime.gridy = 7;
		restaurant_panel_orders_tab.add(lblDeliveryTime, gbc_lblDeliveryTime);
		
		r_time_text = new JTextField();
		GridBagConstraints gbc_r_time_text = new GridBagConstraints();
		gbc_r_time_text.insets = new Insets(0, 0, 5, 5);
		gbc_r_time_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_r_time_text.gridx = 3;
		gbc_r_time_text.gridy = 7;
		restaurant_panel_orders_tab.add(r_time_text, gbc_r_time_text);
		r_time_text.setColumns(10);
		
		JScrollPane r_accepted_scroll = new JScrollPane();
		GridBagConstraints gbc_r_accepted_scroll = new GridBagConstraints();
		gbc_r_accepted_scroll.gridheight = 3;
		gbc_r_accepted_scroll.insets = new Insets(0, 0, 5, 5);
		gbc_r_accepted_scroll.fill = GridBagConstraints.BOTH;
		gbc_r_accepted_scroll.gridx = 1;
		gbc_r_accepted_scroll.gridy = 6;
		restaurant_panel_orders_tab.add(r_accepted_scroll, gbc_r_accepted_scroll);
		
		r_accepted_list = new JList<Order>();
		r_accepted_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		r_accepted_list.setCellRenderer(orderListCellRenderer);
		r_accepted_scroll.setViewportView(r_accepted_list);
		
		JButton btnUpdateStatus = new JButton("Update Status");
		btnUpdateStatus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Order selected = r_accepted_list.getSelectedValue();
				if(selected != null) {
					String timeStr = r_time_text.getText();
					int time;
					if(timeStr != null && !timeStr.isEmpty() && timeStr.matches("\\d{2}:\\d{2}")) {
						String[] split = timeStr.split(":");
						time = ((Integer.valueOf(split[0]) * 60) + Integer.valueOf(split[1]));
					} else {
						time = -1; // i.e. don't update
					}
					
					try {
						restaurants.changeOrderStatus(restaurantID, 
								selected.getOrderID(), 
								r_status_combo.getSelectedItem().toString(), 
								time);

						sendRestaurantMessage("updating order status", selected.getCustomerID());
					} catch (InvalidIDException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Order or restaurant ID does not exist.");
					} catch (NullFieldException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Make sure all fields are provided.");
					} catch (InvalidStatusException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "Invalid status. Please try again.");
					} catch (UnauthorizedException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "You are not authorized to change the status of this order.");
					} catch (RemoteException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(restaurant_panel_orders_tab, "An error occurred. Please try again.");
					} catch (JMSException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_btnUpdateStatus = new GridBagConstraints();
		gbc_btnUpdateStatus.gridwidth = 2;
		gbc_btnUpdateStatus.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateStatus.gridx = 2;
		gbc_btnUpdateStatus.gridy = 8;
		restaurant_panel_orders_tab.add(btnUpdateStatus, gbc_btnUpdateStatus);
		
		JPanel restaurant_panel_menu_tab = new JPanel();
		restaurant_panel.addTab("Menu", null, restaurant_panel_menu_tab, null);
		restaurant_panel_menu_tab.setLayout(new CardLayout(0, 0));
		
		JPanel rp_mt_current_menu = new JPanel();
		restaurant_panel_menu_tab.add(rp_mt_current_menu, "rp_mt_current_menu");
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
		
		JButton btnUpdateMenu = new JButton("Update menu");
		btnUpdateMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) restaurant_panel_menu_tab.getLayout();
				cardLayout.show(restaurant_panel_menu_tab, "rp_mt_update_menu");
			}
		});
		
		JButton btnRefresh_2 = new JButton("Refresh");
		btnRefresh_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Search search = new SearchServiceLocator().getSearch();
					Item[] items = search.getMenu(restaurantID);
					DefaultListModel<Item> dlm = new DefaultListModel<Item>();
					for(int i = 0; i < items.length; i++) {
						dlm.addElement(items[i]);
					}
					menu_list.setModel(dlm);
				}  catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "No menu to display.");
				}  catch (InvalidIDException ex) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Invalid restaurant ID. Please try again.");
				} catch (RemoteException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "An error occurred. Please try again.");
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "An error occurred. Please try again.");
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnRefresh_2 = new GridBagConstraints();
		gbc_btnRefresh_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefresh_2.gridx = 2;
		gbc_btnRefresh_2.gridy = 1;
		rp_mt_current_menu.add(btnRefresh_2, gbc_btnRefresh_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 2;
		rp_mt_current_menu.add(scrollPane_1, gbc_scrollPane_1);
		
		menu_list = new JList();
		menu_list.setCellRenderer(new ListCellRenderer<Item>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value.getTitle() + ": £" + value.getPrice());
			}
			
		});
		scrollPane_1.setViewportView(menu_list);
		GridBagConstraints gbc_btnUpdateMenu = new GridBagConstraints();
		gbc_btnUpdateMenu.anchor = GridBagConstraints.WEST;
		gbc_btnUpdateMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateMenu.gridx = 2;
		gbc_btnUpdateMenu.gridy = 2;
		rp_mt_current_menu.add(btnUpdateMenu, gbc_btnUpdateMenu);
		
		JPanel rp_mt_update_menu = new JPanel();
		restaurant_panel_menu_tab.add(rp_mt_update_menu, "rp_mt_update_menu");
		GridBagLayout gbl_rp_mt_update_menu = new GridBagLayout();
		gbl_rp_mt_update_menu.columnWidths = new int[]{0, 49, 0, 0, 0, 60, 0, 0, 0};
		gbl_rp_mt_update_menu.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_rp_mt_update_menu.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_rp_mt_update_menu.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		rp_mt_update_menu.setLayout(gbl_rp_mt_update_menu);
		
		JLabel lblSummary = new JLabel("Summary:");
		GridBagConstraints gbc_lblSummary = new GridBagConstraints();
		gbc_lblSummary.anchor = GridBagConstraints.WEST;
		gbc_lblSummary.insets = new Insets(0, 0, 5, 5);
		gbc_lblSummary.gridx = 1;
		gbc_lblSummary.gridy = 1;
		rp_mt_update_menu.add(lblSummary, gbc_lblSummary);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 5;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 2;
		rp_mt_update_menu.add(scrollPane_2, gbc_scrollPane_2);
		
		summary_menu_list = new JList<Item>();
		summary_menu_list.setModel(new DefaultListModel<Item>());
		summary_menu_list.setCellRenderer(new ListCellRenderer<Item>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value.getTitle() + ": £" + value.getPrice());
			}
			
		});
		scrollPane_2.setViewportView(summary_menu_list);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<Item> dlm = (DefaultListModel<Item>) summary_menu_list.getModel();
				Item[] items = new Item[dlm.getSize()];
				for(int i = 0; i < dlm.getSize(); i++) {
					items[i] = dlm.getElementAt(i);
				}
				
				try {
					restaurants.updateMenu(restaurantID, items);
					
					CardLayout cardLayout = (CardLayout) restaurant_panel_menu_tab.getLayout();
					cardLayout.show(restaurant_panel_menu_tab, "rp_mt_current_menu");
					
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Menu has been updated. Refresh to view.");
				} catch (InvalidItemException ex) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Invalid item. Make sure no titles or prices are empty.");
				} catch (InvalidIDException ex) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Invalid restaurant ID. Please try again.");
				} catch (RemoteException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "An error occurred. Please try again.");
				}
				
			}
		});
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
		
		r_menu_title_text = new JTextField();
		GridBagConstraints gbc_r_menu_title_text = new GridBagConstraints();
		gbc_r_menu_title_text.insets = new Insets(0, 0, 5, 5);
		gbc_r_menu_title_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_r_menu_title_text.gridx = 2;
		gbc_r_menu_title_text.gridy = 4;
		rp_mt_update_menu.add(r_menu_title_text, gbc_r_menu_title_text);
		r_menu_title_text.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price:");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.EAST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 3;
		gbc_lblPrice.gridy = 4;
		rp_mt_update_menu.add(lblPrice, gbc_lblPrice);
		
		r_menu_price_text = new JTextField();
		GridBagConstraints gbc_r_menu_price_text = new GridBagConstraints();
		gbc_r_menu_price_text.insets = new Insets(0, 0, 5, 5);
		gbc_r_menu_price_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_r_menu_price_text.gridx = 4;
		gbc_r_menu_price_text.gridy = 4;
		rp_mt_update_menu.add(r_menu_price_text, gbc_r_menu_price_text);
		r_menu_price_text.setColumns(10);
		
		JButton button = new JButton("+");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Add to summary
				String title = r_menu_title_text.getText();
				String priceStr = r_menu_price_text.getText();
				float price;
				if(title == null || title.isEmpty() ||
						priceStr == null || priceStr.isEmpty()) {
					JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Both title and price must be provided.");
				} else {
					try {
						price = Float.parseFloat(priceStr);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(restaurant_panel_menu_tab, "Price must be a floating point number.");
						return;
					}
					
					DefaultListModel<Item> dlm = (DefaultListModel<Item>) summary_menu_list.getModel();
					if(dlm == null) {
						dlm = new DefaultListModel<Item>();
					}
					
					Item item = new Item();
					item.setRestaurantID(restaurantID);
					item.setTitle(title);
					item.setPrice(price);
					dlm.addElement(item);
					summary_menu_list.setModel(dlm);
					
					r_menu_title_text.setText("");
					r_menu_price_text.setText("");
				}
			}
		});
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
				} catch (InvalidUsernameException ex) {
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
				} catch (InvalidUsernameException ex) {
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

	private void updateCustomerOrders() throws RemoteException, InvalidIDException {
		Order[] orders = customers.getOrders(customerID);
		DefaultListModel<Order> dlm = new DefaultListModel<Order>();
		for(int i = 0; i < orders.length; i++) {
			dlm.addElement(orders[i]);
		}
		
		c_orders_list.setModel(dlm);
	}
	
	private void updateRestaurantOrders() throws RemoteException, InvalidIDException {
		Order[] orders = restaurants.getOrders(restaurantID);
		
		DefaultListModel<Order> q_dlm = new DefaultListModel<Order>();
		DefaultListModel<Order> a_dlm = new DefaultListModel<Order>();
		for(int i = 0; i < orders.length; i++) {
			Order order = orders[i];
			if(order.getStatus().equals("QUEUED")) {
				q_dlm.addElement(order);
			} else if(!order.getStatus().contentEquals("REJECTED")) {
				a_dlm.addElement(order);
			}
		}
		r_queued_list.setModel(q_dlm);
		r_accepted_list.setModel(a_dlm);
	}
	
	private void sendRestaurantMessage(String data, int customerID) throws JMSException {
		TextMessage message = session.createTextMessage(data);
		message.setIntProperty("CustomerID", customerID);
		MessageProducer producer = session.createProducer(restaurantTopic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.send(message);
	}
	
	private void sendCustomerMessage(String data, int restaurantID) throws JMSException {
		TextMessage message = session.createTextMessage(data);
		message.setIntProperty("RestaurantID", restaurantID);
		MessageProducer producer = session.createProducer(customerTopic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		producer.send(message);
	}
	
	private class CustomerListener implements MessageListener {

		@Override
		public void onMessage(Message arg0) {
			try {
				updateCustomerOrders();
			} catch (InvalidIDException ex) {
				ex.printStackTrace();
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
			JOptionPane.showMessageDialog(home_panel, "Restaurant has changed your order status.");
		}
		
	}
	
	private class RestaurantListener implements MessageListener {

		@Override
		public void onMessage(Message arg0) {
			try {
				updateRestaurantOrders();
			} catch (InvalidIDException ex) {
				ex.printStackTrace();
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
			JOptionPane.showMessageDialog(home_panel, "Customer has placed an order.");
		}
		
	}
}
