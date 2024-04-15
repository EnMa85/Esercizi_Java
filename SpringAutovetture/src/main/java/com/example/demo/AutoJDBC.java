package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AutoJDBC {  
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
    public AutoJDBC(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

	
	// Seleziona tutti nomi dalla tabella dei modelli disponibili
	public List<String> modelli() {
        String query = "SELECT nome FROM auto_modelli";
        return (List<String>) jdbcTemplateObject.queryForList(query, String.class);
    }
	
	
    public int insertAuto(String nome, String colore) {
        String query = "INSERT INTO auto_ordini (nome, colore) VALUES (?, ?)";
        return jdbcTemplateObject.update(query, nome, colore);
    }

    
	// Seleziona tutta la tabella chiamando, per ogni riga, l'interfaccia RowMapper che setta tutti i parametri
	public ArrayList<Autovettura> elencoAuto() {
	    String query = "SELECT * FROM auto_ordini";
	    return (ArrayList<Autovettura>) jdbcTemplateObject.query(query, new AutoRowMapper());
	}
	
	
	// Restituisce una mappa contenente il conteggio di ogni nome di auto
	public Map<String, Integer> conteggioAuto() {
	    String query = "SELECT nome, COUNT(*) AS conteggio FROM auto_ordini GROUP BY nome";
	    
	    Map<String, Integer> conteggioAuto = new HashMap<>();
	   
	    // Usiamo l'interfaccia RowMapper che elabora i record ottenuti dal database
	    jdbcTemplateObject.query(query, new RowMapper<Void>() {
	        @Override
	        public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
	            String nome = rs.getString("nome");
	            int conteggio = rs.getInt("conteggio");
	            // Aggiungiamo alla mappa i dati della riga
	            conteggioAuto.put(nome, conteggio);
	            return null;
	        }
	    });
	    // Restituiamo la mappa, contenente il nome auto come chiave ed il conteggio come valore
	    return conteggioAuto;
	}
	
	
	// Restituisce una mappa contenente il conteggio di ogni colore di auto
	public Map<String, Integer> conteggioColori() {
	    String query = "SELECT colore, COUNT(*) AS conteggio FROM auto_ordini GROUP BY colore";
	    
	    Map<String, Integer> conteggioColori = new HashMap<>();
	   
	    // Usiamo l'interfaccia RowMapper che elabora i record ottenuti dal database
	    jdbcTemplateObject.query(query, new RowMapper<Void>() {
	        @Override
	        public Void mapRow(ResultSet rs, int rowNum) throws SQLException {
	            String colore = rs.getString("colore");
	            int conteggio = rs.getInt("conteggio");
	            // Aggiungiamo alla mappa i dati della riga
	            conteggioColori.put(colore, conteggio);
	            return null;
	        }
	    });
	    // Restituiamo la mappa, contenente il colore auto come chiave ed il conteggio come valore
	    return conteggioColori;
	}
	
    // Metodo per eseguire query DDL
    public void executeDDLQuery(String query) {
        jdbcTemplateObject.execute(query);
    }
}
