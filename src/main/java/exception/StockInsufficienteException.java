package exception;

import com.homestyle.demo.ErroreCodice;

// StockInsufficienteException
public class StockInsufficienteException extends BusinessException {
    public StockInsufficienteException() {
        super(ErroreCodice.PRODOTTO_STOCK_INSUFFICIENTE);
    }
}
