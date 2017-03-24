package com.abbvie.productvisibility.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.ResultSet;

/**
 * Utility for converting ResultSets into some Output formats
 * @author marlonlom
 */
public class Convertor {
    /**
     * Convert a result set into a JSON Array
     * @param resultSet
     * @return a JSONArray
     * @throws Exception
     */
    public static JsonArray convertToJSON(ResultSet resultSet)
            throws Exception {
    	JsonArray jsonArray = new JsonArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JsonObject obj = new JsonObject();
            for (int i = 0; i < total_rows; i++) {
                obj.add(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), (JsonElement)resultSet.getObject(i + 1));
                jsonArray.add(obj);
            }
        }
        return jsonArray;
    }
    /**
     * Convert a result set into a XML List
     * @param resultSet
     * @return a XML String with list elements
     * @throws Exception if something happens
     */
    public static String convertToXML(ResultSet resultSet)
            throws Exception {
        StringBuffer xmlArray = new StringBuffer("<results>");
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            xmlArray.append("<result ");
            for (int i = 0; i < total_rows; i++) {
                xmlArray.append(" " + resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase() + "='" + resultSet.getObject(i + 1) + "'"); }
            xmlArray.append(" />");
        }
        xmlArray.append("</results>");
        return xmlArray.toString();
    }
}
