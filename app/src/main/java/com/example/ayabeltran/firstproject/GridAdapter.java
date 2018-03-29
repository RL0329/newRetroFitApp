package com.example.ayabeltran.firstproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.*;
import java.util.Base64;

/**
 * Created by ayabeltran on 01/02/2018.
 */


public class GridAdapter extends RecyclerView.Adapter <GridAdapter.MyViewHolder> {

    private LayoutInflater gInflater;
    private ArrayList<ImgRepo> places = new ArrayList<>();
    private Context context;

    public GridAdapter(ArrayList<ImgRepo> places, Context context) {
        this.places = places;
        this.context = context;
    }
    public ArrayList<ImgRepo> getPlaces() {
        return this.places;
    }


    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        gInflater = LayoutInflater.from(context);
        View view1 = gInflater.inflate(R.layout.activity_grid_view_layout, parent, false);
        GridAdapter.MyViewHolder holder = new GridAdapter.MyViewHolder(view1);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView photo;
        ImgRepo selectedPlace;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.photo = itemView.findViewById(R.id.imageViewGrid);
            this.name = itemView.findViewById(R.id.textViewGrid);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent preview = new Intent(context, ListDisplay.class);
//                    preview.putExtra("Key", selectedPlace.getImgname());
//                    preview.putExtra("Key2", selectedPlace.getDesc());
//                    preview.putExtra("Key3", selectedPlace.getImgstring());
//                    context.startActivity(preview);
//                }
//            });
        }
    }

    @Override
    public void onBindViewHolder(GridAdapter.MyViewHolder holder, int position) {

        //        getting the original photo from the list
        String originalPhoto = places.get(position).getImgstring();

        //        converting the photo bytes to usable image
//        final String pureBase64Encoded = originalPhoto.substring(originalPhoto.indexOf(",")  + 1);
//        final byte[] decodedBytes = android.util.Base64.decode(pureBase64Encoded, android.util.Base64.DEFAULT);


        holder.name.setText(places.get(position).getImgname());
        holder.selectedPlace = places.get(position);
//        Glide.with(context).load(decodedBytes).into(holder.photo);

        Picasso.get()
                .load(originalPhoto)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void addPlace(ImgRepo item) {
        places.add(item);
        notifyDataSetChanged();
    }

    public void addPlaces(ArrayList<ImgRepo> items) {
        places.addAll(items);
        notifyDataSetChanged();
    }


}