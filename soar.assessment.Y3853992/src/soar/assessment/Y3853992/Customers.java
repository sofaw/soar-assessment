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
	
	public Restaurant[] searchForRestaurants(String searchTerm) throws ClassNotFoundException, SQLException, NoResultsException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE RESTAURANT_NAME LIKE " + "\'%" + searchTerm + "%\'");
		
		ArrayList<Restaurant> results = new ArrayList<Restaurant>();
		while(rs.next()) {
			Restaurant r = new Restaurant();
			r.setRestaurantID(rs.getInt("RESTAURANT_ID"));
			r.setRestaurantName(rs.getString("RESTAURANT_NAME"));
			r.setEmail(rs.getString("EMAIL"));
			r.setAddress(rs.getString("ADDRESS"));
			results.add(r);
		}
		
		if(results.size() == 0) {
			throw new NoResultsException("No results for given search term.");
		}
		
		return results.toArray(new Restaurant[results.size()]);
	}
	
	public Item[] getMenu(int restaurantID) throws ClassNotFoundException, SQLException, NoResultsException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE RESTAURANT_ID=" + restaurantID);
		
		ArrayList<Item> results = new ArrayList<Item>();
		while(rs.next()) {
			Item item = new Item();
			item.setItemID(rs.getInt("ITEM_ID"));
			item.setRestaurantID(restaurantID);
			item.setTitle(rs.getString("TITLE"));
			item.setPrice(rs.getFloat("PRICE"));
			results.add(item);
		}
		
		if(results.size() == 0) {
			throw new NoResultsException("No results for given restaurantID.");
		}
		
		return results.toArray(new Item[results.size()]);
	}
	
	public void placeOrder(int customerID, Item[] items, String cardNumber, String deliveryAddress) throws ClassNotFoundException, SQLException, NullFieldException {
		if(cardNumber == null || cardNumber.isEmpty() ||
				deliveryAddress == null || deliveryAddress.isEmpty()) {
			throw new NullFieldException("Card number and delivery address must be provided.");
		}
		
		// Get item counts
		Map<Item, Integer> itemCounts = new HashMap<Item, Integer>();
		for(int i = 0; i < items.length; i++) {
			// Check if there is existing entry for given itemID
			boolean existingKey = false;
			Item key = null;
			for(Item it : itemCounts.keySet()) {
				if(it.getItemID() == items[i].getItemID()) {
					existingKey = true;
					key = it;
				}
			}
			
			if(existingKey) {
				int count = itemCounts.get(key);
				itemCounts.put(key, count+1);	
			} else {
				itemCounts.put(items[i], 1);
			}
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
        float totalPrice = 0;
        for(Item item : itemCounts.keySet()) {
        	totalPrice += (item.getPrice() * itemCounts.get(item));
        }
        
        // Add order to orders table
        stmt.executeUpdate("INSERT INTO ORDERS (RESTAURANT_ID, CUSTOMER_ID, TOTAL_PRICE, STATUS, CARDNUMBER, DELIVERY_ADDRESS, DELIVERY_TIME) VALUES ("
        		+ "\'" + items[0].getRestaurantID() + "\', "
        		+ "\'" + customerID + "\', "
        		+ "\'" + totalPrice + "\', "
        		+ "\'QUEUED\', "
        		+ "\'" + cardNumber + "\', "
        		+ "\'" + deliveryAddress + "\', "
        		+ "null"
        		+ ")" );
        
        ResultSet rs = stmt.getGeneratedKeys();
        int orderID = -1;
        rs.next();
        orderID = rs.getInt(1);
        
        for(Item item : itemCounts.keySet()) {
            stmt.executeUpdate("INSERT INTO ORDERITEMS (ORDER_ID, ITEM_ID, QUANTITY) VALUES ("
            		+ "\'" + orderID + "\', "
            		+ "\'" + item.getItemID() + "\', "
            		+ "\'" + itemCounts.get(item) + "\'"
            		+ ")" );
        }
	}
	
    public Order[] getOrders(int customerID) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE CUSTOMER_ID=" + customerID);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		while(rs.next()) {
			Order order = new Order();
			int orderID = rs.getInt("ORDER_ID");
			order.setOrderID(orderID);
			order.setTotalPrice(rs.getFloat("TOTAL_PRICE"));
			order.setStatus(rs.getString("STATUS"));
			order.setDeliveryTime(rs.getInt("DELIVERY_TIME"));
			
			ResultSet rs_orderItems = stmt.executeQuery("SELECT * FROM ORDERITEMS WHERE ORDER_ID=" + orderID);
			ArrayList<Item> items = new ArrayList<Item>();
			ArrayList<Integer> quantities = new ArrayList<Integer>();
			while(rs_orderItems.next()) {
				int quantity = rs_orderItems.getInt("QUANTITY");
				int itemID = rs_orderItems.getInt("ITEM_ID");
				ResultSet rs_item = stmt.executeQuery("SELECT * FROM ITEMS WHERE ITEM_ID=" + itemID);
				rs_item.next();
				
				Item item = new Item();
				item.setItemID(itemID);
				item.setPrice(rs_item.getFloat("PRICE"));
				item.setTitle(rs_item.getString("TITLE"));
				item.setRestaurantID(rs_item.getInt("RESTAURANT_ID"));
				
				items.add(item);
				quantities.add(quantity);
			}
			Item[] itemArr = items.toArray(new Item[items.size()]);
			Integer[] quantitiesArr = quantities.toArray(new Integer[quantities.size()]);
			order.setItems(itemArr);
			order.setQuantities(quantitiesArr);
		}
		
		return orders.toArray(new Order[orders.size()]);
    }
}
