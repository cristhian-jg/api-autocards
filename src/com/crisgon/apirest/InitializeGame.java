package com.crisgon.apirest;

import javax.ws.rs.Path;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 */

@Path("/initializegame")
public class InitializeGame {

	// @Path("newgame")
	public void newGame(String idSesion) {

	}

	// @Path("reset")
	public void reset(String idSesion, int idGame) {

	}

	// @Path("raffle")
	public void raffle(String idSesion, int idGame) {

	}

	// @Path("playcard")
	public void playCard(String idSesion, int idGame, String idCard, String feature, String hand) {

	}

	// @Path("ready")
	public void ready(String idSesion, int idGame) {

	}
}
