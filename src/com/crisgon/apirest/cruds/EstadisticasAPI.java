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

import com.crisgon.apirest.controller.ControladorCarta;
import com.crisgon.apirest.controller.ControladorEstadisticas;
import com.crisgon.apirest.model.Estadistica;
import com.google.gson.Gson;

/**
 * Clase que contiene los endpoints para crear, leer, actualizar y eliminar
 * estadisticas de la base de datos.
 *
 * @author Cristhian González.
 * 
 */

@Path("/estadisticas")
public class EstadisticasAPI {

	/**
	 * [ENDPOINT] Permite crear una nueva estadistica.
	 * 
	 * @param id      @param jugador @param partida
	 * @param ganadas @param perdidas @param empatadas
	 * 
	 * @return respuesta positiva con la estadistica creada en JSON.
	 */
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(@FormParam("jugador") String jugador, @FormParam("partida") int partida,
			@FormParam("ganadas") int ganadas, @FormParam("perdidas") int perdidas,
			@FormParam("empatadas") int empatadas) {

		Estadistica estadistica;
		String json;

		if (ControladorEstadisticas.addEstadistica(jugador, partida, ganadas, perdidas, empatadas)) {

			estadistica = new Estadistica(jugador, partida, ganadas, perdidas, empatadas);

			json = new Gson().toJson(estadistica);

			return Response.status(Response.Status.OK).entity(json).build();

		} else {

			return Response.status(400).build();

		}
	}

	/**
	 * [ENDPOINT] Permite obtener todas las estadisticas almacenadas en la base de
	 * datos haciendo una conversión a JSON mediante Gson.
	 * 
	 * @return respuesta positiva con el arreglo de todas las estadisticas
	 *         disponibles en JSON.
	 */
	@Path("/getstats")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticas() {

		ArrayList<Estadistica> estadisticas;
		String json;

		estadisticas = ControladorEstadisticas.getAll();
		json = new Gson().toJson(estadisticas);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * [ENDPOINT] Permite actualizar una estadistica de la base de datos.
	 * 
	 * @param id
	 * @param jugador
	 * @param partida
	 * @param ganadas
	 * @param perdidas
	 * @param empatadas
	 * @return
	 */
	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response doUpdate(@FormParam("id") int id, @FormParam("jugador") String jugador,
			@FormParam("partida") int partida, @FormParam("ganadas") int ganadas, @FormParam("perdidas") int perdidas,
			@FormParam("empatadas") int empatadas) {
		String json;

		try {
			ControladorEstadisticas.updateEstadistica(id, jugador, partida, ganadas, perdidas, empatadas);

			json = new Gson().toJson(ControladorEstadisticas.getEstadistica(id));

			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity("Update failed." + e.toString()).build();
		}
	}

	/**
	 * [ENDPOINT] Permite borrar una estadistica de la base de datos mediante su
	 * identificador.
	 * 
	 * @param id
	 * @return respuesta positiva si ha eliminado la estadistica.
	 * @return respuesta negativa si no ha eliminado la estadistica.
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doDelete(@QueryParam("id") int id) {

		if (ControladorEstadisticas.deteleEstadistica(id)) {

			return Response.status(Response.Status.OK).build();

		} else {

			return Response.status(400).entity("Deletion failed.").build();

		}

	}
}
