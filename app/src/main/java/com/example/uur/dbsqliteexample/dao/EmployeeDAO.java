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
public class EmployeeDAO {
    public static final String TAG = "EmployeeDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_EMPLOYE_ID, DBHelper.COLUMN_EMPLOYE_FIRST_NAME,
            DBHelper.COLUMN_EMPLOYE_LAST_NAME, DBHelper.COLUMN_EMPLOYE_ADDRESS, DBHelper.COLUMN_EMPLOYE_EMAIL,
            DBHelper.COLUMN_EMPLOYE_PHONE_NUMBER,DBHelper.COLUMN_EMPLOYE_SALARY, DBHelper.COLUMN_EMPLOYE_COMPANY_ID };

    public EmployeeDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        }
        catch(SQLException e) {
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

    public Employee createEmploye(String firstName, String lastName, String address, String email, String phoneNumber, double salary, long companyId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMPLOYE_FIRST_NAME, firstName);
        values.put(DBHelper.COLUMN_EMPLOYE_LAST_NAME, lastName);
        values.put(DBHelper.COLUMN_EMPLOYE_ADDRESS, address);
        values.put(DBHelper.COLUMN_EMPLOYE_EMAIL, email);
        values.put(DBHelper.COLUMN_EMPLOYE_PHONE_NUMBER, phoneNumber);
        values.put(DBHelper.COLUMN_EMPLOYE_SALARY, salary);
        values.put(DBHelper.COLUMN_EMPLOYE_COMPANY_ID, companyId);
        long insertId = mDatabase.insert(DBHelper.TABLE_EMPLOYEES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES,
                mAllColumns, DBHelper.COLUMN_EMPLOYE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Employee newEmployee = cursorToEmploye(cursor);
        cursor.close();
        return newEmployee;
    }

    public void deleteEmployee(Employee employee) {
        long id = employee.getmId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYE_ID + " = " + id, null);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> listEmployees = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmploye(cursor);
            listEmployees.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listEmployees;
    }

    public List<Employee> getEmployeesOfCompany(long companyId) {
        List<Employee> listEmployees = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns
                , DBHelper.COLUMN_EMPLOYE_COMPANY_ID + " = ?",
                new String[] { String.valueOf(companyId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmploye(cursor);
            listEmployees.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listEmployees;
    }

    private Employee cursorToEmploye(Cursor cursor) {
        Employee employee = new Employee();
        employee.setmId(cursor.getLong(0));
        employee.setmFirstName(cursor.getString(1));
        employee.setmLastName(cursor.getString(2));
        employee.setmAddress(cursor.getString(3));
        employee.setmEmail(cursor.getString(4));
        employee.setmPhoneNumber(cursor.getString(5));
        employee.setmSalary(cursor.getDouble(6));

        // get The company by id
        long companyId = cursor.getLong(7);
        CompanyDAO dao = new CompanyDAO(mContext);
        Company company = dao.getCompanyById(companyId);
        if(company != null)
            employee.setmCompany(company);

        return employee;
    }

}
