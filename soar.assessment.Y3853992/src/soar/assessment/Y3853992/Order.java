package soar.assessment.Y3853992;

import java.sql.Time;

public class Order {
	protected int orderID;
	protected int restaurantID;
	protected int customerID;
	protected float totalPrice;
	protected String status;
	protected Time deliveryTime;
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Time getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Time deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
}
