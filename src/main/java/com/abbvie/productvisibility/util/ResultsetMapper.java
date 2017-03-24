package com.abbvie.productvisibility.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class ResultsetMapper<T> {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> mapResultSetToObject(ResultSet rs, Class outputClass) {
		List<T> outputList = null;
		try {
			// make sure resultset is not null
			if (rs != null) {
				// check if outputClass has 'Entity' annotation
				if (outputClass.isAnnotationPresent(Entity.class)) {
					// get the resultset metadata
					ResultSetMetaData rsmd = rs.getMetaData();
					// get all the attributes of outputClass
					Field[] fields = outputClass.getDeclaredFields();
					List<String> fList = new ArrayList();
					Map<String, String> fieldNames = new HashMap<String, String>();
					// iterating over outputClass attributes to check if any
					// attribute has 'Column' annotation with matching 'name'
					// value
					for (Field field : fields) {
						if (field.isAnnotationPresent(Column.class)) {
							Column column = field.getAnnotation(Column.class);
							fList.add(column.name().toLowerCase());
							fieldNames.put(column.name().toLowerCase(), field.getName());
						}
					}

					while (rs.next()) {
						T bean = (T) outputClass.newInstance();
						List<String> fDbList = new ArrayList();
						for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
							// getting the SQL column name
							String columnName = rsmd.getColumnName(_iterator + 1).toLowerCase();
							// reading the value of the SQL column
							Object columnValue = rs.getObject(_iterator + 1);
							fDbList.add(columnName);
							// Setting the column with the resultset value
							if (fList.contains(columnName) && columnValue != null) {
								BeanUtils.setProperty(bean, fieldNames.get(columnName), columnValue);
								// System.out.println("IF ColumnName ..
								// "+columnName);
							}
						}
						for (String name : fList) {
							if (!fDbList.contains(name)) {
								BeanUtils.setProperty(bean, fieldNames.get(name), "");
							}
							// System.out.println("ELSE Name.. "+name);
						}
						if (outputList == null) {
							outputList = new ArrayList<T>();
						}
						outputList.add(bean);
					}

					fList = null;
					fieldNames = null;
				}
			} else {
				return null;
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return outputList;
	}

	public List<T> mapResultToObject(Result result, Class outputClass){
		 List<T> outputList = null;
		 Field[] fields = outputClass.getDeclaredFields();
		 try {
			 if(result!=null){
				 T bean = (T) outputClass.newInstance();
				 Map<String,String> fieldNames = new HashMap<String,String>();
				 for(Field field : fields){
					// System.out.println("field.."+field.getName());
					 fieldNames.put(field.getName(),field.getName());
						}
				 Set<Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>>> entries = result.getMap().entrySet();
				    for(Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> familyEntry: entries) 
				    {
				        for(Entry<byte[], NavigableMap<Long, byte[]>> columnEntry: familyEntry.getValue().entrySet()) 
				        {
				        	byte[] column = columnEntry.getKey();
				        	//System.out.println("result field.."+Bytes.toString(column));
				        	byte[] value = columnEntry.getValue().firstEntry().getValue();
				        	for(Map.Entry<String,String> field : fieldNames.entrySet()){
				        		if (field.getKey().equals(Bytes.toString(column)) && column != null) {
									BeanUtils.setProperty(bean, fieldNames.get(Bytes.toString(column)), Bytes.toString(value));
				        	}
				        }
				    }
				    if (outputList == null) {
							outputList = new ArrayList<T>();
						}
						outputList.add(bean);
					}
					fieldNames = null;
			 }
		 } catch (IllegalAccessException e) {
				e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		 
		 return outputList;
	}

}
