package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Search {
	public Restaurant[] searchForRestaurants(String searchTerm) throws ClassNotFoundException, SQLException, EmptySearchTermException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
		Statement stmt = con.createStatement();
		
		if(searchTerm == null || searchTerm.isEmpty()) {
			throw new EmptySearchTermException("A non-empty search term must be provided.");
		}
		
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
		
		return results.toArray(new Restaurant[results.size()]);
	}
	
	
	public Item[] getMenu(int restaurantID) throws ClassNotFoundException, SQLException, InvalidIDException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE RESTAURANT_ID=" + restaurantID);
		if(!rs.next()) {
			throw new InvalidIDException("Not a valid restaurant ID.");
		}
        
        rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE RESTAURANT_ID=" + restaurantID);
        
		ArrayList<Item> results = new ArrayList<Item>();
		while(rs.next()) {
			Item item = new Item();
			item.setItemID(rs.getInt("ITEM_ID"));
			item.setRestaurantID(restaurantID);
			item.setTitle(rs.getString("TITLE"));
			item.setPrice(rs.getFloat("PRICE"));
			results.add(item);
		}
		
		return results.toArray(new Item[results.size()]);
	}
}
