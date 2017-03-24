package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class LocationTO implements Serializable {


	private String locationID;

	private String locationName;

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
	
}
