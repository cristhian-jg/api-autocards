package com.crisgon.apirest.model.mecanica;

import java.util.ArrayList;

import com.crisgon.apirest.model.Carta;

public class Juego {

	private int idGame;
	private ArrayList<Carta> cartasJugador;
	private ArrayList<Carta> cartasMaquina;
	private int numMano;
	
	
	
	public Juego(int idGame, ArrayList<Carta> cartasJugador, ArrayList<Carta> cartasMaquina, int numMano) {
		this.idGame = idGame;
		this.cartasJugador = cartasJugador;
		this.cartasMaquina = cartasMaquina;
		this.numMano = numMano;
	}
	
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
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
	
	public int getNumMano() {
		return numMano;
	}
	
	public void setNumMano(int numMano) {
		this.numMano = numMano;
	}

	@Override
	public String toString() {
		return "Juego [idGame=" + idGame + ", cartasJugador=" + cartasJugador + ", cartasMaquina=" + cartasMaquina
				+ ", numMano=" + numMano + "]";
	}
	
}
