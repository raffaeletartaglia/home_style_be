package exception;
import com.homestyle.demo.ErroreCodice;

public class EntitaNonTrovataException extends BusinessException {

    public EntitaNonTrovataException(String codice) {
        super(ErroreCodice.valueOf(codice));
    }

    public EntitaNonTrovataException(ErroreCodice codice, String messaggioCustom) {
        super(codice, messaggioCustom);
    }

    public EntitaNonTrovataException(ErroreCodice erroreCodice) {
        super(erroreCodice);
    }
}
