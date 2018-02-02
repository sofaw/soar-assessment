package soar.assessment.Y3853992;

import java.sql.*;

public class Customers {
	public Customers() {};
	
	public void createCustomer(Customer details) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

		// Create table
	    String query = "CREATE TABLE CUSTOMERS ("
	    		+ "id INT NOT NULL, "
	    		+ "fullname VARCHAR(20) NOT NULL, "
	    		+ "email VARCHAR(20) NOT NULL, "
	    		+ "username VARCHAR(20) NOT NULL, "
	    		+ "password VARCHAR(20) NOT NULL"
	    		+ ");";
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		
		// Add entries to table
		stmt = conn.createStatement();
		query = "INSERT INTO CUSTOMERS VALUES"
				+ "("
				+ details.getId() + ", " 
				+ details.getFullname() + ", "
				+ details.getEmail() + ", "
				+ details.getUsername() + ", "
				+ details.getPassword() + ");";
		stmt.execute(query);
		
		conn.close();
		
		// TODO: change to try/catch and add faults
	}
	
	public float testConnection(float a, float b) {
		return a + b;
	}
}
