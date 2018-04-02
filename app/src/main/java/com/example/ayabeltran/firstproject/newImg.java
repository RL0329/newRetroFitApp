package com.example.ayabeltran.firstproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newImg extends AppCompatActivity {

    EditText etnewimgname, etdesc;
    ImageView btnimg;
    Button btnaddimg;
    private static int SELECT_IMAGE = 1;
    private static int CAPTURE_IMAGE = 2 ;
    Uri selectedimage;
    Bitmap camImg;
    Bitmap bitmap;

    String imgUrl;

    public static String[] mediaUrlList = {
//            "https://www.everythingcarers.org.au/media/1982/sample.jpg",
//            "https://orig00.deviantart.net/fa32/f/2009/346/5/b/8bit_mario_kart_by_killdoser666.jpg",
//            "https://ih1.redbubble.net/image.130551384.4550/flat,550x550,075,f.u1.jpg",
//            "https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/2T0t-6V/game-over-8bit-retro-4k-a-4k-game-over-screen-8-bit-retro-style_rmhi2u_e__S0001.jpg",
//            "https://cdn.makeuseof.com/wp-content/uploads/2012/01/8bit_mushroom_intro.jpg"
            "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_img);

        etnewimgname = findViewById(R.id.etNewimgname);
        etdesc = findViewById(R.id.etDesc);
        btnimg = findViewById(R.id.btnImg);
        btnaddimg = findViewById(R.id.btnAddimg);




//        btnimg.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(newImg.this);
//                        View mview = getLayoutInflater().inflate(R.layout.dialog_add_image, null);
//                        Button mbtnUseCam = mview.findViewById(R.id.btnUseCam);
//                        Button mbtnUseGal = mview.findViewById(R.id.btnUseGal);
//                        mbuilder.setView(mview);
//                        final AlertDialog dialog = mbuilder.create();
//
//                        mbtnUseCam.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(i, CAPTURE_IMAGE);
//                                dialog.dismiss();
//                            }
//                        });
//
//                        mbtnUseGal.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ActivityCompat.requestPermissions(newImg.this,
//                                new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
//                                SELECT_IMAGE);
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();
//
//                    }
//                }
//        );
        btnaddimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddImage();
                    }
                }
        );

        try {
            getImg();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getImg() throws MalformedURLException {

        Picasso.get()
                .load(getRandomUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(btnimg);
        }

    public String getRandomUrl() throws MalformedURLException {

        int rnd = new Random().nextInt(mediaUrlList.length);
        imgUrl = mediaUrlList[rnd];
        return imgUrl;

    }


    private String imageToString (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte [] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SELECT_IMAGE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, SELECT_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedimage = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedimage);
                bitmap = BitmapFactory.decodeStream(inputStream);
                btnimg.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK && data != null) {
            camImg =(Bitmap) data.getExtras().get("data");
            try {
                btnimg.setImageBitmap(camImg);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void AddImage(){
        final String name = etnewimgname.getText().toString();
        final String des = etdesc.getText().toString();

        if (name.isEmpty()){
            Toast.makeText(newImg.this, "please enter an image name.", Toast.LENGTH_SHORT).show();
            etnewimgname.requestFocus();
            return;
        }

        if(des.isEmpty()){
            Toast.makeText(newImg.this, "please describe image.", Toast.LENGTH_SHORT).show();
            etdesc.requestFocus();
            return;
        }


        ImgRepo newimgDetails = new ImgRepo(

                name,
                des,
                imgUrl
        );

        addImgRequest(newimgDetails);
        finish();


//        mydb.addimg(data, name, des);
//        Toast.makeText(newImg.this, "new photo added", Toast.LENGTH_SHORT).show();
//
//        finish();
    }





    ////////////////////////////////////////////////////////////////////////////////////////
    private void addImgRequest(ImgRepo value) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<ImgRepo> call = client.addimg(value);


        call.enqueue(new Callback<ImgRepo>() {
            @Override
            public void onResponse(Call<ImgRepo> call, Response<ImgRepo> response) {
                Toast.makeText(newImg.this,"img inserted",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ImgRepo> call, Throwable t) {
                Toast.makeText(newImg.this,"error in newimg class",Toast.LENGTH_SHORT).show();

            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////



//    public static byte[] getimagebyte (ImageView imageView){
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//        byte [] bytearray = stream.toByteArray();
//        return bytearray;
//    }
}