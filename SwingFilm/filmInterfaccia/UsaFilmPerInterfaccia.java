package filmInterfaccia;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.DatabaseManager;
import films.Film;


public class UsaFilmPerInterfaccia {
	
	static DatabaseManager database = new DatabaseManager();

	static ArrayList<Film> listaFilm = new ArrayList<>();
	static ArrayList<Object[]> listaObject = new ArrayList<>();
	static String query = "";
	
	
	public static void inserisciFilm() {
		try {
	        JTextField titoloIns = new JTextField(20);
	        JTextField registaIns = new JTextField(20);
	        JTextField annoIns = new JTextField(4);
	        JTextField genereIns = new JTextField(10);
	
	        JComponent[] inputs = new JComponent[] {
	            new JLabel("Titolo:"), titoloIns,
	            new JLabel("Regista"), registaIns,
	            new JLabel("Anno:"), annoIns,
	            new JLabel("Genere:"), genereIns
	        };
	
	        int result = JOptionPane.showConfirmDialog(null, inputs, "Inserisci dati del film", JOptionPane.OK_CANCEL_OPTION);
	
	        if (result == JOptionPane.OK_OPTION) {
	            String titolo = titoloIns.getText();
	            String regista = registaIns.getText();
	            int anno = Integer.parseInt(annoIns.getText());
	            String genere = genereIns.getText();
	            query = "INSERT INTO film (titolo, regista, anno, genere) VALUES (?, ?, ?, ?)";
	    		database.eseguiQuery(query, titolo, regista, anno, genere);
	        }
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void stampaFilm() {
		try {
			listaFilm.clear();
			listaObject.clear();
			
			query = "SELECT * FROM film";
			listaObject = database.eseguiQuery(query);
			
			// Recupera il risultato e lo trasforma in ArrayList di Film
			listaFilm = creaListaDaObject(listaObject);

			stampa(listaFilm);
			
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void cercaGenere() {
		try {
			listaFilm.clear();
			listaObject.clear();
			
			// DISTINCT stampa tutti i campi genere trovati, saltando i doppioni
			query = "SELECT DISTINCT genere FROM film";
			listaObject = database.eseguiQuery(query);
			
			String[] generi = new String[listaObject.size()];
			
		    // Stampa lista generi ottenuti da query
			for (int i = 0; i < listaObject.size(); i++) { 
				generi[i] = listaObject.get(i)[0].toString();
			}
			
			String genere = (String) JOptionPane.showInputDialog(
	            null,
	            "Scegli un genere:",
	            "Selezione Genere",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            generi,
	            generi[0]);

	        if (genere != null) {
	        	query = "SELECT * FROM film WHERE genere = (?)";
	        	listaObject = database.eseguiQuery(query, genere);
	    		listaFilm = creaListaDaObject(listaObject);
	        }
	        stampa(listaFilm);
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void cercaAnno() {
		try {
			listaFilm.clear();
			listaObject.clear();
			
	        String annoString = JOptionPane.showInputDialog(null, "Inserisci l'anno in formato aaaa");
	
			int sceltaAnno = Integer.valueOf(annoString);
			
			query = "SELECT * FROM film WHERE anno = (?)";
			listaObject = database.eseguiQuery(query, sceltaAnno);
			listaFilm = creaListaDaObject(listaObject);
			stampa(listaFilm);
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void modificaAnno() {
		
		try {
	        JTextField annoIns = new JTextField(4);
	        JTextField idIns = new JTextField(5);
	
	        JComponent[] inputs = new JComponent[] {
	            new JLabel("Nuovo anno:"), annoIns,
	            new JLabel("ID del film da modificare:"), idIns
	        };
	
	        int result = JOptionPane.showConfirmDialog(null, inputs, "Inserisci nuovo anno ed id film", JOptionPane.OK_CANCEL_OPTION);
	
	        if (result == JOptionPane.OK_OPTION) {
	            int annoNuovo = Integer.parseInt(annoIns.getText());
	            int annoId = Integer.parseInt(idIns.getText());
	            query = "UPDATE film SET anno = (?) WHERE filmID = (?)";
	    		database.eseguiQuery(query, annoNuovo, annoId);
	        }
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void cancellaFilm() {
		
		try {
	        String idString = JOptionPane.showInputDialog(null, "Inserisci l'id del film da eliminare");
			int cancellaId = Integer.valueOf(idString);
			
			query = "DELETE FROM film WHERE filmID = (?)";
			database.eseguiQuery(query, cancellaId);
			
		} catch (Exception e) {
		    String message = "Errore nella lettura o scrittura dei dati del database: ";
		    e.printStackTrace();
			JOptionPane.showMessageDialog(null, message, "errore", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void stampa(ArrayList<Film> listaFilm) {
		
	 StringBuilder lista = new StringBuilder();
        for (Film film : listaFilm) {
        	lista.append("ID").append(film.getFilmID())
        		 .append(" - Titolo: ").append(film.getTitolo())
        		 .append(" - Regista: ").append(film.getRegista())
                 .append(" - Anno: ").append(film.getAnno())
                 .append("\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString(), "Stampa film", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static ArrayList<Film> creaListaDaObject(ArrayList<Object[]> listaObject) throws SQLException {
		
		ArrayList<Film> listaFilm = new ArrayList<>();		
		
		for (Object[] riga : listaObject) { 
			try {
			    int id = (int) riga[0];
			    String titolo = (String) riga[1];
			    String regista = (String) riga[2];
			    int anno = (int) riga[3];
			    String genere = (String) riga[4];
					    
			    Film film = new Film(id, titolo, regista, anno, genere);
			    listaFilm.add(film);
			    
			} catch (ClassCastException e) {
	            System.out.println("Errore di casting durante la creazione della lista di film: " + e.getMessage());
	        }
		}
		return listaFilm;
	}
		
}