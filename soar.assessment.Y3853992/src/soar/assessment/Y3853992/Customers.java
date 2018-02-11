package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customers {
	public int getCustomerID(String username) throws NoValidEntryException, ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS WHERE USERNAME=" + "\'" + username + "\'");
		if(rs.next()) {
			return rs.getInt("CUSTOMER_ID");
		} else {
			throw new NoValidEntryException("Unable to find a valid ID for the given username.");
		}
	}
	
	public void placeOrder(Order order) throws ClassNotFoundException, SQLException, NullFieldException, InvalidPaymentException {
		if(order.getCardNumber() == null || order.getCardNumber().isEmpty() ||
				order.getDeliveryAddress() == null || order.getDeliveryAddress().isEmpty()) {
			throw new NullFieldException("Card number and delivery address must be provided.");
		}
		
		if(order.getCardNumber().length() != 16) {
			throw new InvalidPaymentException("Card number should be 16 digits.");
		}
		
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ORDERS("
        		+ "ORDER_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "RESTAURANT_ID INT NOT NULL, "
        		+ "CUSTOMER_ID INT NOT NULL, "
        		+ "TOTAL_PRICE FLOAT NOT NULL, "
        		+ "STATUS VARCHAR(35) NOT NULL, "
           		+ "CARDNUMBER VARCHAR(35) NOT NULL, "
           		+ "DELIVERY_ADDRESS VARCHAR(35) NOT NULL, "
        		+ "DELIVERY_TIME INT, "
        		+ "FOREIGN KEY (RESTAURANT_ID) REFERENCES RESTAURANTS(RESTAURANT_ID) ON DELETE CASCADE, "
        		+ "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID) ON DELETE CASCADE"
        		+ ")");
        
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ORDERITEMS("
        		+ "ORDERITEM_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "ORDER_ID INT NOT NULL, "
        		+ "ITEM_ID INT NOT NULL, "
        		+ "QUANTITY INT NOT NULL, "
        		+ "FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ORDER_ID) ON DELETE CASCADE, "
        		+ "FOREIGN KEY (ITEM_ID) REFERENCES ITEMS(ITEM_ID) ON DELETE CASCADE"
        		+ ")");
        
        // Calculate total price
        Item[] items = order.getItems();
        float totalPrice = 0;
        for(int i = 0; i < items.length; i++) {
        	totalPrice += (items[i].getPrice() * items[i].getQuantity());
        }
        
        // Add order to orders table
        stmt.executeUpdate("INSERT INTO ORDERS (RESTAURANT_ID, CUSTOMER_ID, TOTAL_PRICE, STATUS, CARDNUMBER, DELIVERY_ADDRESS, DELIVERY_TIME) VALUES ("
        		+ "\'" + items[0].getRestaurantID() + "\', "
        		+ "\'" + order.getCustomerID() + "\', "
        		+ "\'" + totalPrice + "\', "
        		+ "\'QUEUED\', "
        		+ "\'" + order.getCardNumber() + "\', "
        		+ "\'" + order.getDeliveryAddress() + "\', "
        		+ "-1"
        		+ ")" );
        
        ResultSet rs = stmt.getGeneratedKeys();
        int orderID = -1;
        rs.next();
        orderID = rs.getInt(1);
        
        for(int i = 0; i < items.length; i++) {
            stmt.executeUpdate("INSERT INTO ORDERITEMS (ORDER_ID, ITEM_ID, QUANTITY) VALUES ("
            		+ "\'" + orderID + "\', "
            		+ "\'" + items[i].getItemID() + "\', "
            		+ "\'" + items[i].getQuantity() + "\'"
            		+ ")" );
        }
	}
	
    public Order[] getOrders(int customerID) throws ClassNotFoundException, SQLException, InvalidIDException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		
        ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=" + customerID);
        if(!rs.next()) {
        	throw new InvalidIDException("Invalid customer ID.");
        }
		
		rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE CUSTOMER_ID=" + customerID);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		while(rs.next()) {
			Order order = new Order();
			order.setOrderID(rs.getInt("ORDER_ID"));
			order.setRestaurantID(rs.getInt("RESTAURANT_ID"));
			order.setCustomerID(rs.getInt("CUSTOMER_ID"));
			order.setTotalPrice(rs.getFloat("TOTAL_PRICE"));
			order.setStatus(rs.getString("STATUS"));
			order.setDeliveryTime(rs.getInt("DELIVERY_TIME"));
			orders.add(order);
		}
		
		for(Order order : orders) {
			rs = stmt.executeQuery("SELECT * FROM ORDERITEMS WHERE ORDER_ID=" + order.getOrderID());
			ArrayList<Item> items = new ArrayList<Item>();
			while(rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ITEM_ID"));
				items.add(item);
				item.setQuantity(rs.getInt("QUANTITY"));
			}
			
			for(Item item : items) {
				rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID=" + item.getItemID());
				rs.next();
				item.setPrice(rs.getFloat("PRICE"));
				item.setTitle(rs.getString("TITLE"));
				item.setRestaurantID(rs.getInt("RESTAURANT_ID"));
			}
			
			Item[] itemArr = items.toArray(new Item[items.size()]);
			order.setItems(itemArr);
		}
		
		return orders.toArray(new Order[orders.size()]);
    }
}
