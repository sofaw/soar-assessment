package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Restaurants {
	public int getRestaurantID(String username) throws NoValidEntryException, ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE USERNAME=" + "\'" + username + "\'");
		if(rs.next()) {
			return rs.getInt("RESTAURANT_ID");
		} else {
			throw new NoValidEntryException("Unable to find a valid ID for the given username.");
		}
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
	
	public void updateMenu(int restaurantID, Item[] items) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ITEMS("
        		+ "ITEM_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "RESTAURANT_ID  INT, "
        		+ "TITLE VARCHAR(35), "
        		+ "PRICE FLOAT, "
        		+ "FOREIGN KEY (RESTAURANT_ID) REFERENCES RESTAURANTS(RESTAURANT_ID) ON DELETE CASCADE)");
        
        // Delete old menu
		stmt.executeUpdate("DELETE FROM ITEMS WHERE RESTAURANT_ID=" + restaurantID);
		
		// Add new items
		for(int i = 0; i < items.length; i++) {
            stmt.executeUpdate("INSERT INTO ITEMS (RESTAURANT_ID, TITLE, PRICE) VALUES ("
            		+ "\'" + restaurantID + "\', "
            		+ "\'" + items[i].getTitle() + "\', "
            		+ "\'" + items[i].getPrice() + "\'"
            		+ ")" );
		}
	}

	public Order[] getOrders(int restaurantID) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
		ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE CUSTOMER_ID=" + restaurantID);
		
		ArrayList<Order> orders = new ArrayList<Order>();
		
		while(rs.next()) {
			Order order = new Order();
			order.setOrderID(rs.getInt("ORDER_ID"));
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
	
	public void changeOrderStatus(int restaurantID, int orderID, String status, int deliveryTime) throws ClassNotFoundException, SQLException, InvalidIDException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
		ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE ORDER_ID=" + orderID);
		rs.next();
		if(rs.getInt("RESTAURANT_ID") != restaurantID) {
			throw new InvalidIDException("Not authorized to update this order status.");
		}
        
        if(deliveryTime > 0) {
        	stmt.executeUpdate("UPDATE ORDERS SET DELIVERY_TIME =" + deliveryTime + " WHERE ORDER_ID =" + orderID);
        }
        
        if(status != null && !status.isEmpty()) {
        	stmt.executeUpdate("UPDATE ORDERS SET STATUS =\'" + status + "\' WHERE ORDER_ID =" + orderID);
        }
	}
}
