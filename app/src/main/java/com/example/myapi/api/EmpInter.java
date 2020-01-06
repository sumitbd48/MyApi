package com.example.myapi.api;

import com.example.myapi.model.Employee;
import com.example.myapi.model.Flag;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @Multipart //for image
    @POST("upload")
    Call<Flag> uploadFlag(@Part MultipartBody.Part img); //image file data type MultipartBody

    @FormUrlEncoded
    @POST("addcountry")
    Call<Void> addCountry(@Field("country")String c,
                          @Field("file")String f);

}
