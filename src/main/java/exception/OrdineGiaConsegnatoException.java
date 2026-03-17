package exception;

import com.homestyle.demo.ErroreCodice;

public class OrdineGiaConsegnatoException extends BusinessException {
    public OrdineGiaConsegnatoException() {
        super(ErroreCodice.ORDINE_GIA_CONSEGNATO);
    }
}
