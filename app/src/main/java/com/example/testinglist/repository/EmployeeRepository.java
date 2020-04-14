package com.example.testinglist.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.testinglist.APIService;
import com.example.testinglist.RestClient;
import com.example.testinglist.model.Datum;
import com.example.testinglist.model.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {

    private static final String TAG = "EmployeeRepository";
    private ArrayList<Datum> datumArrayList = new ArrayList<>();
    private MutableLiveData<List<Datum>> mutableLiveDatum = new MutableLiveData<>();

    public EmployeeRepository() {
    }

    public MutableLiveData<List<Datum>> getMutableLiveDatum() {
        final APIService apiService = RestClient.getService();
        Call<EmployeeResponse> call = apiService.getEmployeeResponse();
        call.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                EmployeeResponse employeeResponse = response.body();
                if (employeeResponse != null && employeeResponse.getData() != null) {
                    datumArrayList = (ArrayList<Datum>) employeeResponse.getData();
                    mutableLiveDatum.setValue(datumArrayList);
                }
            }
            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
            }
        });
        return mutableLiveDatum;
    }

}
