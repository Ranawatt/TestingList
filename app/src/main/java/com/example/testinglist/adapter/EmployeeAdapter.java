package com.example.testinglist.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testinglist.R;
import com.example.testinglist.databinding.EmployeeDetailBinding;
import com.example.testinglist.model.Datum;
import com.example.testinglist.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    private ArrayList<Employee> employees;
    private ArrayList<Datum> datumArrayList;


//    public EmployeeAdapter(ArrayList<Datum> employees) {
//        this.employees = employees;
//    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmployeeDetailBinding employeeDetailBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_detail,parent,false);
        return new EmployeeHolder(employeeDetailBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {

        Datum datum = datumArrayList.get(position);
        holder.employeeDetailBinding.setDatum(datum);
//        Employee employee = employees.get(position);
//        holder.employeeDetailBinding.setEmployee(employee);
    }

    @Override
    public int getItemCount() {

        return (datumArrayList != null)? datumArrayList.size():0;
//        return (employees != null)? employees.size():0;
    }

//    public void setEmployeeDetail(ArrayList<Employee> employees) {
//        this.employees = employees;
//        notifyDataSetChanged();
//    }

    public void setEmployeeDetail(ArrayList<Datum> employees) {
        this.datumArrayList = employees;
        notifyDataSetChanged();
    }
    public class EmployeeHolder extends RecyclerView.ViewHolder {
        private EmployeeDetailBinding employeeDetailBinding;
        public EmployeeHolder(@NonNull EmployeeDetailBinding employeeDetailBinding) {
            super(employeeDetailBinding.getRoot());
            this.employeeDetailBinding = employeeDetailBinding;
        }
    }
}
