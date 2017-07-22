package com.expose.safa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.expose.safa.modelclasses.Model;
import com.expose.safa.utilities.Utility;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineesh on 31/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SAFA_PRO";

    // TABLE_CATEGORY table name
    private static final String TABLE_CATEGORY = "PRO_CATEGORY";

    // TABLE_CATEGORY Table Columns names
    private static final String KEY_CID = "cName";
    private static final String KEY_CNAME = "cID";



    //BRANDS TABLE
    private static final String TABLE_BRAND = "PRO_BRAND";


    //BRANDS TABLE ColumnNames
    private static final String KEY_BID = "bName";
    private static final String KEY_BNAME = "bID";




    // TABLE_PRODUCT_DETAILS table name
    private static final String TABLE_PRODUCT_DETAILS = "PRO_DETAILS";

    // TABLE_CATEGORY Table Columns names
    private static final String KEY_PDetails = "Details";
    private static final String KEY_Pimage = "image";
    private static final String KEY_PItem_cName = "Item_cName";
    private static final String KEY_PUnit = "Unit";
    private static final String KEY_PItem_nId = "Item_nId";

    private static final String KEY_Item_nDefaultPrice = "Item_nDefaultPrice";
    private static final String KEY_Brand_nId = "Brand_nId";
    private static final String KEY_Category_nId = "Category_nId";





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_CID + " INTEGER,"
                + KEY_CNAME + " TEXT" + ")";

        db.execSQL(CREATE_CATEGORY_TABLE);





        String CREATE_BRANDS_TABLE = "CREATE TABLE " + TABLE_BRAND + "("
                + KEY_BID + " INTEGER,"
                + KEY_BNAME + " TEXT" + ")";

        db.execSQL(CREATE_BRANDS_TABLE);






        String CREATE_TABLE_PRODUCT_DETAILS = "CREATE TABLE " + TABLE_PRODUCT_DETAILS + "("
                + KEY_PDetails + " TEXT,"
                + KEY_PItem_cName + " TEXT,"
                + KEY_PUnit + " INTEGER,"
                + KEY_PItem_nId + " INTEGER,"
                + KEY_Pimage + " TEXT,"
                + KEY_Item_nDefaultPrice + " INTEGER,"
                + KEY_Brand_nId + " INTEGER,"
                + KEY_Category_nId + " INTEGER"

                + ")";

        db.execSQL(CREATE_TABLE_PRODUCT_DETAILS);





    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        // Create tables again
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_DETAILS);
        onCreate(db);


    }






    public void deleteCatogories( ) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CATEGORY, null, null);
        db.close();

    }





    public void addcategory(Model m) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CID, m.getItemCategory_nId());
        values.put(KEY_CNAME, m.getItemCategory_cName());
        // insert row
        db.insert(TABLE_CATEGORY, null, values);


        db.close();
    }





    // Getting All Contacts
    public List<Model> getAllCatogories() {
        List<Model> proList = new ArrayList<Model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model m = new Model();
                m.setItemCategory_nId(Integer.parseInt(cursor.getString(0)));
                m.setItemCategory_cName(cursor.getString(1));

                // Adding contact to list
                proList.add(m);
            } while (cursor.moveToNext());
        }

        // return contact list
        return proList;
    }







    public void addBrands(Model m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_BID, m.getBrand_nId());
        values.put(KEY_BNAME, m.getBrand_cName());

        db.insert(TABLE_BRAND, null, values);
        db.close();


    }






    public List<Model> getAllbrands() {
        List<Model> proList = new ArrayList<Model>();

        String selectQuery = "SELECT  * FROM " + TABLE_BRAND;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model m = new Model();
                m.setBrand_nId(Integer.parseInt(cursor.getString(0)));
                m.setBrand_cName(cursor.getString(1));

                // Adding contact to list
                proList.add(m);
            } while (cursor.moveToNext());

        }

        return proList;

    }






    public void deleteBrands( ) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BRAND, null, null);
        db.close();

    }





    public void addProducts(Model m) {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_PDetails, m.getDetails());
        values.put(KEY_PItem_cName, m.getItem_cName());
        values.put(KEY_PUnit, m.getUnit());
        values.put(KEY_PItem_nId,m.getItem_nId());
        values.put(KEY_Pimage, m.getImageurl());
        values.put(KEY_Item_nDefaultPrice,m.getItem_price());
        values.put(KEY_Brand_nId,m.getBrand_nId());
        values.put(KEY_Category_nId,m.getCategory_nId());

        db.insert(TABLE_PRODUCT_DETAILS, null, values);
        db.close();

    }






    public List<Model> getAllProducts() {

        List<Model> proList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model m = new Model();

                m.setDetails(cursor.getString(0));
                m.setItem_cName(cursor.getString(1));
                m.setUnit(cursor.getString(2));
                m.setItem_nId(Integer.parseInt(cursor.getString(3)));
                m.setImageurl(cursor.getString(4));
                m.setItem_price(Double.parseDouble(cursor.getString(5)));
                m.setBrand_nId(Integer.parseInt(cursor.getString(6)));
                m.setCategory_nId(Integer.parseInt(cursor.getString(7)));
                // Adding contact to list
                proList.add(m);
            } while (cursor.moveToNext());


        }

        return proList;

    }





    public void deleteProducts() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUCT_DETAILS, null, null);
        db.close();


    }







    public List<Model> getSelectedProducts(int category_selection, int brand_selection) {


        int category_select = category_selection;
        int brand_select = brand_selection;
        String selectQuery=null;

        SQLiteDatabase db;
        Cursor cursor;


        List<Model> proList = new ArrayList<>();

        //select All catogories

        if (category_selection ==0)
        {
            selectQuery = "select * from " + TABLE_PRODUCT_DETAILS
                    + " where "
                    + KEY_Brand_nId
                    + "="
                    + brand_selection;
        }

//select All Brands
        else if (brand_selection ==0)
        {
            selectQuery = "select * from " + TABLE_PRODUCT_DETAILS
                    + " where "
                    + KEY_Category_nId
                    + "="
                    + category_selection;
        }



        else
        {
            selectQuery = "select * from " + TABLE_PRODUCT_DETAILS
                    + " where "
                    + KEY_Category_nId
                    + "="
                    + category_selection
                    + " and "
                    + KEY_Brand_nId
                    + "="
                    + brand_selection;

        }

        db= this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Model m = new Model();


                m.setDetails(cursor.getString(0));
                m.setItem_cName(cursor.getString(1));
                m.setUnit(cursor.getString(2));
                m.setItem_nId(Integer.parseInt(cursor.getString(3)));
                m.setImageurl(cursor.getString(4));
                m.setItem_price(Double.parseDouble(cursor.getString(5)));
                m.setBrand_nId(Integer.parseInt(cursor.getString(6)));
                m.setCategory_nId(Integer.parseInt(cursor.getString(7)));

                // Adding contact to list
                proList.add(m);


            }
            while (cursor.moveToNext());

        }

        return proList;

    }




}
