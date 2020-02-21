package com.crisgon.apirest.model.mecanica;

public class Mano {
	
	private Jugada jugadaJugador;
	private Jugada jugadaMaquina;
	private Integer resultado;
	private Integer numero;

	public Mano(Jugada jugadaJugador, Jugada jugadaMaquina, Integer resultado, Integer numero) {
		this.jugadaJugador = jugadaJugador;
		this.jugadaMaquina = jugadaMaquina;
		this.resultado = resultado;
		this.numero = numero;
	}
	
	public Jugada getJugadaJugador() {
		return jugadaJugador;
	}
	
	public void setJugadaJugador(Jugada jugadaJugador) {
		this.jugadaJugador = jugadaJugador;
	}
	
	public Jugada getJugadaMaquina() {
		return jugadaMaquina;
	}
	
	public void setJugadaMaquina(Jugada jugadaMaquina) {
		this.jugadaMaquina = jugadaMaquina;
	}
	
	public Integer getResultado() {
		return resultado;
	}
	
	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
}
