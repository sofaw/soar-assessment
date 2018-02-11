package soar.assessment.Y3853992;

public class SearchProxy implements soar.assessment.Y3853992.Search {
  private String _endpoint = null;
  private soar.assessment.Y3853992.Search search = null;
  
  public SearchProxy() {
    _initSearchProxy();
  }
  
  public SearchProxy(String endpoint) {
    _endpoint = endpoint;
    _initSearchProxy();
  }
  
  private void _initSearchProxy() {
    try {
      search = (new soar.assessment.Y3853992.SearchServiceLocator()).getSearch();
      if (search != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)search)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)search)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (search != null)
      ((javax.xml.rpc.Stub)search)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public soar.assessment.Y3853992.Search getSearch() {
    if (search == null)
      _initSearchProxy();
    return search;
  }
  
  public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidIDException{
    if (search == null)
      _initSearchProxy();
    return search.getMenu(restaurantID);
  }
  
  public soar.assessment.Y3853992.Restaurant[] searchForRestaurants(java.lang.String searchTerm) throws java.rmi.RemoteException, soar.assessment.Y3853992.EmptySearchTermException{
    if (search == null)
      _initSearchProxy();
    return search.searchForRestaurants(searchTerm);
  }
  
  
}