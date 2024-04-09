package films;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import database.DatabaseManager;


public class UsaFilm {


	public static void main(String[] args) {
		
		DatabaseManager database = new DatabaseManager();
		ArrayList<Film> listaFilm = new ArrayList<>();
		ArrayList<Object[]> listaObject = new ArrayList<>();
		Scanner input = new Scanner(System.in); 
		int scelta = 0;
		
		do {		
			String query = "";

			try {
				System.out.print("\n\nCosa vuoi fare? \n"
						+ "[1] Registrare un nuovo film \n[2] Stampare tutti i film \n"
						+ "[3] Cerca per genere \n[4] Cerca per anno \n"
						+ "[5] Modifica anno \n[6] Cancella film \n[0] EXIT \n"
						+ "La tua scelta: ");
	
				scelta = input.nextInt();
				
				
				if (scelta == 1) {
	
					System.out.print("Titolo: ");
					String titolo = input.nextLine();
					
					System.out.print("Regista: ");
					String regista = input.nextLine();
					
					System.out.print("Anno: ");
					int anno = Integer.parseInt(input.nextLine());
	
					System.out.print("Genere: ");
					String genere = input.nextLine();
					
					query = "INSERT INTO Film (titolo, regista, anno, genere) VALUES (?, ?, ?, ?)";
					database.eseguiQuery(query, titolo, regista, anno, genere);
					
				} else if (scelta == 2) {
					
					listaFilm.clear();
					listaObject.clear();
					
					query = "SELECT * FROM film";
					listaObject = database.eseguiQuery(query);
					
					// Recupera il risultato e lo trasforma in ArrayList di Film
					listaFilm = creaListaDaObject(listaObject);
					System.out.print("\nElenco film: \n");
					stampa(listaFilm);
	
				} else if (scelta == 3) {
					
					listaFilm.clear();
					listaObject.clear();
					
					// DISTINCT stampa tutti i campi genere trovati, saltando i doppioni
					query = "SELECT DISTINCT genere FROM Film";
					listaObject = database.eseguiQuery(query);
					
					System.out.print("\nScegli tra i seguenti generi: \n");
					
				    // Stampa lista generi ottenuti da query
					for (int i = 1; i <= listaObject.size(); i++) { 
						System.out.println(i + " per " + listaObject.get(i-1)[0].toString());
					}
					
					System.out.print("La tua scelta: ");
					
					int sceltaGenere = input.nextInt();
					String genere = "";
				
					// Preleva il genere all'id inserito dall'utente (decrementato perchè parta da zero)
					genere = listaObject.get(sceltaGenere-1)[0].toString();
					
					query = "SELECT * FROM Film WHERE genere = (?)";
					listaObject = database.eseguiQuery(query, genere);
					listaFilm = creaListaDaObject(listaObject);
					
					System.out.println("\nEcco di seguito i film del genere scelto: ");
					stampa(listaFilm);
	
				} else if (scelta == 4) {
					
					listaFilm.clear();
					listaObject.clear();
					
					System.out.print("\nInserisci l' anno in formato aaaa: ");
					int sceltaAnno = input.nextInt();
					
					query = "SELECT * FROM film WHERE anno = (?)";
					listaObject = database.eseguiQuery(query, sceltaAnno);
					listaFilm = creaListaDaObject(listaObject);
					stampa(listaFilm);
	
				} else if (scelta == 5) {
	
					System.out.print("\nInserisci l'id del film da modificare: ");
					int annoId = input.nextInt();
					System.out.print("\nInserisci il nuovo anno in formato aaaa: ");
					int annoNuovo = input.nextInt();
					
					query = "UPDATE film SET anno = (?) WHERE filmID = (?)";
					database.eseguiQuery(query, annoNuovo, annoId);
	
				} else if (scelta == 6) {
					
					System.out.print("\nInserisci l'id del film da cancellare: ");
					int cancellaId = input.nextInt();
					
					query = "DELETE FROM film WHERE filmID = (?)";
					database.eseguiQuery(query, cancellaId);
					
				} else if (scelta == 0) {
					System.out.println("\nHai terminato il programma.");
					break;
					
				} else {
					
					System.out.println("\nHai inserito una scelta non valida");
				}
				
		    // Il catch distingue 3 tipi di errori: di input, di database o generici
		    } catch (Exception e) {

		        if (e instanceof InputMismatchException) {
		            System.out.println("\nErrore: devi inserire un numero intero.");
		            input.nextLine(); // svuota il buffer
		            
		        } else if (e instanceof SQLException) {
				    System.out.print("\nErrore nella lettura o scrittura dei dati del database: ");
				    e.printStackTrace();    
				    
		        } else {
		        	System.out.print("\nSi è verificato un errore: ");
		            e.printStackTrace();
		        }
		    }
		} while (scelta != 0);
		
		input.close(); // Chiude lo scanner
	}


	/* Questo metodo recupera un arrayList di oggetti indefiniti (ottenuti dal DatabaseManager 
	   che lavora su dati astratti) e crea oggetti Film.
	 */
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
	

	// Stampa ogni film dall'array list con il toString
	public static void stampa(ArrayList<Film> lista) {
		
	if (lista.isEmpty()) {
			System.out.println("\nNON CI SONO FILM IN ARCHIVIO!");
		
		} else {
			for (Film f : lista) {
				System.out.println(f.toString());
			}
		} 
	}
}