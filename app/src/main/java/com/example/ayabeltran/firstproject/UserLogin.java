package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class UserLogin extends AppCompatActivity {

    dbhelper mydb;

    public static EditText etloginame, etloginpword;
    Button btn_login;
//    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mydb = new dbhelper(this);

        etloginame =  findViewById(R.id.etLoginUname);
        etloginpword = findViewById(R.id.etLoginPword);
        btn_login = findViewById(R.id.btn_Login);

        login();
    }

    public void login(){
        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String loginame = etloginame.getText().toString();
                        String loginpword = etloginpword.getText().toString();


                        if(loginame.isEmpty()){
                            Toast.makeText(UserLogin.this, "enter a username", Toast.LENGTH_LONG).show();
                            etloginame.setText("");
                            etloginame.requestFocus();
                        }
                        if(loginpword.isEmpty()){
                            Toast.makeText(UserLogin.this, "enter a password", Toast.LENGTH_LONG).show();
                            etloginpword.setText("");
                            etloginpword.requestFocus();
                        }

                        serverLogin();


//                        mydb = new dbhelper(UserLogin.this);
//                        sqLiteDatabase = mydb.getReadableDatabase();
//                        Cursor res = mydb.userlogin(loginame, loginpword, sqLiteDatabase);
//
//                        if(res.moveToFirst()){
//                            Intent intent = new Intent(UserLogin.this,List.class);
//                            startActivity(intent);
//
//                            Toast.makeText(UserLogin.this, "welcome "+loginame+"!", Toast.LENGTH_SHORT).show();
//                        }
//                            else {
//                                Toast.makeText(UserLogin.this, "your username and password do not match!", Toast.LENGTH_LONG).show();
//                                etloginame.setText("");
//                                etloginpword.setText("");
//                                etloginame.requestFocus();
//                            }

                    }
                }
        );
    }

    private void serverLogin(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.List<GitHubRepo>> call = client.logIn(etloginame.getText().toString(),etloginpword.getText().toString());


        call.enqueue(new Callback<java.util.List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> mLogIn = response.body();
                if(mLogIn.isEmpty()){
                    Toast.makeText(UserLogin.this,"your login credentials do not match",Toast.LENGTH_SHORT).show();
                    etloginame.setText("");
                    etloginpword.setText("");
                    etloginame.requestFocus();
                }
                else{
                    Toast.makeText(UserLogin.this,"they match",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(UserLogin.this, com.example.ayabeltran.firstproject.List.class);
                    startActivity(i);
                    etloginame.setText("");
                    etloginpword.setText("");




//                    Intent i = new Intent(UserLogin.this,List.class);
//                    startActivity(i);

                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                Toast.makeText(UserLogin.this,"error in login",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
