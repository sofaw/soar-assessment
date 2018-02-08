/**
 * Item.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package soar.assessment.Y3853992;

public class Item  implements java.io.Serializable {
    private int itemID;

    private float price;

    private int restaurantID;

    private java.lang.String title;

    public Item() {
    }

    public Item(
           int itemID,
           float price,
           int restaurantID,
           java.lang.String title) {
           this.itemID = itemID;
           this.price = price;
           this.restaurantID = restaurantID;
           this.title = title;
    }


    /**
     * Gets the itemID value for this Item.
     * 
     * @return itemID
     */
    public int getItemID() {
        return itemID;
    }


    /**
     * Sets the itemID value for this Item.
     * 
     * @param itemID
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }


    /**
     * Gets the price value for this Item.
     * 
     * @return price
     */
    public float getPrice() {
        return price;
    }


    /**
     * Sets the price value for this Item.
     * 
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }


    /**
     * Gets the restaurantID value for this Item.
     * 
     * @return restaurantID
     */
    public int getRestaurantID() {
        return restaurantID;
    }


    /**
     * Sets the restaurantID value for this Item.
     * 
     * @param restaurantID
     */
    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }


    /**
     * Gets the title value for this Item.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Item.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Item)) return false;
        Item other = (Item) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.itemID == other.getItemID() &&
            this.price == other.getPrice() &&
            this.restaurantID == other.getRestaurantID() &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        _hashCode += getItemID();
        _hashCode += new Float(getPrice()).hashCode();
        _hashCode += getRestaurantID();
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Item.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "itemID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("restaurantID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "restaurantID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://Y3853992.assessment.soar", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
