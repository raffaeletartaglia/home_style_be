package exception;
import com.homestyle.demo.ErroreCodice;

public class EntitaNonTrovataException extends BusinessException {

    public EntitaNonTrovataException(ErroreCodice codice) {
        super(codice);
    }

    public EntitaNonTrovataException(ErroreCodice codice, String messaggioCustom) {
        super(codice, messaggioCustom);
    }
}
