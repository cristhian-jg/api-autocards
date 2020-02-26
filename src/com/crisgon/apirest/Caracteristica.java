package com.crisgon.apirest;

import java.io.Serializable;

/**
 * Clase no utilizada, tuve dificultades para usar ENUM atraves de Retrofit por
 * lo que ahora esta repesentado en String.
 */
public enum Caracteristica implements Serializable {
	MOTOR, POTENCIA, VELOCIDAD, CILINDROS, REVOLUCIONES, CONSUMO
}
