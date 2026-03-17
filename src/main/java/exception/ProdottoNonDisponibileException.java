package exception;

import com.homestyle.demo.ErroreCodice;

public class ProdottoNonDisponibileException extends BusinessException {

    public ProdottoNonDisponibileException() {
        super(ErroreCodice.PRODOTTO_NON_DISPONIBILE);
    }
}
