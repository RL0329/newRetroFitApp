package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Download extends AppCompatActivity {

    ImageView image;
    TextView name,
            description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        image = findViewById(R.id.dlImageView);
        name = findViewById(R.id.tvname);
        description = findViewById(R.id.tvdes);

        Bundle extra = getIntent().getExtras();
        String  Key = extra.getString("Key");
        String  Key2 = extra.getString("Key2");
        String  Key3 = extra.getString("Key3");
//        byte[]  Key3 = extra.getByteArray("Key3");


        name.setText(Key);
        description.setText(Key2);
        Picasso.get()
                .load(Key3)
                .placeholder(R.drawable.ic_launcher_background)
                .into(image);

    }
}

