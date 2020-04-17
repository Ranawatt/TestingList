package com.example.testinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testinglist.adapter.EmployeeAdapter;
import com.example.testinglist.databinding.ActivityMainBinding;
import com.example.testinglist.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private EmployeeAdapter employeeAdapter;
    private List<Datum> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView recyclerView = activityMainBinding.viewEmployees;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        employeeAdapter = new EmployeeAdapter();

//        mainViewModel.getAllEmployee().observe(this, new Observer<List<Employee>>() {
//            @Override
//            public void onChanged(List<Employee> data) {
//                employeeAdapter.setEmployeeDetail((ArrayList<Employee>) data);
//
//            }
//        });

        mainViewModel.getEmployeeData().observe(this, new Observer<List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                employeeAdapter.setEmployeeDetail((ArrayList<Datum>) data);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(employeeAdapter);
    }
}
