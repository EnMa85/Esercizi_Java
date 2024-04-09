package distributore;

import java.io.Serializable;


public class Chiavetta implements Serializable {

	public String id;
	public String password;
	public double saldo;
	
	public Chiavetta(String newID, String password) {

	    this.saldo = 20;
        this.id = newID;
        this.password = password;
	}

	public String getID() {
		return this.id;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public double saldo() {
		return saldo;
	}
	
	public void versa(double importo) {
		saldo += importo;
	}
	
	public void preleva(int importo) {
		if (saldo >= importo) {
			saldo -= importo;
		} else {
			System.out.println("Saldo non sufficiente.");
		}
	}
}