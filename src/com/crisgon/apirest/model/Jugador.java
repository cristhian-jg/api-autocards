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

	public Jugador() {
	}

	public Jugador(String nickname, String nombre, String password) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.password = password;
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

	@Override
	public String toString() {
		return "Jugador [nickname=" + nickname + ", nombre=" + nombre + ", password=" + password + "]";
	}
	
	

}
