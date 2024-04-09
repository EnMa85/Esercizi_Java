package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DipendenteJDBCTemp {
    
	
	private JdbcTemplate jdbcTemplateObject;
	private DipendenteRowMapper dipendenteRowMapper;

	@Autowired
    public DipendenteJDBCTemp(JdbcTemplate jdbcTemplateObject, DipendenteRowMapper dipendenteRowMapper) {
        this.jdbcTemplateObject = jdbcTemplateObject;
        this.dipendenteRowMapper = dipendenteRowMapper;
    }

	// Seleziona tutti i dipendenti chiamando, per ogni riga, l'interfaccia RowMapper che setta tutti i parametri
	public ArrayList<Dipendente> elencoDipendenti() {
	    String query = "SELECT * FROM dipendenti";
	    return (ArrayList<Dipendente>) jdbcTemplateObject.query(query, new DipendenteRowMapper());
	}
    
	// Seleziona il dipendente con un dato id. E' necessario passare l'id come array di un elemento, e non come singola variabile
	public Dipendente selezionaDipendente(int id) {
	    String query = "SELECT * FROM dipendenti WHERE id = ?";
	    return jdbcTemplateObject.queryForObject(query, new Object[]{id}, new DipendenteRowMapper());
	}
	
    public int insertDipendente(String cognome, String nome, String mansione, double stipendio) {
        String query = "INSERT INTO dipendenti (cognome, nome, mansione, stipendio) VALUES (?, ?, ?, ?)";
        return jdbcTemplateObject.update(query, cognome, nome, mansione, stipendio);
    }

    public int updateCognome(int id, String cognome) {
        String query = "UPDATE dipendenti SET cognome = ? WHERE id = ?";
        return jdbcTemplateObject.update(query, cognome, id);
    }

    public int updateNome(int id, String nome) {
        String query = "UPDATE dipendenti SET mansione = ? WHERE id = ?";
        return jdbcTemplateObject.update(query, nome, id);
    }
    
    public int updateMansione(int id, String mansione) {
        String query = "UPDATE dipendenti SET mansione = ? WHERE id = ?";
        return jdbcTemplateObject.update(query, mansione, id);
    }

    public int updateStipendio(int id, int stipendio) {
        String query = "UPDATE dipendenti SET stipendio = ? WHERE id = ?";
        return jdbcTemplateObject.update(query, stipendio, id);
    }

    public int deleteDipendente(int id) {
        String query = "DELETE FROM dipendenti WHERE id = ?";
        return jdbcTemplateObject.update(query, id);
    }

    // Metodo per eseguire query DDL
    public void executeDDLQuery(String query) {
        jdbcTemplateObject.execute(query);
    }
}
