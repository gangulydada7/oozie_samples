package com.abbvie.productvisibility.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleTO implements Serializable {

	private Integer roleId;

	private Integer status;

	private String name;

	private String isSecurityAdmin;
	
	private String isSuperUser;

	private String isCommenter;

	private String isReadOnly;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsSecurityAdmin() {
		return isSecurityAdmin;
	}

	public void setIsSecurityAdmin(String isSecurityAdmin) {
		this.isSecurityAdmin = isSecurityAdmin;
	}

	public String getIsSuperUser() {
		return isSuperUser;
	}

	public void setIsSuperUser(String isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public String getIsCommenter() {
		return isCommenter;
	}

	public void setIsCommenter(String isCommenter) {
		this.isCommenter = isCommenter;
	}

	public String getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	
	
}
