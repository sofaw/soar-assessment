/**
 * Search.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Search extends java.rmi.Remote {
    public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidIDException;
    public soar.assessment.Y3853992.Restaurant[] searchForRestaurants(java.lang.String searchTerm) throws java.rmi.RemoteException, soar.assessment.Y3853992.EmptySearchTermException;
}
