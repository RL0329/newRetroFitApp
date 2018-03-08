package com.example.ayabeltran.firstproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class newImg extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
//    dbhelper mydb;

    EditText etnewimgname, etdesc;
    ImageView btnimg;
    Button btnaddimg;
    private static int SELECT_IMAGE = 1;
    private static int CAPTURE_IMAGE = 2 ;
    Uri selectedimage;
    Bitmap camImg;
    private String mImageUrl = "";
    public static final String URL = "http://10.16.33.98:3000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_img);

//        mydb = new dbhelper(this);


        etnewimgname = findViewById(R.id.etNewimgname);
        etdesc = findViewById(R.id.etDesc);
        btnimg = findViewById(R.id.btnImg);
        btnaddimg = findViewById(R.id.btnAddimg);


        btnimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(newImg.this);
                        View mview = getLayoutInflater().inflate(R.layout.dialog_add_image, null);
                        Button mbtnUseCam = mview.findViewById(R.id.btnUseCam);
                        Button mbtnUseGal = mview.findViewById(R.id.btnUseGal);
                        mbuilder.setView(mview);
                        final AlertDialog dialog = mbuilder.create();

                        mbtnUseCam.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(i, CAPTURE_IMAGE);
                                dialog.dismiss();
                            }
                        });

                        mbtnUseGal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(newImg.this,
                                new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                SELECT_IMAGE);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                }
        );
        btnaddimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddImage();
                    }
                }
        );
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
    protected void onActivityResult(int requestCode, int resultCode, Intent imgdata) {
        super.onActivityResult(requestCode, resultCode, imgdata);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && imgdata != null) {
            selectedimage = imgdata.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedimage);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                btnimg.setImageBitmap(yourselectedimage);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK && imgdata != null) {
            camImg =(Bitmap) imgdata.getExtras().get("img");
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
//        final byte[] data = getimagebyte(btnimg);
        final String imgdata = getBase64String(btnimg);

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
        else {
//            ImageRequest();
            Toast.makeText(newImg.this, "new photo added", Toast.LENGTH_SHORT).show();

            finish();
        }

//        mydb.addimg(imgdata, name, des);
    }


//    public static byte[] getimagebyte (ImageView imageView){
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte [] bytearray = stream.toByteArray();
//        return bytearray;
//    }

    private String getBase64String(ImageView imageView) {

        // give your image file url in mCurrentPhotoPath
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // In case you want to compress your image, here it's at 40%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void ImageRequest (byte[] imageBytes) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubClient rfclient = retrofit.create(gitHubClient.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);

        Call<Imagearr> call = rfclient.uploadImage(body);

        call.enqueue(new Callback<Imagearr>() {
            @Override
            public void onResponse(Call<Imagearr> call, Response<Imagearr> response) {

                if(response.isSuccessful()) {

                    Imagearr responseBody = response.body();
                    mImageUrl = URL + responseBody.getPath();
                    Toast.makeText(newImg.this,"New Photo Added",Toast.LENGTH_SHORT).show();

                }
                else {
                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        Imagearr errorResponse = gson.fromJson(errorBody.string(), Imagearr.class);
                        Toast.makeText(newImg.this,"Error",Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Imagearr> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
//        Call<ImageRepo> call = retrofitInterface.uploadImage(body);

//        Call<ImageRepo> uploadImagecall = rfclient.uploadImage(body);
//
//        uploadImagecall.enqueue(new Callback<ImageRepo>() {
//            @Override
//            public void onResponse(Call<ImageRepo> call, retrofit2.Response<ImageRepo> response) {
//
//
//                if (response.isSuccessful()) {
//
//                    ImageRepo responseBody = response.body();
//                    mImageUrl = URL + responseBody.getPath();
//                    Toast.makeText(newImg.class, "Photo saved!"+, Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    ResponseBody errorBody = response.errorBody();
//
//                    Gson gson = new Gson();
//
//                    try {
//
//                        ImageRepo errorResponse = gson.fromJson(errorBody.string(), ImageRepo.class);
//                        Toast.makeText(newImg.class, "Failed to upload photo!"+, Toast.LENGTH_SHORT).show();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ImageRepo> call, Throwable t) {
//
//                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
//            }
//        });
    }
}