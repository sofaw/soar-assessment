/**
 * RegistrationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public interface RegistrationService extends javax.xml.rpc.Service {
    public java.lang.String getRegistrationAddress();

    public soar.assessment.Y3853992.Registration getRegistration() throws javax.xml.rpc.ServiceException;

    public soar.assessment.Y3853992.Registration getRegistration(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
