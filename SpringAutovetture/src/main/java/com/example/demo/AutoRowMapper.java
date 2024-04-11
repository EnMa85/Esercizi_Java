package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AutoRowMapper implements RowMapper<Autovettura> {
    @Override
    public Autovettura mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
        Autovettura auto = new Autovettura();
        
        auto.setId(rs.getInt("id"));
        auto.setNome(rs.getString("nome"));
        auto.setColore(rs.getString("colore"));

        return auto;
    }
}