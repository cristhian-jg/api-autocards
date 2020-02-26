package com.crisgon.apirest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.crisgon.apirest.controller.ControladorCarta;
import com.crisgon.apirest.controller.ControladorEstadisticas;
import com.crisgon.apirest.controller.JuegoOperations;
import com.crisgon.apirest.controller.ControladorJugador;
import com.crisgon.apirest.controller.ControladorPartidas;
import com.crisgon.apirest.libreria.RandomNoRepeat;
import com.crisgon.apirest.model.Reparto;
import com.crisgon.apirest.model.CPUSelection;
import com.crisgon.apirest.model.Carta;
import com.crisgon.apirest.model.Estadistica;
import com.crisgon.apirest.model.Jugada;
import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;
import com.google.gson.Gson;

/**
 * 
 * Clase que contiene los endpoints que controlan el juego.
 * 
 * @author Cristhian González.
 * 
 */

@Path("/init")
public class IniciarAPI {

	private static final String TAG = "IniciarAPI";

	private static JuegoOperations operaciones = new JuegoOperations();

	/**
	 * Verifica que el usuario/jugador existe en la base de datos y que la
	 * contraseña introducida es correcta
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

		jugadores = ControladorJugador.getAll();

		for (int i = 0; i < jugadores.size(); i++) {
			if (nickname.equals(jugadores.get(i).getNickname()) && password.equals(jugadores.get(i).getPassword())) {
				jugador = jugadores.get(i);
				json = new Gson().toJson(jugador);
				return Response.status(Response.Status.OK).entity(json).build();
			}
		}
		return Response.status(401).entity(TAG + ": Login fail.").build();
	}

	/**
	 * Registra a un nuevo usuario/jugador en la base de datos comprobando que le
	 * nickname introducido no exista ya en la base de datos.
	 * 
	 * @param nombre
	 * @param nickname
	 * @param password
	 * @return
	 */
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("nombre") String nombre, @FormParam("nickname") String nickname,
			@FormParam("password") String password) {

		ArrayList<Jugador> jugadores;
		Jugador jugador;
		boolean existe;

		String json = "";
		jugadores = ControladorJugador.getAll();
		existe = false;

		for (int i = 0; i < jugadores.size(); i++) {
			if (nickname.equals(jugadores.get(i).getNickname())) {
				existe = true;
			}
		}

		if (!existe) {
			if (ControladorJugador.addJugador(nombre, nickname, password)) {

				jugador = new Jugador(nombre, nickname, password);

				json = new Gson().toJson(jugador);

				return Response.status(Response.Status.OK).entity(json).build();
			}
		}

		return Response.status(400).build();
	}

	/**
	 * Comprueba que el usuario no tiene partidas en marcha y opera según el
	 * resultado que obtenga.
	 * 
	 * @param idSession (equivale al nickname del jugador)
	 * @return respuesta positiva 202 en caso de que el usuario tenga una partida en
	 *         marcha.
	 * @return respuesta positiva 200 en caso de que el usuario no tenga partidas en
	 *         marcha.
	 */
	@Path("/newgame")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newGame(@FormParam("idSession") String idSession) {

		ArrayList<Partida> partidas = ControladorPartidas.getAll();
		Partida partida;
		String json;
		boolean existe = false;

		for (int i = 0; i < partidas.size(); i++) {
			if (partidas.get(i).getJugador().equals(idSession) && !partidas.get(i).isTerminada()) {
				// Hay partidas en marcha para el usuario y por lo que devolvemos el idGame de
				// esta partida para terminarla posteriormente.
				existe = true;
				json = new Gson().toJson(partidas.get(i));
				return Response.status(202).entity(json).build();
			}
		}

		if (!existe) {

			int idGame = partidas.get(partidas.size() - 1).getId() + 1;

			Date date = new Date(Calendar.getInstance().getTime().getTime());

			if (ControladorPartidas.addPartida(idGame, idSession, false, false, date)) {

				partida = new Partida(idGame, idSession, false, false, date);

				json = new Gson().toJson(partida);

				return Response.status(Response.Status.OK).entity(json).build();
			}

		}
		return Response.status(400).build();
	}

	/**
	 * Permite terminar las partidas de un jugador.
	 * 
	 * @param idSession
	 * @param idGame
	 * 
	 * @return respuesta positiva con la partida actualizada/terminada.
	 */
	@Path("/reset")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reset(@FormParam("idSession") String idSession, @FormParam("idGame") int idGame) {

		Partida partida;
		String json = "";

		partida = ControladorPartidas.getPartida(idGame);
		partida.setTerminada(true);

		ControladorPartidas.updatePartida(partida.getId(), partida.getJugador(), partida.isGanada(),
				partida.isTerminada(), partida.getFecha());
		json = new Gson().toJson(partida);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * Sortea el primer turno y opera según quien empiece primero, además reparte
	 * las cartas a cada jugador.
	 * 
	 * @param idSession
	 * @param idGame
	 * 
	 * @return respuesta positiva 200 si empieza el jugador.
	 * @return respuesta positiva 202 si empieza la CPU.
	 * @return respuesta negativa 400 si algo salió mal.
	 */
	@Path("/raffle")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response raffle(@FormParam("idSession") String idSession, @FormParam("idGame") int idGame) {

		ArrayList<Carta> cartas = ControladorCarta.getAll();
		ArrayList<Carta> cartasJugador;
		ArrayList<Carta> cartasMaquina;

		Jugador jugador;
		String json;
		Random random;
		int aleatorio;
		int numMano;

		operaciones.raffle(cartas);

		cartasJugador = operaciones.getCartasJugador();
		cartasMaquina = operaciones.getCartasMaquina();

		operaciones.setHand(1);

		Reparto barajas = new Reparto(cartasJugador, cartasMaquina);

		json = new Gson().toJson(barajas);

		switch (operaciones.sortear()) {
		case 0:
			// Si elige el jugador respuesta 200.
			return Response.status(Response.Status.OK).entity(json).build();
		case 1:
			// Si elige la CPU respuesta 202.
			return Response.status(202).entity(json).build();
		}

		return Response.status(400).build();
	}

	/**
	 * Se encarga de recibir la jugada del jugador, decidir la jugada de la CPU y
	 * comprobar los resultados.
	 * 
	 * @param idSession
	 * @param idGame
	 * @param idCard
	 * @param feature
	 * @param hand
	 * @return Devuelve el resultado de la jugada
	 */
	@Path("/playcard")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response playCard(@FormParam("idSession") String idSession, @FormParam("idGame") int idGame,
			@FormParam("idCard") String idCard, @FormParam("feature") String feature, @FormParam("hand") int hand) {
		HandResult result = new HandResult();

		// Si se completan las manos lo vuelve a poner a 0
		if (hand > 6) {
			result.setMarcadorCPU(0);
			result.setMarcadorJugador(0);
		}

		// Hace que la CPU elija una carta de sus restantes
		Carta carta = operaciones.cpuEligeCarta();

		// Se juegan las cartas y devuelve un resultado
		result = operaciones.playCard(idSession, idGame, idCard, feature, hand);
		result.setCartaSeleccionadaCPU(carta);
		operaciones.isTurnoJugador(true);

		return Response.status(Response.Status.OK).entity(result).build();
	}

	/**
	 * Indica que es el turno de la CPU y tiene que elegir una caracteristica.
	 * 
	 * @param idSession
	 * @param idGame
	 * @return Respuesta positiva
	 */
	@Path("/ready")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ready(@FormParam("idSession") String idSession, @FormParam("idGame") int idGame) {

		System.out.print("ready");

		operaciones.isTurnoJugador(false);
		String caracteristicaSeleccionada = operaciones.featureSelect();

		CPUSelection selection = new CPUSelection(caracteristicaSeleccionada);

		String json = new Gson().toJson(selection);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * 
	 * @param idSession
	 * @param idGame
	 * @return Devuelve las estadisticas de la ultima partida.
	 */
	@Path("/getstats")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStats(@QueryParam("idSession") String idSession, @QueryParam("idGame") int idGame) {

		ArrayList<Estadistica> estadisticas;
		Estadistica estadistica;

		estadisticas = ControladorEstadisticas.getAll();

		for (int i = 0; i < estadisticas.size(); i++) {
			if (estadisticas.get(i).getJugador().equals(idSession) && estadisticas.get(i).getPartida() == idGame) {
				estadistica = estadisticas.get(i);
			}
		}

		String json = new Gson().toJson(estadisticas);
		return Response.status(Response.Status.OK).entity(json).build();
	}
}
