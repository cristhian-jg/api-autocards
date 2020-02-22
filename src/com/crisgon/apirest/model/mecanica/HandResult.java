package com.crisgon.apirest.model.mecanica;

public class HandResult {

	private static int marcadorJugador;
	private static int marcadorCPU;

	public static void aumentarMarcadorJugador() {
		marcadorJugador++;
	}

	public static void aumentarMarcadorCPU() {
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

	@Override
	public String toString() {
		return "HandResult [marcadorJugador=" + marcadorJugador + ", marcadorCPU=" + marcadorCPU + "]";
	}
	
	
	
}
