package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MyController {
 
	// Inizializziamo un ArrayList per contenere l'elenco delle auto
	ArrayList<Autovettura> elencoAuto = new ArrayList<>();
	
	
	// Inizializziamo una variabile alla quale assegneremo i dati di configurazione
	private final AutoJDBC autoJDBC;
	
	
	@Autowired
	public MyController(AutoJDBC autoJDBC) {
	    this.autoJDBC = autoJDBC;
	}
		
		
	// Prima chiamata get, che rimanda alla index
	@GetMapping("/")
	public String getIndex() {
		return "index";
	}
	
	
	// Pulsante che rimanda alla pagina di selezione
	@GetMapping("/selezionaAuto")
	public String getSelezionaAuto( Model model ) {
		List<String> modelli = autoJDBC.modelli();
		model.addAttribute("modelli", modelli);
		
		return "selezionaAuto";
	}
	
	
	// Richiesta post, una volta inviato il form di inserimento auto
    @PostMapping("/selezionaAuto")
    public String selezionaAuto( @RequestParam("nome") String nome, @RequestParam("colore") String colore, RedirectAttributes redirectAttributes) {
	     	
    	// Eseguiamo la query
    	autoJDBC.insertAuto(nome, colore);
    			
    	// Creiamo un oggetto Autovettura da restituire al template
		Autovettura auto = new Autovettura();
		auto.setNome(nome);
		auto.setColore(colore);

		// e lo settiamo nel flash attribute e non model (in quanto il model perderebbe i dati nel redirect
		redirectAttributes.addFlashAttribute("selezione", "La tua selezione: " + auto.toString());
    	
    	// Restituiamo la stessa pagina, che stamperà la conferma sotto il form
    	return "redirect:/selezionaAuto";
    }
    
    
	// Pulsante che rimanda alla pagina di stampa
	@GetMapping("/stampaAuto")
	public String getStampaaAuto( Model model ) {
		
    	// Eseguiamo la query che seleziona tutta la tabella 
    	elencoAuto = autoJDBC.elencoAuto();
    	// ed otteniamo un arrayList che settiamo nel model
		model.addAttribute("lista", elencoAuto);
		
		return "stampaAuto";
	}
	

	// Pulsante che rimanda alla pagina dei grafici
	@GetMapping("/paginaGrafici")
	public String getPaginaGrafici( Model model ) {
		
		try {
			// Ricaviamo la mappa dei conteggi delle auto e di colori
			Map<String, Integer> conteggioAuto = autoJDBC.conteggioAuto();
			Map<String, Integer> conteggioColori = autoJDBC.conteggioColori();
			
			
		    // Estraiamo le chiavi e i valori dalle due mappe
		    List<String> nomi = new ArrayList<>(conteggioAuto.keySet());
		    List<Integer> contNomi = new ArrayList<>(conteggioAuto.values());
		    
		    List<String> colori = new ArrayList<>(conteggioColori.keySet());
		    List<Integer> contColori = new ArrayList<>(conteggioColori.values());
	
			// Settiamo i conteggi nel model per stamparli in tabella
			model.addAttribute("conteggioColori", conteggioColori);
			model.addAttribute("conteggioAuto", conteggioAuto);
			
		
		    // Generiamo tutti i grafici e settiamo nel model l'immagine e la descrizione del grafico
		    
		    // Creazione grafici
			Grafici.generaGraficoBarre(nomi, contNomi, "autoBarre", "Statistiche per modello");
			Grafici.generaGraficoBarre(colori, contColori, "coloreBarre", "Statistiche per colore");
			Grafici.generaGraficoTorta(conteggioAuto, "autoTorta", "Statistiche per modello");
			Grafici.generaGraficoTorta(conteggioColori, "coloreTorta", "Statistiche per colore");
			
			
			// Passaggio dei grafici al model (per precaricarli nella cache
		    model.addAttribute("autoBarre", "autoBarre.png");	
		    model.addAttribute("coloreBarre", "coloreBarre.png");
		    model.addAttribute("autoTorta", "autoTorta.png");	
		    model.addAttribute("coloreTorta", "coloreTorta.png");
		    
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "stampaGrafico";
	}
	
	
	@GetMapping("/stampaGrafico")
	public String getStampaGrafico(@RequestParam(name = "grafico", required = false) String nomeGrafico, Model model) {
    
        // Aggiungiamo il nome dell'immagine al model per renderlo disponibile nel template
        model.addAttribute("nomeGrafico", nomeGrafico);
	       
        // Restituisci la pagina per visualizzare il grafico
        return "stampaGrafico";
    }
}