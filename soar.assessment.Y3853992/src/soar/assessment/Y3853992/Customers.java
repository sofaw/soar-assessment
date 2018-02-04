package soar.assessment.Y3853992;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customers {
	// TODO: delete
	public String getUsername(int customerID) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
        Statement stmt = con.createStatement();
        
		ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID=" + customerID);
		if(rs.next()) {
			return rs.getString("USERNAME");
		} else {
			return "customer ID does not exist";
		}
	}
}
