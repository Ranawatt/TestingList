package com.example.testinglist;

import com.example.testinglist.model.EmployeeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("v1/employees")
    Call<EmployeeResponse> getEmployeeResponse();
}
