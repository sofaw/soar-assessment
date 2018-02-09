/**
 * Order.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public class Order  implements java.io.Serializable {
    private int deliveryTime;

    private soar.assessment.Y3853992.Item[] items;

    private int orderID;

    private java.lang.String status;

    private float totalPrice;

    public Order() {
    }

    public Order(
           int deliveryTime,
           soar.assessment.Y3853992.Item[] items,
           int orderID,
           java.lang.String status,
           float totalPrice) {
           this.deliveryTime = deliveryTime;
           this.items = items;
           this.orderID = orderID;
           this.status = status;
           this.totalPrice = totalPrice;
    }


    /**
     * Gets the deliveryTime value for this Order.
     * 
     * @return deliveryTime
     */
    public int getDeliveryTime() {
        return deliveryTime;
    }


    /**
     * Sets the deliveryTime value for this Order.
     * 
     * @param deliveryTime
     */
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    /**
     * Gets the items value for this Order.
     * 
     * @return items
     */
    public soar.assessment.Y3853992.Item[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this Order.
     * 
     * @param items
     */
    public void setItems(soar.assessment.Y3853992.Item[] items) {
        this.items = items;
    }


    /**
     * Gets the orderID value for this Order.
     * 
     * @return orderID
     */
    public int getOrderID() {
        return orderID;
    }


    /**
     * Sets the orderID value for this Order.
     * 
     * @param orderID
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    /**
     * Gets the status value for this Order.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Order.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the totalPrice value for this Order.
     * 
     * @return totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }


    /**
     * Sets the totalPrice value for this Order.
     * 
     * @param totalPrice
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Order)) return false;
        Order other = (Order) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.deliveryTime == other.getDeliveryTime() &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            this.orderID == other.getOrderID() &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            this.totalPrice == other.getTotalPrice();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getDeliveryTime();
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getOrderID();
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        _hashCode += new Float(getTotalPrice()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Order.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "Order"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "deliveryTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "Item"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "orderID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "totalPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
