package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class OrderReferenceSearchResult implements Serializable {

	private String orderReferenceNo;
	private String materialID;
	private String description;
	private String batchNo;
	private String gTin;
	private String locationID;
	private String currentLocation;
	private String overAllStatus;
	private String mfgLineNo;
	public String getOrderReferenceNo() {
		return orderReferenceNo;
	}
	public void setOrderReferenceNo(String orderReferenceNo) {
		this.orderReferenceNo = orderReferenceNo;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getgTin() {
		return gTin;
	}
	public void setgTin(String gTin) {
		this.gTin = gTin;
	}
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getOverAllStatus() {
		return overAllStatus;
	}
	public void setOverAllStatus(String overAllStatus) {
		this.overAllStatus = overAllStatus;
	}
	public String getMfgLineNo() {
		return mfgLineNo;
	}
	public void setMfgLineNo(String mfgLineNo) {
		this.mfgLineNo = mfgLineNo;
	}
	
	
}
