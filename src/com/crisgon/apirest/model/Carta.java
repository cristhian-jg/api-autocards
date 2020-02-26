package com.crisgon.apirest.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * Clase modelo que representa la tabla de cartas.
 * 
 * @author Cristhian González.
 * 
 */

public class Carta implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identificador;
	private String marca;
	private String modelo;
	private byte[] foto;
	private Integer motor;
	private Integer potencia;
	private Integer velocidad;
	private Integer cilindros;
	private Integer revoluciones;
	private Double consumo;

	public Carta() {
	}

	public Carta(String identificador, String marca, String modelo, byte[] foto, Integer motor, Integer potencia,
			Integer velocidad, Integer cilindros, Integer revoluciones, Double consumo) {
		super();
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

	public Integer getMotor() {
		return motor;
	}

	public void setMotor(Integer motor) {
		this.motor = motor;
	}

	public Integer getPotencia() {
		return potencia;
	}

	public void setPotencia(Integer potencia) {
		this.potencia = potencia;
	}

	public Integer getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Integer velocidad) {
		this.velocidad = velocidad;
	}

	public Integer getCilindros() {
		return cilindros;
	}

	public void setCilindros(Integer cilindros) {
		this.cilindros = cilindros;
	}

	public Integer getRevoluciones() {
		return revoluciones;
	}

	public void setRevoluciones(Integer revoluciones) {
		this.revoluciones = revoluciones;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	@Override
	public String toString() {
		return "Carta [identificador=" + identificador + ", marca=" + marca + ", modelo=" + modelo + ", foto="
				+ Arrays.toString(foto) + ", motor=" + motor + ", potencia=" + potencia + ", cilindros=" + cilindros
				+ ", revoluciones=" + revoluciones + ", consumo=" + consumo + "]";
	}

}
