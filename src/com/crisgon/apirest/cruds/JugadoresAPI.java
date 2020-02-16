package com.crisgon.apirest.cruds;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crisgon.apirest.controller.JugadorOperations;
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

@Path("/playerscrud")
public class JugadoresAPI {

	private static final String TAG = "JugadoresAPI";

	/** Endpoint que crea un jugador en la base de datos */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(Jugador jugador) {

		String json;

		JugadorOperations.addJugador(jugador);

		try {
			jugador = JugadorOperations.getJugador(jugador.getNickname());

			json = new Gson().toJson(jugador);

			return Response.status(Response.Status.OK).entity(json).build();
		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Creation failed, the user already exists" + e.toString()).build();
		}
	}

	/** Endpoint que lee un jugador en la base de datos */
	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRead() {

		ArrayList<Jugador> jugadores;
		String json;

		try {
			jugadores = JugadorOperations.getAll();

			json = new Gson().toJson(jugadores);

			return Response.status(Response.Status.OK).entity(json).build();
		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Error " + e.toString()).build();
		}
	}

	/** Endpoint que actualiza un jugador en la base de datos */
	@Path("update/{nickname}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(Jugador jugador, @PathParam("nickname") String nickname) {

		
		String json;

		try {
			jugador.setNickname(nickname);
			JugadorOperations.updateJugador(jugador);
			
			json = new Gson().toJson(JugadorOperations.getJugador(nickname));
			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Update failed." + e.toString()).build();

		}
	}

	/** Endpoint que elimina un jugador en la base de datos */
	@Path("delete/{nickname}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("nickname") String nickname) {

		Jugador jugador;
		String json;

		try {
			json = new Gson().toJson(JugadorOperations.getJugador(nickname));
			
			JugadorOperations.deleteJugador(nickname);

			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Deletion failed." + e.toString()).build();

		}

	}

}
