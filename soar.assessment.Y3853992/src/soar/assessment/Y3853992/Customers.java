package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
