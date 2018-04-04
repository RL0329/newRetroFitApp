package com.example.ayabeltran.firstproject;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
//    boolean downloading = false;
    ProgressBar progressBar;
    String Key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        image = findViewById(R.id.dlImageView);
        name = findViewById(R.id.tvname);
        description = findViewById(R.id.tvdes);
        progressBar = findViewById(R.id.mProgressbar);

        btndl = findViewById(R.id.btnDL);

        Bundle extra = getIntent().getExtras();
        Key = extra.getString("Key");
        final String Key2 = extra.getString("Key2");
        final String Key3 = extra.getString("Key3");
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

//                    AlertDialog dialog = new SpotsDialog(Download.this);
//                    dialog.show();
//                    dialog.setMessage("Downloading...");


//                    String fileName = Key+".jpg";
//
//                    Picasso.get()
//                            .load(Key3)
//                            .into(new saveImageHelper(getBaseContext(),
//                                    dialog,
//                                    getApplicationContext().getContentResolver(),
//                                    fileName,Key2));


                DownloadData(uri, v);
//                btndl.setText("Play");
//
//                    if(downloading==true){
//
//                        Toast.makeText(Download.this, "DLtrue", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
////                        Toast.makeText(Download.this, "DLfalse", Toast.LENGTH_SHORT).show();
//
//                        dialog.dismiss();
//                        Toast.makeText(Download.this, "Image downloaded", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }



            }
        });

    }


    public long DownloadData(Uri uri, View v) {
//        downloading=true;

        final long downloadReference;

        // Create request for android download manager
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if (v.getId() == R.id.btnDL)
            request.setDestinationInExternalFilesDir(Download.this, Environment.DIRECTORY_DOWNLOADS, Key +".mp4");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadReference);

                    final Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    final int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    final int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {

                        downloading = false;


                    }

                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.setProgress(dl_progress);
                            System.out.println(dl_progress);

                            if(dl_progress == 100) {
                                btndl.setText("Play");
                            }

//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//
//                                @Override
//                                public void run() {
//
//                                    progressBar.setProgress(dl_progress);
//
//
//                                }
//                            }, 1000);

                        }
                    });

                    Log.d("Check Status",statusMessage(cursor));
                    cursor.close();
                }

            }
        }).start();

//        downloading=false;


        return downloadReference;
    }

    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                System.out.println(Environment.DIRECTORY_DOWNLOADS);
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }

        return (msg);
    }
}


