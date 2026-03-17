package com.homestyle.demo;

import org.springframework.http.HttpStatus;

public enum ErroreCodice {

    // ================== GENERICI ==================
    ERRORE_VALIDAZIONE("ERRORE_VALIDAZIONE", "Dati non validi", HttpStatus.BAD_REQUEST),
    ERRORE_INTERNO("ERRORE_INTERNO", "Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR),
    OPERAZIONE_NON_CONSENTITA("OPERAZIONE_NON_CONSENTITA", "Operazione non consentita", HttpStatus.BAD_REQUEST),

    // ================== UTENTE ==================
    UTENTE_NON_TROVATO("UTENTE_NON_TROVATO", "Utente non trovato", HttpStatus.NOT_FOUND),
    UTENTE_DUPLICATO("UTENTE_DUPLICATO", "Esiste già un utente con questi dati", HttpStatus.CONFLICT),

    // ================== CATEGORIA ==================
    CATEGORIA_NON_TROVATA("CATEGORIA_NON_TROVATA", "Categoria non trovata", HttpStatus.NOT_FOUND),
    CATEGORIA_NOME_NON_VALIDO("CATEGORIA_NOME_NON_VALIDO", "Nome categoria non valido", HttpStatus.BAD_REQUEST),
    CATEGORIA_DESCRIZIONE_NON_VALIDA("CATEGORIA_DESCRIZIONE_NON_VALIDA", "Descrizione categoria non valida", HttpStatus.BAD_REQUEST),
    CATEGORIA_DUPLICATA("CATEGORIA_DUPLICATA", "Esiste già una categoria con questo nome", HttpStatus.CONFLICT),

    // ================== PRODOTTO ==================
    PRODOTTO_NON_TROVATO("PRODOTTO_NON_TROVATO", "Prodotto non trovato", HttpStatus.NOT_FOUND),
    PRODOTTO_NON_DISPONIBILE("PRODOTTO_NON_DISPONIBILE", "Prodotto non disponibile", HttpStatus.BAD_REQUEST),
    PRODOTTO_STOCK_INSUFFICIENTE("PRODOTTO_STOCK_INSUFFICIENTE", "Quantità richiesta non disponibile a magazzino", HttpStatus.BAD_REQUEST),

    // ================== CARRELLO ==================
    CARRELLO_NON_TROVATO("CARRELLO_NON_TROVATO", "Carrello non trovato", HttpStatus.NOT_FOUND),
    CARRELLO_GIA_ESISTENTE_PER_UTENTE("CARRELLO_GIA_ESISTENTE_PER_UTENTE", "Esiste già un carrello per questo utente", HttpStatus.CONFLICT),
    CARRELLO_NON_ATTIVO("CARRELLO_NON_ATTIVO", "Il carrello non è in stato ATTIVO", HttpStatus.BAD_REQUEST),
    CARRELLO_VUOTO("CARRELLO_VUOTO", "Il carrello è vuoto", HttpStatus.BAD_REQUEST),

    // ================== CARRELLO_PRODOTTO ==================
    CARRELLO_PRODOTTO_NON_TROVATO("CARRELLO_PRODOTTO_NON_TROVATO", "Elemento carrello non trovato", HttpStatus.NOT_FOUND),
    CARRELLO_PRODOTTO_DUPLICATO("CARRELLO_PRODOTTO_DUPLICATO", "Prodotto già presente nel carrello", HttpStatus.CONFLICT),
    CARRELLO_PRODOTTO_QUANTITA_NON_VALIDA("CARRELLO_PRODOTTO_QUANTITA_NON_VALIDA", "Quantità non valida per il prodotto nel carrello", HttpStatus.BAD_REQUEST),

    // ================== ORDINE ==================
    ORDINE_NON_TROVATO("ORDINE_NON_TROVATO", "Ordine non trovato", HttpStatus.NOT_FOUND),
    ORDINE_STATO_NON_VALIDO("ORDINE_STATO_NON_VALIDO", "Transizione di stato ordine non valida", HttpStatus.BAD_REQUEST),
    ORDINE_GIA_ANNULLATO("ORDINE_GIA_ANNULLATO", "Ordine già annullato", HttpStatus.BAD_REQUEST),
    ORDINE_GIA_CONSEGNATO("ORDINE_GIA_CONSEGNATO", "Ordine già consegnato, operazione non consentita", HttpStatus.BAD_REQUEST),

    // ================== DETTAGLIO_ORDINE ==================
    DETTAGLIO_ORDINE_NON_TROVATO("DETTAGLIO_ORDINE_NON_TROVATO", "Dettaglio ordine non trovato", HttpStatus.NOT_FOUND),

    // ================== PAGAMENTO ==================
    PAGAMENTO_NON_TROVATO("PAGAMENTO_NON_TROVATO", "Pagamento non trovato", HttpStatus.NOT_FOUND),
    PAGAMENTO_GIA_EFFETTUATO("PAGAMENTO_GIA_EFFETTUATO", "Pagamento già effettuato", HttpStatus.BAD_REQUEST),
    PAGAMENTO_IMPORTO_NON_VALIDO("PAGAMENTO_IMPORTO_NON_VALIDO", "Importo pagamento non valido", HttpStatus.BAD_REQUEST),
    MODALITA_PAGAMENTO_NON_TROVATA("MODALITA_PAGAMENTO_NON_TROVATA", "Modalità di pagamento non trovata", HttpStatus.NOT_FOUND),
    CARTA_PAGAMENTO_NON_TROVATA("CARTA_PAGAMENTO_NON_TROVATA", "Carta di pagamento non trovata", HttpStatus.NOT_FOUND),
    CARTA_PAGAMENTO_NON_VALIDA("CARTA_PAGAMENTO_NON_VALIDA", "Carta di pagamento non valida", HttpStatus.BAD_REQUEST),

    // ================== INDIRIZZO ==================
    INDIRIZZO_NON_TROVATO("INDIRIZZO_NON_TROVATO", "Indirizzo non trovato", HttpStatus.NOT_FOUND),
    INDIRIZZO_NON_VALIDO("INDIRIZZO_NON_VALIDO", "Indirizzo non valido", HttpStatus.BAD_REQUEST),

    // ================== PRENOTAZIONE ==================
    PRENOTAZIONE_NON_TROVATA("PRENOTAZIONE_NON_TROVATA", "Prenotazione non trovata", HttpStatus.NOT_FOUND),
    PRENOTAZIONE_STATO_NON_VALIDO("PRENOTAZIONE_STATO_NON_VALIDO", "Transizione di stato prenotazione non valida", HttpStatus.BAD_REQUEST),

    // ================== RESO ==================
    RESO_NON_TROVATO("RESO_NON_TROVATO", "Reso non trovato", HttpStatus.NOT_FOUND),
    RESO_GIA_ESISTENTE("RESO_GIA_ESISTENTE", "Esiste già un reso per questa riga d'ordine", HttpStatus.CONFLICT),
    RESO_STATO_NON_VALIDO("RESO_STATO_NON_VALIDO", "Transizione di stato reso non valida", HttpStatus.BAD_REQUEST),
    RESO_NON_ANNULLABILE("RESO_NON_ANNULLABILE", "Non è possibile annullare questo reso", HttpStatus.BAD_REQUEST),

    // ================== SPEDIZIONE ==================
    SPEDIZIONE_NON_TROVATA("SPEDIZIONE_NON_TROVATA", "Spedizione non trovata", HttpStatus.NOT_FOUND),
    SPEDIZIONE_STATO_NON_VALIDO("SPEDIZIONE_STATO_NON_VALIDO", "Transizione di stato spedizione non valida", HttpStatus.BAD_REQUEST),

    // ================== MOVIMENTO_MAGAZZINO ==================
    MOVIMENTO_MAGAZZINO_NON_TROVATO("MOVIMENTO_MAGAZZINO_NON_TROVATO", "Movimento magazzino non trovato", HttpStatus.NOT_FOUND),
    MOVIMENTO_MAGAZZINO_NON_VALIDO("MOVIMENTO_MAGAZZINO_NON_VALIDO", "Movimento magazzino non valido", HttpStatus.BAD_REQUEST),

    // ================== RECENSIONE ==================
    RECENSIONE_NON_TROVATA("RECENSIONE_NON_TROVATA", "Recensione non trovata", HttpStatus.NOT_FOUND),
    RECENSIONE_GIA_ESISTENTE("RECENSIONE_GIA_ESISTENTE", "Esiste già una recensione per questo ordine/prodotto", HttpStatus.CONFLICT),
    RECENSIONE_VALUTAZIONE_NON_VALIDA("RECENSIONE_VALUTAZIONE_NON_VALIDA", "Valutazione recensione non valida", HttpStatus.BAD_REQUEST),

    // ================== WISHLIST ==================
    WISHLIST_ITEM_NON_TROVATO("WISHLIST_ITEM_NON_TROVATO", "Elemento wishlist non trovato", HttpStatus.NOT_FOUND),
    WISHLIST_ITEM_GIA_ESISTENTE("WISHLIST_ITEM_GIA_ESISTENTE", "Prodotto già presente in wishlist", HttpStatus.CONFLICT),
    WISHLIST_PRIORITA_NON_VALIDA("WISHLIST_PRIORITA_NON_VALIDA", "Priorità wishlist non valida", HttpStatus.BAD_REQUEST),

    // ================== CARTA PAGAMENTO ==================
    CARTA_PAGAMENTO_DUPLICATA("CARTA_PAGAMENTO_DUPLICATA", "Carta già presente per questo utente", HttpStatus.CONFLICT);

    private final String codice;
    private final String messaggioDiDefault;
    private final HttpStatus httpStatus;

    ErroreCodice(String codice, String messaggioDiDefault, HttpStatus httpStatus) {
        this.codice = codice;
        this.messaggioDiDefault = messaggioDiDefault;
        this.httpStatus = httpStatus;
    }

    public String getCodice() {
        return codice;
    }

    public String getMessaggioDiDefault() {
        return messaggioDiDefault;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
