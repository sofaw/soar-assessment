package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Restaurants {
	public int getRestaurantID(String username) throws ClassNotFoundException, SQLException, InvalidUsernameException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE USERNAME=" + "\'" + username + "\'");
		if(rs.next()) {
			return rs.getInt("RESTAURANT_ID");
		} else {
			throw new InvalidUsernameException(username);
		}
	}
	
	public void updateMenu(int restaurantID, Item[] items) throws ClassNotFoundException, SQLException, InvalidItemException, InvalidIDException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE RESTAURANT_ID=" + restaurantID);
        if(!rs.next()) {
        	throw new InvalidIDException(restaurantID);
        }
        
        // Check items are valid
        for(int i = 0; i < items.length; i++) {
        	if(items[i].getTitle() == null || items[i].getTitle().isEmpty() ||
        			items[i].getPrice() < 0) {
        		throw new InvalidItemException(items[i].getItemID());
        	}
        }
		
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

	public Order[] getOrders(int restaurantID) throws ClassNotFoundException, SQLException, InvalidIDException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE RESTAURANT_ID=" + restaurantID);
        if(!rs.next()) {
        	throw new InvalidIDException(restaurantID);
        }
        
		rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE RESTAURANT_ID=" + restaurantID);
		
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
	
	public void changeOrderStatus(int restaurantID, int orderID, String status, int deliveryTime) throws ClassNotFoundException, 
		SQLException, InvalidIDException, NullFieldException, InvalidStatusException, UnauthorizedException {
		
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE RESTAURANT_ID=" + restaurantID);
		if(!rs.next()) {
			throw new InvalidIDException(restaurantID);
		}
		rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE ORDER_ID=" + orderID);
		if(!rs.next()) {
			throw new InvalidIDException(orderID);
		}
		if(rs.getInt("RESTAURANT_ID") != restaurantID) {
			throw new UnauthorizedException(restaurantID);
		}
		
		boolean updated = false;
        if(deliveryTime > 0) {
        	stmt.executeUpdate("UPDATE ORDERS SET DELIVERY_TIME =" + deliveryTime + " WHERE ORDER_ID =" + orderID);
        	updated = true;
        }
        
        if(status != null && !status.isEmpty()) {
        	if(!(status.equals("QUEUED") || status.equals("ACCEPTED") || status.equals("REJECTED") 
        			|| status.equals("UNDER PREPARATION") || status.equals("ON THE WAY") || status.equals("DELIVERED"))) {
        		throw new InvalidStatusException(status);
        	}
        	stmt.executeUpdate("UPDATE ORDERS SET STATUS =\'" + status + "\' WHERE ORDER_ID =" + orderID);
        	updated = true;
        }
        
        // If both status and deliveryTime are invalid (i.e. no updates) throw exception
        if(!updated) {
        	throw new NullFieldException();
        }
	}
}
