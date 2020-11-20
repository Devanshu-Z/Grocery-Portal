package com.example.groceryportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    public static final String Database_name = "GroceryPortal.db";
    public static final int DATABASE_Version = 1;

    public static  String table_name1 = "ItemDetails";
    public static final String col1 = "ItemID";
    public static final String col2 = "ItemName";
    public static final String col3 = "Quantity";
    public static final String col4 = "Price";

    public static  String table_name2 = "OrderMaster";
    public static final String colm1 = "OrderId";
    public static final String colm2 = "CustomerName";
    public static final String colm3 = "Discount";
    public static final String colm4 = "BillAmount";

    public static  String table_name3 = "OrderDetails";
    public static final String cold1 = colm1;
    public static final String cold2 = col1;
    public static final String cold3 = "QuantityPurchased";
    public static final String cold4 = "Amount";

    private static final String SQL_CREATE_TABLE_ItemDetails = "CREATE TABLE " + table_name1 + "("
            + col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + col2 + " TEXT NOT NULL, "
            + col3 + " REAL NOT NULL, "
            + col4 + " Real NOT NULL"
            +");";
    private static final String SQL_CREATE_TABLE_OrderMaster = "CREATE TABLE " + table_name2 + "("
            + colm1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + colm2 + " TEXT NOT NULL, "
            + colm3 + " REAL NOT NULL, "
            + colm4 + " Real NOT NULL "
            +");";
    private static final String SQL_CREATE_TABLE_OrderDetails = "CREATE TABLE " + table_name3 + "("
            + cold1 + " INTEGER NOT NULL, "
            + cold2 + " INTEGER NOT NULL, "
            + cold3 + " INTEGER NOT NUll, "
            + cold4 + " Real NOT NULL, FOREIGN KEY ("
            + cold1 + ") REFERENCES "
            + table_name2 + "("
            + colm1 + "), FOREIGN KEY ("
            + cold2 + ") REFERENCES "
            + table_name1 + "("
            + col1 + ")"
            +");";



    public dbHelper(@Nullable Context context) {
    super(context, Database_name, null, DATABASE_Version);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_OrderMaster);
        db.execSQL(SQL_CREATE_TABLE_ItemDetails);
        db.execSQL(SQL_CREATE_TABLE_OrderDetails);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS OrderMaster");
            db.execSQL("DROP TABLE IF EXISTS ItemDetails");
            db.execSQL("DROP TABLE IF EXISTS OrderDetails");

            onCreate(db);
        }
    }

    public boolean insertDataid(String c2, Double c3 , Double c4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

            contentValues.put(col2, c2);
            contentValues.put(col3, c3);
            contentValues.put(col4, c4);


        long result = db.insert("ItemDetails", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataom(String c2, Double c3 , Double c4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


            contentValues.put(colm2, c2);
            contentValues.put(colm3, c3);
            contentValues.put(colm4, c4);


        long result = db.insert("OrderMaster", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataod(Integer c1,Integer c2,  Integer c3 , Double c4){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

            contentValues.put(cold1, c1);
            contentValues.put(cold2, c2);
            contentValues.put(cold3, c3);
            contentValues.put(cold4, c4);


        long result = db.insert("OrderDetails", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDataid(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from ItemDetails", null);
        return res;

    }

    public Cursor getDataom(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from OrderMaster", null);
        return res;

    }

    public Cursor getDataod(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from OrderDetails", null);
        return res;

    }
}
