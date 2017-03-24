package com.abbvie.productvisibility.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


public class UpdateProductVisibilityResponseTO implements Serializable {

	private boolean success;
	private String responseMessage;

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
	
}
