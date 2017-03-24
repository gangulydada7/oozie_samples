package com.abbvie.productvisibility.to;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserResponseTO extends BaseResponseTO implements Serializable{
	
	
	private UserTO userDetails;
	
	private List<UserTO> userList;
	
	private String responseMessage;
	
	public UserTO getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserTO userDetails) {
		this.userDetails = userDetails;
	}		

	public List<UserTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserTO> userList) {
		this.userList = userList;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}


}
	

