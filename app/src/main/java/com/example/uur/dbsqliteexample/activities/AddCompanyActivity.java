package com.example.uur.dbsqliteexample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uur.dbsqliteexample.R;
import com.example.uur.dbsqliteexample.dao.CompanyDAO;
import com.example.uur.dbsqliteexample.model.Company;

public class AddCompanyActivity extends Activity implements OnClickListener {

	public static final String TAG = "AddCompanyActivity";

	private EditText mTxtCompanyName;
	private EditText mTxtAddress;
	private EditText mTxtPhoneNumber;
	private EditText mTxtWebsite;
	private Button mBtnAdd;

	private CompanyDAO mCompanyDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_company);

		initViews();

		this.mCompanyDao = new CompanyDAO(this);
	}

	private void initViews() {
		this.mTxtCompanyName = (EditText) findViewById(R.id.txt_company_name);
		this.mTxtAddress = (EditText) findViewById(R.id.txt_address);
		this.mTxtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
		this.mTxtWebsite = (EditText) findViewById(R.id.txt_website);
		this.mBtnAdd = (Button) findViewById(R.id.btn_add);

		this.mBtnAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			Editable companyName = mTxtCompanyName.getText();
			Editable address = mTxtAddress.getText();
			Editable phoneNumber = mTxtPhoneNumber.getText();
			Editable website = mTxtWebsite.getText();
			if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(address)
					&& !TextUtils.isEmpty(website)
					&& !TextUtils.isEmpty(phoneNumber)) {
				// add the company to database
				Company createdCompany = mCompanyDao.createCompany(
						companyName.toString(), address.toString(),
						website.toString(), phoneNumber.toString());
				
				Log.d(TAG, "added company : "+ createdCompany.getmNAme());
				Intent intent = new Intent();
				intent.putExtra(ListCompaniesActivity.EXTRA_ADDED_COMPANY, createdCompany);
				setResult(RESULT_OK, intent);
				Toast.makeText(this, R.string.company_created_successfully, Toast.LENGTH_LONG).show();
				finish();
			}
			else {
				Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCompanyDao.close();
	}
}
