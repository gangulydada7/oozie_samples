/**
 * 
 */
package com.abbvie.productvisibility.dao;

import org.apache.solr.client.solrj.SolrServerException;

import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.to.LocationTO;
import com.abbvie.productvisibility.to.MasterTableProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.RequestTO;
import com.abbvie.productvisibility.to.ProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.StatusTO;
import com.abbvie.productvisibility.to.SubscribeSettingsTORequest;
import com.abbvie.productvisibility.to.SystemTO;
import com.abbvie.productvisibility.to.UpdateProductVisibilityResponseTO;

/**
 * 
 * 
 */
public interface ProductVisibilityDAO {

	public abstract ProductVisibilityResponseTO getOrderReferenceDetails(
			RequestTO requestTO) throws ProductVisibilityAPIException;

	public abstract ProductVisibilityResponseTO getSearchResult(
			RequestTO requestTO) throws ProductVisibilityAPIException,
			SolrServerException;

	public abstract UpdateProductVisibilityResponseTO updateUserSubscribeSettings(
			SubscribeSettingsTORequest subscribeSettingsTO)
			throws ProductVisibilityAPIException;

	public abstract MasterTableProductVisibilityResponseTO getSCPVStatus()
			throws ProductVisibilityAPIException;

	public abstract MasterTableProductVisibilityResponseTO getSCPVLocation()
			throws ProductVisibilityAPIException;

	public abstract MasterTableProductVisibilityResponseTO getSCPVSystem()
			throws ProductVisibilityAPIException;
}
