package com.abbvie.productvisibility.security.util;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.abbvie.productvisibility.constants.ApplicationConstants;
import com.abbvie.productvisibility.dao.UserDAO;
import com.abbvie.productvisibility.dao.UserDAOImpl;
import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.to.RequestTO;
import com.abbvie.productvisibility.to.UserRequestTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;

public class UserEndpoint {

	@Inject
	private SimpleKeyGenerator keyGenerator;

	public UserResponseTO authenticateUser(RequestTO requestTO)
			throws ProductVisibilityAPIException {
		UserTO userTO = requestTO.getSaveUser();
		String networkID = userTO.getNetworkId();
		String password = userTO.getServicePwd();
		String externalToken = userTO.getExternalToken();
		UserResponseTO userResponse = new UserResponseTO();
		StringBuilder userTOValues = new StringBuilder();
		userTOValues.append(checkLastCharacter(userTOValues) ? "," : "");
		userTOValues.append(StringUtils.isEmpty(networkID) ? "networkID" : "");
		userTOValues.append(checkLastCharacter(userTOValues) ? "," : "");
		userTOValues.append(StringUtils.isEmpty(password) ? "password" : "");
		userTOValues.append(checkLastCharacter(userTOValues) ? "," : "");
		userTOValues
				.append(StringUtils.isEmpty(externalToken) ? "externalToken"
						: "");
		userTOValues.append(checkLastCharacter(userTOValues) ? "," : "");
		if (userTOValues.length() > 1) {
			userResponse.setSuccess(false);
			userResponse.setResponseMessage(userTOValues.toString()
					+ " can not be empty");
			return userResponse;
		}

		// Authenticate the user using the credentials provided
		UserRequestTO userRequestTO = new UserRequestTO();
		userRequestTO.setUserId(networkID);
		requestTO.setUser(userRequestTO);
		UserResponseTO response = authenticate(networkID, password, requestTO);
		if (response == null || !response.isSuccess()) {
			return userResponse;
		}
		try {
			// Issue a token for the user
			// token = issueToken();
			final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
			Calendar date1 = Calendar.getInstance();
			long longCurrent = date1.getTimeInMillis();
			int maxSession = Integer.parseInt(ApplicationConstants.SERVICE_SESSION_TIME);
			Date afterAddingMaxSession = new Date(longCurrent
					+ (maxSession * ONE_MINUTE_IN_MILLIS));
			long longAfterThirty = afterAddingMaxSession.getTime();
			StringBuilder tokenBuilder = new StringBuilder();
			String strKey = ApplicationConstants.key;
			tokenBuilder.append(SCPVRampart.encrypt(
					String.valueOf(longCurrent), strKey));
			tokenBuilder.append(" ^^ ");
			tokenBuilder.append(SCPVRampart.encrypt(
					String.valueOf(longAfterThirty), strKey));
			tokenBuilder.append(" ^^ ");
			tokenBuilder.append(SCPVRampart.encrypt(externalToken, strKey));
			UserTO userDetails = response.getUserDetails();
			if (userDetails != null) {
				userDetails.setServiceToken(tokenBuilder.toString());
				userDetails.setExternalToken(externalToken);
				userResponse
						.setResponseMessage("Please use this valid service token");
				userResponse.setSuccess(true);
				userResponse.setUserDetails(userDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("####### Exception ##### " + e.getMessage());
		}
		return userResponse;
	}

	private UserResponseTO authenticate(String networkID, String password,
			RequestTO requestTO) throws ProductVisibilityAPIException {
		UserResponseTO response = new UserResponseTO();
		if (StringUtils.isEmpty(networkID) || StringUtils.isEmpty(password)) {
			response.setResponseMessage("User's network ID or password can not be empty");
			response.setSuccess(false);
			return response;
		}
		UserDAO userDAO = new UserDAOImpl();
		response = userDAO.getUserLoginDetails(requestTO.getUser());
		return response;
	}

	private String issueToken() {
		/**
		 * if (keyGenerator == null) { keyGenerator = new SimpleKeyGenerator();
		 * } Key key = keyGenerator.generateKey();
		 * System.out.println("####### key ##### "+key.toString()); if(uriInfo
		 * != null) { final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
		 * Calendar date = Calendar.getInstance(); long t =
		 * date.getTimeInMillis(); Date afterAddingThirtyMins = new Date(t + (30
		 * * ONE_MINUTE_IN_MILLIS)); String jwtToken =
		 * Jwts.builder().setSubject(networkID)
		 * .setIssuer(uriInfo.getAbsolutePath().toString()) .setIssuedAt(new
		 * Date()).setExpiration(afterAddingThirtyMins)
		 * .signWith(SignatureAlgorithm.HS512, key).compact();
		 * System.out.println("####### jwtToken ##### "+jwtToken); return
		 * jwtToken; } else { return key.toString(); }
		 **/
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		int length = rand.nextInt((99 - 10) + 1) + 10;
		for (int i = length; i > 0; i -= 12) {
			int n = min(12, abs(i));
			sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36),
					n, '0'));
		}
		return sb.toString();

	}

	/**
	 * public boolean checkExternalToken(String externalToken) {
	 * if(StringUtils.isEmpty(externalToken)) { return false; } HttpSession
	 * session = request.getSession(); String sessionToken =
	 * session.getAttribute("externalToken").toString();
	 * if(!StringUtils.isEmpty(sessionToken) &&
	 * externalToken.equalsIgnoreCase(sessionToken)) { return true; } else {
	 * return false; } }
	 * 
	 * public boolean checkServiceToken(String serviceToken) {
	 * if(StringUtils.isEmpty(serviceToken)) { return false; } HttpSession
	 * session = request.getSession(); String sessionToken =
	 * session.getAttribute("serviceToken").toString();
	 * if(!StringUtils.isEmpty(sessionToken) &&
	 * serviceToken.equalsIgnoreCase(sessionToken)) { return true; } else {
	 * return false; } }
	 */
	/**
	 * 
	 * @param strbuildToleranceVal
	 * @return false/true
	 * @author logandx
	 */
	public static boolean checkLastCharacter(StringBuilder strbuildToleranceVal) {
		if (strbuildToleranceVal.length() <= 0) {
			return false;
		}
		return strbuildToleranceVal.toString()
				.substring(strbuildToleranceVal.length() - 1)
				.equalsIgnoreCase(",") ? false : true;
	}
}
