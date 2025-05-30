package com.example.selltrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.Model.SalesItemModel;
import com.example.selltrack.Model.SalesModel;
import com.example.selltrack.parameters.DbParams;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler instance;

    public DBHandler(Context context) {
        super(context, DbParams.DB_NAME, null, DbParams.DB_VERSION);
    }

    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createInventory = "CREATE TABLE " + DbParams.INVENTORY_TABLE + "( " +
                DbParams.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.PRODUCT_NAME + " TEXT UNIQUE NOT NULL, " +
                DbParams.PRODUCT_PRICE + " FLOAT NOT NULL, " +
                DbParams.PRODUCT_QUANTITY + " INTEGER NOT NULL " + ")";
        db.execSQL(createInventory);

        String createCitiesTable = "CREATE TABLE " + DbParams.CITY_TABLE + "( " +
                DbParams.CITY_NAME + " TEXT NOT NULL, " +
                DbParams.CITY_AREA_NAME + " TEXT NOT NULL, " +
                DbParams.CITY_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                ")";
        db.execSQL(createCitiesTable);

        String createCustomerTable = "CREATE TABLE " + DbParams.CUSTOMER_TABLE + "( " +
                DbParams.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.CUSTOMER_NAME + " TEXT NOT NULL, " +
                DbParams.CUSTOMER_AREA_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DbParams.CUSTOMER_AREA_ID + ") REFERENCES " +
                DbParams.CITY_TABLE + "(" + DbParams.CITY_AREA_ID + ")" +
                ")";
        db.execSQL(createCustomerTable);

        String createSellsTable = "CREATE TABLE " + DbParams.SELLS_TABLE + " (" +
                DbParams.SELL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.SELLS_CUSTOMER_ID + " INTEGER NOT NULL, " +
                DbParams.TOTAL_PRICE + " FLOAT NOT NULL, " +
                DbParams.DATE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DbParams.SELLS_CUSTOMER_ID + ") REFERENCES " +
                DbParams.CUSTOMER_TABLE + "(" + DbParams.CUSTOMER_ID + ")" +
                ")";
        db.execSQL(createSellsTable);

        String createSalesItemsTable = "CREATE TABLE " + DbParams.SALES_ITEM_TABLE + "(" +
                DbParams.SALES_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.SALES_SELL_ID + " INTEGER NOT NULL, " +
                DbParams.SALES_PRODUCT_ID + " INTEGER NOT NULL, " +
                DbParams.SALES_SOLD_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DbParams.SALES_SELL_ID + ") REFERENCES " + DbParams.SELLS_TABLE + "(" + DbParams.SELL_ID + "), " +
                "FOREIGN KEY (" + DbParams.SALES_PRODUCT_ID + ") REFERENCES " + DbParams.INVENTORY_TABLE + "(" + DbParams.PRODUCT_ID + ")" +
                ")";
        db.execSQL(createSalesItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.INVENTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.CITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.SELLS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.SALES_ITEM_TABLE);
        dataBaseVersion11(db);
    }

    private void dataBaseVersion11(SQLiteDatabase db) {

        String createInventory = "CREATE TABLE " + DbParams.INVENTORY_TABLE + "( " +
                DbParams.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.PRODUCT_NAME + " TEXT UNIQUE NOT NULL, " +
                DbParams.PRODUCT_PRICE + " FLOAT NOT NULL, " +
                DbParams.PRODUCT_QUANTITY + " INTEGER NOT NULL " + ")";
        db.execSQL(createInventory);

        String createCitiesTable = "CREATE TABLE " + DbParams.CITY_TABLE + "( " +
                DbParams.CITY_NAME + " TEXT NOT NULL, " +
                DbParams.CITY_AREA_NAME + " TEXT NOT NULL, " +
                DbParams.CITY_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                ")";
        db.execSQL(createCitiesTable);

        String createCustomerTable = "CREATE TABLE " + DbParams.CUSTOMER_TABLE + "( " +
                DbParams.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.CUSTOMER_NAME + " TEXT NOT NULL, " +
                DbParams.CUSTOMER_AREA_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DbParams.CUSTOMER_AREA_ID + ") REFERENCES " +
                DbParams.CITY_TABLE + "(" + DbParams.CITY_AREA_ID + ")" +
                ")";
        db.execSQL(createCustomerTable);

        String createSellsTable = "CREATE TABLE " + DbParams.SELLS_TABLE + " (" +
                DbParams.SELL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.SELLS_CUSTOMER_ID + " INTEGER NOT NULL, " +
                DbParams.TOTAL_PRICE + " FLOAT NOT NULL, " +
                DbParams.DATE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DbParams.SELLS_CUSTOMER_ID + ") REFERENCES " +
                DbParams.CUSTOMER_TABLE + "(" + DbParams.CUSTOMER_ID + ")" +
                ")";
        db.execSQL(createSellsTable);

        String createSalesItemsTable = "CREATE TABLE " + DbParams.SALES_ITEM_TABLE + "(" +
                DbParams.SALES_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.SALES_SELL_ID + " INTEGER NOT NULL, " +
                DbParams.SALES_PRODUCT_ID + " INTEGER NOT NULL, " +
                DbParams.SALES_SOLD_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DbParams.SALES_SELL_ID + ") REFERENCES " + DbParams.SELLS_TABLE + "(" + DbParams.SELL_ID + "), " +
                "FOREIGN KEY (" + DbParams.SALES_PRODUCT_ID + ") REFERENCES " + DbParams.INVENTORY_TABLE + "(" + DbParams.PRODUCT_ID + ")" +
                ")";
        db.execSQL(createSalesItemsTable);
    }

    public List<SalesItemModel> getSalesItemList(int saleId) {
        List<SalesItemModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.SALES_ITEM_TABLE + " WHERE " + DbParams.SALES_SELL_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(saleId)});
        if(cursor.moveToFirst()) {
            do {
                int salesItemId = cursor.getInt(0);
                int salesId = cursor.getInt(1);
                int productId = cursor.getInt(2);
                int soldQuantity = cursor.getInt(3);
                SalesItemModel salesItem = new SalesItemModel(salesItemId, salesId, productId, soldQuantity);
                list.add(salesItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public void addItemInventory(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.PRODUCT_NAME, item.getItemName());
        values.put(DbParams.PRODUCT_PRICE, item.getPrice());
        values.put(DbParams.PRODUCT_QUANTITY, item.getQuantity());
        db.insert(DbParams.INVENTORY_TABLE, null, values);
        db.close();
    }
    public List<ItemModel> getInventoryItems() {
        List<ItemModel> itemModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + DbParams.INVENTORY_TABLE;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                ItemModel i = new ItemModel();
                i.setId(cursor.getInt(0));
                i.setItemName(cursor.getString(1));
                i.setPrice(cursor.getFloat(2));
                i.setQuantity(cursor.getInt(3));
                itemModelList.add(i);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemModelList;
    }
    public void checkTables() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.d("CheckDB", "Checking tables...");

            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (cursor.getCount() == 0) {
                Log.e("CheckDB", "Cursor is null! No tables found.");
                return;
            }

            if (cursor.moveToFirst()) {
                do {
                    Log.d("CheckDB", "Table Found: " + cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("CheckDB", "Error checking tables: " + e.getMessage());
        }
    }
    public boolean itemExists(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * " + " FROM " + DbParams.INVENTORY_TABLE + " WHERE " + DbParams.PRODUCT_NAME + " = ?";
        Cursor cursor = db.rawQuery(select, new String[]{itemName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public void deleteItem(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbParams.INVENTORY_TABLE, DbParams.PRODUCT_NAME + "= ?", new String[]{itemName});
        db.close();
    }
    public void updateItem(ItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.PRODUCT_NAME, item.getItemName());
        values.put(DbParams.PRODUCT_QUANTITY, item.getQuantity());
        values.put(DbParams.PRODUCT_PRICE, item.getPrice());
        String[] where = {item.getItemName()};
        db.update(DbParams.INVENTORY_TABLE, values, DbParams.PRODUCT_NAME + " = ?", where);
    }
    public void updateCity(CitiesModel city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.CITY_NAME, city.getCityName());
        values.put(DbParams.CITY_AREA_NAME, city.getAreaName());
        String[] where = {String.valueOf(city.getAreaId())};
        db.update(DbParams.CITY_TABLE, values, DbParams.CITY_AREA_ID + " = ?", where);
        db.close();
    }
    public void addCity(CitiesModel city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.CITY_NAME, city.getCityName());
        values.put(DbParams.CITY_AREA_NAME, city.getAreaName());
        db.insert(DbParams.CITY_TABLE, null, values);
        db.close();
    }
    public List<CitiesModel> getAllCities() {
        List<CitiesModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + DbParams.CITY_TABLE;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                CitiesModel city = new CitiesModel();
                city.setCityName(cursor.getString(0));
                city.setAreaName(cursor.getString(1));
                city.setAreaId(cursor.getInt(2));
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteCity(CitiesModel city) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbParams.CITY_TABLE, DbParams.CITY_NAME + "= ? AND " +
                DbParams.CITY_AREA_NAME, new String[]{city.getCityName(), city.getAreaName()});
        db.close();
    }
    public boolean isCityExists(CitiesModel city) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " + " FROM " + DbParams.CITY_TABLE +
                " WHERE " + DbParams.CITY_NAME + " = ? AND " + DbParams.CITY_AREA_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{city.getCityName(), city.getAreaName()});
        boolean exist = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exist;
    }
    public void addCustomer(CustomerModel customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.CUSTOMER_NAME, customer.getCustomerName());
        values.put(DbParams.CUSTOMER_AREA_ID, customer.getCustomerArea());
        db.insert(DbParams.CUSTOMER_TABLE, null, values);
    }
    public void deleteCustomer(CustomerModel customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbParams.CUSTOMER_TABLE, DbParams.CUSTOMER_ID + " = ? ",
                new String[]{String.valueOf(customer.getCustomerId())});
        db.close();
    }
    public void updateCustomer(CustomerModel customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.CUSTOMER_NAME, customer.getCustomerName());
        values.put(DbParams.CUSTOMER_AREA_ID, customer.getCustomerArea());
        db.update(DbParams.CUSTOMER_TABLE, values,
                DbParams.CUSTOMER_ID + " = ?", new String[]{String.valueOf(customer.getCustomerId())});
        db.close();
    }
    public boolean customerExists(CustomerModel customer) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.CUSTOMER_TABLE +
                " WHERE " + DbParams.CUSTOMER_ID + " = ?" +
                " AND " + DbParams.CUSTOMER_NAME + " = ?" +
                " AND " + DbParams.CUSTOMER_AREA_ID + " = ?";
        String[] where = {String.valueOf(customer.getCustomerId()), customer.getCustomerName(), String.valueOf(customer.getCustomerArea())};
        Cursor cursor = db.rawQuery(query, where);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public List<CustomerModel> getAllCustomers() {
        List<CustomerModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CustomerModel customerModel = new CustomerModel();
                int cId = cursor.getInt(0);
                String name = cursor.getString(1);
                int aId = cursor.getInt(2);
                customerModel.setCustomerId(cId);
                customerModel.setCustomerName(name);
                customerModel.setCustomerArea(aId);
                list.add(customerModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<String> getAreas() {
        List<String> list = new ArrayList<>();
        List<CitiesModel> cities = getAllCities();
        for (int i = 0; i < cities.size(); i++) {
            CitiesModel area = cities.get(i);
            String s = area.getAreaId() + ". [" + area.getAreaName().toUpperCase() + ", (" + area.getCityName().toUpperCase() + ")]";
            list.add(s);
        }
        return list;
    }
    public List<String> getCustomers(int areaId) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.CUSTOMER_TABLE + " WHERE " + DbParams.CUSTOMER_AREA_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(areaId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String str = id + ". [" + name.toUpperCase() + "]";
                list.add(str);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<String> getCustomers() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String str = id + ". " + name.toUpperCase();
                list.add(str);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public ItemModel getItemDetails(ItemModel item) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.INVENTORY_TABLE + " WHERE " + DbParams.PRODUCT_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{item.getItemName()});
        ItemModel itemModel = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float price = cursor.getFloat(2);
            int quantity = cursor.getInt(3);
            itemModel = new ItemModel(id, name, quantity, price);
        }
        cursor.close();
        db.close();
        return itemModel;
    }
    public ItemModel getItemDetails(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.INVENTORY_TABLE + " WHERE " + DbParams.PRODUCT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(itemId)});
        ItemModel itemModel = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            float price = cursor.getFloat(2);
            int quantity = cursor.getInt(3);
            itemModel = new ItemModel(id, name, quantity, price);
        }
        cursor.close();
        db.close();
        return itemModel;
    }

    public CustomerModel getCustomerDetails(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.CUSTOMER_TABLE + " WHERE " + DbParams.CUSTOMER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerId)});
        CustomerModel customerModel = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int areaId = cursor.getInt(2);
            customerModel = new CustomerModel(id, name, areaId);
        }
        cursor.close();
        db.close();
        return customerModel;
    }
    public int addSales(SalesModel sale) {
        SQLiteDatabase dbw = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.SELLS_CUSTOMER_ID, sale.getCustomerId());
        values.put(DbParams.TOTAL_PRICE, sale.getTotalPrice());
        values.put(DbParams.DATE, sale.getDate());
        long insertedRow = dbw.insert(DbParams.SELLS_TABLE, null, values);
        dbw.close();
        return (int) insertedRow;
    }

    public List<SalesModel> getSalesList() {
        List<SalesModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbParams.SELLS_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int salesId = cursor.getInt(0);
                int customerId = cursor.getInt(1);
                float totalPrice = cursor.getFloat(2);
                String date = cursor.getString(3);
                SalesModel sale = new SalesModel(salesId, customerId, totalPrice, date);
                list.add(sale);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public void addSalesItem(int sellId, int productId, int soldQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbParams.SALES_SELL_ID, sellId);
        values.put(DbParams.SALES_PRODUCT_ID, productId);
        values.put(DbParams.SALES_SOLD_QUANTITY, soldQuantity);
        db.insert(DbParams.SALES_ITEM_TABLE, null, values);
        // Subtract From Inventory
        ItemModel item = getItemDetails(productId);
        item.setQuantity(item.getQuantity() - soldQuantity);
        updateItem(item);
        db.close();
    }
}