package com.abbvie.productvisibility.valueobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ProductVisibilityMaster implements Serializable{
		
	private String metricsId;
	
	private String metricsName;	
	
	private String metricsCategory;
	
	public String getMetricsId() {
		return metricsId;
	}

	public void setMetricsId(String metricsId) {
		this.metricsId = metricsId;
	}

	public String getMetricsName() {
		return metricsName;
	}

	public void setMetricsName(String metricsName) {
		this.metricsName = metricsName;
	}

	public String getMetricsCategory() {
		return metricsCategory;
	}

	public void setMetricsCategory(String metricsCategory) {
		this.metricsCategory = metricsCategory;
	}

	
	


}
