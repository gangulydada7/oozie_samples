package com.abbvie.productvisibility.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONUtil {
	
	/**
	 * Convert to Json Item.
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static Object convertJsonItem(Object o) throws JSONException {
		if (o == null) {
			return "null";
		}

		if (o instanceof JSONObject) {
			return getListFromJsonObject((JSONObject) o);
		}

		if (o instanceof JSONArray) {
			return getListFromJsonArray((JSONArray) o);
		}

		if (o.equals(Boolean.FALSE) || (o instanceof String &&
				((String) o).equalsIgnoreCase("false"))) {
			return false;
		}

		if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
			return true;
		}

		if (o instanceof Number) {
			return o;
		}

		return o.toString();
	}
	
	/**
	 * get Json Representation
	 * @param value
	 * @return
	 * @throws JSONException
	 */
	public static String getJsonRepresentation(Object value) throws JSONException {
		if (value == null || value.equals(null)) {
			return "null";
		}
		/*
		 * This has been commented out to prevent the need for the Kawa library. Do NOT use Fstring
		 * or YailList in any of your data, otherwise this conversion won't work.
		 * 
      if (value instanceof FString) {
        return JSONObject.quote(value.toString());
      }
      if (value instanceof YailList) {
        return ((YailList) value).toJSONString();
      }
		 */

		if (value instanceof Number) {
			return JSONObject.numberToString((Number) value);
		}
		if (value instanceof Boolean) {
			return value.toString();
		}
		if (value.getClass().isArray()) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			String separator = "";
			for (Object o: (Object[]) value) {
				sb.append(separator).append(getJsonRepresentation(o));
				separator = ",";
			}
			sb.append("]");
			return sb.toString();
		}
		if (value instanceof String) {
			return value.toString();
		}
		return JSONObject.quote(value.toString());
	}
	
	/**
	 * Get List from Json Array
	 * @param jArray
	 * @return
	 * @throws JSONException
	 */
	public static List<Object> getListFromJsonArray(JSONArray jArray) throws JSONException {
		List<Object> returnList = new ArrayList<Object>();
		for (int i = 0; i < jArray.length(); i++) {
			returnList.add(convertJsonItem(jArray.get(i)));
		}
		return returnList;
	}
	
	/**
	 * Get List from Json object
	 * @param jObject
	 * @return
	 * @throws JSONException
	 */
	public static List getListFromJsonObject(JSONObject jObject) throws JSONException {
		List returnList = new ArrayList();
		Iterator<String> keys = jObject.keys();

		List<String> keysList = new ArrayList<String>();
		while (keys.hasNext()) {
			keysList.add(keys.next());
		}
		Collections.sort(keysList);

		for (String key : keysList) {
			List<Object> nestedList = new ArrayList<Object>();
			nestedList.add(key);
			nestedList.add(convertJsonItem(jObject.get(key)));
			returnList.add(nestedList);
		}

		return returnList;
	}



}
