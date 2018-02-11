package soar.assessment.Y3853992;

public class InvalidItemException extends Exception {
	protected int id;
	public InvalidItemException() {}
	public InvalidItemException(int id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "Invalid item " + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
