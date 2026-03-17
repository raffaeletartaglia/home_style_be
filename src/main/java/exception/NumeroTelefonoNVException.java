package exception;

import com.homestyle.demo.ErroreCodice;
import lombok.Getter;


@Getter
public class NumeroTelefonoNVException extends RuntimeException {

	private final ErroreCodice erroreCodice;

	public NumeroTelefonoNVException(String messaggio, ErroreCodice codice) {
		super(messaggio);
		this.erroreCodice = codice;
	}

}

