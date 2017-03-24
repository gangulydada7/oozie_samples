/**
 * 
 */
package com.abbvie.productvisibility.dao;

import com.abbvie.productvisibility.exception.ProductVisibilityAPIException;
import com.abbvie.productvisibility.to.UserRequestTO;
import com.abbvie.productvisibility.to.UserResponseTO;
import com.abbvie.productvisibility.to.UserTO;

/**
 * @author MOHAMFJ
 *
 */
public interface UserDAO {
	
	public abstract UserResponseTO getUserLoginDetails(
			UserRequestTO userRequestTO) throws ProductVisibilityAPIException;
	
	public abstract UserResponseTO updateUserLoginDetails(
			UserTO userTO) throws ProductVisibilityAPIException;
	
}
