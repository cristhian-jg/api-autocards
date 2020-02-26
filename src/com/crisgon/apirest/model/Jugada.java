package com.crisgon.apirest.model;

import java.io.Serializable;

/**
 * Clase que utilizo para empaquetar la información de la jugada que hacer el
 * jugador o la CPU.
 * 
 * @author Cristhian González.
 *
 * 
 */

public class Jugada implements Serializable {

	private String idSession;
	private int idGame;
	private String idCard;
	private String feature;
	private int hand;

	public String getIdSession() {
		return idSession;
	}

	public int getIdGame() {
		return idGame;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getFeature() {
		return feature;
	}

	public int getHand() {
		return hand;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setHand(int hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return "Jugada{" + "idSession='" + idSession + '\'' + ", idGame=" + idGame + ", idCard='" + idCard + '\''
				+ ", feature='" + feature + '\'' + ", hand=" + hand + '}';
	}

}
