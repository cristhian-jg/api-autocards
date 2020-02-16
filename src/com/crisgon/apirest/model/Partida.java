package com.crisgon.apirest.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 */

public class Partida implements Serializable {

	private int id;
	private String jugador;
	private boolean ganada;
	private boolean terminada;
	private Date fecha;

	public Partida() {

	}

	public Partida(int id, String jugador, boolean ganada, boolean terminada, Date fecha) {
		this.id = id;
		this.jugador = jugador;
		this.ganada = ganada;
		this.terminada = terminada;
		this.fecha = fecha;
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

	public boolean isGanada() {
		return ganada;
	}

	public void setGanada(boolean ganada) {
		this.ganada = ganada;
	}

	public boolean isTerminada() {
		return terminada;
	}

	public void setTerminada(boolean terminada) {
		this.terminada = terminada;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", jugador=" + jugador + ", ganada=" + ganada + ", terminada=" + terminada
				+ ", fecha=" + fecha + "]";
	}

}
