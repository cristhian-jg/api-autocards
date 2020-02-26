package com.crisgon.apirest.cruds;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crisgon.apirest.controller.ControladorJugador;
import com.crisgon.apirest.controller.ControladorPartidas;
import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;
import java.sql.Date;
import com.google.gson.Gson;

/**
 * 
 * Clase que contiene los endpoints para crear, leer, actualizar y eliminar
 * partidas de la base de datos.
 * 
 * @author Cristhian González.
 * 
 */

@Path("/partidas")
public class PartidasAPI {

	private static final String TAG = "PartidasAPI";

	/**
	 * [ENDPOINT] Permite crear una nueva partida.
	 * 
	 * @param id        @param jugador @param ganada
	 * @param terminada @param fecha
	 * @return
	 */
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(@FormParam("id") int id, @FormParam("jugador") String jugador,
			@FormParam("ganada") boolean ganada, @FormParam("terminada") boolean terminada,
			@FormParam("fecha") Date fecha) {

		Partida partida;
		String json;

		if (ControladorPartidas.addPartida(id, jugador, ganada, terminada, fecha)) {

			partida = new Partida(id, jugador, ganada, terminada, fecha);
			json = new Gson().toJson(partida);

			return Response.status(Response.Status.OK).entity(json).build();

		} else {

			return Response.status(400).build();

		}

	}

	/**
	 * [ENDPOINT] Permite obtener todas las partidas almacenadas en la base de datos
	 * haciendo una conversión a JSON mediante Gson.
	 * 
	 * @return respuesta positiva con el arreglo de todas las partidas disponibles
	 *         en JSON.
	 */
	@Path("/getgames")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPartidas() {

		ArrayList<Partida> partidas;
		String json;

		partidas = ControladorPartidas.getAll();
		json = new Gson().toJson(partidas);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * /** [ENDPOINT] Permite actualizar una estadistica
	 * 
	 * @param id
	 * @param jugador
	 * @param ganada
	 * @param terminada
	 * @param fecha
	 * @return
	 */
	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(@FormParam("id") int id, @FormParam("jugador") String jugador,
			@FormParam("ganada") boolean ganada, @FormParam("terminada") boolean terminada,
			@FormParam("fecha") Date fecha) {

		String json;

		try {
			ControladorPartidas.updatePartida(id, jugador, ganada, terminada, fecha);

			json = new Gson().toJson(ControladorPartidas.getPartida(id));
			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Update failed." + e.toString()).build();
		}
	}

	/**
	 * [ENDPOINT] Permite borrar una partida de la base de datos mediante su
	 * identificador.
	 * 
	 * @param id
	 * @return respuesta positiva si ha eliminado la estadistica.
	 * @return respuesta negativa si no ha eliminado la estadistica.
	 */
	@Path("/delete")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doDelete(@QueryParam("id") int id) {
		if (ControladorPartidas.deletePartida(id)) {

			return Response.status(Response.Status.OK).build();

		} else {

			return Response.status(400).build();

		}
	}

}
