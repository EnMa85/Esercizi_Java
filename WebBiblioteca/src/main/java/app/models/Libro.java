package app.models;

public class Libro {
	
	private int id;
	private String titolo;
	private String autore;
	private int anno;
	private int periodo_prestito;
	private boolean stato = true;

	public Libro () {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getPeriodoPrestito() {
		return this.periodo_prestito;
	}
	
	public void setPeriodoPrestito(int giorni) {
		this.periodo_prestito = giorni;
	}
	
	public String getStato() {
		return this.stato ? "disponibile" : "non disponibile";
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	public void setDisponibile() {
		this.stato = true;
	}
	
	public void setNonDisponibile() {
		this.stato = false;
	}
	
	public boolean prenota() {
		if (!this.stato) {
			return false;
		} else {
			setNonDisponibile();
			return true;
		}
	}
	
	@Override
	public String toString() {
		return "Titolo: " + titolo + ", autore: " + autore + ", anno: " + anno + " stato: " + stato;
	}
}
