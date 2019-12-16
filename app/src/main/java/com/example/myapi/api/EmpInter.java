package com.example.myapi.api;

import com.example.myapi.model.Employee;
import com.example.myapi.model.Flag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmpInter {

    @GET("employees")
    Call<List<Employee>> getEmployees();

    @GET("employee/{id}")
    Call<Employee>
    getEmployeeById(@Path("id") int id);

    @POST("create")
    Call<Void> addEmployee(@Body Employee employee);

    @PUT("update/{id}")
    Call<Void> updateEmployee(@Path("id") int id, @Body Employee employee);

    @GET("singleflag/{id}")
    Call<Flag> getFlagById(@Path("id") int id);

}
