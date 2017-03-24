package com.abbvie.productvisibility.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.abbvie.productvisibility.constants.ApplicationConstants;

public class MapComparator implements Comparator<Map<String, Object>> {
	private final String key;

	public MapComparator(String key) {
		this.key = key;
	}

	public int compare(Map<String, Object> first, Map<String, Object> second) {
		// TODO: Null checking, both for maps and values
		if(key.equalsIgnoreCase("createdDate")){
			SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT);
			String firstValue = (String) first.get(key);
			String secondValue = (String) second.get(key);
			Date date1 = null;
			Date date2 = null;
			if(!StringUtils.isEmpty(firstValue) && !StringUtils.isEmpty(secondValue)){
				try{
					date1=sdf.parse(firstValue);
					date2=sdf.parse(secondValue);
				}catch(ParseException ex){System.out.println(ex);}
				// date in descending order
				return date2.compareTo(date1);
			}
		}else{
			String firstValue = (String) first.get(key);
			String secondValue = (String) second.get(key);
			if(!StringUtils.isEmpty(firstValue) && !StringUtils.isEmpty(secondValue)){
				// ascending order of key 
				return firstValue.compareTo(secondValue);
			}
		}
		return 0;
	}

}