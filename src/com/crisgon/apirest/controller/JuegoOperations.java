package com.crisgon.apirest.controller;

import java.util.ArrayList;
import java.util.Random;

import com.crisgon.apirest.libreria.RandomNoRepeat;
import com.crisgon.apirest.model.Carta;

public class JuegoOperations {

	private static final String TAG = "JuegoOperations";
	private static final int CARTAS_POR_MANO = 6;

	private static ArrayList<Carta> cartasJugador;
	private static ArrayList<Carta> cartasMaquina;

	private static ArrayList<Integer> numerosAleatoriosNoRepetidos;

	public static void repartir(ArrayList<Carta> cartas) {

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

	public static ArrayList<Carta> getCartasJugador() {
		return cartasJugador;
	}

	public static ArrayList<Carta> getCartasMaquina() {
		return cartasMaquina;
	}
}
