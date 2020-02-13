package com.crisgon.apirest.cruds;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar jugadores de la base de datos.
 */

@Path("/playerscrud")
public class PlayersCrud {

	/** Endpoint que crea un jugador en la base de datos*/
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doCreate() {
		
	}
	
	/** Endpoint que lee un jugador en la base de datos */
	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void doRead() {
		
	}
	
	/** Endpoint que actualiza un jugador en la base de datos */
	@Path("update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void doUpdate() {
		
	}
	
	/** Endpoint que elimina un jugador en la base de datos */
	@Path("delete")
	@DELETE
	public void doDelete() {
		
	}
	
}
