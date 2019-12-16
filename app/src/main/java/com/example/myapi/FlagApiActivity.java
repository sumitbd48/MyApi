package com.example.myapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapi.api.EmpInter;
import com.example.myapi.model.Flag;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlagApiActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Retrofit retrofit;
    EmpInter empInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_api);

        textView = findViewById(R.id.tvCountry);
        imageView = findViewById(R.id.ivFlag);

        getInstance();
        getCountryById(5);
    }

    private void getInstance() {
        retrofit = new Retrofit.Builder().baseUrl("http://sujitg.com.np/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        empInter = retrofit.create(EmpInter.class);


    }

    private void getCountryById(int id){
        Call<Flag> flagCall = empInter.getFlagById(id);

        flagCall.enqueue(new Callback<Flag>() {
            @Override
            public void onResponse(Call<Flag> call, Response<Flag> response) {
                textView.setText(response.body().getCountry());
                Picasso.with(FlagApiActivity.this).load("http://sujitg.com.np/wc/teams/"+ response.body().getFile()).into(imageView);

            }

            @Override
            public void onFailure(Call<Flag> call, Throwable t) {
                Log.d("Api Ex",t.toString());
            }
        });
    }
}

