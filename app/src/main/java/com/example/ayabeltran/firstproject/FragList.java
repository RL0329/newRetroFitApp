package com.example.ayabeltran.firstproject;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    ProgressBar progressBar,
                progressBar2;

    RecyclerAdapter recyclerAdapter;

    Boolean isScrolling = false;

    int rowCount;

    int pageNumber =1;

    boolean isfetching = false;

//    int totalItems;
//    int lastVisItem;

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


        loadData();
        EndlessScroll();

        mswipeRefreshLayout.setRefreshing(false);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadData();

                if (mswipeRefreshLayout.isRefreshing())

                    progressBar2.setVisibility(View.GONE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                            progressBar2.setVisibility(View.GONE);
                            mswipeRefreshLayout.setRefreshing(false);
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
        Call<java.util.ArrayList<ImgRepo>> call = client.allData("mediaItems");


        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>>() {
            @Override
            public void onResponse(Call<java.util.ArrayList<ImgRepo>> call, Response<ArrayList<ImgRepo>> response) {
                ArrayList<ImgRepo> mGetAllData = response.body();

                if (mGetAllData.isEmpty()) {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                }

                else {

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

        getDataFromServer();

        pageNumber=1;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>> call = client.displayimg("mediaItems");

        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>>() {
            @Override
            public void onResponse(Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>> response) {

                java.util.ArrayList<ImgRepo> repos = response.body();
                recyclerAdapter = new RecyclerAdapter(repos, getContext());
                recyclerView.setAdapter(recyclerAdapter);

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

              int totalItems = mLayoutManager.getItemCount();
              int lastVisItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                System.out.println("row count: " + rowCount);
                System.out.println("last visible item: " + lastVisItem);
                System.out.println("total items:" + totalItems);
                System.out.println("recycler count:" + recyclerAdapter.getItemCount());
                System.out.println("page no:" + pageNumber);


                if (isScrolling && (lastVisItem + 1) == totalItems && (totalItems < rowCount)) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            progressBar.setVisibility(View.GONE);
                            recyclerAdapter.notifyDataSetChanged();
                            isScrolling = false;

                            if(isfetching==true){


                                Toast.makeText(getActivity(), "is fetching true", Toast.LENGTH_SHORT).show();


                            }
                            else{
                                Toast.makeText(getActivity(), "is fetching false", Toast.LENGTH_SHORT).show();

                                pageNumber++;
                                fetchData();
                            }
                        }
                    }, 3000);
                    isfetching =false;
                }
            }

        });
    }

    private void fetchData() {

        isfetching =true;



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>>call = client.fetchNewData("id", "desc", 5,pageNumber);

        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>> () {
            @Override
            public void onResponse (Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>>response){

                java.util.ArrayList<ImgRepo> newdata = response.body();

                recyclerAdapter.addPlaces(newdata);

                recyclerAdapter.getItemCount();

//                isfetching = false;

            }

            @Override
            public void onFailure (Call<java.util.ArrayList<ImgRepo>>call, Throwable t){

                Toast.makeText(getActivity(), "error in fraglist", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

