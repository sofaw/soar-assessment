package soar.assessment.Y3853992;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.message.token.UsernameToken;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class ClientApp {

	public static void main(String[] args) throws ServiceException, RemoteException {
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
		
		restaurants.setUsername("sophie");
		restaurants.addMenuItem(restaurantID, "pizza", 9.5f);
		
		customers.setUsername("Sophie");
		customers.setPassword("password");
		
		String username = customers.getUsername(1);
		System.out.println(username);
	}

}
