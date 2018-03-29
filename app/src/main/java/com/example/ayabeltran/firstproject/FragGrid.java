package com.example.ayabeltran.firstproject;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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


public class FragGrid extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mswipeRefreshLayout;

    ProgressBar progressBar,
                progressBar2;

    GridAdapter gridAdapter;

    Boolean isScrolling = false;

    int rowCount;

    int gridPageNumber =1;

    boolean isfetching = false;



    public FragGrid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_frag_grid, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);
        mswipeRefreshLayout = v.findViewById(R.id.swiperefresh);
        progressBar = v.findViewById(R.id.progress);
        progressBar2 = v.findViewById(R.id.progress2);


        // adapter
        mLayoutManager = new GridLayoutManager(getActivity(),3);
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


        gridPageNumber = 1;

        getDataFromServer();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>> call = client.displaygridimg("mediaItems");

        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>>() {
            @Override
            public void onResponse(Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>> response) {

                java.util.ArrayList<ImgRepo> repos = response.body();
                gridAdapter = new GridAdapter(repos, getContext());
                recyclerView.setAdapter(gridAdapter);

            }

            @Override
            public void onFailure(Call<java.util.ArrayList<ImgRepo>> call, Throwable t) {

                Toast.makeText(getActivity(), "error in fraggrid", Toast.LENGTH_SHORT).show();
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

                int gridtotalItems = mLayoutManager.getItemCount();
                int gridlastVisItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                System.out.println("row count: " + rowCount);
                System.out.println("grid last visible item: " + gridlastVisItem);
                System.out.println("gridtotal items:" + gridtotalItems);
                System.out.println("grid page no:" + gridPageNumber);



                if (isScrolling && (gridlastVisItem + 1) == gridtotalItems && (gridtotalItems < rowCount)) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            progressBar.setVisibility(View.GONE);
                            gridAdapter.notifyDataSetChanged();
                            isScrolling = false;

                            if(isfetching==true){

                                Toast.makeText(getActivity(),"fetching true",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity(),"fetching false",Toast.LENGTH_SHORT).show();


                                fetchData();
                            }
                        }
                    }, 3000);

                }
            }

        });
    }

    private void fetchData() {

        isfetching =true;

        gridPageNumber++;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(create_user.baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        gitHubClient client = retrofit.create(gitHubClient.class);
        Call<java.util.ArrayList<ImgRepo>>call = client.fetchNewGridData("id","desc",12,gridPageNumber );
        call.enqueue(new Callback<java.util.ArrayList<ImgRepo>> () {
            @Override
            public void onResponse (Call<java.util.ArrayList<ImgRepo>> call, Response<java.util.ArrayList<ImgRepo>>response){

                java.util.ArrayList<ImgRepo> newdata = response.body();

                gridAdapter.addPlaces(newdata);

                gridAdapter.getItemCount();

                isfetching = false;

            }

            @Override
            public void onFailure (Call<java.util.ArrayList<ImgRepo>>call, Throwable t){

                Toast.makeText(getActivity(), "error in fraggrid", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

