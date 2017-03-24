package com.abbvie.productvisibility.to;

import java.io.Serializable;

public class RoleMenuTO implements Serializable {

	private String name;
	private String createRole;
	private String updateRole;
	private String viewRole;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateRole() {
		return createRole;
	}
	public void setCreateRole(String createRole) {
		this.createRole = createRole;
	}
	public String getUpdateRole() {
		return updateRole;
	}
	public void setUpdateRole(String updateRole) {
		this.updateRole = updateRole;
	}
	public String getViewRole() {
		return viewRole;
	}
	public void setViewRole(String viewRole) {
		this.viewRole = viewRole;
	}
	
	
}
