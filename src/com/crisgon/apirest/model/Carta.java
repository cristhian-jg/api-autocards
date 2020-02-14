package com.crisgon.apirest.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by @cristhian-jg on 14/02/2020
 *
 * @author Cristhian González.
 * 
 */

public class Carta implements Serializable {

	private String identificador;
	private String marca;
	private String modelo;
	private byte[] foto;
	private int motor;
	private int potencia;
	private int velocidad;
	private int cilindros;
	private int revoluciones;
	private double consumo;

	public Carta() {
	}

	public Carta(String identificador, String marca, String modelo, byte[] foto, int motor, int potencia, int velocidad,
			int cilindros, int revoluciones, double consumo) {
		this.identificador = identificador;
		this.marca = marca;
		this.modelo = modelo;
		this.foto = foto;
		this.motor = motor;
		this.potencia = potencia;
		this.velocidad = velocidad;
		this.cilindros = cilindros;
		this.revoluciones = revoluciones;
		this.consumo = consumo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public int getMotor() {
		return motor;
	}

	public void setMotor(int motor) {
		this.motor = motor;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}
	
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getCilindros() {
		return cilindros;
	}

	public void setCilindros(int cilindros) {
		this.cilindros = cilindros;
	}

	public int getRevoluciones() {
		return revoluciones;
	}

	public void setRevoluciones(int revoluciones) {
		this.revoluciones = revoluciones;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	@Override
	public String toString() {
		return "Carta [identificador=" + identificador + ", marca=" + marca + ", modelo=" + modelo + ", foto="
				+ Arrays.toString(foto) + ", motor=" + motor + ", potencia=" + potencia + ", cilindros=" + cilindros
				+ ", revoluciones=" + revoluciones + ", consumo=" + consumo + "]";
	}

}
