package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class StatusTO implements Serializable {


	private String statusID;

	private String statusName;

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	
}
