package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DipendenteRowMapper implements RowMapper<Dipendente> {
    @Override
    public Dipendente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Dipendente dipendente = new Dipendente();
        dipendente.setId(rs.getInt("id"));
        dipendente.setNome(rs.getString("nome"));
        dipendente.setCognome(rs.getString("cognome"));
        dipendente.setMansione(rs.getString("mansione"));
        dipendente.setStipendio(rs.getInt("stipendio"));
        return dipendente;
    }
}