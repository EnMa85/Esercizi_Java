package database;


import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "";
    private String user = "root";
    private String password = "";
    
    	
   
    // 'params' è un array di tipo Object. Non avendo un tipo definito può passare al metodo 
    // una serie di parametri senza necessità di specificarne il numero ed il tipo.
    
    public ArrayList<Object[]> eseguiQuery(String query, Object... params) {
    	
    	ArrayList<Object[]> listaRS = new ArrayList<>();
    	   	
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
                    Object[] riga = new Object[numeroColonne];
                    
                    for (int i = 0; i < numeroColonne; i++) {
                    	riga[i] = resultSet.getObject(i + 1);
                    }
                    listaRS.add(riga);
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
        
        return listaRS;
    }
}