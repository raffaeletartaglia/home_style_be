package exception;

import com.homestyle.demo.ErroreCodice;

public class CarrelloNonAttivoException extends BusinessException {

    public CarrelloNonAttivoException() {
        super(ErroreCodice.CARRELLO_NON_ATTIVO);
    }
}
