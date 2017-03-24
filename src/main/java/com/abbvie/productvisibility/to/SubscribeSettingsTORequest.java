package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class SubscribeSettingsTORequest  implements Serializable {

	private String userNetworkID;
	private String userRoleID;
	private String userRoleName;
	private String userName;
	private String mfgLocationID;
	private String packagingLine;
	private String integrationHub;
	private String serializationRepositry;
	public String getUserNetworkID() {
		return userNetworkID;
	}
	public void setUserNetworkID(String userNetworkID) {
		this.userNetworkID = userNetworkID;
	}
	public String getUserRoleID() {
		return userRoleID;
	}
	public void setUserRoleID(String userRoleID) {
		this.userRoleID = userRoleID;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMfgLocationID() {
		return mfgLocationID;
	}
	public void setMfgLocationID(String mfgLocationID) {
		this.mfgLocationID = mfgLocationID;
	}
	public String getPackagingLine() {
		return packagingLine;
	}
	public void setPackagingLine(String packagingLine) {
		this.packagingLine = packagingLine;
	}
	public String getIntegrationHub() {
		return integrationHub;
	}
	public void setIntegrationHub(String integrationHub) {
		this.integrationHub = integrationHub;
	}
	public String getSerializationRepositry() {
		return serializationRepositry;
	}
	public void setSerializationRepositry(String serializationRepositry) {
		this.serializationRepositry = serializationRepositry;
	}
	
	
	
}
