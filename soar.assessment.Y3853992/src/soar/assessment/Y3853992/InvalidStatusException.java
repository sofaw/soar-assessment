package soar.assessment.Y3853992;

public class InvalidStatusException extends Exception {
	protected String status;
	
	public InvalidStatusException() {}
	
	public InvalidStatusException(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String getMessage() {
		return "Invalid status " + status;
	}
}
