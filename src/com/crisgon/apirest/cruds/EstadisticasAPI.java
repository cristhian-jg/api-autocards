package com.crisgon.apirest.cruds;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by @cristhian-jg on 13/02/2020
 *
 * @author Cristhian González.
 * 
 *         Clase que contiene los endpoints para crear, leer, actualizar y
 *         eliminar estadisticas de la base de datos.
 */

@Path("/statisticscrud")
public class EstadisticasAPI {

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doCreate() {

	}

	@Path("read")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void doRead() {

	}

	@Path("update")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void doUpdate() {

	}

	@Path("delete")
	@DELETE
	public void doDelete() {

	}
}
