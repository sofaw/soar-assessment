/**
 * Restaurants.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Restaurants extends java.rmi.Remote {
    public soar.assessment.Y3853992.Item[] getMenu(int restaurantID) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoResultsException;
    public int getRestaurantID(java.lang.String username) throws java.rmi.RemoteException, soar.assessment.Y3853992.NoValidEntryException;
    public soar.assessment.Y3853992.Order[] getOrders(int restaurantID) throws java.rmi.RemoteException;
    public void changeOrderStatus(int restaurantID, int orderID, java.lang.String status, int deliveryTime) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidIDException;
    public void updateMenu(int restaurantID, soar.assessment.Y3853992.Item[] items) throws java.rmi.RemoteException;
}
