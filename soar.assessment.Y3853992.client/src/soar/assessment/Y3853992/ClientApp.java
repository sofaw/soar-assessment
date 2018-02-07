package soar.assessment.Y3853992;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.message.token.UsernameToken;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class ClientApp {

	public static void main(String[] args) throws ServiceException, RemoteException, ClassNotFoundException, SQLException {
		// Registration service
		Registration registration = new RegistrationServiceLocator().getRegistration();
		
		// Restaurants service
		EngineConfiguration restaurantsConfig = 
				new FileProvider(ClientApp.class.getResourceAsStream("restaurantclient.wsdd"));
		RestaurantsSoapBindingStub restaurants = (RestaurantsSoapBindingStub) 
				new RestaurantsServiceLocator(restaurantsConfig).getRestaurants();
		
		// Customers service
		EngineConfiguration customersConfig = 
				new FileProvider(ClientApp.class.getResourceAsStream("customerclient.wsdd"));
		CustomersSoapBindingStub customers = (CustomersSoapBindingStub) 
				new CustomersServiceLocator(customersConfig).getCustomers();
				
		int restaurantID = registration.registerRestaurant(
				"soph_rest", 
				"coolRestaurant", 
				"37 lawrence st", 
				"sof@gmail.com", 
				"secret");
		
		System.out.println(restaurantID);
		
		restaurants.setUsername("richiebn");
		restaurants.setPassword("supersecret");
		restaurants.addMenuItem(restaurantID, "pizza", 9.5f);
		
		try {
			restaurants.setUsername("richiebn");
			restaurants.setPassword("wrongpassword");
			restaurants.addMenuItem(restaurantID, "pizza", 9.5f);
		} catch(Exception e) {
			System.out.println("Incorrect username/password");
		}
		
		/*try {
			restaurants.setUsername("richiebn");
			restaurants.setPassword("supersecret");
			restaurants.doCustomException();
		} catch (MyCustomException e) {
			System.out.println(e);
		}
		
		restaurants.setUsername("richiebn");
		restaurants.setPassword("supersecret");
		MyCustomEntity mce = new MyCustomEntity();
		mce.setPrice(0.7f);
		mce.setTitle("sophie_title");
		System.out.println(restaurants.doUseMyCustomEntity(mce));*/
		
		
		customers.setUsername("sophie");
		customers.setPassword("secret");
		
		String username = customers.getUsername(1);
		System.out.println(username);
		
		try {
			customers.setUsername("richiebn");
			customers.setPassword("shhhhh");
		
			username = customers.getUsername(1);
			System.out.println(username);
		} catch (Exception e) {
			System.out.println("Incorrect username/password");
		}
	}

}
