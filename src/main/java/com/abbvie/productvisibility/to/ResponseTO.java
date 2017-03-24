package com.abbvie.productvisibility.to;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ResponseTO implements Serializable{
	
	private ProductVisibilityResponseTO getMetricsDataList;
	
	private ProductVisibilityResponseTO searchMetricsByColumn;
	
	public ProductVisibilityResponseTO getGetMetricsDataList() {
		return getMetricsDataList;
	}

	public void setGetMetricsDataList(ProductVisibilityResponseTO getMetricsDataList) {
		this.getMetricsDataList = getMetricsDataList;
	}

	public ProductVisibilityResponseTO getSearchMetricsByColumn() {
		return searchMetricsByColumn;
	}

	public void setSearchMetricsByColumn(ProductVisibilityResponseTO searchMetricsByColumn) {
		this.searchMetricsByColumn = searchMetricsByColumn;
	}

	

}
	

