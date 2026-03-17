package exception;

import com.homestyle.demo.ErroreCodice;

public class OperazioneNonConsentitaException extends BusinessException {
    public OperazioneNonConsentitaException(String messaggioCustom) {
        super(ErroreCodice.OPERAZIONE_NON_CONSENTITA, messaggioCustom);
    }
}
