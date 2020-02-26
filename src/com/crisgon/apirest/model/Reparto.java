package com.crisgon.apirest.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Utilizo esta clase para empaquetar la información de las cartas de jugador y
 * las cartas de maquina para posteriormente recogerlas con el Retrofit.
 * 
 * @author Cristhian González.
 *
 * 
 */
public class Reparto implements Serializable {

	private ArrayList<Carta> cartasJugador;
	private ArrayList<Carta> cartasMaquina;

	public Reparto(ArrayList<Carta> cartasJugador, ArrayList<Carta> cartasMaquina) {
		this.cartasJugador = cartasJugador;
		this.cartasMaquina = cartasMaquina;
	}

	public ArrayList<Carta> getCartasJugador() {
		return cartasJugador;
	}

	public void setCartasJugador(ArrayList<Carta> cartasJugador) {
		this.cartasJugador = cartasJugador;
	}

	public ArrayList<Carta> getCartasMaquina() {
		return cartasMaquina;
	}

	public void setCartasMaquina(ArrayList<Carta> cartasMaquina) {
		this.cartasMaquina = cartasMaquina;
	}

}
