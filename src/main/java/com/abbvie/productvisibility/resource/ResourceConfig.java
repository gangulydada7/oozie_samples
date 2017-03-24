package com.abbvie.productvisibility.resource;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ResourceConfig implements ServletContextListener {
	    private static final String ATTRIBUTE_NAME = "config";
	    private Properties config = new Properties();

	    @Override
	    public void contextInitialized(ServletContextEvent event) {
	        try {
	            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("phoenix.properties"));
	        } catch (IOException e) {
	            
	        }
	        event.getServletContext().setAttribute(ATTRIBUTE_NAME, this);
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent event) {
	        // NOOP.
	    }

	    public static ResourceConfig getInstance(ServletContext context) {
	        return (ResourceConfig) context.getAttribute(ATTRIBUTE_NAME);
	    }

	    public String getProperty(String key) {
	        return config.getProperty(key);
	    }
	}

