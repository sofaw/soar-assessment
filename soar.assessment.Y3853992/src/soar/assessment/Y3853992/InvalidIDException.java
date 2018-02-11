package soar.assessment.Y3853992;

public class InvalidIDException extends Exception {
	protected int id;
	public InvalidIDException() {}
	public InvalidIDException(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String getMessage() {
		return "Invalid ID " + id;
	}
}
