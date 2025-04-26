package com.example.selltrack.parameters;

public class DbParams {

    // Database
    public static final int DB_VERSION = 7;
    public static final String DB_NAME = "SellTrackDB";

    // Inventory Table
    public static final String INVENTORY_TABLE = "inventory";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";

    // Customer Table
    public static final String CUSTOMER_TABLE = "customers";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String CUSTOMER_AREA_ID = "area_id";

    // City Table
    public static final String CITY_TABLE = "cities";
    public static final String CITY_NAME = "city_name";
    public static final String CITY_AREA_NAME = "area_name";
    public static final String CITY_AREA_ID = "area_id";

    // Sells Table (Version 5)
    public static final String SELLS_TABLE = "sells";
    public static final String SELL_ID = "sell_id";
    public static final String SELLS_CUSTOMER_ID = "customer_id";
    public static final String TOTAL_PRICE = "total_price";
    public static final String DATE = "date";

    // Sales Items Table (New)
    public static final String SALES_ITEM_TABLE = "sales_items";
    public static final String SALES_ITEM_ID = "sales_item_id";
    public static final String SALES_SELL_ID = "sell_id";
    public static final String SALES_PRODUCT_ID = "product_id";
    public static final String SALES_SOLD_QUANTITY = "sold_quantity";
}
