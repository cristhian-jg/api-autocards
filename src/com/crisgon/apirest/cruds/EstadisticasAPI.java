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

import com.crisgon.apirest.controller.CartaOperations;
import com.crisgon.apirest.controller.EstadisticasOperations;
import com.crisgon.apirest.model.Estadistica;
import com.google.gson.Gson;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar estadisticas de la base de datos.
 */

@Path("/estadisticas")
public class EstadisticasAPI {

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(Estadistica estadistica) {
		if(EstadisticasOperations.addEstadistica(estadistica)) {
			String json = new Gson().toJson(estadistica);
			return Response.status(Response.Status.OK).entity(json).build();
		} else return Response.status(400).entity("Creation failed, the user already exists").build();
	}

	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRead() {
		ArrayList<Estadistica> estadisticas = EstadisticasOperations.getAll();
		String json = new Gson().toJson(estadisticas);
		return Response.status(Response.Status.OK).entity(json).build();
	}

	@Path("update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void doUpdate() {
	}

	@Path("delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("id") int id) {
		String json = new Gson().toJson(EstadisticasOperations.getEstadistica(id));
		if (EstadisticasOperations.deteleEstadistica(id)) {
			return Response.status(Response.Status.OK).entity(json).build();
		} else
			return Response.status(400).entity("Deletion failed.").build();
	}
}
