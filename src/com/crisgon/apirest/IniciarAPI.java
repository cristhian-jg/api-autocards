package com.crisgon.apirest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.crisgon.apirest.controller.ControlJuego;
import com.crisgon.apirest.controller.JugadorOperations;
import com.crisgon.apirest.controller.PartidasOperations;
import com.crisgon.apirest.model.Carta;
import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;
import com.crisgon.apirest.model.mecanica.Juego;
import com.google.gson.Gson;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 */

@Path("/iniciar")
public class IniciarAPI {

	private static final String TAG = "IniciarAPI";

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
		return Response.status(401).entity(TAG + ": Login fail.").build();
	}

	@Path("login/{nickname}/{password}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("nickname") String nickname, @PathParam("password") String password) {

		ArrayList<Jugador> jugadores;
		Jugador jugador;

		String json = "";

		jugadores = JugadorOperations.getAll();

		for (int i = 0; i < jugadores.size(); i++) {

			nickname = jugadores.get(i).getNickname();
			password = jugadores.get(i).getPassword();

			if (nickname.equals(nickname) && password.equals(password)) {
				jugador = jugadores.get(i);
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}

		return Response.status(401).entity(TAG + ": Login fail.").build();
	}

	@Path("newgame/{idSession}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(@PathParam("idSession") String idSession) { // Podría ser sustituida por una clase Jugador.

		ArrayList<Partida> partidas = PartidasOperations.getAll();
		String json;

		for (int i = 0; i < partidas.size(); i++) {
			if (!partidas.get(i).getJugador().equals(idSession) && partidas.get(i).isTerminada()) {
				// Hay partidas en marcha para el usuario y por lo que devolvemos el idGame de
				// esta partida para terminarla posteriormente.
				json = new Gson().toJson(partidas.get(i).getId());
				return Response.status(Response.Status.OK).entity(json).build();
			} else {
				Date date = new Date(Calendar.getInstance().getTime().getTime());
				Partida partida = new Partida(idSession, false, false, date);
				PartidasOperations.addPartida(partida);
				int id = partidas.get(partidas.size() - 1).getId();
				json = new Gson().toJson(id);
				// Debe devolver el id de la nueva partida.
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}
		return Response.status(400).entity("Something went wrong...").build();
	}

	// En caso de que haya devuelto el ID de una partida sin terminar habra que
	// terminarla.
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

	/** Sortea quien comienza primero y se reparten las cartas */
	@Path("raffle/{idSession}/{idGame}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response raffle(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame) {

		ControlJuego control;
		Juego juego;

		ArrayList<Carta> cartasDisponibles = CartaOperations.getAll();
		ArrayList<Carta> cartasJugador;
		ArrayList<Carta> cartasMaquina;

		Jugador jugador;
		String json;
		Random random;
		int aleatorio;

		cartasJugador = new ArrayList<>();
		cartasMaquina = new ArrayList<>();

		random = new Random();
		aleatorio = random.nextInt(1);

		control = new ControlJuego();
		control.setCartas(cartasDisponibles);
		cartasJugador = control.repartir();
		cartasMaquina = control.repartir();

		switch (aleatorio) {
		case 0:
			juego = new Juego(idGame, cartasJugador, cartasMaquina, idSession);
			json = new Gson().toJson(juego);
			return Response.status(Response.Status.OK).entity(json).build();
		case 1:
			juego = new Juego(idGame, cartasJugador, cartasMaquina);
			json = new Gson().toJson(juego);
			return Response.status(Response.Status.OK).entity(json).build();
		}

		return Response.status(400).entity("Something went wrong...").build();
	}

	@Path("playcard/{idSession}/{idGame}/{idCard}/{feature}/{hand}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response playCard(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame,
			@PathParam("idCard") String idCard, @PathParam("feature") String feature, @PathParam("hand") String hand) {

		return null;
	}

	@Path("ready/{idSession}/{idGame}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response ready(@PathParam("idSession") String idSesion, @PathParam("idGame") int idGame) {
		return Response.status(Response.Status.OK).build();
	}
}
