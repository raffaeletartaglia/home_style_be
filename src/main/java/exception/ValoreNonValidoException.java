package exception;

import com.homestyle.demo.ErroreCodice;

// ValoreNonValidoException
public class ValoreNonValidoException extends BusinessException {
    public ValoreNonValidoException(ErroreCodice codice, String messaggioCustom) {
        super(codice, messaggioCustom);
    }
}
