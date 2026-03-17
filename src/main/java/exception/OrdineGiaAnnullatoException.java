package exception;

import com.homestyle.demo.ErroreCodice;

public class OrdineGiaAnnullatoException extends BusinessException {
    public OrdineGiaAnnullatoException() {
        super(ErroreCodice.ORDINE_GIA_ANNULLATO);
    }
}
