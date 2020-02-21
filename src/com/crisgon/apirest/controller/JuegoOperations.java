package com.crisgon.apirest.controller;

import java.util.ArrayList;
import java.util.Random;

import com.crisgon.apirest.libreria.RandomNoRepeat;
import com.crisgon.apirest.model.Carta;

public class JuegoOperations {

	private static final String TAG = "JuegoOperations";
	private static final int CARTAS_POR_MANO = 6;

	private ArrayList<Carta> cartasJugador;
	private ArrayList<Carta> cartasMaquina;

	private ArrayList<Integer> numerosAleatoriosNoRepetidos;

	public JuegoOperations(ArrayList<Carta> cartas) {
		repartir(cartas);
	}

	public void repartir(ArrayList<Carta> cartas) {

		int aleatorio;

		cartasJugador = new ArrayList<>();
		cartasMaquina = new ArrayList<>();
		numerosAleatoriosNoRepetidos = RandomNoRepeat.randomNoRepeat(12, cartas.size() - 1);

		for (int i = 0; i < numerosAleatoriosNoRepetidos.size(); i++) {

			aleatorio = numerosAleatoriosNoRepetidos.get(i);

			if (i < CARTAS_POR_MANO)
				cartasJugador.add(cartas.get(aleatorio));
			else
				cartasMaquina.add(cartas.get(aleatorio));
		}
	}

	public ArrayList<Carta> getCartasJugador() {
		return cartasJugador;
	}

	public ArrayList<Carta> getCartasMaquina() {
		return cartasMaquina;
	}
}
