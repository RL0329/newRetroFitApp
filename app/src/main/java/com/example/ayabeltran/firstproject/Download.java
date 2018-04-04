package com.example.ayabeltran.firstproject;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class Download extends AppCompatActivity {

    ImageView image;
    TextView name,
            description;
    Button btndl;
    Uri uri;
    boolean downloading=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        image = findViewById(R.id.dlImageView);
        name = findViewById(R.id.tvname);
        description = findViewById(R.id.tvdes);

        btndl= findViewById(R.id.btnDL);

        Bundle extra = getIntent().getExtras();
        final String Key = extra.getString("Key");
        final String  Key2 = extra.getString("Key2");
        final String  Key3 = extra.getString("Key3");
        final String Key4 = extra.getString("Key4");
//        byte[]  Key3 = extra.getByteArray("Key3");

         uri = Uri.parse(Key4);

            name.setText(Key);
            description.setText(Key2);
            Picasso.get()
                    .load(Key3)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(image);


            btndl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog dialog = new SpotsDialog(Download.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");

//                    String fileName = Key+".jpg";
//
//                    Picasso.get()
//                            .load(Key3)
//                            .into(new saveImageHelper(getBaseContext(),
//                                    dialog,
//                                    getApplicationContext().getContentResolver(),
//                                    fileName,Key2));



                    DownloadData(uri, v);

                    if(downloading==true){
                        Toast.makeText(Download.this, "DLtrue", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                        Toast.makeText(Download.this, "DLfalse", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                        Toast.makeText(Download.this, "Image downloaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            });

    }



    private long DownloadData (Uri uri, View v) {
        downloading=true;

        long downloadReference;

        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if(v.getId() == R.id.btnDL)
            request.setDestinationInExternalFilesDir(Download.this, Environment.DIRECTORY_DOWNLOADS,"small.mp4");


        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        downloading=false;

        return downloadReference;
    }


}

