package com.example.uur.dbsqliteexample.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uur.dbsqliteexample.R;
import com.example.uur.dbsqliteexample.model.Employee;

public class ListEmployeesAdapter extends BaseAdapter {
	
	public static final String TAG = "ListEmployeesAdapter";
	
	private List<Employee> mItems;
	private LayoutInflater mInflater;
	
	public ListEmployeesAdapter(Context context, List<Employee> listCompanies) {
		this.setItems(listCompanies);
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Employee getItem(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
	}

	@Override
	public long getItemId(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getmId() : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if(v == null) {
			v = mInflater.inflate(R.layout.list_item_emloyee, parent, false);
			holder = new ViewHolder();
			holder.txtEmployeeName = (TextView) v.findViewById(R.id.txt_employee_name);
			holder.txtCompanyName = (TextView) v.findViewById(R.id.txt_company_name);
			holder.txtAddress = (TextView) v.findViewById(R.id.txt_address);
			holder.txtPhoneNumber = (TextView) v.findViewById(R.id.txt_phone_number);
			holder.txtEmail = (TextView) v.findViewById(R.id.txt_email);
			holder.txtSalary = (TextView) v.findViewById(R.id.txt_salary);
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		// fill row data
		Employee currentItem = getItem(position);
		if(currentItem != null) {
			holder.txtEmployeeName.setText(currentItem.getmFirstName()+" "+currentItem.getmLastName());
			holder.txtAddress.setText(currentItem.getmAddress());
			holder.txtEmail.setText(currentItem.getmEmail());
			holder.txtPhoneNumber.setText(currentItem.getmPhoneNumber());
			holder.txtSalary.setText(String.valueOf(currentItem.getmSalary())+" $");
			holder.txtPhoneNumber.setText(currentItem.getmPhoneNumber());
			holder.txtCompanyName.setText(currentItem.getmCompany().getmNAme());
		}
		
		return v;
	}
	
	public List<Employee> getItems() {
		return mItems;
	}

	public void setItems(List<Employee> mItems) {
		this.mItems = mItems;
	}

	class ViewHolder {
		TextView txtEmployeeName;
		TextView txtEmail;
		TextView txtPhoneNumber;
		TextView txtAddress;
		TextView txtCompanyName;
		TextView txtSalary;
	}

}
