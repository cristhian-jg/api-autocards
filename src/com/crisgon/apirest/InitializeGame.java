package com.crisgon.apirest;

import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crisgon.apirest.controller.CartaOperations;
import com.crisgon.apirest.controller.JugadorOperations;
import com.crisgon.apirest.controller.PartidasOperations;
import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;
import com.google.gson.Gson;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 */

@Path("/initializegame")
public class InitializeGame {

	private static final String TAG = "IntializeGame";

	/**
	 * Verifica que el usuario existe en la base de datos y que la contraseña
	 * introducida es correcta
	 * 
	 * @param Un jugador con su usuario y contraseña.
	 * @return Response.OK con la información del jugador.
	 * @return Response LOGIN FAIL si el jugador no existe o la contraseña no es
	 *         correcta
	 */
	@Path("login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Jugador jugador) {

		ArrayList<Jugador> jugadores;

		String nickname;
		String password;

		String json = "";

		jugadores = JugadorOperations.getAll();

		for (int i = 0; i < jugadores.size(); i++) {

			nickname = jugadores.get(i).getNickname();
			password = jugadores.get(i).getPassword();

			if (nickname.equals(jugador.getNickname()) && password.equals(jugador.getPassword())) {
				jugador = jugadores.get(i);
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}

		return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Login fail.").build();
	}

	// TODO Implementar una clase que registre usuarios.
	/**
	 * 
	 * @param Un jugador con usuario y contraseña.
	 * @return Response REGISTER FAIL si algo salió mal durante la operación.
	 */
	@Path("register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Jugador jugador) {

		return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Register fail.").build();
	}

	@Path("newgame/{idSession}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(@PathParam("idSession") String idSession) { // Podría ser sustituida por una clase Jugador.

		ArrayList<Partida> partidas;
		Jugador jugador;

		String nickname;
		boolean terminada;

		String json = "";

		partidas = PartidasOperations.getAll();
		jugador = JugadorOperations.getJugador(idSession);

		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getJugador().equals(jugador.getNickname()) && partidas.get(i).isTerminada()) {
				// Ya no hay partidas en marcha para el usuario y hay que devolver el id de la
				// partida.
				json = new Gson().toJson(partidas.get(i).getId());
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}

		// Debe devolver el id de la partida.
		return Response.status(Response.Status.OK).entity(json).build();
	}

	// Poner la partida como terminada
	@Path("reset/{idSession}/{idGame}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reset(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame) {

		Partida partida;
		String json = "";

		partida = PartidasOperations.getPartida(idGame);
		partida.setTerminada(true);

		PartidasOperations.updatePartida(partida);
		json = new Gson().toJson(partida);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/** Sortea quien comienza primero */
	@Path("raffle/{idSession}/{idGame}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response raffle(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame) {
		Jugador jugador;
		String json;
		Random random;
		int aleatorio;

		random = new Random();
		aleatorio = random.nextInt(1);

		switch (aleatorio) {
		case 0:
			jugador = JugadorOperations.getJugador(idSession);
			json = new Gson().toJson(jugador);
			return Response.status(Response.Status.OK).entity(json).build();
		case 1:
			return null;
		}
		return null;
	}

	@Path("playcard/{idSession}/{idGame}/{idCard}/{feature}/{hand}")
	@POST 
	@Produces(MediaType.APPLICATION_JSON)
	public Response playCard(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame,
			@PathParam("idCard") String idCard, @PathParam("feature") String feature, @PathParam("hand") String hand) {

		
		return null;
	}

	// @Path("ready")
	public void ready(String idSesion, int idGame) {

	}
}
