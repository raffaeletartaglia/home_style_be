package exception;

import com.homestyle.demo.ErroreCodice;

public class NumeroEsistenteException extends RuntimeException {
	
	public NumeroEsistenteException(String messaggio, ErroreCodice utenteNumeroGiaRegistrato) {
		super(messaggio);
	}

}//NumeroEsistenteException
