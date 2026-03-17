package utils;
import com.homestyle.demo.entity.Indirizzo;
import com.homestyle.demo.entity.Ordine;
import exception.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.homestyle.demo.entity.Indirizzo;
import com.homestyle.demo.entity.Ordine;

@Component
@Slf4j
public class ControlliUtils {
	//Todo() Aggiungi i LOG
	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(it|com|org)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
    private static final String NUMERO_REGEX = "^3\\d{9}$";
    private static final Pattern NUMERO_PATTERN = Pattern.compile(NUMERO_REGEX);

    public static void controlloTransizioneStatoValida(Ordine.StatoOrdine statoAttuale, Ordine.StatoOrdine nuovoStato) {

	public static void controlloStatoOrdineValido(Ordine.StatoOrdine stato) {
		if (stato == null) {
			throw new IllegalArgumentException("Lo stato dell'ordine non può essere nullo");
		}
	}


	public static void controlloTransizioneStatoValida(Ordine.StatoOrdine statoAttuale, Ordine.StatoOrdine nuovoStato) {
		boolean transazioneValida = switch (statoAttuale) {
			case IN_ELABORAZIONE -> nuovoStato == Ordine.StatoOrdine.SPEDITO
					|| nuovoStato == Ordine.StatoOrdine.ANNULLATO;
			case SPEDITO         -> nuovoStato == Ordine.StatoOrdine.CONSEGNATO
					|| nuovoStato == Ordine.StatoOrdine.ANNULLATO;
			case CONSEGNATO      -> false; // stato finale, nessuna transizione possibile
			case ANNULLATO       -> false; // stato finale, nessuna transizione possibile
		};

		if (!transazioneValida) {
			throw new IllegalStateException(
					"Transizione non valida: " + statoAttuale + " → " + nuovoStato
			);
		}
	}//controlloTransizioneStatoValida

	//Controllo esistenza entità
	public void controlloEsistenzaEntita(Object entity,String messaggio) {
		if (entity == null) {
            throw new EntitaNonTrovataException(messaggio);
        }
	}//controlloEsistenzaEntita
	
	public static void controlloEsistenzaCampo(Object oggetto, String messaggio) {
		if(oggetto==null) {
			throw new CampoNullException("ID di " + messaggio + " non può essere null");
		}
		
	}//controlloEsistenzaCampo
	
	
	
	//controllo valori numeri positivi
	public void controlloValPositivi(Number valore, String nome) {
		if(valore== null || valore.doubleValue()<=0) {
			throw new ValoreNonValidoException(nome + " non può essere minore o pari a 0");
		}
	}//controlloValPositivi
	
	
	//Controllo valori compresi in un range
	public void controlloRange(int valore, int min, int max, String nome) {
		if(valore<min || valore>max) {
			throw new ValoreFuoriRangeException(nome + " deve essere tra " + min + " e " + max);
		}
	}//controlloRange
	
	
	//Controllo valori ENUM
		try {
            Enum.valueOf(classeEnum, valore);
        } catch (IllegalArgumentException e) {
            throw new ValoreEnumNonValidoException(nome + " non valido: " + valore);
        }
	}//controlloValoreEnum
	
	
	//controllo date logiche(non nel passato)
	public void controlloDateFuture(LocalDate data, String nome) {
		if (data == null || data.isBefore(LocalDate.now())) {
            throw new DataNonFuturaException(nome + " deve essere futura ");
        }
    }//controlloDateFuture
	
	public static void controlloIdValido(Object id, String nomeEntita) {
	    // Log opzionale: puoi usare il logger di Spring
	    // log.error("Controllo ID per {}: {}", nomeEntita, id);
		if (id == null) {
	        throw new ValoreNonValidoException("ID di " + nomeEntita + " non può essere null");
	    }
	}//controlloIdValido
	
	 public static boolean emailValida(String email) {
	        if (email == null || email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
	            throw new EmailNonValidaException(email + " non è un'email valida");
	        }
	        return true;
	 }//emailValida
	 
	 public static boolean passValida(String password) {
	        if (password == null || password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
	            throw new PasswordNonValidaException("Password non valida: deve avere almeno 8 caratteri, una maiuscola, un numero e un carattere speciale");
	        }
	        return true;
	    }//passValida
	 
	 public static boolean numeroTelefonoValido(String numero) {
	        if (numero == null || numero.isEmpty() || !NUMERO_PATTERN.matcher(numero).matches()) {
	            throw new NumeroTelefonoNVException("Numero di telefono non valido: deve iniziare con 3 e contenere 10 cifre");
	        }
	        return true;
	    }//numeroTelefonoValido

