package com.crisgon.apirest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crisgon.apirest.controller.CartaOperations;
import com.crisgon.apirest.controller.JuegoOperations;
import com.crisgon.apirest.controller.JugadorOperations;
import com.crisgon.apirest.controller.PartidasOperations;
import com.crisgon.apirest.model.Carta;
import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;
import com.crisgon.apirest.model.mecanica.Juego;
import com.crisgon.apirest.model.mecanica.Jugada;
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
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("nickname") String nickname, @FormParam("password") String password) {

		ArrayList<Jugador> jugadores;
		Jugador jugador;

		String json = "";

		jugadores = JugadorOperations.getAll();

		for (int i = 0; i < jugadores.size(); i++) {
			if (nickname.equals(jugadores.get(i).getNickname()) && password.equals(jugadores.get(i).getPassword())) {
				jugador = jugadores.get(i);
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}
		return Response.status(401).entity(TAG + ": Login fail.").build();
	}

	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("nombre") String nombre, @FormParam("nickname") String nickname, @FormParam("password") String password) {
		
		ArrayList<Jugador> jugadores;
		Jugador jugador;
		boolean existe;
		
		String json = "";
		jugadores = JugadorOperations.getAll();
		existe = false;
		
		for (int i = 0; i < jugadores.size(); i++) {
			if (nickname.equals(jugadores.get(i).getNickname())) {
				existe = true;
			}
		}
		
		if (!existe) {
			jugador = new Jugador(nombre, nickname, password);
			if (JugadorOperations.addJugador(jugador)) {
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}
		return Response.status(400).entity(TAG + "Registration fail.").build();
	}
	
	@Path("newgame/{idSession}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(@PathParam("idSession") String idSession) { // Podría ser sustituida por una clase Jugador.

		ArrayList<Partida> partidas = PartidasOperations.getAll();
		String json;
		boolean existe = false;

		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getJugador().equals(idSession) && !partidas.get(i).isTerminada()) {
				// Hay partidas en marcha para el usuario y por lo que devolvemos el idGame de
				// esta partida para terminarla posteriormente.
				existe = true;
				System.out.print("newGame EXISTE y no esta terminado");
				System.out.print("num " + partidas.get(i).getJugador());
				json = new Gson().toJson(partidas.get(i).getId());
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}

		if (!existe) {
			System.out.print("newGame no EXISTE");
			int idGame = partidas.get(partidas.size() - 1).getId() + 1;
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			Partida partida = new Partida(idGame, idSession, false, false, date);
			PartidasOperations.addPartida(partida);

			json = new Gson().toJson(idGame);
			// Debe devolver el id de la nueva partida.
			System.out.println(partida.toString());
			return Response.status(Response.Status.OK).entity(json).build();
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
	@Path("raffle")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response raffle(@QueryParam("idSession") String idSession, @QueryParam("idGame") int idGame) {

		JuegoOperations operations;
		Juego juego;

		System.out.print("sesion "+idSession);
		System.out.print("game "+idGame);
		
		ArrayList<Carta> cartas = CartaOperations.getAll();
		ArrayList<Carta> cartasJugador;
		ArrayList<Carta> cartasMaquina;

		Jugador jugador;
		String json;
		Random random;
		int aleatorio;

		cartasJugador = new ArrayList<>();
		cartasMaquina = new ArrayList<>();

		operations = new JuegoOperations(cartas);

		random = new Random();
		aleatorio = random.nextInt(1);

		cartasJugador = operations.getCartasJugador();
		cartasMaquina = operations.getCartasMaquina();

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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response playCard(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame,
			@PathParam("idCard") String idCard, @PathParam("feature") Caracteristica feature,
			@PathParam("hand") int hand) {
		Jugada jugada = new Jugada(idSession, idGame, idCard, feature, hand);
		String json = new Gson().toJson(jugada);
		return Response.status(Response.Status.OK).entity(json).build();
	}

	@Path("ready/{idSession}/{idGame}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ready(@PathParam("idSession") String idSesion, @PathParam("idGame") int idGame) {
		return Response.status(Response.Status.OK).build();
	}

//	@Path("check/")
//	@POST()
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response check(@PathParam("") String string) {
//		
//		return null;
//	}

	@Path("estadisticas/{idSession}/{idGame}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStats(@PathParam("idSession") String idSession, @PathParam("idGame") int idGame) {

		return null;
	}
}
