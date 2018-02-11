package soar.assessment.Y3853992;

public class InvalidUsernameException extends Exception {
	protected String username;
	
	public InvalidUsernameException() {}
	
	public InvalidUsernameException(String username) {
		this.username = username;
	}
	
	@Override
	public String getMessage() {
		return "Invalid username " + username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