	 public static boolean numeroCartaPagamento(String numeroCartaP) {

		    // controllo null
		    if (numeroCartaP == null) {
		        throw new IllegalArgumentException("Il numero della carta non può essere null");
		    }

		    numeroCartaP = numeroCartaP.trim();

		    // controllo vuoto
		    if (numeroCartaP.isEmpty()) {
		        throw new IllegalArgumentException("Il numero della carta non può essere vuoto");
		    }

		    // solo cifre
		    if (!numeroCartaP.matches("\\d+")) {
		        throw new IllegalArgumentException("Il numero della carta deve contenere solo numeri");
		    }

		    // lunghezza reale carte (13 - 19 cifre)
		    if (numeroCartaP.length() < 13 || numeroCartaP.length() > 19) {
		        throw new IllegalArgumentException("Il numero della carta deve avere tra 13 e 19 cifre");
		    }

		    // regex tipi carta
		    String regexVisa = "^4[0-9]{12}(?:[0-9]{3})?(?:[0-9]{3})?$";
		    String regexMastercard = "^5[1-5][0-9]{14}$";
		    String regexMaestro = "^(5018|5020|5038|5893|6304|6759|6761|6763)[0-9]{8,15}$";

		    if (numeroCartaP.matches(regexVisa) ||
		        numeroCartaP.matches(regexMastercard) ||
		        numeroCartaP.matches(regexMaestro)) {

		        return true;
		    }

		    throw new IllegalArgumentException("Numero carta non valido o tipo carta non supportato");

		}// numeroCartaPagamento

	 public static boolean controllaIntestatarioCarta(String intestatario) {

		    if (intestatario == null) {
		        throw new IllegalArgumentException("L'intestatario della carta non può essere null");
		    }

		    intestatario = intestatario.trim();

		    if (intestatario.isEmpty()) {
		        throw new IllegalArgumentException("L'intestatario della carta non può essere vuoto");
		    }

		    // solo lettere e spazi
		    if (!intestatario.matches("^[A-Za-z ]+$")) {
		        throw new IllegalArgumentException("L'intestatario della carta può contenere solo lettere");
		    }

		    return true;
	 }//controllaIntestatarioCarta

	 public static boolean controlloScadenzaCarta(LocalDate scadenza) {

		    if (scadenza == null) {
		        throw new IllegalArgumentException("La scadenza della carta non può essere null");
		    }

		    if (scadenza.isBefore(LocalDate.now())) {
		        throw new IllegalArgumentException("La carta è scaduta");
		    }

		    return true;
		}//controlloScadenzaCarta

	 public static boolean controlloCVV(String cvv) {

		    if (cvv == null) {
		        throw new IllegalArgumentException("Il CVV non può essere null");
		    }

		    cvv = cvv.trim();

		    if (cvv.isEmpty()) {
		        throw new IllegalArgumentException("Il CVV non può essere vuoto");
		    }

		    if (!cvv.matches("\\d{3}")) {
		        throw new IllegalArgumentException("Il CVV deve contenere esattamente 3 cifre numeriche");
		    }

		    return true;
	 }//controlloCVV

	 public static boolean controllaTipoCarta(String numeroCarta) {
		    if (numeroCarta == null || numeroCarta.trim().isEmpty()) {
		        throw new IllegalArgumentException("Numero carta non può essere null o vuoto");
		    }
		    numeroCarta = numeroCarta.trim();

		    if (numeroCarta.matches("^4[0-9]{12}(?:[0-9]{3})?$")) return true; // VISA
		    if (numeroCarta.matches("^5[1-5][0-9]{14}$")) return true;         // MASTERCARD
		    if (numeroCarta.matches("^(5018|5020|5038|5893|6304|6759|6761|6763)[0-9]{8,15}$")) return true; // MAESTRO

		    throw new IllegalArgumentException("Tipo carta non riconosciuto");
		}//controllaTipoCarta
	 /**
		 * Controllo per verificare se il tipo di indirizzo che ci viene passato è corretto o no
		 * @param tipoIndirizzo
		 * @return true se va bene, false se no
		 */
		public static void controlloTipoIndirizzo(String tipoIndirizzo) {
			if (tipoIndirizzo == null || tipoIndirizzo.isBlank()) {
				log.error("Il tipo indirizzo è null o vuoto");
				throw new IllegalArgumentException("Il tipo indirizzo è null o vuoto");
			}
			try {
				Indirizzo.Tipo.valueOf(tipoIndirizzo.toUpperCase());
			} catch (IllegalArgumentException e) {
				log.error("Il tipo indirizzo {}, non è valido", tipoIndirizzo);
				throw  new IllegalArgumentException("Il tipo indirizzo non è valido");
			}
		}//controlloTipoIndirizzo


}//ControlliUtils
