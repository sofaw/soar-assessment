package soar.assessment.Y3853992;

public class RegistrationProxy implements soar.assessment.Y3853992.Registration {
  private String _endpoint = null;
  private soar.assessment.Y3853992.Registration registration = null;
  
  public RegistrationProxy() {
    _initRegistrationProxy();
  }
  
  public RegistrationProxy(String endpoint) {
    _endpoint = endpoint;
    _initRegistrationProxy();
  }
  
  private void _initRegistrationProxy() {
    try {
      registration = (new soar.assessment.Y3853992.RegistrationServiceLocator()).getRegistration();
      if (registration != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)registration)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)registration)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (registration != null)
      ((javax.xml.rpc.Stub)registration)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public soar.assessment.Y3853992.Registration getRegistration() {
    if (registration == null)
      _initRegistrationProxy();
    return registration;
  }
  
  public int registerRestaurant(java.lang.String username, java.lang.String name, java.lang.String address, java.lang.String email, java.lang.String password) throws java.rmi.RemoteException{
    if (registration == null)
      _initRegistrationProxy();
    return registration.registerRestaurant(username, name, address, email, password);
  }
  
  public int registerCustomer(java.lang.String username, java.lang.String name, java.lang.String cardNumber, java.lang.String password) throws java.rmi.RemoteException{
    if (registration == null)
      _initRegistrationProxy();
    return registration.registerCustomer(username, name, cardNumber, password);
  }
  
  
}