package com.abbvie.productvisibility.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import com.abbvie.productvisibility.constants.ApplicationConstants;
import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.security.util.SCPVRampart;
import com.abbvie.productvisibility.to.ProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.RequestTO;
import com.abbvie.productvisibility.to.UpdateProductVisibilityResponseTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;


public class ProductVisibilityAPIValidation {


	/**
	 * 
	 * @param table
	 * @return rowCount
	 * @throws IOException
	 */
	public static int getRowCount(HTable table) throws IOException {
		int rowCount = 0;
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			rowCount++;
		}
		return rowCount;
	}

	/**
	 * 
	 * @param resultScanner
	 * @param table
	 * @param config
	 * @author logandx
	 */
	public static void closeHBaseObjects(ResultScanner resultScanner,
			HTable table, Connection conn) {
		try {
			if (resultScanner != null) {
				resultScanner.close();
			}
			if (table != null) {
				table.close();
			}
			if (conn != null) {
				conn.close();
			}
			/**
			 * if(config != null) { config.clear(); }
			 **/
		} catch (Exception Ex) {
			Ex.getMessage();
		}
	}

	/**
	 * 
	 * @param noRequestParam
	 * @return response
	 */
	public static ProductVisibilityResponseTO checkRequestParamNull(RequestTO requestTO) {
		ProductVisibilityResponseTO response = new ProductVisibilityResponseTO();
		if (requestTO == null) {
			response.setResponseMessage("Request Parameters can not be empty");
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
	public static UpdateProductVisibilityResponseTO checkUpdateReqParam(boolean bool) {
		UpdateProductVisibilityResponseTO response = new UpdateProductVisibilityResponseTO();
		if (!bool) {
			response.setResponseMessage("Request Parameters can not be empty");
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
	public static UpdateProductVisibilityResponseTO updateErrorMessage(
			boolean noRequestParam) {
		UpdateProductVisibilityResponseTO response = new UpdateProductVisibilityResponseTO();
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
	 * @param isSuccess
	 * @param strAction
	 * @param strColumnFamily
	 * @return updateStatus
	 */
	public static String updateStatusMessage(boolean isSuccess,
			String strAction, String strColumnFamily) {
		String updateStatus = "";
		if (StringUtils.isEmpty(strAction)
				|| StringUtils.isEmpty(strColumnFamily)) {
			return updateStatus;
		}
		if (isSuccess) {
			if (strAction.equalsIgnoreCase("insert")) {
				updateStatus = strColumnFamily + " Inserted Successfully!!!";
			} else {
				updateStatus = strColumnFamily + " Updated Successfully!!!";
			}
		} else {
			if (strAction.equalsIgnoreCase("insert")) {
				updateStatus = "Cannot Insert " + strColumnFamily
						+ ". Metrics Already Exists!";
			} else {
				updateStatus = "Cannot Update " + strColumnFamily
						+ ". Metrics does not Exist!";
			}
		}
		return updateStatus;
	}

	public static List<Map<String, Object>> updateUserDisplayName(
			List<Map<String, Object>> responseMapList,
			UserResponseTO userResponseTO) {
		if (CollectionUtils.isEmpty(responseMapList)
				|| CollectionUtils.isEmpty(userResponseTO.getUserList())) {
			return responseMapList;
		}
		List<Map<String, Object>> updatedResMapList = new ArrayList<Map<String, Object>>();
		String strNetworkID = "";
		List<UserTO> userList = userResponseTO.getUserList();
		for (Map<String, Object> mapResponse : responseMapList) {
			if (MapUtils.isEmpty(mapResponse)) {
				updatedResMapList.add(mapResponse);
				continue;
			}
			if (!mapResponse.containsKey("createdBy")) {
				updatedResMapList.add(mapResponse);
				continue;
			}
			strNetworkID = StringUtils.isEmpty(mapResponse.get("createdBy")
					.toString()) ? "" : mapResponse.get("createdBy").toString();
			if (StringUtils.isEmpty(strNetworkID)) {
				updatedResMapList.add(mapResponse);
				continue;
			}
			for (UserTO userTO : userList) {
				if (userTO == null) {
					continue;
				}
				if (!StringUtils.isEmpty(userTO.getNetworkId())
						&& strNetworkID.equalsIgnoreCase(userTO.getNetworkId())) {
					mapResponse.put("userName", userTO.getfName() + " "
							+ userTO.getlName());
					break;
				}
			}
			updatedResMapList.add(mapResponse);
		}
		return updatedResMapList;
	}

	/**
	 * 
	 * @param token
	 * @param externalToken
	 * @return updateMetricsResponseTO
	 * @throws ProductVisibilityAPIException
	 */
	public static UpdateProductVisibilityResponseTO validateSessionTokens(String token,
			String externalToken) throws ProductVisibilityAPIException {
		UpdateProductVisibilityResponseTO updateProductVisibilityResponseTO = new UpdateProductVisibilityResponseTO();
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(externalToken)) {
			return nullCheckForAuthorizationHeader(token, externalToken);
		}
		try {
			String strStartTime = "";
			String strEndTime = "";
			String strExtToken = "";
			String deli = " ^^ ";
			if (token.indexOf(deli) > 1) {
				int firstDeli = token.indexOf(deli);
				int lastDeli = token.lastIndexOf(deli);
				strStartTime = firstDeli > 1 ? token.substring(0, firstDeli)
						: "";
				if (lastDeli > 1 && lastDeli > firstDeli) {
					strEndTime = token.substring(firstDeli + 3, lastDeli);
					strExtToken = token.substring(lastDeli + 3, token.length());
				}
			}
			String strKey = ApplicationConstants.key;
			String decryptedStartTime = SCPVRampart.decrypt(strStartTime,
					strKey);
			String decryptedEndTime = SCPVRampart
					.decrypt(strEndTime, strKey);
			String strExtDecrypt = SCPVRampart.decrypt(strExtToken, strKey);
			if (StringUtils.isEmpty(decryptedStartTime)
					|| StringUtils.isEmpty(decryptedEndTime)
					|| StringUtils.isEmpty(strExtDecrypt)) {
				StringBuilder strbuildTokenStatus = new StringBuilder();
				strbuildTokenStatus
						.append(StringUtils.isEmpty(decryptedStartTime)
								|| StringUtils.isEmpty(decryptedEndTime) ? "authorization, "
								: "");
				strbuildTokenStatus
						.append(StringUtils.isEmpty(strExtDecrypt) ? "authorization-external"
								: "");
				updateProductVisibilityResponseTO.setSuccess(false);
				updateProductVisibilityResponseTO
						.setResponseMessage("The Authentication header "
								+ strbuildTokenStatus.toString()
								+ " values incorrect");
				return updateProductVisibilityResponseTO;
			}
			long longStartTime = Long.parseLong(decryptedStartTime);
			long longEndTime = Long.parseLong(decryptedEndTime);
			Calendar date1 = Calendar.getInstance();
			long longCurrent = date1.getTimeInMillis();
			if (longCurrent < longEndTime && longCurrent > longStartTime
					&& !StringUtils.isEmpty(strExtDecrypt)
					&& strExtDecrypt.equalsIgnoreCase(externalToken)) {
				updateProductVisibilityResponseTO.setSuccess(true);
				updateProductVisibilityResponseTO
						.setResponseMessage("The Authentication is success");
				return updateProductVisibilityResponseTO;
			} else {
				updateProductVisibilityResponseTO.setSuccess(false);
				if (longCurrent > longEndTime || longCurrent < longStartTime) {
					updateProductVisibilityResponseTO
							.setResponseMessage("The Authentication session got expired");
				}
				if (!StringUtils.isEmpty(strExtDecrypt)
						&& !strExtDecrypt.equalsIgnoreCase(externalToken)) {
					updateProductVisibilityResponseTO
							.setResponseMessage("The Authentication header authorization-external is incorrect");
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			updateProductVisibilityResponseTO.setSuccess(false);
			updateProductVisibilityResponseTO
					.setResponseMessage("The Authentication token is invalid or session got expired");
		}
		return updateProductVisibilityResponseTO;
	}

	/**
	 * 
	 * @param token
	 * @param externalToken
	 * @return updateMetricsResponseTO
	 */
	public static UpdateProductVisibilityResponseTO nullCheckForAuthorizationHeader(
			String token, String externalToken) {
		UpdateProductVisibilityResponseTO updateProductVisibilityResponseTO = new UpdateProductVisibilityResponseTO();
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(externalToken)) {
			StringBuilder strbuildTokenStatus = new StringBuilder();
			strbuildTokenStatus
					.append(StringUtils.isEmpty(token) ? "authorization, " : "");
			strbuildTokenStatus
					.append(StringUtils.isEmpty(externalToken) ? "authorization-external"
							: "");
			updateProductVisibilityResponseTO.setSuccess(false);
			updateProductVisibilityResponseTO
					.setResponseMessage("The Authentication header "
							+ strbuildTokenStatus.toString()
							+ " can not be empty");
		} else {
			updateProductVisibilityResponseTO.setSuccess(true);
			updateProductVisibilityResponseTO
					.setResponseMessage("The Authentication header 'authorization' and 'authorization-external' is not empty");
		}
		return updateProductVisibilityResponseTO;
	}

}
