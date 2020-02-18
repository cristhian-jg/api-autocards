package com.crisgon.apirest.cruds;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar partidas de la base de datos.
 */

@Path("/partidas")
public class PartidasAPI {

	private static final String TAG = "PartidasAPI";

	/** Endpoint que crea una partida en la base de datos */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(Partida partida) {
		if (PartidasOperations.addPartida(partida)) {
			partida = PartidasOperations.getPartida(partida.getId());
			String json = new Gson().toJson(partida);
			return Response.status(Response.Status.OK).entity(json).build();
		} else
			return Response.status(400).entity("Creation failed, the user already exists").build();
	}

	/** Endpoint que lee una partida en la base de datos */
	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRead() {
		ArrayList<Partida> partidas = PartidasOperations.getAll();
		String json = new Gson().toJson(partidas);
		return Response.status(Response.Status.OK).entity(json).build();
	}

	/** Endpoint que actualiza una partida en la base de datos */
	@Path("update/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(Partida partida, @PathParam("id") int id) {

		String json;

		try {
			partida.setId(id);
			PartidasOperations.updatePartida(partida);

			json = new Gson().toJson(PartidasOperations.getPartida(id));
			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Update failed." + e.toString()).build();
		}
	}

	/** Endpoint que elimina una partida en la base de datos */
	@Path("delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("id") int id) {
		if (PartidasOperations.deletePartida(id)) {
			String json = new Gson().toJson(PartidasOperations.getPartida(id));
			return Response.status(Response.Status.OK).entity(json).build();
		} else
			return Response.status(400).entity("Deletion failed.").build();
	}

}
