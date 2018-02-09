package soar.assessment.Y3853992;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
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
		
		int restaurantID = 1;
		try {
			Restaurant restaurant = new Restaurant();
			restaurant.setUsername("cs");
			restaurant.setRestaurantName("chicken shop");
			restaurant.setAddress("123 chicken road");
			restaurant.setEmail("chick@chicken.com");
			restaurant.setPassword("welovechicken");
			restaurantID = registration.registerRestaurant(restaurant);
		} catch (UsernameAlreadyTakenException e) {
			System.out.println(e);
		}
		
		System.out.println(restaurantID);
		
		// try incomplete
		restaurantID = 1;
		try {
			Restaurant restaurant = new Restaurant();
			restaurant.setUsername("abc");
			restaurantID = registration.registerRestaurant(restaurant);
		} catch (UsernameAlreadyTakenException e) {
			System.out.println(e);
		} catch (NullFieldException e) {
			System.out.println(e);
		}
		
		int customerID = 1;
		try {
			Customer customer = new Customer();
			customer.setUsername("rb");
			customer.setFullname("richard tynan");
			customer.setEmail("rb@gmail.com");
			customer.setPassword("secret");
			customerID = registration.registerCustomer(customer);
		} catch (UsernameAlreadyTakenException e) {
			System.out.println(e);
		} catch (NullFieldException e) {
			System.out.println(e);
		}
		
		System.out.println(restaurantID);
		
		// Try incomplete 
		try {
			Customer customer = new Customer();
			customer.setUsername("onlyusername");
			customerID = registration.registerCustomer(customer);
		} catch (UsernameAlreadyTakenException e) {
			System.out.println(e);
		} catch (NullFieldException e) {
			System.out.println(e);
		}
		
		
		/*restaurants.setUsername("cs");
		restaurants.setPassword("welovechicken");
		restaurants.addMenuItem(restaurantID, "pizza", 9.5f);
		
		try {
			restaurants.setUsername("cs");
			restaurants.setPassword("wrongpassword");
			restaurants.addMenuItem(restaurantID, "pizza", 9.5f);
		} catch(RemoteException e) {
			if(e instanceof AxisFault) {
				AxisFault af = (AxisFault) e;
				if(af.getFaultString().contains("WSSecurityException")) {
					System.out.println("Security exception: incorrect username/password");
				}
			} else {
				System.out.println("Issues with server");
			}
		}*/
		
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
		
		
		customers.setUsername("rb");
		customers.setPassword("secret");
		
		/*String username = customers.getUsername(1);
		System.out.println(username);
		
		try {
			customers.setUsername("rb");
			customers.setPassword("wrongpassword");
		
			username = customers.getUsername(1);
			System.out.println(username);
		} catch (Exception e) {
			System.out.println("Incorrect username/password");
		}*/
	}

}
