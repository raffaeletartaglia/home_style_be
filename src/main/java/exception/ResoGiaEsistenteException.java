package exception;

import com.homestyle.demo.ErroreCodice;

public class ResoGiaEsistenteException extends BusinessException {
    public ResoGiaEsistenteException() {
        super(ErroreCodice.RESO_GIA_ESISTENTE);
    }
}//ResoGiaEsistenteException
