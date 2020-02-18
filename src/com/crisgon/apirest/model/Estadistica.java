package com.crisgon.apirest.model;

public class Estadistica {

	private int id;
	private String jugador;
	private int ganadas;
	private int perdidas;
	private int empatadas;
	
	public Estadistica() {
	}

	public Estadistica(int id, String jugador, int ganadas, int perdidas, int empatadas) {
		this.id = id;
		this.jugador = jugador;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
		this.empatadas = empatadas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	public int getGanadas() {
		return ganadas;
	}

	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}

	public int getPerdidas() {
		return perdidas;
	}

	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}

	public int getEmpatadas() {
		return empatadas;
	}

	public void setEmpatadas(int empatadas) {
		this.empatadas = empatadas;
	}

	@Override
	public String toString() {
		return "Estadistica [id=" + id + ", jugador=" + jugador + ", ganadas=" + ganadas + ", perdidas=" + perdidas
				+ ", empatadas=" + empatadas + "]";
	}
	
}
