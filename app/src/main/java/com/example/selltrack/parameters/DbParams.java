package com.example.selltrack.parameters;

public class DbParams {
    // Database Information
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "appDataBase";

    // Inventory Table Information
    public static final String INVENTORY_TABLE = "inventory";

    // Inventory Table Attributes
    public static final String  PRODUCT_ID = "product_id";
    public static final String  PRODUCT_NAME = "product_name";
    public static final String  PRODUCT_PRICE = "product_price";
    public static final String  PRODUCT_QUANTITY = "product_quantity";

    // Sells Table Information
    public static final String SELLS_TABLE = "sells";

    // Attributes in Sells Table
    public static final String SELL_ID = "sell_id";
    public static final String ITEM_ID = "product_id";
    public static final String SOLD_QUANTITY = "sold_quantity";
    public static final String PRICE_PER_PRODUCT = "price_per_product";
    public static final String PER_DAY = "per_day";


}