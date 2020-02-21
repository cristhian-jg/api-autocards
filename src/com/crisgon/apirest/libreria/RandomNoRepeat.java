package com.crisgon.apirest.libreria;

import java.util.ArrayList;
import java.util.Random;

public class RandomNoRepeat {

   public static ArrayList<Integer> randomNoRepeat (int cantidad, int rangg) {
       ArrayList<Integer> numeros = new ArrayList<>();
       for (int i = 0; i < cantidad; i++) {
           numeros.add(generarRandom(numeros, rangg));
       }
       return numeros;
   }

   private static int generarRandom(ArrayList<Integer> numeros, int rango) {
       Random ra = new Random();
       int numero = ra.nextInt(rango);
       if (!numeros.contains(numero)) {
           return numero;
       }else {
           return generarRandom(numeros, rango);
       }
   }
	
}
