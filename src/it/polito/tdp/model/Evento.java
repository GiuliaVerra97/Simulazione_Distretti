package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	
	public enum TipoEvento{
		CRIMINE, LIBERO
	}
	
	
	private LocalDateTime tempo;
	private TipoEvento tipo;
	private Agente a;
	private Event crimine;
	
	public Evento(LocalDateTime tempo, TipoEvento tipo, Agente a, Event crimine) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
		this.a = a;
		this.crimine = crimine;
	}

	public LocalDateTime getTempo() {
		return tempo;
	}

	public void setTempo(LocalDateTime tempo) {
		this.tempo = tempo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public Agente getA() {
		return a;
	}

	public void setA(Agente a) {
		this.a = a;
	}

	public Event getCrimine() {
		return crimine;
	}

	public void setCrimine(Event crimine) {
		this.crimine = crimine;
	}

	@Override
	public int compareTo(Evento other) {
		return this.tempo.compareTo(other.getTempo());
	}
	
	
	
	
	
	
}
