package com.example.demo;

public class Autovettura {
	
	private int id;
	private String nome;
	private String colore;


	public Autovettura() {}

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
	
	public String getColore() {
		return colore;
	}
	
	public void setColore (String colore) {
		this.colore = colore;
	}
	
	@Override
	public String toString() {
		return nome + " colore " + colore;
	}
}