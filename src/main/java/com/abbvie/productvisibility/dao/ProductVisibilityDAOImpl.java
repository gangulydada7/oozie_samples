package com.abbvie.productvisibility.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.solr.client.solrj.SolrServerException;

import com.abbvie.productvisibility.constants.ApplicationConstants;
import com.abbvie.productvisibility.constants.SQLConstants;
import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.factory.HBaseConnectionFactory;
import com.abbvie.productvisibility.to.ExternalSystemStatus;
import com.abbvie.productvisibility.to.FilterSearchRequest;
import com.abbvie.productvisibility.to.LocationTO;
import com.abbvie.productvisibility.to.MasterTableProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.OrderReferenceDetails;
import com.abbvie.productvisibility.to.ProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.RequestTO;
import com.abbvie.productvisibility.to.SolrSearchRequest;
import com.abbvie.productvisibility.to.StatusTO;
import com.abbvie.productvisibility.to.SubscribeSettingsTORequest;
import com.abbvie.productvisibility.to.SystemTO;
import com.abbvie.productvisibility.to.UpdateProductVisibilityResponseTO;


public class ProductVisibilityDAOImpl implements ProductVisibilityDAO {

	@Override
	public ProductVisibilityResponseTO getOrderReferenceDetails(
			RequestTO requestTO)
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
		OrderReferenceDetails orderRefDetails = new OrderReferenceDetails();
		orderRefDetails.setOrderReferenceNo("342342342345");
		orderRefDetails.setMaterialID("04347625");
		orderRefDetails.setDescription("Humira Adailmunab");
		orderRefDetails.setBatchNo("(21)27200000002143");
		orderRefDetails.setGtin("30300074309");
		orderRefDetails.setLocationID("092323112");
		orderRefDetails.setCurrentLocation("APs, 1 waukegan rd, North Chicago, IL 60064");
		orderRefDetails.setGln("341238745689");
		orderRefDetails.setDeeFiftySix("1-004339-004-001-002");
		orderRefDetails.setOverAllStatus("In Progress");
		orderRefDetails.setImageID("00074937402");
		ExternalSystemStatus packagineLine = new ExternalSystemStatus();
		packagineLine.setName("Antares");
		packagineLine.setStatus("Completed");
		packagineLine.setCompletedDate("11/02/20116 10:23 am");
		packagineLine.setErrorMessage("");
		orderRefDetails.setPackagingLine(packagineLine);
		ExternalSystemStatus integrationHub = new ExternalSystemStatus();
		integrationHub.setName("TIBCO");
		integrationHub.setStatus("Completed");
		integrationHub.setCompletedDate("11/02/20116 10:20 am");
		integrationHub.setErrorMessage("");
		orderRefDetails.setIntegrationHub(integrationHub);
		ExternalSystemStatus serializationRepositry = new ExternalSystemStatus();
		serializationRepositry.setName("OER");
		serializationRepositry.setStatus("Failed");
		serializationRepositry.setCompletedDate("11/02/20116 10:20 am");
		serializationRepositry.setErrorMessage("Invalid Ids");
		orderRefDetails.setSerializationRepositry(serializationRepositry);
		pVResponseTO.setSuccess(true);
		pVResponseTO.setResponseMessage("Please refer the order reference details");
		pVResponseTO.setOrderRefDetails(orderRefDetails);
		return pVResponseTO;
	}
	
	@Override
	public ProductVisibilityResponseTO getSearchResult(
			RequestTO requestTO)
			throws ProductVisibilityAPIException, SolrServerException {
		/*String urlString = "http://localhost:8983/solr/gettingstarted";
		HttpSolrServer solr = new HttpSolrServer("http://10.74.230.150:8983/solr");
		SolrQuery query = new SolrQuery();
	    query.setQuery("ollectionhari_shard1_replica1");
	    query.addFilterQuery("ord_ref_nbr:100*","overall_status:Fail*");
	    //query.setFields("id","price","merchant","cat","store");
	    query.setStart(0);    
	    query.set("defType", "json");
	    System.out.println("############## 11111111111111111 ###############");
	    QueryResponse response = solr.query(query);
	    System.out.println("############## 2222222222222222222 ###############");
	    SolrDocumentList results = response.getResults();
	    System.out.println("############## 333333333333 ###############");
	    for (int i = 0; i < results.size(); ++i) {
	      System.out.println(results.get(i));
	    }*/
		ProductVisibilityResponseTO pVResponseTO = new ProductVisibilityResponseTO();
		if(requestTO == null) {			
			return ProductVisibilityAPIValidation.checkRequestParamNull(requestTO);
		}	
		SolrSearchRequest solrSearch = requestTO.getSolrSearch() == null ? null : requestTO.getSolrSearch();
		FilterSearchRequest filterSearch = requestTO.getFilterSearch() == null ? null : requestTO.getFilterSearch();
		if(solrSearch == null && filterSearch == null) {
			return ProductVisibilityAPIValidation.checkRequestParamNull(requestTO);
		}
		String orderReferenceNo = "";
		String locationID = "";
		String gTin = "";
		String materialID = "";
		String batchNo = "";
		String mfgStartDate = "";
		String mfgEndDate = "";	
		String overAllStatus = "";
		if(solrSearch != null) {
			orderReferenceNo = StringUtils.isEmpty(solrSearch.getOrderReferenceNo()) ? "" : solrSearch.getOrderReferenceNo();
			locationID = StringUtils.isEmpty(solrSearch.getLocationID()) ? "" : solrSearch.getLocationID();
			gTin = StringUtils.isEmpty(solrSearch.getgTin()) ? "" : solrSearch.getgTin();
			materialID = StringUtils.isEmpty(solrSearch.getMaterialID()) ? "" : solrSearch.getMaterialID();
			batchNo = StringUtils.isEmpty(solrSearch.getBatchNo()) ? "" : solrSearch.getBatchNo();
		}
		if(filterSearch != null) {
			mfgStartDate = StringUtils.isEmpty(filterSearch.getMfgStartDate()) ? "" : filterSearch.getMfgStartDate();
			mfgEndDate = StringUtils.isEmpty(filterSearch.getMfgEndDate()) ? "" : filterSearch.getMfgEndDate();
			overAllStatus = StringUtils.isEmpty(filterSearch.getOverAllStatus()) ? "" : filterSearch.getOverAllStatus();
		}
		return pVResponseTO;
	}
	
	@Override
	public UpdateProductVisibilityResponseTO updateUserSubscribeSettings(
			SubscribeSettingsTORequest subscribeSettingsTO)
			throws ProductVisibilityAPIException {
		UpdateProductVisibilityResponseTO subscribeSettingsResponse = new UpdateProductVisibilityResponseTO();		
		if (subscribeSettingsTO == null) {
			return ProductVisibilityAPIValidation.checkUpdateReqParam(false);
		}		
		String userNetworkID = StringUtils.isEmpty(subscribeSettingsTO.getUserNetworkID()) ? "" : subscribeSettingsTO.getUserNetworkID();
		String userRoleID = StringUtils.isEmpty(subscribeSettingsTO.getUserRoleID()) ? "" : subscribeSettingsTO.getUserRoleID();
		String userRoleName = StringUtils.isEmpty(subscribeSettingsTO.getUserRoleName()) ? "" : subscribeSettingsTO.getUserRoleName();
		String userName = StringUtils.isEmpty(subscribeSettingsTO.getUserName()) ? "" : subscribeSettingsTO.getUserName();
		String mfgLocationID = StringUtils.isEmpty(subscribeSettingsTO.getMfgLocationID()) ? "" : subscribeSettingsTO.getMfgLocationID();
		String packagingLine = StringUtils.isEmpty(subscribeSettingsTO.getPackagingLine()) ? "" : subscribeSettingsTO.getPackagingLine();
		String integrationHub = StringUtils.isEmpty(subscribeSettingsTO.getIntegrationHub()) ? "" : subscribeSettingsTO.getIntegrationHub();
		String serializationRepositry = StringUtils.isEmpty(subscribeSettingsTO.getSerializationRepositry()) ? "" : subscribeSettingsTO.getSerializationRepositry();
		
		return subscribeSettingsResponse;
	}
	
	
	@Override
	public MasterTableProductVisibilityResponseTO getSCPVStatus()
			throws ProductVisibilityAPIException {
		Connection connection = null;
		HTable table = null;
		ResultScanner resultScanner = null;
		List<StatusTO> listOfStatusTO = new ArrayList<StatusTO>();
		MasterTableProductVisibilityResponseTO scpvStatus = new MasterTableProductVisibilityResponseTO();
		try {
			// Instantiating Configuration class
			Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.STATUS_TABLE));				
			Scan scan = new Scan();			
			resultScanner = table.getScanner(scan); 
			StatusTO statusTO = null;
			for(Result result : resultScanner) {
				if ( null == result || result.isEmpty()) { 
					continue;
				}
				statusTO = new StatusTO();
				statusTO.setStatusID((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.STATUS_TABLE_COLFAMILY), Bytes.toBytes("statusID")))));
				statusTO.setStatusName((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.STATUS_TABLE_COLFAMILY), Bytes.toBytes("statusName")))));
				listOfStatusTO.add(statusTO);
			}
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);			
		}			
		if(CollectionUtils.isEmpty(listOfStatusTO)) {
			scpvStatus.setSuccess(false);
			scpvStatus.setResponseMessage("The SCPV Status table is empty");
			return scpvStatus;
		}
		scpvStatus.setStatus(listOfStatusTO);
		scpvStatus.setSuccess(true);
		scpvStatus.setResponseMessage("Please refer the SCPV Status table values");
		return scpvStatus;
	}
	
	@Override
	public MasterTableProductVisibilityResponseTO getSCPVLocation()
			throws ProductVisibilityAPIException {
		Connection connection = null;
		HTable table = null;
		ResultScanner resultScanner = null;
		List<LocationTO> listOfLocationTO = new ArrayList<LocationTO>();
		MasterTableProductVisibilityResponseTO scpvStatus = new MasterTableProductVisibilityResponseTO();
		try {
			// Instantiating Configuration class
			Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.LOCATION_TABLE));				
			Scan scan = new Scan();			
			resultScanner = table.getScanner(scan); 
			LocationTO locationTO = null;
			for(Result result : resultScanner) {
				if ( null == result || result.isEmpty()) { 
					continue;
				}
				locationTO = new LocationTO();
				locationTO.setLocationID((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.LOCATION_TABLE_COLFAMILY), Bytes.toBytes("locationID")))));
				locationTO.setLocationName((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.LOCATION_TABLE_COLFAMILY), Bytes.toBytes("locationName")))));				
				listOfLocationTO.add(locationTO);
			}
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);			
		}			
		if(CollectionUtils.isEmpty(listOfLocationTO)) {
			scpvStatus.setSuccess(false);
			scpvStatus.setResponseMessage("The SCPV Location table is empty");
			return scpvStatus;
		}
		scpvStatus.setLocation(listOfLocationTO);
		scpvStatus.setSuccess(true);
		scpvStatus.setResponseMessage("Please refer the SCPV Location table values");
		return scpvStatus;
	}
	
	@Override
	public MasterTableProductVisibilityResponseTO getSCPVSystem()
			throws ProductVisibilityAPIException {
		Connection connection = null;
		HTable table = null;
		ResultScanner resultScanner = null;
		List<SystemTO> listOfSystemTO = new ArrayList<SystemTO>();
		MasterTableProductVisibilityResponseTO scpvStatus = new MasterTableProductVisibilityResponseTO();
		try {
			// Instantiating Configuration class
			Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.SYSTEM_TABLE));				
			Scan scan = new Scan();			
			resultScanner = table.getScanner(scan); 
			SystemTO systemTO = null;
			for(Result result : resultScanner) {
				if ( null == result || result.isEmpty()) { 
					continue;
				}
				systemTO = new SystemTO();
				systemTO.setSystemID((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.SYSTEM_TABLE_COLFAMILY), Bytes.toBytes("systemID")))));
				systemTO.setSystemName((Bytes.toStringBinary(result.getValue(Bytes.toBytes(SQLConstants.SYSTEM_TABLE_COLFAMILY), Bytes.toBytes("systemName")))));
				listOfSystemTO.add(systemTO);
			}
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);			
		}			
		if(CollectionUtils.isEmpty(listOfSystemTO)) {
			scpvStatus.setSuccess(false);
			scpvStatus.setResponseMessage("The SCPV System table is empty");
			return scpvStatus;
		}
		scpvStatus.setSystem(listOfSystemTO);
		scpvStatus.setSuccess(true);
		scpvStatus.setResponseMessage("Please refer the SCPV System table values");
		return scpvStatus;
	}
}
