package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MyController {
	
	// Inizializziamo un ArrayList per contenere l'elenco dipendenti
	ArrayList<Dipendente> dipendenti = new ArrayList<>();
	
	// Inizializziamo una variabile alla quale assegneremo i dati di configurazione
	private final DipendenteJDBCTemp JDBC;
		
	// che peschiamo direttamente dalla classe DipendenteJDBCTem in cui sono stati settati
	@Autowired
	public MyController(DipendenteJDBCTemp dipendenteJDBCTemp) {
	    this.JDBC = dipendenteJDBCTemp;
	}
	 
	// Prima chiamata get, che rimanda alla index
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	// Richiesta get al primo avvio della pagina di inserimento
	@GetMapping("/inserisciDipendente")
    public String getFormInserimento() {
    	return "inserisciDipendente";
    }
	
	// Richiesta post, una volta inviato il form di inserimento
    @PostMapping("/inserisciDipendente")
    public String inserisciDIpendente(
						    		@RequestParam("nome") String nome, 
						    		@RequestParam("cognome") String cognome, 
						    		@RequestParam("mansione") String mansione,
						    		@RequestParam("stipendio") String stringStipendio,
						    		Model model) {
	     	
    	int stipendio = Integer.parseInt(stringStipendio);
    	
		// Eseguiamo la query
		JDBC.insertDipendente(cognome, nome, mansione, stipendio);
		
    	// Creiamo un oggetto Dipendente da restituire al template
		Dipendente dip = new Dipendente();
		dip.setNome(nome);
		dip.setCognome(cognome);
		dip.setMansione(mansione);
		dip.setStipendio(stipendio);

		// e lo settiamo nel model
    	model.addAttribute("conferma", "Conferma inserimento dipendente: " + dip.toString());
    	// Restituiamo la stessa pagina, che stamperà la conferma sotto il form
    	return("inserisciDipendente");
    }
    
    // Richiesta get per stampare l'elenco dei dipendenti
    @GetMapping("/stampaDipendenti")
    public String stampaDipendenti(Model model) {

    	// Eseguiamo la query che seleziona tutta la tabella 
    	dipendenti = JDBC.elencoDipendenti();
    	// ed otteniamo un arrayList che settiamo nel model
		model.addAttribute("lista", dipendenti);
    	
    	return("elencoDipendenti");
    }
    
    // Ritorna la pagina di elenco restituendo il dipendente selezionato
    @PostMapping("/selezionaDipendente")
    public String selezionaDipendente(@RequestParam("selId") int id, Model model) {
	     	
    	// Selezioniamo direttamente dal DB il dipendente con l'id ricevuto e settiamolo nel model
    	Dipendente dip = JDBC.selezionaDipendente(id);
    	model.addAttribute("dipendente", dip.toString());
    	
    	// Aggiungiamo nuovamente l'elenco dipendenti completo
    	dipendenti = JDBC.elencoDipendenti();
    	model.addAttribute("lista", dipendenti);
    	
    	// Passiamo al template anche l'id del dipendente
    	model.addAttribute("id", id);
    	
    	// La pagina stavolta stamperà un secondo form per la variazione del dipendente selezionato
    	return("elencoDipendenti");
    }
    
    // Riceve il secondo form della pagina di elenco, relativo alla modifica selezionata
    @PostMapping("/eseguiOperazione")
    public String eseguiOperazione(
					    		@RequestParam(value = "selId", required = false) String StringId,
					            @RequestParam("operazione") String operazione,
					            @RequestParam(value = "stipendio", required = false) Integer stipendio,
					            @RequestParam(value = "mansione", required = false) String mansione,
   								Model model) {
        
    	// Dopo aver ricevuto i parametri (il required è settato a opzionale perchè riceveremo solo uno di questi campi)
    	// verifichiamo il tipo di operazione e chiamiamo la query specifica, settando nel model il dipendente modificato
    	
    	if (StringId == null || StringId.isEmpty()) {
            // Se non è stato selezionato un id, rimanda alla pagina di stampa
    		return "redirect:/stampaDipendenti";
        }
        
    	// Se l'id è presente, prosegui
    	int id = Integer.parseInt(StringId);
    	
    	switch (operazione) {
	    	case "variaStipendio":
	            if (stipendio != null) {
	            	// Eseguiamo la query e poi selezioniamo il dipendente con i dati aggiornati
	            	JDBC.updateStipendio(id, stipendio);
	            	Dipendente dip = (Dipendente) JDBC.selezionaDipendente(id);
	            	model.addAttribute("dipendente", "Conferma variazione stipendio dipendente: " + dip.toString());
	            }
	            break;
	            
	    	 case "variaMansione":
	             if (mansione != null) {
	            	JDBC.updateMansione(id, mansione);
	             	Dipendente dip = (Dipendente) JDBC.selezionaDipendente(id);
	             	model.addAttribute("dipendente", "Conferma variazione mansione dipendente: " + dip.toString());
	             }
	             break;

	    	 case "eliminaDipendente":
	    		 	// Selezioniamo il dipendente (prima di cancellarlo) e dopo eseguiamo la query
	    		 	Dipendente dip = JDBC.selezionaDipendente(id);
	    	    	JDBC.deleteDipendente(id);
	    	    	model.addAttribute("dipendente", "Conferma eliminazione dipendente: " + dip.toString());
	    	    	break;
    	}
    
    	// Adesso ricaviamo la lista aggiornata di tutti i dipendenti e la rimandiamo al model, insieme al dipendente modificato
		dipendenti = JDBC.elencoDipendenti();
		model.addAttribute("lista", dipendenti);
   	
    	return("elencoDipendenti");
    }
}