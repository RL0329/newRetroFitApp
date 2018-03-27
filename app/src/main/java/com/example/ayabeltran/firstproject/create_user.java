package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class create_user extends AppCompatActivity {

    Button btnreg2;
    EditText etemail,
             etuname,
             etpword,
             etcpword,
             etfname,
             etlname;

    public static final String baseURL = "http://10.20.110.30:3000";

    public static String image_url_1 = "http://sample-images.com/wp-content/uploads/2014/01/DSC06564.jpg";
    public static String image_url_2 = "http://sample-images.com/wp-content/uploads/2014/01/DSC06562.jpg";
    public static String image_url_3 = "http://sample-images.com/wp-content/uploads/2014/01/DSC06556.jpg";
    public static String image_url_4 = "http://sample-images.com/wp-content/uploads/2014/01/DSC06571.jpg";
    public static String image_url_5 = "http://sample-images.com/wp-content/uploads/2014/01/DSC07144.jpg";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        etemail = findViewById(R.id.etEmail);
        etuname = findViewById(R.id.etUname);
        etpword = findViewById(R.id.etPword);
        etcpword = findViewById(R.id.etCpword);
        etfname = findViewById(R.id.etFname);
        etlname = findViewById(R.id.etLname);
        btnreg2 = findViewById(R.id.btnReg2);



        Register();
    }

    public void Register() {
        btnreg2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddUser();
                    }
                });
    }
    public void AddUser(){


        if(etemail.getText().toString().isEmpty() || !etemail.getText().toString().contains("@")) {
            Toast.makeText(this, "Please enter an Email address.", Toast.LENGTH_SHORT).show();
            etemail.requestFocus();
            etemail.setText("");
            return;

        }
        if(etuname.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a username.", Toast.LENGTH_SHORT).show();
            etuname.requestFocus();
            etuname.setText("");
            return;
        }
        if(etpword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
            etpword.requestFocus();
            etpword.setText("");
            return;
        }
        if(!etcpword.getText().toString().equals(etpword.getText().toString())){
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            etcpword.requestFocus();
            etcpword.setText("");
            return;
        }
        if(etfname.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your 1st name.", Toast.LENGTH_SHORT).show();
            etfname.requestFocus();
            etfname.setText("");
            return;
        }
        if(etlname.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your surname.", Toast.LENGTH_SHORT).show();
            etlname.requestFocus();
            etlname.setText("");
            return;
        }

        existingUser();
    }

    private void addUserRequest(GitHubRepo newuser) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<GitHubRepo> call = client.adduser(newuser);


        call.enqueue(new Callback<GitHubRepo>() {
            @Override
            public void onResponse(Call<GitHubRepo> call, Response<GitHubRepo> response) {
               Toast.makeText(create_user.this,"user created with ID: " + response.body().getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GitHubRepo> call, Throwable t) {
                Toast.makeText(create_user.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void existingUser() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.List<GitHubRepo>> call = client.eUser(etuname.getText().toString());


        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

            List<GitHubRepo> mUsername = response.body();
                if(mUsername.isEmpty()){

                    GitHubRepo newuser = new GitHubRepo(
                            etemail.getText().toString(),
                            etuname.getText().toString(),
                            etpword.getText().toString(),
                            etfname.getText().toString(),
                            etlname.getText().toString()
                    );

                    addUserRequest(newuser);
                    Toast.makeText(create_user.this,"registered",Toast.LENGTH_SHORT).show();
                    finish();


                }
                else{
                    Toast.makeText(create_user.this, "username already exists", Toast.LENGTH_SHORT).show();
                    etuname.requestFocus();
                    etuname.setText("");
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                Toast.makeText(create_user.this,"con error",Toast.LENGTH_SHORT).show();

            }

        });

    }

}
