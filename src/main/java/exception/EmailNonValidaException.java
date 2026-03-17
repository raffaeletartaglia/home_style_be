package exception;

import com.homestyle.demo.ErroreCodice;

public class EmailNonValidaException extends RuntimeException {
	
	 public EmailNonValidaException(String messaggio, ErroreCodice utenteEmailNonValida) {
	        super(messaggio);
	    }

}
