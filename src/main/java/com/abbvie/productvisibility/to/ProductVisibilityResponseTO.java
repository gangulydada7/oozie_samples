package com.abbvie.productvisibility.to;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.abbvie.productvisibility.valueobject.ProductVisibilityMaster;


@XmlRootElement
public class ProductVisibilityResponseTO extends BaseResponseTO implements Serializable{
		
	private boolean isSuccess;
	private String responseMessage;
	private List<OrderReferenceSearchResult> orderRefSearchResult;
	private OrderReferenceDetails orderRefDetails;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public List<OrderReferenceSearchResult> getOrderRefSearchResult() {
		return orderRefSearchResult;
	}
	public void setOrderRefSearchResult(
			List<OrderReferenceSearchResult> orderRefSearchResult) {
		this.orderRefSearchResult = orderRefSearchResult;
	}
	public OrderReferenceDetails getOrderRefDetails() {
		return orderRefDetails;
	}
	public void setOrderRefDetails(OrderReferenceDetails orderRefDetails) {
		this.orderRefDetails = orderRefDetails;
	}
	
	
}
