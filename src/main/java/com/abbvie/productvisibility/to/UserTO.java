package com.abbvie.productvisibility.to;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserTO implements Serializable {

	private Integer userId;

	private String fName;

	private String mName;
	
	private String lName;

	private String networkId;

	private String emailId;
	
	private Integer roleId;

	private Integer status;

	private String createdBy;
	
	private String createdDate;

	private String updatedBy;

	private String updatedDate;
	
	private String roleName;
	
	private String action;
	
	private String servicePwd;
	
	private String serviceToken;
	
	private String externalToken;
	
	private String timeZoneID;
	
	private String isEmailRequired;
	
	private String comments;
	
	private String upi;
	
	private List<RoleMenuTO> roleMenu;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getServicePwd() {
		return servicePwd;
	}

	public void setServicePwd(String servicePwd) {
		this.servicePwd = servicePwd;
	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	public String getExternalToken() {
		return externalToken;
	}

	public void setExternalToken(String externalToken) {
		this.externalToken = externalToken;
	}

	public String getTimeZoneID() {
		return timeZoneID;
	}

	public void setTimeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
	}

	public String getIsEmailRequired() {
		return isEmailRequired;
	}

	public void setIsEmailRequired(String isEmailRequired) {
		this.isEmailRequired = isEmailRequired;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<RoleMenuTO> getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(List<RoleMenuTO> roleMenu) {
		this.roleMenu = roleMenu;
	}

	public String getUpi() {
		return upi;
	}

	public void setUpi(String upi) {
		this.upi = upi;
	}
	
	
}
