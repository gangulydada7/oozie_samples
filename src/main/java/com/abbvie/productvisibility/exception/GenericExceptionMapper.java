package com.abbvie.productvisibility.exception;


/**
 * Class to map application related exceptions
 * 
 * @author Duraiarasan
 *
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.abbvie.productvisibility.constants.ApplicationConstants;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
 

	
	public Response toResponse(Throwable ex) {
		
		ProductVisibilityAPIErrorMessage errorMessage = new ProductVisibilityAPIErrorMessage();		
		setHttpStatus(ex, errorMessage);
		errorMessage.setCode(ApplicationConstants.GENERIC_APP_ERROR_CODE);
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		errorMessage.setDeveloperMessage(errorStackTrace.toString());
		errorMessage.setLink(ApplicationConstants.SCPVAPI_URL);
				
		return Response.status(errorMessage.getStatus())
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();	
	}

	private void setHttpStatus(Throwable ex, ProductVisibilityAPIErrorMessage errorMessage) {
		if(ex instanceof WebApplicationException ) { //NICE way to combine both of methods, say it in the blog 
			errorMessage.setStatus(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
		}
	}
}

