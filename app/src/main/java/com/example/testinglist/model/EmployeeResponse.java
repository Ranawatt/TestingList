package com.example.testinglist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeResponse {

//    @SerializedName("page")
//    @Expose
//    private Integer page;
//    @SerializedName("per_page")
//    @Expose
//    private Integer perPage;
//    @SerializedName("total")
//    @Expose
//    private Integer total;
//    @SerializedName("total_pages")
//    @Expose
//    private Integer totalPages;
//    @SerializedName("data")
//    @Expose
//    private List<Employee> employee = null;
//    public Integer getPage() {
//        return page;
//    }
//    public void setPage(Integer page) {
//        this.page = page;
//    }
//    public Integer getPerPage() {
//        return perPage;
//    }
//    public void setPerPage(Integer perPage) {
//        this.perPage = perPage;
//    }
//    public Integer getTotal() {
//        return total;
//    }
//    public void setTotal(Integer total) {
//        this.total = total;
//    }
//    public Integer getTotalPages() {
//        return totalPages;
//    }
//    public void setTotalPages(Integer totalPages) {
//        this.totalPages = totalPages;
//    }
//    public List<Employee> getEmployee() {
//        return employee;
//    }
//    public void setEmployee(List<Employee> employee) {
//        this.employee = employee;
//    }


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
