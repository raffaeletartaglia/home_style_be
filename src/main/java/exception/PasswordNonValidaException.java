package exception;

public class PasswordNonValidaException extends RuntimeException {
	
	public PasswordNonValidaException(String messaggio) {
		super(messaggio);
	}

}
