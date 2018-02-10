package soar.assessment.Y3853992;

public class CustomersProxy implements soar.assessment.Y3853992.Customers {
  private String _endpoint = null;
  private soar.assessment.Y3853992.Customers customers = null;
  
  public CustomersProxy() {
    _initCustomersProxy();
  }
  
  public CustomersProxy(String endpoint) {
    _endpoint = endpoint;
    _initCustomersProxy();
  }
  
  private void _initCustomersProxy() {
    try {
      customers = (new soar.assessment.Y3853992.CustomersServiceLocator()).getCustomers();
      if (customers != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)customers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)customers)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (customers != null)
      ((javax.xml.rpc.Stub)customers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public soar.assessment.Y3853992.Customers getCustomers() {
    if (customers == null)
      _initCustomersProxy();
    return customers;
  }
  
  public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException{
    if (customers == null)
      _initCustomersProxy();
    return customers.getMenu(restaurantID);
  }
  
  public soar.assessment.Y3853992.Order[] getOrders(int customerID) throws java.rmi.RemoteException{
    if (customers == null)
      _initCustomersProxy();
    return customers.getOrders(customerID);
  }
  
  public int getCustomerID(java.lang.String username) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoValidEntryException{
    if (customers == null)
      _initCustomersProxy();
    return customers.getCustomerID(username);
  }
  
  public soar.assessment.Y3853992.Restaurant[] searchForRestaurants(java.lang.String searchTerm) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException{
    if (customers == null)
      _initCustomersProxy();
    return customers.searchForRestaurants(searchTerm);
  }
  
  public void placeOrder(soar.assessment.Y3853992.Order order) throws java.rmi.RemoteException, soar.assessment.Y3853992.NullFieldException{
    if (customers == null)
      _initCustomersProxy();
    customers.placeOrder(order);
  }
  
  
}