package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RestaurantServices {
	
	public int registerRestaurant(String username, String name, String address, 
			String email, String password) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RESTAURANTS("
        		+ "RESTAURANT_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "USERNAME VARCHAR(35), "
        		+ "NAME VARCHAR(35), "
        		+ "ADDRESS VARCHAR(255), "
        		+ "EMAIL VARCHAR(255), "
        		+ "PASSWORD VARCHAR(35))");
        
        stmt.executeUpdate("INSERT INTO RESTAURANTS (USERNAME, NAME, ADDRESS, EMAIL, PASSWORD) VALUES ("
        		+ "\'" + username + "\', "
        		+ "\'" + name + "\', "
        		+ "\'" + address + "\', "
        		+ "\'" + email + "\', "
        		+ "\'" + password + "\')" );

        ResultSet rs = stmt.getGeneratedKeys();
        int restaurantID = -1;
        if (rs.next()) {
            restaurantID = rs.getInt(1);
        } else {
            // TODO throw an exception from here - registration unsuccessful
        }
        
    	return restaurantID;
	}
}
