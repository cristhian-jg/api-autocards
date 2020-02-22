package com.crisgon.apirest.cruds;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crisgon.apirest.controller.ControladorJugador;
import com.crisgon.apirest.model.Jugador;
import com.google.gson.Gson;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar jugadores de la base de datos.
 */

@Path("/jugadores")
public class JugadoresAPI {

	private static final String TAG = "JugadoresAPI";

	/**
	 * [ENDPOINT] Permite crear un nuevo jugador.
	 * 
	 * @param nickname
	 * @param nombre
	 * @param password
	 * 
	 * @return respuesta positiva con el jugador creado en JSON.
	 */
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(@FormParam("nickname") String nickname, @FormParam("nombre") String nombre,
			@FormParam("password") String password) {

		Jugador jugador;
		String json;

		if (ControladorJugador.addJugador(nickname, nombre, password)) {

			jugador = new Jugador(nickname, nombre, password);

			json = new Gson().toJson(jugador);

			return Response.status(Response.Status.OK).entity(json).build();

		} else {

			return Response.status(400).build();

		}

	}

	/**
	 * [ENDPOINT] Permite obtener todos los jugadores almacenadas en la base de
	 * datos haciendo una conversión a JSON mediante Gson.
	 * 
	 * @return
	 */
	@Path("/getplayers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRead() {

		ArrayList<Jugador> jugadores;
		String json;

		jugadores = ControladorJugador.getAll();
		json = new Gson().toJson(jugadores);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	// TODO REHACER METODO PARA ACTUALIZAR JUGADOR.
	/**
	 * 
	 * @param jugador
	 * @param nickname
	 * @return
	 */
	@Path("{nickname}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doUpdate(Jugador jugador, @PathParam("nickname") String nickname) {

		String json;

		try {
			jugador.setNickname(nickname);
			ControladorJugador.updateJugador(jugador);

			json = new Gson().toJson(ControladorJugador.getJugador(nickname));
			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Update failed." + e.toString()).build();
		}
	}

	/**
	 * [ENDPOINT] Permite borrar un jugador de la base de datos mediante su
	 * identificador.
	 * 
	 * @param nickname
	 * @return respuesta positiva si ha eliminado el jugador.
	 * @return respuesta negativa si no ha elimnado el jugador. 
	 */
	@Path("/delete")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doDelete(@QueryParam("nickname") String nickname) {

		if (ControladorJugador.deleteJugador(nickname)) {

			return Response.status(Response.Status.OK).build();
			
		} else {
			
			return Response.status(400).entity("Deletion failed.").build();
			
		}
	}

}
