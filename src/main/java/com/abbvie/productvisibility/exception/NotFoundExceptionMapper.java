package com.abbvie.productvisibility.exception;


/**
 * Class to map application related exceptions
 * 
 * @author Duraiarasan
 *
 */

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	public Response toResponse(NotFoundException ex) {
		return Response.status(ex.getResponse().getStatus())
				.entity(new ProductVisibilityAPIErrorMessage(ex))
				.type(MediaType.APPLICATION_JSON) //this has to be set to get the generated JSON 
				.build();
	}

}
