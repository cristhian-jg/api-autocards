package com.crisgon.apirest.model;

/**
 * Clase modelo que representa la tabla Estadistica.
 * 
 * @author Cristhian González.
 *
 */

public class Estadistica {

	private int id;
	private String jugador;
	private int partida;
	private int ganadas;
	private int perdidas;
	private int empatadas;

	public Estadistica() {
	}

	public Estadistica(int id, String jugador, int partida, int ganadas, int perdidas, int empatadas) {
		this.id = id;
		this.jugador = jugador;
		this.partida = partida;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
		this.empatadas = empatadas;
	}

	public Estadistica(String jugador, int partida, int ganadas, int perdidas, int empatadas) {
		this.jugador = jugador;
		this.partida = partida;
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

	public int getPartida() {
		return partida;
	}

	public void setPartida(int partida) {
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "Estadistica [id=" + id + ", jugador=" + jugador + ", partida=" + partida + ", ganadas=" + ganadas
				+ ", perdidas=" + perdidas + ", empatadas=" + empatadas + "]";
	}

}
