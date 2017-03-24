package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class FilterSearchRequest implements Serializable {

	/*private String orderType;
	private String glnDescrption;
	private String glnExtensionNo;
	private String countryMarket;
	private String batchID;
	private String mfgLineNo;*/
	
	private String mfgStartDate;
	private String mfgEndDate;	
	private String overAllStatus;

	public String getMfgStartDate() {
		return mfgStartDate;
	}
	public void setMfgStartDate(String mfgStartDate) {
		this.mfgStartDate = mfgStartDate;
	}
	public String getMfgEndDate() {
		return mfgEndDate;
	}
	public void setMfgEndDate(String mfgEndDate) {
		this.mfgEndDate = mfgEndDate;
	}	
	public String getOverAllStatus() {
		return overAllStatus;
	}
	public void setOverAllStatus(String overAllStatus) {
		this.overAllStatus = overAllStatus;
	}

}
