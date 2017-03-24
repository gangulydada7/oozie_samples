package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class OrderReferenceDetails implements Serializable {
	
	private String orderReferenceNo;
	private String materialID;
	private String description;
	private String batchNo;
	private String gtin;
	private String locationID;
	private String currentLocation;
	private String overAllStatus;
	private String mfgLineNo;
	private String gln;
	private String deeFiftySix;
	private String imageID;
	private ExternalSystemStatus packagingLine;
	private ExternalSystemStatus integrationHub;
	private ExternalSystemStatus serializationRepositry;
	
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
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
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
	public String getGln() {
		return gln;
	}
	public void setGln(String gln) {
		this.gln = gln;
	}
	public String getDeeFiftySix() {
		return deeFiftySix;
	}
	public void setDeeFiftySix(String deeFiftySix) {
		this.deeFiftySix = deeFiftySix;
	}
	public ExternalSystemStatus getPackagingLine() {
		return packagingLine;
	}
	public void setPackagingLine(ExternalSystemStatus packagingLine) {
		this.packagingLine = packagingLine;
	}
	public ExternalSystemStatus getIntegrationHub() {
		return integrationHub;
	}
	public void setIntegrationHub(ExternalSystemStatus integrationHub) {
		this.integrationHub = integrationHub;
	}
	public ExternalSystemStatus getSerializationRepositry() {
		return serializationRepositry;
	}
	public void setSerializationRepositry(
			ExternalSystemStatus serializationRepositry) {
		this.serializationRepositry = serializationRepositry;
	}
	
	public String getImageID() {
		return imageID;
	}
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}	

	
}
