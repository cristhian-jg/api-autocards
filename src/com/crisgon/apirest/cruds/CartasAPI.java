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
import com.crisgon.apirest.model.Carta;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *  Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar cartas de la base de datos.
 *
 * @author Cristhian González.
 * 
 *        
 */

@Path("/cartas")
@Api(value = "REST service para cartas")
public class CartasAPI {

	private static final String TAG = "CartasAPI";

	/**
	 * [ENDPOINT] Permite crear una nueva carta la cual podrá aparecer en las
	 * proximas partidas.
	 * 
	 * @param identificador @param marca @param modelo
	 * @param motor         @param potencia @param velocidad
	 * @param cilindros     @param revoluciones @param consumo
	 * 
	 * @return respuesta positiva con la carta creada en JSON.
	 * @return respuesta negativa si no ha creado la estadistica.
	 */
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doCreate(@FormParam("identificador") String identificador, @FormParam("marca") String marca,
			@FormParam("modelo") String modelo, @FormParam("motor") int motor, @FormParam("potencia") int potencia,
			@FormParam("velocidad") int velocidad, @FormParam("cilindros") int cilindros,
			@FormParam("revoluciones") int revoluciones, @FormParam("consumo") double consumo) {

		Carta carta;
		String json;

		if (ControladorCarta.addCarta(identificador, marca, modelo, null, motor, potencia, velocidad, cilindros,
				revoluciones, consumo)) {

			carta = new Carta(identificador, marca, modelo, null, motor, potencia, velocidad, cilindros, revoluciones,
					consumo);

			json = new Gson().toJson(carta);

			return Response.status(Response.Status.OK).entity(json).build();

		} else {

			return Response.status(400).build();

		}

	}

	/**
	 * [ENDPOINT] Permite obtener todas las cartas almacenadas en la base de datos
	 * haciendo una conversión a JSON mediante Gson.
	 * 
	 * @return respuesta positiva con el arreglo de todas las cartas disponibles en
	 *         JSON.
	 */
	@ApiOperation("Obten la lista de cartas")
	@Path("/getcards")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCartas() {

		ArrayList<Carta> cartas;
		String json;

		cartas = ControladorCarta.getAll();
		json = new Gson().toJson(cartas);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * [ENDPOINT] Permite obtener una carta de la base de datos mediante su
	 * identificador haciendo una conversión a JSON mediante Gson.
	 * 
	 * @param identificador
	 * @return una carta en formato JSON.
	 */
	@ApiOperation("Obten una carta")
	@Path("/getcard")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarta(@QueryParam("identificador") String identificador) {

		Carta carta;
		String json;

		carta = ControladorCarta.getCarta(identificador);
		json = new Gson().toJson(carta);

		return Response.status(Response.Status.OK).entity(json).build();
	}

	/**
	 * [ENDPOINT] Permite actualizar una carta.
	 * @param identificador
	 * @param marca
	 * @param modelo
	 * @param motor
	 * @param potencia
	 * @param velocidad
	 * @param cilindros
	 * @param revoluciones
	 * @param consumo
	 * @return
	 */
	@Path("/update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response doUpdate(@FormParam("identificador") String identificador, @FormParam("marca") String marca,
			@FormParam("modelo") String modelo, @FormParam("motor") int motor, @FormParam("potencia") int potencia,
			@FormParam("velocidad") int velocidad, @FormParam("cilindros") int cilindros,
			@FormParam("revoluciones") int revoluciones, @FormParam("consumo") double consumo) {
		String json;

		try {
			ControladorCarta.updateCarta(identificador, marca, modelo, null, motor, potencia, velocidad, cilindros,
					revoluciones, consumo);

			json = new Gson().toJson(ControladorCarta.getCarta(identificador));

			return Response.status(Response.Status.OK).entity(json).build();

		} catch (Exception e) {
			return Response.status(Response.Status.SEE_OTHER).entity(TAG + ": Update failed." + e.toString()).build();
		}
	}

	/**
	 * [ENDPOINT] Permite borrar una carta de la base de datos medainte su
	 * identificador.
	 * 
	 * @param identificador
	 * @return respuesta positiva si ha eliminado la carta.
	 * @return respuesta negativa si no ha eliminado la carta.
	 */
	@ApiOperation("Borra una carta")
	@Path("/delete")
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doDelete(@QueryParam("identificador") String identificador) {

		if (ControladorCarta.deleteCarta(identificador)) {

			return Response.status(Response.Status.OK).build();

		} else {

			return Response.status(400).build();

		}

	}
}
