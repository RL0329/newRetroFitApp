package com.example.ayabeltran.firstproject;

import java.util.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Lorenzo11 on 07/03/2018.
 */

public interface gitHubClient {
    @GET("/{user}")
    Call<java.util.List<GitHubRepo>>
    reposForUser(
            @Path("user") String user
    );

    @GET("/posts")
        ////////////////////////////////method/////////column///////////value
    Call<java.util.List<GitHubRepo>> eUser(@Query("username") String name);


    @GET("/posts")
    Call<java.util.List<GitHubRepo>> logIn(@Query("username")String uname, @Query("password") String pword);

    @GET("{comments}?_sort=id&_order=desc&_page=1&_limit=5")
    Call<java.util.ArrayList<ImgRepo>> displayimg(@Path("comments") String comments);

    @GET("{comments}?_sort=id&_order=desc&_page=1&_limit=12")
    Call<java.util.ArrayList<ImgRepo>> displaygridimg(@Path("comments") String comments);


    @POST("posts")
    Call<GitHubRepo>
    adduser(@Body GitHubRepo posts);

    @GET("comments")
    Call<java.util.ArrayList<ImgRepo>>fetchNewData (@Query("_sort") String id,
                                                    @Query("_order")String desc,
                                                    @Query("_limit") int limit,
                                                    @Query("_page") int pageNumber);

    @GET("comments")
    Call<java.util.ArrayList<ImgRepo>>fetchNewGridData (@Query("_sort") String id,
                                                    @Query("_order")String desc,
                                                    @Query("_limit") int limit,
                                                    @Query("_page") int pageNumber);

//    @GET("{comments}?_sort=id&_order=desc&_limit=5}")
//    Call<java.util.ArrayList<ImgRepo>>fetchNewData (@Path("comments") String comments);


//    @GET("{comments}?_sort=id&_order=desc")
//    Call<java.util.ArrayList<ImgRepo>>fetchNewGridData (@Path("comments") String comments);

    @POST("/comments")Call<ImgRepo> addimg(@Body ImgRepo value);

    @GET("{comments}")
    Call<java.util.ArrayList<ImgRepo>> allData (@Path("comments") String comments);



}
