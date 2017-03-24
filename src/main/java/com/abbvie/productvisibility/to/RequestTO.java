package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class RequestTO implements Serializable{
	
	private UserRequestTO user;
	
	private UserTO saveUser;
	
    private SolrSearchRequest solrSearch;
    
    private FilterSearchRequest filterSearch;
        
    private String orderReferenceNo;
    
    private SubscribeSettingsTORequest subscribeSettingsReq;
    
	public UserRequestTO getUser() {
		return user;
	}

	public void setUser(UserRequestTO user) {
		this.user = user;
	}

	public UserTO getSaveUser() {
		return saveUser;
	}

	public void setSaveUser(UserTO saveUser) {
		this.saveUser = saveUser;
	}

	public SolrSearchRequest getSolrSearch() {
		return solrSearch;
	}

	public void setSolrSearch(SolrSearchRequest solrSearch) {
		this.solrSearch = solrSearch;
	}

	public FilterSearchRequest getFilterSearch() {
		return filterSearch;
	}

	public void setFilterSearch(FilterSearchRequest filterSearch) {
		this.filterSearch = filterSearch;
	}

	public String getOrderReferenceNo() {
		return orderReferenceNo;
	}

	public void setOrderReferenceNo(String orderReferenceNo) {
		this.orderReferenceNo = orderReferenceNo;
	}

	public SubscribeSettingsTORequest getSubscribeSettingsReq() {
		return subscribeSettingsReq;
	}

	public void setSubscribeSettingsReq(
			SubscribeSettingsTORequest subscribeSettingsReq) {
		this.subscribeSettingsReq = subscribeSettingsReq;
	}
	
	
}
	

