/**
 * Customers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Customers extends java.rmi.Remote {
    public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException;
    public int getCustomerID(java.lang.String username) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoValidEntryException;
    public soar.assessment.Y3853992.Restaurant[] searchForRestaurants(java.lang.String searchTerm) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException;
    public void placeOrder(int customerID, soar.assessment.Y3853992.Item[] items, java.lang.String cardNumber, java.lang.String deliveryAddress) throws java.rmi.RemoteException, soar.assessment.Y3853992.NullFieldException;
    public soar.assessment.Y3853992.Order[] getOrders(int customerID) throws java.rmi.RemoteException;
}
