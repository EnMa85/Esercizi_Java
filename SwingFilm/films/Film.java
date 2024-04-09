package films;

public class Film {

	int filmID;
	String titolo;
	String regista;
	int anno;
	String genere;

	public Film (int filmID, String titolo, String regista, int anno, String genere) {
		
		this.filmID = filmID;
		this.titolo = titolo;
		this.regista = regista;
		this.anno = anno;
		this.genere = genere;
	}

	@Override
	public String toString() {
		return "Film ID: " + filmID + " - titolo: " + titolo + " - regista: " + regista + " - anno: " + anno
				+ " - genere: " + genere;
	}

	public int getFilmID() {
		return filmID;
	}

	public void setFilmID(int filmID) {
		this.filmID = filmID;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getRegista() {
		return regista;
	}

	public int getAnno() {
		return anno;
	}

	public String getGenere() {
		return genere;
	}
}
