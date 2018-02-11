/**
 * Customers.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Customers extends java.rmi.Remote {
    public soar.assessment.Y3853992.Order[] getOrders(int customerID) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidIDException;
    public int getCustomerID(java.lang.String username) throws java.rmi.RemoteException, soar.assessment.Y3853992.InvalidUsernameException;
    public void placeOrder(soar.assessment.Y3853992.Order order) throws java.rmi.RemoteException, soar.assessment.Y3853992.NullFieldException, soar.assessment.Y3853992.InvalidPaymentException;
}
