package br.com.getset.calendarchurch.teste;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
@Path("/ftocservice")
public class FtoCService {
 
	  @SuppressWarnings("unused")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoC() {//throws JSONException {
 
		//JSONObject jsonObject = new JSONObject(); retirada a dependencia json, comentada pom.xml
		Double fahrenheit = 98.24;
		Double celsius = (fahrenheit - 32)*5/9; 
		//jsonObject.put("F Value", fahrenheit); 
		//jsonObject.put("C Value", celsius);
 
		String result = "";// "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
 
	  @Path("{f}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius =  (f - 32)*5/9; 
		jsonObject.put("F Value", f); 
		jsonObject.put("C Value", celsius);
 
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
}