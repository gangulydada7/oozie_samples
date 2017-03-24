package com.abbvie.productvisibility.dao;

import org.apache.commons.lang.StringUtils;

import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.to.UserRequestTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;


public class UserAPIValidation {


	/**
	 * 
	 * @param UserTO
	 * @return UserResponseTO
	 * @author mohamfj
	 */
	public static UserResponseTO validateUser(
			UserTO user) {
		UserResponseTO response = new UserResponseTO();
		if (user == null) {
			response.setSuccess(false);
			response
					.setResponseMessage("Please insert all the necessary values");
			return response;
		}
		String strAction = StringUtils.isEmpty(user.getAction()) ? ""
				: user.getAction().toString();
		if (!StringUtils.isEmpty(strAction)
				&& !(strAction.equalsIgnoreCase("insert") || strAction
						.equalsIgnoreCase("update"))) {
			response.setSuccess(false);
			response
					.setResponseMessage("Save Option is Incorrect ");
			return response;
		}
		String strNetworkId = StringUtils
				.isEmpty(user.getNetworkId()) ? "" : user.getNetworkId();

		String strfName = StringUtils.isEmpty(user.getfName()) ? "" : user.getfName();
		String strlName = StringUtils.isEmpty(user.getlName()) ? "" : user.getlName();
		String strmName = StringUtils.isEmpty(user.getmName()) ? "" : user.getmName();
		
		String strEmailId  = StringUtils.isEmpty(user.getEmailId()) ? "" :user.getEmailId();
					
		String strCreatedBy= StringUtils.isEmpty(user.getCreatedBy()) ? "": user.getCreatedBy();
		
		StringBuilder strbuildUserVal = new StringBuilder();
		strbuildUserVal
				.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal
				.append(StringUtils.isEmpty(strNetworkId) ? "networkId" : "");
		strbuildUserVal
				.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal
				.append(StringUtils.isEmpty(strfName) ? "fName"
						: "");
		strbuildUserVal
				.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal.append(StringUtils.isEmpty(strlName) ? "lName" : "");
		strbuildUserVal
				.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal.append(StringUtils.isEmpty(strmName) ? "mName"
				: "");
		strbuildUserVal
				.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal.append(StringUtils.isEmpty(strEmailId) ? "emailId"
				: "");
		strbuildUserVal
		.append(checkLastCharacter(strbuildUserVal) ? "," : "");
		strbuildUserVal.append(StringUtils.isEmpty(strCreatedBy) ? "createdBy"
		: "");
		
		if (strbuildUserVal.length() > 0) {
			response.setSuccess(false);
			response.setResponseMessage(strbuildUserVal
					.toString() + " can not be empty");
			return response;
		}
		
		if (!StringUtils.isEmpty(strNetworkId)
				&& !StringUtils.isEmpty(strfName)
				&& !StringUtils.isEmpty(strlName)
				&& !StringUtils.isEmpty(strmName)
				&& !StringUtils.isEmpty(strEmailId)){
			response.setSuccess(true);
			response.setResponseMessage("User update is in progress");
		}
		return response;
	}

	
	/**
	 * 
	 * @param noRequestParam
	 * @return response
	 */
	public static UserResponseTO getErrorMessage(boolean noRequestParam) {
		UserResponseTO response = new UserResponseTO();
		if (!noRequestParam) {
			response.setResponseMessage("User Details can not be empty");
		} else {
			response.setResponseMessage("Please check HBASE server connection");
		}
		response.setSuccess(false);
		return response;
	}

	/**
	 * 
	 * @param noRequestParam
	 * @return response
	 */
	public static UserResponseTO updateErrorMessage(
			boolean noRequestParam) {
		UserResponseTO response = new UserResponseTO();
		if (noRequestParam) {
			response.setResponseMessage("Please check HBASE server connection");
		} else {
			response.setResponseMessage("update parameters can not be empty");
		}
		response.setSuccess(false);
		return response;
	}
	/**
	 * 
	 * @param strbuildToleranceVal
	 * @return false/true
	 */
	private static boolean checkLastCharacter(StringBuilder strBuilder) {
		if (strBuilder.length() <= 0) {
			return false;
		}
		return strBuilder.toString()
				.substring(strBuilder.length() - 1)
				.equalsIgnoreCase(",") ? false : true;
	}

	/**
	 * getReadOnlyAndCommenter - is used to get read only roles user
	 * @param networkID
	 * @return userReadOnlyAndCommener
	 * @throws MetricsAPIException
	 */
	public static boolean getReadOnly(String networkID)
			throws ProductVisibilityAPIException {
		boolean userReadOnly = false;
		if (StringUtils.isEmpty(networkID)) {
			return userReadOnly;
		}
		UserDAOImpl userDAOImpl = new UserDAOImpl();
		UserRequestTO userRequestTO = new UserRequestTO();
		userRequestTO.setUserId(networkID);
		UserResponseTO userResponseTO = userDAOImpl
				.getUserLoginDetails(userRequestTO);
		// check if the user object success true
		// Scenario. if user does not exist.. then isSuccess will be false 
		// from getUserLoginDetails service
		if(userResponseTO.isSuccess()){
			if (userResponseTO != null) {
				UserTO userTO = userResponseTO.getUserDetails();
				if (userTO != null) {
					userReadOnly = true;
				}
			}
		}else{
			// set the read only as true so that user does 
			// not have privilege will be set.
			userReadOnly = true;
		}
		return userReadOnly;
	}
}
