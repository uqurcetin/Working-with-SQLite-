package com.example.uur.dbsqliteexample.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uur.dbsqliteexample.R;
import com.example.uur.dbsqliteexample.adapter.ListEmployeesAdapter;
import com.example.uur.dbsqliteexample.dao.EmployeeDAO;
import com.example.uur.dbsqliteexample.model.Employee;

public class ListEmployeesActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

	public static final String TAG = "ListEmployeesActivity";

	public static final int REQUEST_CODE_ADD_EMPLOYEE = 40;
	public static final String EXTRA_ADDED_EMPLOYEE = "extra_key_added_employee";
	public static final String EXTRA_SELECTED_COMPANY_ID = "extra_key_selected_company_id";

	private ListView mListviewEmployees;
	private TextView mTxtEmptyListEmployees;
	private ImageButton mBtnAddEmployee;

	private ListEmployeesAdapter mAdapter;
	private List<Employee> mListEmployees;
	private EmployeeDAO mEmployeeDao;

	private long mCompanyId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_employees);

		// initialize views
		initViews();

		// get the company id from extras
		mEmployeeDao = new EmployeeDAO(this);
		Intent intent  = getIntent();
		if(intent != null) {
			this.mCompanyId = intent.getLongExtra(EXTRA_SELECTED_COMPANY_ID, -1);
		}

		if(mCompanyId != -1) {
			mListEmployees = mEmployeeDao.getEmployeesOfCompany(mCompanyId);
			// fill the listView
			if(mListEmployees != null && !mListEmployees.isEmpty()) {
				mAdapter = new ListEmployeesAdapter(this, mListEmployees);
				mListviewEmployees.setAdapter(mAdapter);
			}
			else {
				mTxtEmptyListEmployees.setVisibility(View.VISIBLE);
				mListviewEmployees.setVisibility(View.GONE);
			}
		}
	}

	private void initViews() {
		this.mListviewEmployees = (ListView) findViewById(R.id.list_employees);
		this.mTxtEmptyListEmployees = (TextView) findViewById(R.id.txt_empty_list_employees);
		this.mBtnAddEmployee = (ImageButton) findViewById(R.id.btn_add_employee);
		this.mListviewEmployees.setOnItemClickListener(this);
		this.mListviewEmployees.setOnItemLongClickListener(this);
		this.mBtnAddEmployee.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_employee:
			Intent intent = new Intent(this, AddEmployeeActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ADD_EMPLOYEE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ADD_EMPLOYEE) {
			if(resultCode == RESULT_OK) {
				//refresh the listView
				if(mListEmployees == null || !mListEmployees.isEmpty()) {
					mListEmployees = new ArrayList<Employee>();
				}

				if(mEmployeeDao == null)
					mEmployeeDao = new EmployeeDAO(this);
				mListEmployees = mEmployeeDao.getEmployeesOfCompany(mCompanyId);
				
				if(mListEmployees != null && !mListEmployees.isEmpty() && 
						mListviewEmployees.getVisibility() != View.VISIBLE) {
					mTxtEmptyListEmployees.setVisibility(View.GONE);
					mListviewEmployees.setVisibility(View.VISIBLE);
				}
				
				if(mAdapter == null) {
					mAdapter = new ListEmployeesAdapter(this, mListEmployees);
					mListviewEmployees.setAdapter(mAdapter);
				}
				else {
					mAdapter.setItems(mListEmployees);
					mAdapter.notifyDataSetChanged();
				}
			}
		}
		else 
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEmployeeDao.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Employee clickedEmployee = mAdapter.getItem(position);
		Log.d(TAG, "clickedItem : "+clickedEmployee.getmFirstName()+" "+clickedEmployee.getmLastName());
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Employee clickedEmployee = mAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : "+clickedEmployee.getmFirstName()+" "+clickedEmployee.getmLastName());
		
		showDeleteDialogConfirmation(clickedEmployee);
		return true;
	}
	
	private void showDeleteDialogConfirmation(final Employee employee) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
        alertDialogBuilder.setTitle("Delete");
		alertDialogBuilder
				.setMessage("Are you sure you want to delete the employee \""
						+ employee.getmFirstName() + " "
						+ employee.getmLastName() + "\"");
 
        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// delete the employee and refresh the list
				if(mEmployeeDao != null) {
					mEmployeeDao.deleteEmployee(employee);
					
					//refresh the listView
					mListEmployees.remove(employee);
					if(mListEmployees.isEmpty()) {
						mListviewEmployees.setVisibility(View.GONE);
						mTxtEmptyListEmployees.setVisibility(View.VISIBLE);
					}

					mAdapter.setItems(mListEmployees);
					mAdapter.notifyDataSetChanged();
				}
				
				dialog.dismiss();
				Toast.makeText(ListEmployeesActivity.this, R.string.employee_deleted_successfully, Toast.LENGTH_SHORT).show();

			}
		});
        
        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Dismiss the dialog
                dialog.dismiss();
			}
		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
	}
}
