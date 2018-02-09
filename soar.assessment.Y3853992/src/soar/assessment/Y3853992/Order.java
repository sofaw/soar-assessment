package soar.assessment.Y3853992;

public class Order {
	protected int orderID;
	protected float totalPrice;
	protected String status;
	protected int deliveryTime;
	protected Item[] items;
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
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
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}
}
