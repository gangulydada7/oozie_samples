package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class SolrSearchRequest implements Serializable {

	private String orderReferenceNo;
	private String locationID;
	private String gTin;
	private String materialID;
	private String batchNo;
	
	public String getOrderReferenceNo() {
		return orderReferenceNo;
	}
	public void setOrderReferenceNo(String orderReferenceNo) {
		this.orderReferenceNo = orderReferenceNo;
	}
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getgTin() {
		return gTin;
	}
	public void setgTin(String gTin) {
		this.gTin = gTin;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	
}
