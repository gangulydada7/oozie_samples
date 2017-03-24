package com.abbvie.productvisibility.exception;


/**
 * Class to map application related exceptions
 * 
 * @author Duraiarasan
 *
 */

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ProductVisibilityAPIExceptionMapper implements ExceptionMapper<ProductVisibilityAPIException> {

	public Response toResponse(ProductVisibilityAPIException ex) {
		return Response.status(ex.getStatus())
				.entity(new ProductVisibilityAPIErrorMessage(ex))
				.type(MediaType.APPLICATION_JSON).
				build();
	}

}
