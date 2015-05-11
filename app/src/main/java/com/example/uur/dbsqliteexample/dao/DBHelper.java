package com.example.uur.dbsqliteexample.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Uur on 1.2.2015.
 */
public class DBHelper extends SQLiteOpenHelper
{
    public static final String TAG = "DBHelper";


    public static final String TABLE_COMPANIES ="companies";
    public static final String COLUMN_COMPANY_ID = "_id";
    public static final String COLUMN_COMPANY_NAME ="company_name";
    public static final String COLUMN_COMPANY_ADDRESS = "address";
    public static final String COLUMN_COMPANY_WEBSITE = "website";
    public static final String COLUMN_COMPANY_PHONE_NUMBER = "phone_number";

    public static final String TABLE_EMPLOYEES  = "employees";
    public static final String COLUMN_EMPLOYE_ID = COLUMN_COMPANY_ID;
    public static final String COLUMN_EMPLOYE_FIRST_NAME = "first_name";
    public static final String COLUMN_EMPLOYE_LAST_NAME = "last_name";
    public static final String COLUMN_EMPLOYE_ADDRESS = COLUMN_COMPANY_ADDRESS;
    public static final String COLUMN_EMPLOYE_EMAIL = "email";
    public static final String COLUMN_EMPLOYE_PHONE_NUMBER = COLUMN_COMPANY_PHONE_NUMBER;
    public static final String COLUMN_EMPLOYE_SALARY = "salary";
    public static final String COLUMN_EMPLOYE_COMPANY_ID = "company_id";

    private static final String DATABASE_NAME = "companies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String  SQL_CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + "("
            + COLUMN_EMPLOYE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMPLOYE_FIRST_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYE_LAST_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYE_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_EMPLOYE_EMAIL + " TEXT NOT NULL, "
            + COLUMN_EMPLOYE_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_EMPLOYE_SALARY + " REAL NOT NULL, "
            + COLUMN_EMPLOYE_COMPANY_ID + " INTEGER NOT NULL "
            +");";

    private static final String SQL_CREATE_TABLE_COMPANIES = "CREATE TABLE " + TABLE_COMPANIES + "("
            + COLUMN_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMPANY_NAME + " TEXT NOT NULL, "
            + COLUMN_COMPANY_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_COMPANY_WEBSITE + " TEXT NOT NULL, "
            + COLUMN_COMPANY_PHONE_NUMBER + " TEXT NOT NULL "
            +");";



    public DBHelper(Context context ) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_COMPANIES);
        db.execSQL(SQL_CREATE_TABLE_EMPLOYEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version" + oldVersion + "to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANIES);
        onCreate(db);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }


}
