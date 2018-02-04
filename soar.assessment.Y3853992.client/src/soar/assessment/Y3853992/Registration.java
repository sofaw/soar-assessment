/**
 * Registration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Registration extends java.rmi.Remote {
    public int registerRestaurant(java.lang.String username, java.lang.String name, java.lang.String address, java.lang.String email, java.lang.String password) throws java.rmi.RemoteException;
    public int registerCustomer(java.lang.String username, java.lang.String name, java.lang.String cardNumber, java.lang.String password) throws java.rmi.RemoteException;
}
