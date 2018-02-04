package soar.assessment.Y3853992;

public class RestaurantsProxy implements soar.assessment.Y3853992.Restaurants {
  private String _endpoint = null;
  private soar.assessment.Y3853992.Restaurants restaurants = null;
  
  public RestaurantsProxy() {
    _initRestaurantsProxy();
  }
  
  public RestaurantsProxy(String endpoint) {
    _endpoint = endpoint;
    _initRestaurantsProxy();
  }
  
  private void _initRestaurantsProxy() {
    try {
      restaurants = (new soar.assessment.Y3853992.RestaurantsServiceLocator()).getRestaurants();
      if (restaurants != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)restaurants)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)restaurants)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (restaurants != null)
      ((javax.xml.rpc.Stub)restaurants)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public soar.assessment.Y3853992.Restaurants getRestaurants() {
    if (restaurants == null)
      _initRestaurantsProxy();
    return restaurants;
  }
  
  public void deleteMenu(int restaurantID) throws java.rmi.RemoteException{
    if (restaurants == null)
      _initRestaurantsProxy();
    restaurants.deleteMenu(restaurantID);
  }
  
  public void addMenuItem(int restaurantID, java.lang.String title, float price) throws java.rmi.RemoteException{
    if (restaurants == null)
      _initRestaurantsProxy();
    restaurants.addMenuItem(restaurantID, title, price);
  }
  
  
}