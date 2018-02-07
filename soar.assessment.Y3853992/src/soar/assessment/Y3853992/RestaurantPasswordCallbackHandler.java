package soar.assessment.Y3853992;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class RestaurantPasswordCallbackHandler implements CallbackHandler {
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for(Callback callback : callbacks) {
			WSPasswordCallback wspc = (WSPasswordCallback) callback;
			
			// Get password from database for given username
			Connection con;
			try {
				Class.forName("org.h2.Driver");
				con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa" );
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM RESTAURANTS WHERE USERNAME=" + "\'" + wspc.getIdentifer() + "\'");

				if(rs.next()) {
					wspc.setPassword(rs.getString("PASSWORD"));
				} else {
					// TODO: throw user does not exist exception
				}
				con.createStatement().execute("SHUTDOWN");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
