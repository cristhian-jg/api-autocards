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
import com.crisgon.apirest.model.Carta;
import com.google.gson.Gson;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar cartas de la base de datos.
 */

@Path("/cartas")
public class CartasAPI {

	private static final String TAG = "CartasAPI";
	
	/** Endpoint que crea una carta en la base de datos */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(Carta carta) {

		String json;
		
		CartaOperations.addCarta(carta);
		
		try {
			carta = CartaOperations.getCarta(carta.getIdentificador());
			json = new Gson().toJson(carta);
			
			return Response.status(Response.Status.OK).entity(json).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Creation failed, the user already exists" + e.toString()).build();
		}
		
	}

	/** Endpoint que lee una carta en la base de datos */
	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doRead() {

		ArrayList<Carta> cartas;
		String json;

		try {
			
			cartas = CartaOperations.getAll();
			json = new Gson().toJson(cartas);
			return Response.status(Response.Status.OK).entity(json).build();
		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Error " + e.toString()).build();
		}
	}

	/** Endpoint que actualiza una carta en la base de datos */
	@Path("update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void doUpdate() {

	}

	/** Endpoint que elimina una carta en la base de datos */
	@Path("delete/{identificador}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response doDelete(@PathParam("identificador") String identificador) {

		Carta carta;
		String json;
		
		try {
			json = new Gson().toJson(CartaOperations.getCarta(identificador));
			
			CartaOperations.deleteCarta(identificador);
			
			return Response.status(Response.Status.OK).entity(json).build();
		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Deletion failed." + e.toString()).build();
		}
	}
}
