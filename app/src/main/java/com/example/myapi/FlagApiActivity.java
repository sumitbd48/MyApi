package com.example.myapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapi.api.EmpInter;
import com.example.myapi.model.Flag;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Button btnChoose, btnAdd;
    Uri uri;
    MultipartBody.Part image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_api);

        textView = findViewById(R.id.tvCountry);
        imageView = findViewById(R.id.ivFlag);
        btnChoose = findViewById(R.id.btnChoose);
        btnAdd = findViewById(R.id.btnAdd);

        getInstance();

//        getCountryById(5);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.
                                Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image);
                getImgReady();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 ){
            uri = data.getData();
            imageView.setImageURI(uri);
            askPermission();
        }
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else {
            getImgReady();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getImgReady();
            }
            else {
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getImgReady(){
        String [] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn,null,null,null);
        assert cursor !=null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgPath = cursor.getString(columnIndex);
        File file = new File(imgPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        image = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
    }

    private void uploadImage (MultipartBody.Part image){
        Call<Flag> flagUpload = empInter.uploadFlag(image);

        flagUpload.enqueue(new Callback<Flag>() {
            @Override
            public void onResponse(Call<Flag> call, Response<Flag> response) {
                Toast.makeText(FlagApiActivity.this, response.body().getFile() +"Uploaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Flag> call, Throwable t) {
                Log.d("Api Ex",t.toString());
            }
        });
    }

}

