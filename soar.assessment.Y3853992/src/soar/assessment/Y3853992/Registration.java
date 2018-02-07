package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Registration {
	public int registerRestaurant(Restaurant restaurant) throws ClassNotFoundException, SQLException, UsernameAlreadyTakenException, NullFieldException {
		Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RESTAURANTS("
        		+ "RESTAURANT_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "USERNAME VARCHAR(35) NOT NULL, "
        		+ "RESTAURANT_NAME VARCHAR(35) NOT NULL, "
        		+ "ADDRESS VARCHAR(255) NOT NULL, "
        		+ "EMAIL VARCHAR(255) NOT NULL, "
        		+ "PASSWORD VARCHAR(35) NOT NULL)");
        
        // Check username not already taken
        ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE USERNAME=" + "\'" + restaurant.getUsername() + "\'");
        if(rs.next()) {
        	throw new UsernameAlreadyTakenException("Username is already taken.");
        }
        
        // Check no fields are null/empty
        if(restaurant.getUsername() == null || restaurant.getUsername().isEmpty() ||
        		restaurant.getRestaurantName() == null || restaurant.getRestaurantName().isEmpty() ||
        		restaurant.getAddress() == null || restaurant.getAddress().isEmpty() ||
        		restaurant.getEmail() == null || restaurant.getEmail().isEmpty() ||
        		restaurant.getPassword() == null || restaurant.getPassword().isEmpty()) {
        	throw new NullFieldException("All registration fields must be provided.");
        }
        
        stmt.executeUpdate("INSERT INTO RESTAURANTS (USERNAME, RESTAURANT_NAME, ADDRESS, EMAIL, PASSWORD) VALUES ("
        		+ "\'" + restaurant.getUsername() + "\', "
        		+ "\'" + restaurant.getRestaurantName() + "\', "
        		+ "\'" + restaurant.getAddress() + "\', "
        		+ "\'" + restaurant.getEmail() + "\', "
        		+ "\'" + restaurant.getPassword() + "\')" );

        rs = stmt.getGeneratedKeys();
        int restaurantID = -1;
        rs.next();
        restaurantID = rs.getInt(1);
        
    	return restaurantID;
	}
	
	public int registerCustomer(Customer customer) throws ClassNotFoundException, SQLException, UsernameAlreadyTakenException, NullFieldException {
		Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMERS("
        		+ "CUSTOMER_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "USERNAME VARCHAR(35) NOT NULL, "
        		+ "FULLNAME VARCHAR(35) NOT NULL, "
        		+ "EMAIL VARCHAR(255) NOT NULL, "
        		+ "PASSWORD VARCHAR(35) NOT NULL)");
        
        // Check username not already taken
        ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS WHERE USERNAME=" + "\'" + customer.getUsername() + "\'");
        if(rs.next()) {
        	throw new UsernameAlreadyTakenException("Username is already taken.");
        }
        
        // Check no fields are null/empty
        if(customer.getUsername() == null || customer.getUsername().isEmpty() ||
        		customer.getFullname() == null || customer.getFullname().isEmpty() ||
        		customer.getEmail() == null || customer.getEmail().isEmpty() ||
        		customer.getPassword() == null || customer.getPassword().isEmpty()) {
        	throw new NullFieldException("customer");
        }
        
        stmt.executeUpdate("INSERT INTO CUSTOMERS (USERNAME, FULLNAME, EMAIL, PASSWORD) VALUES ("
        		+ "\'" + customer.getUsername() + "\', "
        		+ "\'" + customer.getFullname() + "\', "
        		+ "\'" + customer.getEmail() + "\', "
        		+ "\'" + customer.getPassword() + "\')" );

        rs = stmt.getGeneratedKeys();
        int customerID = -1;
        rs.next();
        customerID = rs.getInt(1);
        
    	return customerID;
	}
}
