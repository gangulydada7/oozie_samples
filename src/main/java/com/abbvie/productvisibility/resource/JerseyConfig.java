package com.abbvie.productvisibility.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.commons.lang.StringUtils;

import com.abbvie.productvisibility.constants.ApplicationConstants;
import com.abbvie.productvisibility.constants.SQLConstants;



@ApplicationPath("")
public class JerseyConfig extends Application {

	public static final String PROPERTIES_FILE = "productvisibilityapi.properties";
	public static String PROPERTIES_INPUT_FILE = "productvisibilityapi.properties";
	public static String ENVIRONMENT="PROD";
	public static Properties properties = new Properties();

	private Properties readProperties() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_INPUT_FILE);
		if (inputStream != null) {
			try {
				System.out.println(" in the read props ");
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Add your custom fail-over code here
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	public void reloadProps(){
		readProperties();
		setProperties();
	}
	
	public void reloadProps(String propsFile,String env){
		PROPERTIES_INPUT_FILE=propsFile;
		readProperties();
		setProperties();
	}
	
	
	private void setProperties()
	{
		if(StringUtils.isNotEmpty(properties.getProperty("USER_TABLE"))){
			SQLConstants.USER_TABLE=properties.getProperty("USER_TABLE");
		}
		if(StringUtils.isNotEmpty(properties.getProperty("ROLE_TABLE"))){
			SQLConstants.ROLE_TABLE=properties.getProperty("ROLE_TABLE");
		}		
		if(StringUtils.isNotEmpty(properties.getProperty("KEYTAB_LOCATION"))){
			ApplicationConstants.KEYTAB_LOCATION=properties.getProperty("KEYTAB_LOCATION");
		}
		if(StringUtils.isNotEmpty(properties.getProperty("USERNAME"))){
			ApplicationConstants.USERNAME=properties.getProperty("USERNAME");
		}
	 }


	@Override
	public Set<Class<?>> getClasses() {     
		// Read the properties file
		//readProperties();
		//setProperties();
		
		System.out.println(" in the get class");
		// Set up your Jersey resources
		Set<Class<?>> rootResources = new HashSet<Class<?>>();
		rootResources.add(ProductVisibilityResource.class);
		rootResources.add(UploadFileService.class);
		rootResources.add(ProductVisibilityResponseFilter.class);
			return rootResources;
	}

}
