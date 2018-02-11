package soar.assessment.Y3853992;

public class InvalidPaymentException extends Exception {
	protected String cardNumber;
	
	public InvalidPaymentException() {}
	public InvalidPaymentException(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Override
	public String getMessage() {
		return "Invalid card number " + cardNumber;
	}
}
