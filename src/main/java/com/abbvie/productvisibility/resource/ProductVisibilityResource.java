package com.abbvie.productvisibility.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;

import com.abbvie.productvisibility.dao.ProductVisibilityAPIValidation;
import com.abbvie.productvisibility.dao.ProductVisibilityDAO;
import com.abbvie.productvisibility.dao.ProductVisibilityDAOImpl;
import com.abbvie.productvisibility.dao.UserAPIValidation;
import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.security.util.UserEndpoint;
import com.abbvie.productvisibility.to.FilterSearchRequest;
import com.abbvie.productvisibility.to.LocationTO;
import com.abbvie.productvisibility.to.MasterTableProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.ProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.RequestTO;
import com.abbvie.productvisibility.to.SolrSearchRequest;
import com.abbvie.productvisibility.to.StatusTO;
import com.abbvie.productvisibility.to.SubscribeSettingsTORequest;
import com.abbvie.productvisibility.to.SystemTO;
import com.abbvie.productvisibility.to.UpdateProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;
import com.google.inject.Inject;

@Path("rest/scpv")
public class ProductVisibilityResource {

	@Inject
	UserEndpoint userEndpoint;

	@Context
	private HttpServletRequest request;

	/**
	 * 
	 * @return
	 */
	@GET
	@Path("/load")
	public String get() {
		JerseyConfig config = new JerseyConfig();
		config.reloadProps();
		return config.properties.toString();
	}	

	/**
	 * This service provides a token for insert/update service access for 30
	 * minutes
	 * 
	 * @param requestTO
	 * @return UserResponseTO
	 * @throws ProductVisibilityAPIException
	 */
	@POST
	@Path("authentication")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserResponseTO getAuthenticationToken(RequestTO requestTO)
			throws ProductVisibilityAPIException {
		if (requestTO == null) {
			return UserAPIValidation.updateErrorMessage(false);
		}
		UserTO userTO = requestTO.getSaveUser();
		if (userTO == null) {
			return UserAPIValidation.updateErrorMessage(false);
		}
		if (userEndpoint == null) {
			userEndpoint = new UserEndpoint();
		}
		return userEndpoint.authenticateUser(requestTO);
	}
	
	/**
	 * 
	 * @param requestTO
	 * @return
	 * @throws ProductVisibilityAPIException
	 * @throws SolrServerException
	 */
	@POST
	@Path("solrsearch")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ProductVisibilityResponseTO getSearchResult(RequestTO requestTO)
			throws ProductVisibilityAPIException, SolrServerException {
		ProductVisibilityResponseTO pVResponseTO = new ProductVisibilityResponseTO();
		/*if(requestTO == null) {			
			return ProductVisibilityAPIValidation.checkRequestParamNull(requestTO);
		}	
		FilterSearchRequest filterSearch = requestTO.getFilterSearch() == null ? null : requestTO.getFilterSearch();
		SolrSearchRequest solrSearch = requestTO.getSolrSearch() == null ? null : requestTO.getSolrSearch();
		if(solrSearch == null && filterSearch == null) {
			return ProductVisibilityAPIValidation.checkRequestParamNull(requestTO);
		}*/
		ProductVisibilityDAO productVisibilityDAO = new ProductVisibilityDAOImpl();
		pVResponseTO = productVisibilityDAO.getSearchResult(requestTO);
		return pVResponseTO;
	}
	
	/**
	 * 
	 * @param requestTO
	 * @return
	 * @throws ProductVisibilityAPIException
	 */
	@POST
	@Path("orderreferencedetails")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ProductVisibilityResponseTO getOrderReferenceDetails(RequestTO requestTO)
			throws ProductVisibilityAPIException {
		ProductVisibilityResponseTO pVResponseTO = new ProductVisibilityResponseTO();
		if(requestTO == null) {			
			return ProductVisibilityAPIValidation.checkRequestParamNull(requestTO);
		}
		String orderRefNo = StringUtils.isEmpty(requestTO.getOrderReferenceNo()) ? "" : requestTO.getOrderReferenceNo();
		if(StringUtils.isEmpty(orderRefNo)) {
			pVResponseTO.setSuccess(false);
			pVResponseTO.setResponseMessage("Order Reference Number can not be empty");
			return pVResponseTO;
		}
		ProductVisibilityDAO productVisibilityDAO = new ProductVisibilityDAOImpl();
		pVResponseTO = productVisibilityDAO.getOrderReferenceDetails(requestTO);
		return pVResponseTO;
	}

	/**
	 * 
	 * @return
	 * @throws ProductVisibilityAPIException
	 */
	@GET
	@Path("getscpvstatus")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public MasterTableProductVisibilityResponseTO getSCPVStatus()
			throws ProductVisibilityAPIException {
		ProductVisibilityDAO productVisibilityDAO = new ProductVisibilityDAOImpl();
		return productVisibilityDAO.getSCPVStatus();
	}
	
	/**
	 * 
	 * @return
	 * @throws ProductVisibilityAPIException
	 */
	@GET
	@Path("getscpvlocation")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public MasterTableProductVisibilityResponseTO getSCPVLocation()
			throws ProductVisibilityAPIException {
		ProductVisibilityDAO productVisibilityDAO = new ProductVisibilityDAOImpl();
		return productVisibilityDAO.getSCPVLocation();
	}
	
	/**
	 * 
	 * @return
	 * @throws ProductVisibilityAPIException
	 */
	@GET
	@Path("getscpvsystem")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public MasterTableProductVisibilityResponseTO getSCPVSystem()
			throws ProductVisibilityAPIException {
		ProductVisibilityDAO productVisibilityDAO = new ProductVisibilityDAOImpl();
		return productVisibilityDAO.getSCPVSystem();
	}
}
