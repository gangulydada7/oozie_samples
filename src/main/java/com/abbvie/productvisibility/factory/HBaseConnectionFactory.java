package com.abbvie.productvisibility.factory;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;

import com.abbvie.productvisibility.constants.ApplicationConstants;

public class HBaseConnectionFactory {


    private static HBaseConnectionFactory instance = new HBaseConnectionFactory();
    
    private static Configuration conf; 

    //private constructor
    private HBaseConnectionFactory() {

    }

    private Connection createConnection() {
      Connection connection = null;
        try {
            Configuration config = HBaseConfiguration.create();
            connection = ConnectionFactory.createConnection(config);
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return connection;
    }


    public static Connection getConnection() {
        return instance.createConnection();
    }

    /**      * Create a HBase configuration based on the data store context info. 
     * @return 
     */ 
    public static Configuration getConfiguration() { 
      // log.debug("Entering getConfiguration()"); 
       if(conf == null){
       	conf = HBaseConfiguration.create();
       	conf.set("hadoop.security.authentication", "Kerberos");
       	UserGroupInformation.setConfiguration(conf);
       	try
       	{
       	  UserGroupInformation.loginUserFromKeytab(ApplicationConstants.USERNAME, ApplicationConstants.KEYTAB_LOCATION);
       	}
       	catch(IOException e)
       	{
       		e.printStackTrace();
       	} 
       }
       return conf; 
    } 

}
