package com.example.ayabeltran.firstproject;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.net.URI;
import java.security.Key;

public class PlayVideo extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        videoView = findViewById(R.id.vv);
        mediaC = new MediaController(this);


        String vidPath= "/storage/emulated/0/Android/data/com.example.ayabeltran.firstproject/files/Download/"+ Download.vidFileName+".mp4";
//        String vidPath= "/sdcard/"+ Download.vidFileName+".mp4";
        Uri uri = Uri.parse(vidPath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaC);
        mediaC.setAnchorView(videoView);
        videoView.start();
        Toast.makeText(this, vidPath, Toast.LENGTH_SHORT).show();
    }
}
