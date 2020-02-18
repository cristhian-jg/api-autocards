package com.crisgon.apirest.model.mecanica;

import com.crisgon.apirest.Caracteristica;

public class Jugada {
	
	private String idSession;
	private int idGame;
	private String idCard;
	private Caracteristica feature;
	private int hand;
	
	public Jugada(String idSession, int idGame, String idCard, Caracteristica feature, int hand) {
		this.idSession = idSession;
		this.idGame = idGame;
		this.idCard = idCard;
		this.feature = feature;
		this.hand = hand;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Caracteristica getFeature() {
		return feature;
	}

	public void setFeature(Caracteristica feature) {
		this.feature = feature;
	}

	public int getHand() {
		return hand;
	}

	public void setHand(int hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return "Jugada [idSession=" + idSession + ", idGame=" + idGame + ", idCard=" + idCard + ", feature=" + feature
				+ ", hand=" + hand + "]";
	}
	
}
