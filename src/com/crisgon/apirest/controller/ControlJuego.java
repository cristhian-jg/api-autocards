package com.crisgon.apirest.controller;

import java.util.ArrayList;
import java.util.Random;

import com.crisgon.apirest.model.Carta;

public class ControlJuego {

	private static final String TAG = "JuegoOperations";
	private static final int CARTAS_POR_MANO = 6;

	private ArrayList<Carta> cartas;
	private ArrayList<Carta> cartasRepartidas;

	public ArrayList<Carta> repartir() {
		ArrayList<Carta> cartasObtenidas;
		Carta carta;
		Random random;
		int aleatorio;
		
		cartasObtenidas = new ArrayList<>();
		random = new Random();
		
		for (int i = 0; i < CARTAS_POR_MANO; i++) {
			for (int j = 0; j < i; j++) {
				aleatorio = random.nextInt(cartas.size()-1);
				if (!cartas.get(aleatorio).getIdentificador().equals(cartasRepartidas.get(j).getIdentificador())) {
					cartasObtenidas.add(cartas.get(i));
					cartasRepartidas.add(cartas.get(i));
				} else i--;
			}
		}

		return cartasObtenidas;
	}
	
	public void setCartas(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}
}
