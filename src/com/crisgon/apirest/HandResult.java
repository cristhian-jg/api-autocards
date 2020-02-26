package com.crisgon.apirest;

import com.crisgon.apirest.model.Carta;

/**
 * Clase que se encarga de llevar el resultado de la partida.
 * 
 * @author Cristhian González.
 *
 * 
 */
public class HandResult {

	private int marcadorJugador;
	private int marcadorCPU;

	private Carta cartaSeleccionadaCPU;

	public void aumentarMarcadorJugador() {
		marcadorJugador++;
	}

	public void aumentarMarcadorCPU() {
		marcadorCPU++;
	}

	public int getMarcadorJugador() {
		return marcadorJugador;
	}

	public void setMarcadorJugador(int marcadorJugador) {
		this.marcadorJugador = marcadorJugador;
	}

	public int getMarcadorCPU() {
		return marcadorCPU;
	}

	public void setMarcadorCPU(int marcadorCPU) {
		this.marcadorCPU = marcadorCPU;
	}

	public Carta getCartaSeleccionadaCPU() {
		return cartaSeleccionadaCPU;
	}

	public void setCartaSeleccionadaCPU(Carta cartaSeleccionadaCPU) {
		this.cartaSeleccionadaCPU = cartaSeleccionadaCPU;
	}

	@Override
	public String toString() {
		return "HandResult [marcadorJugador=" + marcadorJugador + ", marcadorCPU=" + marcadorCPU + "]";
	}

}
