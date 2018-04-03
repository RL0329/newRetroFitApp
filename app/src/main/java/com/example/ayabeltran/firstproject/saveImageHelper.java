package com.example.ayabeltran.firstproject;import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by Lorenzo11 on 03/04/2018.
 */

public class saveImageHelper implements com.squareup.picasso.Target{


    private Context c;
    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;

    private String name;

    public saveImageHelper(Context c,AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.c = c;
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference =  new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    private String desc;

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        ContentResolver r = contentResolverWeakReference.get();
        AlertDialog dialog =alertDialogWeakReference.get();

        if(r != null){
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);
        }

        dialog.dismiss();

//        //open gallery after DL
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        c.startActivity(Intent.createChooser(i,"View Photo"));

    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
