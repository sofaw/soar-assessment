/**
 * RegistrationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public class RegistrationServiceLocator extends org.apache.axis.client.Service implements soar.assessment.Y3853992.RegistrationService {

    public RegistrationServiceLocator() {
    }


    public RegistrationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistrationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Registration
    private java.lang.String Registration_address = "http://localhost:8080/soar.assessment.Y3853992/services/Registration";

    public java.lang.String getRegistrationAddress() {
        return Registration_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistrationWSDDServiceName = "Registration";

    public java.lang.String getRegistrationWSDDServiceName() {
        return RegistrationWSDDServiceName;
    }

    public void setRegistrationWSDDServiceName(java.lang.String name) {
        RegistrationWSDDServiceName = name;
    }

    public soar.assessment.Y3853992.Registration getRegistration() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Registration_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistration(endpoint);
    }

    public soar.assessment.Y3853992.Registration getRegistration(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            soar.assessment.Y3853992.RegistrationSoapBindingStub _stub = new soar.assessment.Y3853992.RegistrationSoapBindingStub(portAddress, this);
            _stub.setPortName(getRegistrationWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistrationEndpointAddress(java.lang.String address) {
        Registration_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (soar.assessment.Y3853992.Registration.class.isAssignableFrom(serviceEndpointInterface)) {
                soar.assessment.Y3853992.RegistrationSoapBindingStub _stub = new soar.assessment.Y3853992.RegistrationSoapBindingStub(new java.net.URL(Registration_address), this);
                _stub.setPortName(getRegistrationWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Registration".equals(inputPortName)) {
            return getRegistration();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "RegistrationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "Registration"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Registration".equals(portName)) {
            setRegistrationEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
