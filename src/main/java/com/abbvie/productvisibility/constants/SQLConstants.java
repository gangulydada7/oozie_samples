package com.abbvie.productvisibility.constants;

public class SQLConstants {
    /*
     * DDL
     */
    public static String UPSERT = "UPSERT INTO ";
    public static String SELECT = "SELECT * FROM ";
    public static String DELETE = "DELETE FROM ";
    public static String WHERE = " where ";

     /*
     * Supply Chain Product Visibility Table Details
     */
    public static String NAMESPACE = "scpv:";
    public static String USER_TABLE = NAMESPACE+"users";
  	public static String ROLE_TABLE = NAMESPACE+"roles";
  	public static String STATUS_TABLE = NAMESPACE+"status";
  	public static String STATUS_TABLE_COLFAMILY = "status";
  	public static String LOCATION_TABLE = NAMESPACE+"location";
  	public static String LOCATION_TABLE_COLFAMILY = "location";
  	public static String SYSTEM_TABLE = NAMESPACE+"system";
  	public static String SYSTEM_TABLE_COLFAMILY = "system";
  	public static int TOTAL_MENU = 5;
  	public static String MENU_COLUMN_NAME = "menu_";
}
