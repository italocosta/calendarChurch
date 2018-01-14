package br.com.getset.calendarchurch.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.getset.calendarchurch.model.GroupNotifications;
import br.com.getset.calendarchurch.service.EventService;


@Path("/event")
@RequestScoped
@Named
public class EventRest{
	
	@Inject
	private EventService eService;
	
	@GET
	@Produces("application/json")
	public GroupNotifications atualizaIpRetornaChave(){
		return eService.getEventsShow();
		//return Response.status(201).entity(chave).build();
	}
}
