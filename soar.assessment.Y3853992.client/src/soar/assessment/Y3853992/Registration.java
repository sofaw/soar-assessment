/**
 * Registration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface Registration extends java.rmi.Remote {
    public int registerRestaurant(soar.assessment.Y3853992.Restaurant restaurant) throws java.rmi.RemoteException, soar.assessment.Y3853992.NullFieldException, soar.assessment.Y3853992.UsernameAlreadyTakenException;
    public int registerCustomer(soar.assessment.Y3853992.Customer customer) throws java.rmi.RemoteException, soar.assessment.Y3853992.NullFieldException, soar.assessment.Y3853992.UsernameAlreadyTakenException;
}
