package soar.assessment.Y3853992;

public class UnauthorizedException extends Exception {
	protected int id;
	
	public UnauthorizedException() {}
	
	public UnauthorizedException(int id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "Unauthorized ID" + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
