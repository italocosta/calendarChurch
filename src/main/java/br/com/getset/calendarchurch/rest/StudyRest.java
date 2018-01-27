package br.com.getset.calendarchurch.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.getset.calendarchurch.model.ContentStudy;
import br.com.getset.calendarchurch.model.GroupStudies;
import br.com.getset.calendarchurch.service.StudyService;


@Path("/study")
@RequestScoped
@Named
public class StudyRest{
	
	@Inject
	private StudyService sService;
	
	@GET
	@Produces("application/json")
	public GroupStudies getStudies(){
		return sService.getStudiesCell();
	}
	@GET
	@Path("/all")
	@Produces("application/json")
	public GroupStudies getStudiesAll(){
		return sService.getAllStudies();
	}
	
	@GET
	@Produces("application/json")
	@Path("{id}")
	public ContentStudy getStudy(@PathParam("id") Long id ){
		return  sService.getStudyContent(id);
	}
}
