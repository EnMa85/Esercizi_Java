package com.example.demo;

public class Dipendente {
	
	private int id;
	private String nome;
	private String cognome;
	private String mansione;
	private int stipendio;
	
	public Dipendente() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getMansione() {
		return mansione;
	}
	
	public void setMansione(String mansione) {
		this.mansione = mansione;
	}
	
	public int getStipendio() {
		return stipendio;
	}
	
	public void setStipendio(int stipendio) {
		this.stipendio = stipendio;
	}
	
	public boolean isRich() {
		double soglia = 3000;
		if (this.stipendio > soglia) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return cognome + " " + nome + " - " + mansione + " - Stipendio: € " + stipendio + " - Condizione: " + (this.isRich() ? "ricco" : "povero");
	}
}
