package it.polito.tdp.model;

public class Agente {
	
	
	private int codice;
	private int luogo;
	private boolean occupato;
	
	
	

	public Agente(int codice, int luogo) {
		super();
		this.codice = codice;
		this.luogo=luogo;
		this.occupato=false;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public int getLuogo() {
		return luogo;
	}

	public void setLuogo(int luogo) {
		this.luogo = luogo;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	@Override
	public String toString() {
		return "agente "+codice+" dove "+luogo+" occupato "+occupato;
	}
	
	
	

}
