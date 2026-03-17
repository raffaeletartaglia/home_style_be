package exception;

import com.homestyle.demo.ErroreCodice;

public class PasswordNonValidaException extends RuntimeException {
	
	public PasswordNonValidaException(String messaggio, ErroreCodice utentePasswordNonValida) {
		super(messaggio);
	}

}
