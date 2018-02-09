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
  
  public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException{
    if (restaurants == null)
      _initRestaurantsProxy();
    return restaurants.getMenu(restaurantID);
  }
  
  public soar.assessment.Y3853992.Order[] getOrders(int restaurantID) throws java.rmi.RemoteException{
    if (restaurants == null)
      _initRestaurantsProxy();
    return restaurants.getOrders(restaurantID);
  }
  
  public void changeOrderStatus(int restaurantID, int orderID, java.lang.String status, int deliveryTime) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidIDException{
    if (restaurants == null)
      _initRestaurantsProxy();
    restaurants.changeOrderStatus(restaurantID, orderID, status, deliveryTime);
  }
  
  public int getRestaurantID(java.lang.String username) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoValidEntryException{
    if (restaurants == null)
      _initRestaurantsProxy();
    return restaurants.getRestaurantID(username);
  }
  
  public void updateMenu(int restaurantID, soar.assessment.Y3853992.Item[] items) throws java.rmi.RemoteException{
    if (restaurants == null)
      _initRestaurantsProxy();
    restaurants.updateMenu(restaurantID, items);
  }
  
  
}