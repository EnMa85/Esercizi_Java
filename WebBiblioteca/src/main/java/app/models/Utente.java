package app.models;

public class Utente {

private String cf;
private String password;
private boolean stato_account = true;

	public Utente() {
	}
	
	public String getCf() {
		return cf;
	}
	
	public void setCf(String cf) {
		this.cf = cf;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getStatoAccount() {
		return stato_account;
	}
	
	public void setStatoAccount(boolean stato_account) {
		this.stato_account = stato_account;
	}
}