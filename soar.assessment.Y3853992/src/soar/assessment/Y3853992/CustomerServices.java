package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerServices {
	public int registerCustomer(String username, String name, String cardNumber, 
			String password) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CUSTOMERS("
        		+ "CUSTOMER_ID INT PRIMARY KEY AUTO_INCREMENT, "
        		+ "USERNAME VARCHAR(35), "
        		+ "NAME VARCHAR(35), "
        		+ "CARDNUMBER VARCHAR(255), "
        		+ "PASSWORD VARCHAR(35))");
        
        stmt.executeUpdate("INSERT INTO CUSTOMERS (USERNAME, NAME, CARDNUMBER, PASSWORD) VALUES ("
        		+ "\'" + username + "\', "
        		+ "\'" + name + "\', "
        		+ "\'" + cardNumber + "\', "
        		+ "\'" + password + "\')" );

        ResultSet rs = stmt.getGeneratedKeys();
        int customerID = -1;
        if (rs.next()) {
            customerID = rs.getInt(1);
        } else {
            // TODO throw an exception from here - registration unsuccessful
        }
        
    	return customerID;
	}
}
