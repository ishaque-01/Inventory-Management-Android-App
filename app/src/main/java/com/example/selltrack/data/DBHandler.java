package com.example.selltrack.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.selltrack.Model.ItemModel;
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
        Log.d("CheckDB", "Inventory TABLE CREATED");

        String createSells = "CREATE TABLE " + DbParams.SELLS_TABLE + "( " +
                DbParams.SELL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbParams.ITEM_ID + " INTEGER, " +
                DbParams.SOLD_QUANTITY + " TEXT NOT NULL, " +
                DbParams.PRICE_PER_PRODUCT + " FLOAT NOT NULL, " +
                DbParams.PER_DAY + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DbParams.ITEM_ID + ") REFERENCES " +  DbParams.INVENTORY_TABLE + "(" + DbParams.PRODUCT_ID + "))";

        db.execSQL(createSells);
        Log.d("CheckDB", "Sells TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.INVENTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbParams.SELLS_TABLE);
        onCreate(db);
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
        return itemModelList;
    }
    public void checkTables() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.d("CheckDB", "Checking tables...");

            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (cursor == null || cursor.getCount() == 0) {
                Log.e("CheckDB", "Cursor is null! No tables found.");
                return;
            }


            if (cursor.moveToFirst()) {
                do {
                    Log.d("CheckDB", "Table Found: " + cursor.getString(0));
                } while (cursor.moveToNext());
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            Log.e("CheckDB", "Error checking tables: " + e.getMessage());
        }
    }

    public boolean itemExists(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT " + DbParams.PRODUCT_NAME + " FROM " + DbParams.INVENTORY_TABLE + " WHERE " + DbParams.PRODUCT_NAME + " = ?";
        Cursor cursor = db.rawQuery(select, new String[] {itemName});
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

}