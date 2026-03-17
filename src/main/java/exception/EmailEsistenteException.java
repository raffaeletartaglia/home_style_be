package exception;

import com.homestyle.demo.ErroreCodice;

public class EmailEsistenteException extends RuntimeException {
	
	public EmailEsistenteException(String messaggio, ErroreCodice utenteEmailGiaRegistrata) {
        super(messaggio);
    }

}//EmailEsistenteException
