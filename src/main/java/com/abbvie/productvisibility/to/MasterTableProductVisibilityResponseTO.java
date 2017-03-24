package com.abbvie.productvisibility.to;

import java.io.Serializable;
import java.util.List;

public class MasterTableProductVisibilityResponseTO implements Serializable {

	private boolean success;
	private String responseMessage;
	private List<StatusTO> status;
	private List<LocationTO> location;
	private List<SystemTO> system;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<StatusTO> getStatus() {
		return status;
	}

	public void setStatus(List<StatusTO> status) {
		this.status = status;
	}

	public List<LocationTO> getLocation() {
		return location;
	}

	public void setLocation(List<LocationTO> location) {
		this.location = location;
	}

	public List<SystemTO> getSystem() {
		return system;
	}

	public void setSystem(List<SystemTO> system) {
		this.system = system;
	}


	
}
