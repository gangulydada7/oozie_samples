package com.abbvie.productvisibility.valueobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ApplicationUsers {
	
	
	private String timeZone;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="country")
	private String country;
		
	@Column(name="user_Id")
	private Integer userId;
	
	@Column(name="created_date")
	private String createdOn;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="role_name")
	private String roleName;
	
	@Column(name="is_active")
	private String isActive;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="phone_no")
	private String phoneNo;

    @Column(name="nonabbvie_user")
	private String nonAbbVieUser;
	    
    @Column(name="time_stamp")
	private String timeStamp;
    
    @Column(name="last_login_in")
   	private String lastLoggedInDate;
    
    @Column(name="modified_by")
	private Integer modifiedBy;
    
    
    @Column(name="modified_date")
	private String modifiedOn;
    
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getNonAbbVieUser() {
		return nonAbbVieUser;
	}

	public void setNonAbbVieUser(String nonAbbVieUser) {
		this.nonAbbVieUser = nonAbbVieUser;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLastLoggedInDate() {
		return lastLoggedInDate;
	}

	public void setLastLoggedInDate(String lastLoggedInDate) {
		this.lastLoggedInDate = lastLoggedInDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
		
}
