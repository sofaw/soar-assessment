/**
 * Restaurants.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Restaurants extends java.rmi.Remote {
    public void doCustomException() throws java.rmi.RemoteException, soar.assessment.Y3853992.MyCustomException;
    public void addMenuItem(int restaurantID, java.lang.String title, float price) throws java.rmi.RemoteException;
    public java.lang.String doUseMyCustomEntity(soar.assessment.Y3853992.MyCustomEntity mce) throws java.rmi.RemoteException;
    public void deleteMenu(int restaurantID) throws java.rmi.RemoteException;
}
