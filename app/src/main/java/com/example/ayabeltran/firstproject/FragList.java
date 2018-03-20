package com.example.ayabeltran.firstproject;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.*;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragList extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mswipeRefreshLayout;

    ArrayList<Integer> places = new ArrayList();
    dbhelper mydb;
    SQLiteDatabase sqLiteDatabase;
    //    Cursor pulled;
    ProgressBar progressBar,
            progressBar2;
    RecyclerAdapter recyclerAdapter;

    Boolean isScrolling = false;
    public static int totalItems;
    int rowCount;
    int listSize;

    public FragList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_frag_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);
        mswipeRefreshLayout = v.findViewById(R.id.swiperefresh);
        progressBar = v.findViewById(R.id.progress);
        progressBar2 = v.findViewById(R.id.progress2);


        // adapter
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

//        mydb = new dbhelper(getActivity());
//        sqLiteDatabase = mydb.getReadableDatabase();

//        refreshList();


        getDataFromServer();
        loadData();
        EndlessScroll();

        mswipeRefreshLayout.setRefreshing(false);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                places.clear();

                loadData();
//              refreshList();

                if (mswipeRefreshLayout.isRefreshing())

                    progressBar2.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {

//                            recyclerAdapter.notifyDataSetChanged();
                            progressBar2.setVisibility(View.GONE);
                            mswipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 3000);

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getDataFromServer() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>> call = client.allData("comments");


        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>>() {
            @Override
            public void onResponse(Call<java.util.ArrayList<ImgRepo>> call, Response<ArrayList<ImgRepo>> response) {
                ArrayList<ImgRepo> mGetAllData = response.body();

                if (mGetAllData.isEmpty()) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                } else {

                    rowCount = mGetAllData.size();
                }


            }

            @Override
            public void onFailure(Call<java.util.ArrayList<ImgRepo>> call, Throwable t) {

                Toast.makeText(getActivity(), "error in fraglist", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadData() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>> call = client.displayimg("comments");

        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>>() {
            @Override
            public void onResponse(Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>> response) {

                Toast.makeText(getContext(), "Load", Toast.LENGTH_SHORT).show();
                java.util.ArrayList<ImgRepo> repos = response.body();
                recyclerAdapter = new RecyclerAdapter(repos, getContext());
                recyclerView.setAdapter(recyclerAdapter);

                int count = repos.size();

                for(int i=0;i<count;i++){
                  places.add(i);
                }
            }

            @Override
            public void onFailure(Call<java.util.ArrayList<ImgRepo>> call, Throwable t) {

                Toast.makeText(getActivity(), "error in fraglist", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void EndlessScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItems = mLayoutManager.getItemCount();
//                pulled = pulledItems(sqLiteDatabase);


                int listSize = places.size();
                int itemsOnScreen = mLayoutManager.getChildCount();
                int lastVisItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                System.out.println("row count: " + rowCount);
                System.out.println("list size: " + listSize);
                System.out.println("last visible item: " + lastVisItem);
                System.out.println("items on screen: " + itemsOnScreen);
                System.out.println("total items:" + totalItems);

                if (isScrolling && (lastVisItem + 2) > listSize && (listSize < 15)) {

                    fetchData();
                    isScrolling = false;

                }
            }

        });
    }
//private void displayimg(){
//
//}
//
//    public Cursor pulledItems(SQLiteDatabase db) {
//        String pull = "select * from imgTable order by " + dbhelper.imgID + " desc limit 5 offset " + (totalItems);
//        Log.d("pull", pull);
//        Cursor cursor = db.rawQuery(pull, null);
//        return cursor;
//    }

//    private ArrayList<ImgRepo> convertCursorToListPlace(Cursor cursor) {
//        ArrayList<ImgRepo> places = new ArrayList<>();

//        if (cursor.moveToFirst()) {
//            do {
//                int id;
//                String name, des;
//                byte[] photo;
//
//                id = cursor.getInt(cursor.getColumnIndex("id"));
//                photo = cursor.getBlob(cursor.getColumnIndex("photo"));
//                name = cursor.getString(cursor.getColumnIndex("name"));
//                des = cursor.getString(cursor.getColumnIndex("des"));
//
//                ImgRepo place = new Place(id, photo, name, des);
//                places.add(place);
//            }
//            while (cursor.moveToNext());
//
//        }
//
//        return places;
    //}

//    private void refreshList() {
//        Cursor cursor = getItemsFromDB();
//        populateList(cursor);
//
//    }

//    private Cursor getItemsFromDB() {
//        return mydb.itemslisted(sqLiteDatabase);
//    }

    //    private void populateList(Cursor cursor) {
//        places = convertCursorToListPlace(cursor);
//
//        recyclerAdapter = new RecyclerAdapter(places, getContext());
//
//        recyclerView.setAdapter(recyclerAdapter);
//    }
//
    private void fetchData() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>>call = client.fetchNewData("comments");
        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>> () {
            @Override
            public void onResponse (Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>>response){

                java.util.ArrayList<ImgRepo> newdata = response.body();
                recyclerAdapter = new RecyclerAdapter(newdata, getContext());
                recyclerView.setAdapter(recyclerAdapter);

                int count = newdata.size();

                for(int i=0;i<count;i++){
                    places.add(i);
                }



            }

            @Override
            public void onFailure (Call<java.util.ArrayList<ImgRepo>>call, Throwable t){

                Toast.makeText(getActivity(), "error in fraglist", Toast.LENGTH_SHORT).show();
            }
        });


//        if (pulled.moveToFirst()) {
//            do {
//                int id;
//                String name, des;
//                byte[] photo;
//
//                id = pulled.getInt(pulled.getColumnIndex("id"));
//                photo = pulled.getBlob(pulled.getColumnIndex("photo"));
//                name = pulled.getString(pulled.getColumnIndex("name"));
//                des = pulled.getString(pulled.getColumnIndex("des"));
//
//                ImgRepo places = new ImgRepo(id, photo, name, des);
//                recyclerAdapter.getPlaces().add(places);
//
//            }
//            while (pulled.moveToNext());
//
//
//            String count = String.valueOf(totalItems);
//            Toast.makeText(getActivity(), count, Toast.LENGTH_SHORT).show();
//        }

//        progressBar.setVisibility(View.VISIBLE);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {

//            @Override
//            public void run() {
////                for (int i = 0; i < 3; i++) {
//
////                recyclerAdapter.notifyDataSetChanged();
//                fetchData();
//                progressBar.setVisibility(View.GONE);
//
////                }
//
//            }

//
//        },0);
    }
}

