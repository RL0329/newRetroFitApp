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

    @GET("{comments}")
    Call<java.util.ArrayList<ImgRepo>> displayimg(@Path("comments") String comments);


    @POST("posts")
    Call<GitHubRepo>
    adduser(@Body GitHubRepo posts);

    @POST("/comments")Call<ImgRepo> addimg(@Body ImgRepo value);



}
