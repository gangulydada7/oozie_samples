package com.abbvie.productvisibility.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.hadoop.hbase.client.Result;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Util {
		
	public static JSONObject buildJSON(String stringJson) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(stringJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
}
