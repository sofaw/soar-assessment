package soar.assessment.Y3853992;

public class NullFieldException extends Exception {
	String fieldName;
	
	public NullFieldException() {}
	
	public NullFieldException(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public String getMessage() {
		return "Field " + fieldName + " cannot be null";
	}
}
