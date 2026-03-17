package exception;

import com.homestyle.demo.ErroreCodice;

public class UnicitaException extends RuntimeException {
    public UnicitaException(String messaggio, ErroreCodice recensioneGiaEsistente) {
        super(messaggio);
    }
}//UnicitaException
