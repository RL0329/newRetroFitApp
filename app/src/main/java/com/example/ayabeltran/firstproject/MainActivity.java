package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnreg1;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logIn();
    }
    public void logIn(){
        btnreg1 = findViewById(R.id.btnReg1);
        Login = findViewById(R.id.btnLogin);

        //on clicks
        btnreg1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,create_user.class);
                        startActivity(i);
                    }
                }
        );
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UserLogin.class);
                startActivity(i);
            }
        });
    }
}
