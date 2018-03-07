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

public class UserLogin extends AppCompatActivity {

    dbhelper mydb;

    public static EditText etloginame, etloginpword;
    Button btn_login;
    SQLiteDatabase sqLiteDatabase;

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
                        mydb = new dbhelper(UserLogin.this);
                        sqLiteDatabase = mydb.getReadableDatabase();
                        String loginame = etloginame.getText().toString();
                        String loginpword = etloginpword.getText().toString();
                        Cursor res = mydb.userlogin(loginame, loginpword, sqLiteDatabase);

                        if(res.moveToFirst()){
                            Intent intent = new Intent(UserLogin.this,List.class);
                            startActivity(intent);

                            Toast.makeText(UserLogin.this, "welcome "+loginame+"!", Toast.LENGTH_SHORT).show();
                        }
                            else {
                                Toast.makeText(UserLogin.this, "your username and password do not match!", Toast.LENGTH_LONG).show();
                                etloginame.setText("");
                                etloginpword.setText("");
                                etloginame.requestFocus();
                            }
                    }
                }
        );
    }
}
