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
  
  public java.lang.String getUsername(int customerID) throws java.rmi.RemoteException{
    if (customers == null)
      _initCustomersProxy();
    return customers.getUsername(customerID);
  }
  
  
}