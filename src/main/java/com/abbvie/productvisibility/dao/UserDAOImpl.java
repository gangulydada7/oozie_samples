package com.abbvie.productvisibility.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.procedure2.util.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.Filter; 
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.CompareFilter; 
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.client.Connection;

import com.abbvie.productvisibility.constants.ApplicationConstants;
import com.abbvie.productvisibility.constants.SQLConstants;
import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.factory.HBaseConnectionFactory;
import com.abbvie.productvisibility.to.RoleMenuTO;
import com.abbvie.productvisibility.to.UserRequestTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;
import com.abbvie.productvisibility.util.ResultsetMapper;

public class UserDAOImpl implements UserDAO  {
	
	/**
	 *@see com.abbvie.productvisibility.dao.UserDAO#getUserLoginDetails
	 *     (com.abbvie.prodcutvisibility.to.UserResponseTO)
	 *@param userRequestTO
	 *@return UserRequestTO
	 *@throws ProductVisibilityAPIException
	 *@author mohamfj
	 *Method to retrieve the login user details from sccm:users table
	 */
	public UserResponseTO getUserLoginDetails(
			UserRequestTO userRequestTO) throws ProductVisibilityAPIException{
		ResultScanner resultScanner = null;
		UserResponseTO response = new UserResponseTO();
		UserTO user = null;
		List<UserTO> userList = null;
		Connection connection = null;
		HTable table = null;
		try {

			// Instantiating Configuration class
			Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.USER_TABLE));
			SingleColumnValueFilter userFilter = null;
			// Search the users table for input networkId
			if(!StringUtils.isEmpty(userRequestTO.getUserId())){
				userFilter = new SingleColumnValueFilter(
						Bytes.toBytes(ApplicationConstants.USER_COLUMN_FAMILY),
						Bytes.toBytes("networkId"), CompareOp.EQUAL,
						Bytes.toBytes(userRequestTO.getUserId().toLowerCase()));
				Scan scan = new Scan();
				scan.addFamily(Bytes
						.toBytes(ApplicationConstants.USER_COLUMN_FAMILY));				
				if(userFilter!=null) {
					scan.setFilter(userFilter);
				}
				resultScanner = table.getScanner(scan);
				for (final Result result : resultScanner) {
					// Add to the response Map only if column Family is present in
					// result.
					ResultsetMapper<UserTO> rsm = new ResultsetMapper<UserTO>();
					userList = rsm.mapResultToObject(result, UserTO.class);
				}
				if(!CollectionUtils.isEmpty(userList)){
					user = userList.get(0);
					UserTO userRoles=null;
					//System.out.println("user roles.."+user.getRoleId());
					// Get the corresponding user roles from roles table
					if(user!=null & user.getRoleId()!=null){
						userRoles = getUserRoles(user.getRoleId());
					}
					// Set the retrieved roles to the user object
					if(userRoles!=null)
					{
						user.setRoleName(userRoles.getRoleName());
						user.setRoleMenu(userRoles.getRoleMenu());
					}
					response.setUserDetails(user);
					response.setSuccess(true);
					response.setResponseMessage(ApplicationConstants.SUCCESS_MESSAGE);
				}
				else
				{
					response.setSuccess(false);
					response.setResponseMessage(ApplicationConstants.NO_RECORDS_FOUND_MESSAGE);
				}
			}
			else
			{
				response.setSuccess(false);
				response.setResponseMessage("userId cannot be empty");
			}
				
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);
		}
		
		return response;
	}
	
	/**
	 * @param roleId
	 * @return RoleTO
	 * @throws ProductVisibilityAPIException
	 * Method to retrieve the user roles from sccm:roles table 
	 */
	private UserTO getUserRoles(Integer roleId) throws ProductVisibilityAPIException{
		
		UserTO userRoles = new UserTO();
		ResultScanner resultScanner = null;
		List<UserTO> roleList = null;
		Connection connection = null;
		HTable table = null;
		try {

			// Instantiating Configuration class
			//Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			/*TableName TABLE_NAME = TableName
					.valueOf(SQLConstants.ROLE_TABLE);
			table = new HTable(config, TABLE_NAME);*/
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.ROLE_TABLE));
			// Add the metrics Filter			
			SingleColumnValueFilter roleFilter = null;
			//System.out.println("role id.."+roleId);
			if(roleId>0)
			{
				roleFilter = new SingleColumnValueFilter(
						Bytes.toBytes(ApplicationConstants.ROLE_COLUMN_FAMILY),
						Bytes.toBytes("roleId"), CompareOp.EQUAL,
						Bytes.toBytes(roleId+"")); // convert Integer to String 
												   //so that in byte array compare input data
				Scan scan = new Scan();
				scan.addFamily(Bytes
						.toBytes(ApplicationConstants.ROLE_COLUMN_FAMILY));
				if(roleFilter!=null) {
					scan.setFilter(roleFilter);
				}
				int numberOfMenuColumn = SQLConstants.TOTAL_MENU;
				if(numberOfMenuColumn > 0) {
					String menuColumnName = SQLConstants.MENU_COLUMN_NAME;
					for(int intI = 1; intI < numberOfMenuColumn; intI++) {
						scan.addFamily(Bytes
								.toBytes(menuColumnName+intI));
					}
				}
				resultScanner = table.getScanner(scan);
				String roleColFamily = "role";
				RoleMenuTO roleMenu = null;
				List<RoleMenuTO> listOfRoleMenu = new ArrayList();
				for (final Result result : resultScanner) {
					userRoles.setRoleId(Integer.parseInt(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("roleId")))));
					userRoles.setRoleName(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("name"))));
					userRoles.setCreatedBy(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("createdBy"))));
					userRoles.setCreatedDate(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("createdDate"))));
					userRoles.setUpdatedBy(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("updatedBy"))));
					userRoles.setUpdatedDate(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("updatedDate"))));
					userRoles.setStatus(Integer.parseInt(Bytes.toStringBinary(result.getValue(Bytes.toBytes(roleColFamily), Bytes.toBytes("status")))));
					if(numberOfMenuColumn > 0) {
						String menuColumnName = SQLConstants.MENU_COLUMN_NAME;
						for(int intI = 1; intI < numberOfMenuColumn; intI++) {
							roleMenu = new RoleMenuTO();
							roleMenu.setName(Bytes.toStringBinary(result.getValue(Bytes.toBytes(menuColumnName+intI), Bytes.toBytes("name"))));
							roleMenu.setCreateRole(Bytes.toStringBinary(result.getValue(Bytes.toBytes(menuColumnName+intI), Bytes.toBytes("createRole"))));
							roleMenu.setUpdateRole(Bytes.toStringBinary(result.getValue(Bytes.toBytes(menuColumnName+intI), Bytes.toBytes("updateRole"))));
							roleMenu.setViewRole(Bytes.toStringBinary(result.getValue(Bytes.toBytes(menuColumnName+intI), Bytes.toBytes("viewRole"))));
							listOfRoleMenu.add(roleMenu);
						}
					}	
					userRoles.setRoleMenu(listOfRoleMenu);					
				}				
			}			
		}catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);
		}
		return userRoles;
	}
	

	/**
	 * @param isSecurityAdmin
	 * @param isReadOnly
	 * @param isCommenter
	 * @param isSuperUser
	 * @return roleId
	 * @throws ProductVisibilityAPIException
	 * @author mohamfj
	 * Method to retrieve the role Id based on the privilege details from sccm:roles table 
	 */
	private Integer getRoleId(Integer isSecurityAdmin,Integer isReadOnly,
			Integer isCommenter,Integer isSuperUser) 
			throws ProductVisibilityAPIException{
		
		Integer roleId = 0;
		ResultScanner resultScanner = null;
		Connection connection = null;
		HTable table = null;
		try {

			// Instantiating Configuration class
			//Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			/*TableName TABLE_NAME = TableName
					.valueOf(SQLConstants.ROLE_TABLE);
			table = new HTable(config, TABLE_NAME);*/
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.ROLE_TABLE));
			int rolePrivilege=0;
			String searchColumn=null;
			if(isSecurityAdmin>0){
				searchColumn="isSecurityAdmin";
				rolePrivilege = isSecurityAdmin;
			}else if(isReadOnly>0){
				searchColumn="isReadOnly";
				rolePrivilege =isReadOnly;
			}	
			else if(isCommenter>0){
				searchColumn="isCommenter";
				rolePrivilege=isCommenter;		
			}	
			else if(isSuperUser>0){
				searchColumn="isSuperUser";
				rolePrivilege=isSuperUser;
			}	
			SingleColumnValueFilter roleFilter = null;	
			FilterList filterList = null;
			if(!StringUtils.isEmpty(searchColumn) && rolePrivilege>0){
				roleFilter = new SingleColumnValueFilter(
						Bytes.toBytes(ApplicationConstants.ROLE_COLUMN_FAMILY),
						Bytes.toBytes(searchColumn), CompareOp.EQUAL,
						Bytes.toBytes(rolePrivilege+"")); // convert Integer to String 
												   //so that in byte array compare input data
				if (roleFilter != null) {
					filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL); // could
																					// be
																					// FilterList.Operator.MUST_PASS_ALL
																					// instead
					// Add the status flag to check for active role
					SingleColumnValueFilter statusFilter = new SingleColumnValueFilter(
							Bytes.toBytes(ApplicationConstants.ROLE_COLUMN_FAMILY),
							Bytes.toBytes("status"), CompareOp.EQUAL,
							Bytes.toBytes("1")); // active status
					filterList.addFilter(roleFilter);
					filterList.addFilter(statusFilter);
				}
				
				
				Scan scan = new Scan();
				scan.addFamily(Bytes
						.toBytes(ApplicationConstants.ROLE_COLUMN_FAMILY));
				if(filterList!=null) {
					scan.setFilter(filterList);
				}
				//System.out.println("role Filter***"+scan.getFilter());
				resultScanner = table.getScanner(scan);
				for (final Result result : resultScanner) {
					Iterator<KeyValue> iter = result.list().iterator();
					while (iter.hasNext()) {
						KeyValue kv = iter.next();
						if(Bytes.toString(kv.getQualifier()).equals("roleId")) {
								// If user already present in user table then return user exist as true
							roleId = Integer.parseInt(Bytes.toString(kv.getValue()));
							break;
						}
					}
				}
			}
		}catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);
		}
		return roleId;
	}
	
	/**
	 *@see com.abbvie.productvisibility.dao.UserDAO#updateUserLoginDetails
	 *     (com.abbvie.prodcutvisibility.to.UserResponseTO)
	 *@param userRequestTO
	 *@return UserResponseTO
	 *@throws ProductVisibilityAPIException
	 *@author mohamfj
	 *Method to update the login user details from sccm:users table
	 */
	@SuppressWarnings("deprecation")
	public UserResponseTO updateUserLoginDetails(
			UserTO user) throws ProductVisibilityAPIException{
		ResultScanner resultScanner = null;
		Connection connection = null;
		UserResponseTO response = new UserResponseTO();
		String sAction = user.getAction();
		String strColumnFamily = ApplicationConstants.USER_COLUMN_FAMILY;
		HTable table = null;
		try {

			// Instantiating Configuration class
			//Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			/*TableName TABLE_NAME = TableName
					.valueOf(SQLConstants.USER_TABLE);
			table = new HTable(config, TABLE_NAME);*/
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.USER_TABLE));
			Put put = null;
			boolean exist=false;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String currentDate = sdf.format(new Date());
			// get the role for the user based on the privilege entered
			int roleId=0;/*getRoleId(user.getIsSecurityAdmin(), user.getIsReadOnly(),
					user.getIsCommenter(), user.getIsSuperUser());*/
			
			if(roleId==0){
				response.setSuccess(false);
				response
						.setResponseMessage("Cannot Insert. No User Roles Exist!");
				return response;
			}
			Map<String, Object> checkExist=checkUserExists(user.getNetworkId(),table);
			exist = (Boolean) checkExist.get("exist");
			
			if (sAction.equalsIgnoreCase(ApplicationConstants.INSERT_ACTION) && roleId>0) {
				// User already exist, so do not insert sent isSuccess as
				// false
				if (!exist) {
					String currentRow =(String) checkExist.get("rowCount");
					put = new Put(Bytes.toBytes(currentRow));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("userId"),
							Bytes.toBytes(currentRow));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("networkId"),
							Bytes.toBytes(user.getNetworkId().toLowerCase()));// convert to lower case
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("fName"),
							Bytes.toBytes(user.getfName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("lName"),
							Bytes.toBytes(user.getlName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("mName"),
							Bytes.toBytes(user.getmName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("emailId"),
							Bytes.toBytes(user.getEmailId()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("status"),
							Bytes.toBytes(user.getStatus()+"")); // 1= active 0=Inactive
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("roleId"),Bytes.toBytes(roleId+"")); 
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("createdBy"),
								Bytes.toBytes(user.getCreatedBy()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("createdDate"),
								Bytes.toBytes(currentDate));
					put.add(Bytes.toBytes(strColumnFamily),
						Bytes.toBytes("updatedBy"),
							Bytes.toBytes(user.getCreatedBy()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("updatedDate"),
								Bytes.toBytes(currentDate));
					table.put(put);
					response.setSuccess(true);
					response
							.setResponseMessage("User Details Inserted Successfully");
				} else {
					response.setSuccess(false);
					response
							.setResponseMessage("Cannot Insert. User Already Exists!");
				}
			} else if (sAction
					.equalsIgnoreCase(ApplicationConstants.UPDATE_ACTION) && roleId>0) {
				if (exist) {
					String currentRow = (String) checkExist.get("key");
					put = new Put(Bytes.toBytes(currentRow));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("userId"),
							Bytes.toBytes(currentRow));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("networkId"),
							Bytes.toBytes(user.getNetworkId().toLowerCase())); // convert to lower case
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("fName"),
							Bytes.toBytes(user.getfName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("lName"),
							Bytes.toBytes(user.getlName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("mName"),
							Bytes.toBytes(user.getmName()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("emailId"),
							Bytes.toBytes(user.getEmailId()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("status"),
							Bytes.toBytes(user.getStatus()+"")); // 1= active 0=Inactive
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("roleId"),Bytes.toBytes(roleId+"")); 
					put.add(Bytes.toBytes(strColumnFamily),
						Bytes.toBytes("updatedBy"),
							Bytes.toBytes(user.getCreatedBy()));
					put.add(Bytes.toBytes(strColumnFamily),
							Bytes.toBytes("updatedDate"),
								Bytes.toBytes(currentDate));
					table.put(put);
					response.setSuccess(true);
					response
							.setResponseMessage("User Details Updated Successfully");
				} else {
					response.setSuccess(false);
					response
							.setResponseMessage("Cannot Update. User does not Exists!!! ");
				}
			}
				
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);
		}
		
		return response;
	}
	/**
	 * 
	 * @param userId
	 * @return responseMap
	 * @throws IOException
	 * @throws ProductVisibilityAPIException
	 * @author mohamfj
	 * Method to check if user Already exist in sccm:users table
	 */
	public static Map<String, Object> checkUserExists(String networkId,
			   HTable table)
			throws IOException, ProductVisibilityAPIException {
		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		boolean userExist = false;
		ResultScanner scanner = null;
		try {
			FilterList filterList = null;
			SingleColumnValueFilter filterUser = null;
			if (!StringUtils.isEmpty(networkId)) {
				filterUser = new SingleColumnValueFilter(
						Bytes.toBytes(ApplicationConstants.USER_COLUMN_FAMILY),
						Bytes.toBytes("networkId"), CompareOp.EQUAL, Bytes
								.toBytes(networkId.toLowerCase()));
			}
			if (filterUser != null) {
				filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL); // could
																				// be
																				// FilterList.Operator.MUST_PASS_ALL
																				// instead
				if (filterUser != null)
					filterList.addFilter(filterUser);
			}
			Scan scan = new Scan();
			if (filterList != null)
				scan.setFilter(filterList);
			scan.addFamily(Bytes
					.toBytes(ApplicationConstants.USER_COLUMN_FAMILY));
			scanner = table.getScanner(scan);
			// System.out.println("scann.."+scanner.toString());
			for (Result result = scanner.next(); result != null; result = scanner
					.next()) {
				Iterator<KeyValue> iter = result.list().iterator();
				while (iter.hasNext()) {
					KeyValue kv = iter.next();
					if(Bytes.toString(kv.getQualifier()).equals("networkId")
					   && Bytes.toString(kv.getValue()).equalsIgnoreCase(networkId)) {
							// If user already present in user table then return user exist as true
								userExist = true;
								responseMap.put("key",Bytes.toString(result.getRow())); // for updating exist user 
																		// store the row key in key variable
								break;
					}
				}
			}
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(scanner, null, null);
		}
		int rowCount = ProductVisibilityAPIValidation.getRowCount(table);
		rowCount++;
		responseMap.put("exist", userExist);
		responseMap.put("rowCount", rowCount+"");
		return responseMap;
	}
	
	public static UserResponseTO getUserDetails() throws ProductVisibilityAPIException{
		ResultScanner resultScanner = null;
		Connection connection = null;
		UserResponseTO response = new UserResponseTO();		
		UserTO userTO = null;
		HTable table = null;
		try {
			// Instantiating Configuration class
			//Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			/*TableName TABLE_NAME = TableName
					.valueOf(SQLConstants.USER_TABLE);
			table = new HTable(config, TABLE_NAME);*/
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.USER_TABLE));
			Scan scan = new Scan(); 
			scan.addFamily(Bytes.toBytes(ApplicationConstants.USER_COLUMN_FAMILY));
			resultScanner = table.getScanner(scan);
			if(resultScanner == null) {
				response.setSuccess(false);
				response.setResponseMessage(ApplicationConstants.NO_RECORDS_FOUND_MESSAGE);
				return response;
			}
			//ResultsetMapper<UserTO> rsm = new ResultsetMapper<UserTO>();
			//userList = rsm.mapResultToObject(resultScanner, UserTO.class);
			String qualifier = "";
			String value = "";
			List<UserTO> userList = new ArrayList<UserTO>();
			for(Result result : resultScanner) {
				if(result == null) {
					continue;
				}
				userTO = new UserTO();
				for (Cell cell : result.listCells()) {
					if(cell == null) {
						continue;
					}
			        qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
			        value = Bytes.toString(CellUtil.cloneValue(cell));
			        if(qualifier.equalsIgnoreCase("networkId")) {
			        	userTO.setNetworkId(value);
			        }
			        if(qualifier.equalsIgnoreCase("fName")) {
			        	userTO.setfName(value);
			        }
			        if(qualifier.equalsIgnoreCase("lName")) {
			        	userTO.setlName(value);
			        }
			        if(qualifier.equalsIgnoreCase("mName")) {
			        	userTO.setmName(value);
			        }
			        if(qualifier.equalsIgnoreCase("roleId")) {
			        	userTO.setRoleId(Integer.parseInt(value));
			        }
			    }				
				userList.add(userTO);
			}
		    if(CollectionUtils.isEmpty(userList)) {
		    	response.setSuccess(false);
				response.setResponseMessage(ApplicationConstants.NO_RECORDS_FOUND_MESSAGE);
				return response;
		    }
		    response.setUserList(userList);
			response.setSuccess(true);
			response.setResponseMessage(ApplicationConstants.SUCCESS_MESSAGE);
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);
		}		
		return response;
	}
	
	public boolean isUserExist(String networkID) throws ProductVisibilityAPIException {
		if(StringUtils.isEmpty(networkID)) {
			return false;
		}
		Connection connection = null;
		HTable table = null;
		ResultScanner resultScanner = null;
		try {
			// Instantiating Configuration class
			//Configuration config = HBaseConnectionFactory.getConfiguration();
			connection = HBaseConnectionFactory.getConnection();
			if (connection == null) {
				throw new ProductVisibilityAPIException(
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.GENERIC_APP_ERROR_CODE,
						ApplicationConstants.DATABASE_ERROR,
						ApplicationConstants.DATABASE_ERROR_MESSAGE,
						ApplicationConstants.SCPVAPI_URL);
			}
			table = (HTable) connection.getTable(TableName.valueOf(SQLConstants.USER_TABLE));
			/**TableName TABLE_NAME = TableName
					.valueOf(SQLConstants.USER_TABLE);
			table = new HTable(config, TABLE_NAME);*/			
			Scan scan = new Scan();
			Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
					new BinaryPrefixComparator(Bytes.toBytes(networkID)));
			scan = scan.setFilter(rowFilter);
			resultScanner = table.getScanner(scan); 			
			for(Result result : resultScanner) {
				if ( null == result || result.isEmpty()) { 
					continue;
				}
				System.out.println("######## Result Scanner Size #######"+Bytes.toString(result.getRow()));
				if(!StringUtils.isEmpty(Bytes.toString(result.getRow())) && Bytes.toString(result.getRow()).equalsIgnoreCase(networkID) ) {
					return true;
				}
			}
		} catch (IOException e) {
			throw new ProductVisibilityAPIException(
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.GENERIC_APP_ERROR_CODE,
					ApplicationConstants.DATABASE_ERROR,
					ApplicationConstants.DATABASE_ERROR_MESSAGE,
					ApplicationConstants.SCPVAPI_URL);
		} finally {
			ProductVisibilityAPIValidation.closeHBaseObjects(resultScanner, table, connection);			
		}		
		return false;
	}
}


