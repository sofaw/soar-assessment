package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Restaurants {
	public void addMenuItem(int restaurantID, String title, float price) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ITEMS("
        		+ "ITEM_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "RESTAURANT_ID  INT, "
        		+ "TITLE VARCHAR(35), "
        		+ "PRICE FLOAT, "
        		+ "FOREIGN KEY (RESTAURANT_ID) REFERENCES RESTAURANTS(RESTAURANT_ID) ON DELETE CASCADE)");

        stmt.executeUpdate("INSERT INTO ITEMS (RESTAURANT_ID, TITLE, PRICE) VALUES ("
        		+ "\'" + restaurantID + "\', "
        		+ "\'" + title + "\', "
        		+ "\'" + price + "\')" );
	}
	
	public void deleteMenu(int restaurantID) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
		
		stmt.executeUpdate("DELETE FROM ITEMS WHERE RESTAURANT_ID=" + restaurantID);
	}
	
	// TODO
	/*public void changeOrderStatus(int orderID, String status) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
	}*/
}
