package com.crisgon.apirest.libreria;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que permite generar numeros aleatorios sin que se repitan.
 * 
 * @author Cristhian González.
 *
 */

public class RandomNoRepeat {

	/**
	 * Crea un nuevo ArrayList de numeros enteros no repetidos utilinzado el metodo
	 * generar random.
	 * 
	 * @param cantidad
	 * @param rango
	 * @return
	 */
	public static ArrayList<Integer> randomNoRepeat(int cantidad, int rango) {
		ArrayList<Integer> numeros = new ArrayList<>();
		for (int i = 0; i < cantidad; i++) {
			numeros.add(generarRandom(numeros, rango));
		}
		return numeros;
	}

	/**
	 * Genera numeros aleatorios teniendo en cuenta no añadir un numero que ya
	 * contenga el array list, si esto se da vuelve a invocarse a el mismo hasta
	 * tener el array completo.
	 * 
	 * @param numeros
	 * @param rango
	 * @return
	 */
	private static int generarRandom(ArrayList<Integer> numeros, int rango) {
		Random ra = new Random();
		int numero = ra.nextInt(rango);
		if (!numeros.contains(numero)) {
			return numero;
		} else {
			return generarRandom(numeros, rango);
		}
	}

}
