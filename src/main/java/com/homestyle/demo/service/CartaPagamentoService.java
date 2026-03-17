package com.homestyle.demo.service;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service; 
import java.time.LocalDate; 
import java.util.List; 
import com.homestyle.demo.repository.CartaPagamentoRepository; 
import com.homestyle.demo.entity.CartaPagamento;
import com.homestyle.demo.entity.Recensione;
import com.homestyle.demo.entity.Utente; 
import com.homestyle.demo.repository.UtenteRepository; 
import lombok.RequiredArgsConstructor; 
import lombok.extern.slf4j.Slf4j; 
import utils.ControlliUtils; 
import exception.*;



@Service
@RequiredArgsConstructor
@Slf4j
public class CartaPagamentoService {

    private final CartaPagamentoRepository cartaPagamentoRepo;
    private final UtenteRepository utenteRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CartaPagamento addCartaPagamento(CartaPagamento cartaPagamento){
    	
    	log.info("Creazione nuova carta di pagamento per utenteId={}", cartaPagamento.getUtente().getId());

    	
    	ControlliUtils.controlloIdValido(cartaPagamento.getUtente().getId(), "Utente");
    	log.info("Id utente valido: {}", cartaPagamento.getUtente().getId());

    	
    	Utente utente = utenteRepo.findById(cartaPagamento.getUtente().getId())
    	        .orElseThrow(() -> new EntitaNonTrovataException("Utente non trovato"));
    	cartaPagamento.setUtente(utente);
    	log.info("Utente trovato e associato alla carta: {}", utente.getId());


        
        if (!controllaNumeroCarta(cartaPagamento.getNumeroCarta())) {
            log.error("Numero carta non valido: {}", cartaPagamento.getNumeroCarta());
            throw new ValoreNonValidoException("Numero della carta non valido");
        }
        log.info("Numero carta valido");

        
        if (!controllaIntestatario(cartaPagamento.getIntestatario())) {
            log.error("Intestatario non valido: {}", cartaPagamento.getIntestatario());
            throw new ValoreNonValidoException("Intestatario non valido");
        }
        log.info("Intestatario carta valido");

        
        if (!controllaScadenza(cartaPagamento.getScadenza())) {
            log.error("Scadenza carta non valida: {}", cartaPagamento.getScadenza());
            throw new ValoreNonValidoException("Valore della scadenza non valido");
        }
        log.info("Scadenza carta valida: {}", cartaPagamento.getScadenza());

        
        if (!controlloCvvCarta(cartaPagamento.getCvv())) {
            log.error("CVV non valido");
            throw new ValoreNonValidoException("Valore del cvv non valido");
        }
        log.info("CVV valido");

        
        if (!controllaTipoCarta(cartaPagamento.getNumeroCarta())) {
            log.error("Tipo carta non valido per numero: {}", cartaPagamento.getNumeroCarta());
            throw new ValoreNonValidoException("Tipo carta non valido");
        }
        log.info("Tipo carta valido");

        //Criptazione CVV prima del salvataggio
        String cvvCriptato = passwordEncoder.encode(cartaPagamento.getCvv());
        cartaPagamento.setCvv(cvvCriptato);
        log.info("CVV criptato e pronto per il salvataggio");

        //Salvataggio carta nel DB
        CartaPagamento cartaSalvata = cartaPagamentoRepo.save(cartaPagamento);
        log.info("Carta salvata correttamente con id: {}", cartaSalvata.getId());

        return cartaSalvata;
    }//addCartaPagamento
    
    public List<CartaPagamento> getCarteByUtente(UUID utenteId){
    	
    	log.info("Trovo tutte le carte intestate all' utenteId={}", utenteId);
    	
    	ControlliUtils.controlloIdValido(utenteId, "Utente");
    	log.info("Id utente valido: {}", utenteId);
    	
    	List<CartaPagamento> cartePagamenti = cartaPagamentoRepo.findByUtenteId(utenteId);
    	if(cartePagamenti.isEmpty()){
	        log.error("Nessuna carta pagamento trovata per l'utenteId={}", utenteId);
	        throw new ValoreNonValidoException("Lista carte pagamento vuota");
	    }
	    return cartePagamenti;
    }//getCarteByUtente
    
   public CartaPagamento getCartaById(UUID cartaId) {
	   log.info("Trovo una carta pagamento specifica per mezzo l'uso di cartaId={}", cartaId);
	   	
   	   ControlliUtils.controlloIdValido(cartaId, "Carta Pagamento");
   	   log.info("Id carta pagamento valido: {}", cartaId);
   	   
   	   CartaPagamento cartaPagamento = cartaPagamentoRepo.findById(cartaId).orElseThrow(
   			   () -> {
   				   log.error("Carta non trovata per l'id: {}", cartaId);
   				   throw new ValoreNonValidoException("id carta Pagamento non trovato");
   			   }
   			   );
   	   return cartaPagamento;
   }//getCartaById
   
   public void deleteCartaPagamento(UUID cartaId) {
	   log.info("Trovo la  carta pagamento da eliminare tramite l'uso di cartaId={}", cartaId);
	   
	   ControlliUtils.controlloIdValido(cartaId, "Carta Pagamento");
	   log.info("Id carta pagamento valido: {}", cartaId);
	   
	   CartaPagamento cartaPagamento = cartaPagamentoRepo.findById(cartaId).orElseThrow(
   			   () -> {
   				   log.error("Carta non trovata per l'id: {}", cartaId);
   				   throw new ValoreNonValidoException("id carta Pagamento non trovato");
   			   }
   			   );
	   log.info("Carta Pagamento eliminata con successo");
	   cartaPagamentoRepo.deleteById(cartaId);
	}//deleteCartaPagamento
   
   
    
    private boolean controllaNumeroCarta(String numeroCarta) {
        log.debug("Controllo numero carta: {}", numeroCarta);
        return ControlliUtils.numeroCartaPagamento(numeroCarta);
    }//controllaNumeroCarta

    private boolean controllaIntestatario(String intestatario) {
        log.debug("Controllo intestatario: {}", intestatario);
        return ControlliUtils.controllaIntestatarioCarta(intestatario);
    }//controllaIntestatario

    private boolean controllaScadenza(LocalDate scadenza) {
        log.debug("Controllo scadenza carta: {}", scadenza);
        return ControlliUtils.controlloScadenzaCarta(scadenza);
    }//controllaScadenza

    private boolean controlloCvvCarta(String cvv) {
        log.debug("Controllo CVV: {}", cvv);
        return ControlliUtils.controlloCVV(cvv);
    }//controlloCvvCarta

    private boolean controllaTipoCarta(String numeroCarta) {
        log.debug("Controllo tipo carta per numero: {}", numeroCarta);
        return ControlliUtils.controllaTipoCarta(numeroCarta);
    }//controllaTipoCarta

}//CartaPagamentoService


