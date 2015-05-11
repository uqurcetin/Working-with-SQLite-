package com.example.uur.dbsqliteexample.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uur.dbsqliteexample.model.Company;
import com.example.uur.dbsqliteexample.model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uur on 1.2.2015.
 */
public class CompanyDAO {public static final String TAG = "CompanyDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_COMPANY_ID,
            DBHelper.COLUMN_COMPANY_NAME, DBHelper.COLUMN_COMPANY_ADDRESS,
            DBHelper.COLUMN_COMPANY_WEBSITE,
            DBHelper.COLUMN_COMPANY_PHONE_NUMBER };

    public CompanyDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Company createCompany(String name, String address, String website,
                                 String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_NAME, name);
        values.put(DBHelper.COLUMN_COMPANY_ADDRESS, address);
        values.put(DBHelper.COLUMN_COMPANY_WEBSITE, website);
        values.put(DBHelper.COLUMN_COMPANY_PHONE_NUMBER, phoneNumber);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_COMPANIES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_COMPANIES, mAllColumns,
                DBHelper.COLUMN_COMPANY_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Company newCompany = cursorToCompany(cursor);
        cursor.close();
        return newCompany;
    }

    public void deleteCompany(Company company) {
        long id = company.getmId();
        // delete all employees of this company
        EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        }

        System.out.println("the deleted company has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_COMPANIES, DBHelper.COLUMN_COMPANY_ID
                + " = " + id, null);
    }

    public List<Company> getAllCompanies() {
        List<Company> listCompanies = new ArrayList<Company>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_COMPANIES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Company company = cursorToCompany(cursor);
                listCompanies.add(company);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listCompanies;
    }

    public Company getCompanyById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_COMPANIES, mAllColumns,
                DBHelper.COLUMN_COMPANY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Company company = cursorToCompany(cursor);
        return company;
    }

    protected Company cursorToCompany(Cursor cursor) {
        Company company = new Company();
        company.setmId(cursor.getLong(0));
        company.setmNAme(cursor.getString(1));
        company.setmAddress(cursor.getString(2));
        company.setmWebsite(cursor.getString(3));
        company.setmPhoneNumber(cursor.getString(4));
        return company;
    }

}