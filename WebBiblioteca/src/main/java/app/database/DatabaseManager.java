package app.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatabaseManager {
  
    private static String url;
    private static String dbName;
    private static String user;
    private static String password;
    
    // Recupera i dati di accesso dal file di configurazione
    static {
        try {
            Properties props = new Properties();
            InputStream in = DatabaseManager.class.getClassLoader().getResourceAsStream("/app/database/database.properties");
            if (in != null) {
                props.load(in);
                in.close();

                url = props.getProperty("db.url");
                dbName = props.getProperty("db.dbName");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
                
            } else {
                System.out.println("File di configurazione non trovato");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    // 'params' è un array di tipo Object. Non avendo un tipo definito può passare al metodo 
    // una serie di parametri senza necessità di specificarne il numero ed il tipo.   
    public static ArrayList<Map<String, Object>> eseguiQuery(String query, Object... params) {
        ArrayList<Map<String, Object>> listaMap = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url + dbName, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Legge i parametri dall'array e li assegna dinamicamente allo statement con i segnaposto
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            // Esegue la query e restituisce true se rileva sia di tipo select
            boolean isResultSet = stmt.execute();

            // Se la query ha restituito un result set (ovvero era una select) salva il risultato
            if (isResultSet) {    
            	ResultSet resultSet = stmt.getResultSet();
            	
            	// Scorre il ResultSet e crea un ArrayList di Object
                while (resultSet.next()) {
                	
                    int numeroColonne = resultSet.getMetaData().getColumnCount();
                    Map<String, Object> riga = new HashMap<>();
                    
                    for (int i = 0; i < numeroColonne; i++) {
                        String nomeColonna = resultSet.getMetaData().getColumnName(i + 1);
                        Object valoreColonna = resultSet.getObject(i + 1);
                        riga.put(nomeColonna, valoreColonna);
                    }
                    listaMap.add(riga);
                }
            	
            } else {
                // Se era una query di aggiornamento, stampa la conferma dell'operazione
            	int rowsAffected = stmt.getUpdateCount();
                System.out.println("Numero di righe aggiornate: " + rowsAffected);
            }

        } catch (SQLException e) {
             System.out.println("Errore durante l'esecuzione della query:");
             e.printStackTrace();
        }
        
        return listaMap;
    }
}