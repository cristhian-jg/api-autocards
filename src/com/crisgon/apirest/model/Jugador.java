package com.crisgon.apirest.model;

import java.io.Serializable;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 */

public class Jugador implements Serializable {

	private String nickname;
	private String nombre;
	private String password;
	private int ganadas;
	private int perdidas;
	private int empatadas;

	public Jugador() {
	}

	public Jugador(String nickname, String nombre, String password, int ganadas, int perdidas, int empatadas) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.password = password;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
		this.empatadas = empatadas;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGanadas() {
		return ganadas;
	}

	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}

	public int getPerdidas() {
		return perdidas;
	}

	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}

	public int getEmpatadas() {
		return empatadas;
	}

	public void setEmpatadas(int empatadas) {
		this.empatadas = empatadas;
	}

	@Override
	public String toString() {
		return "Jugador [nickname=" + nickname + ", nombre=" + nombre + ", password=" + password + ", ganadas="
				+ ganadas + ", perdidas=" + perdidas + ", empatadas=" + empatadas + "]";
	}
	
	

}
