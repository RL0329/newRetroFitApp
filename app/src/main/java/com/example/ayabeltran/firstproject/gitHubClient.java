package com.example.ayabeltran.firstproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lorenzo11 on 07/03/2018.
 */

public interface gitHubClient {


    //                          create use
    @POST("posts")
    Call<GitHubRepo>
    adduser(@Body GitHubRepo posts);

    @GET("/posts")
        ////////////////////////////////method/////////column///////////value
    Call<java.util.List<GitHubRepo>> eUser(@Query("username") String name);


    //                          login
    @GET("/posts")
    Call<java.util.List<GitHubRepo>> logIn(@Query("username")String uname, @Query("password") String pword);


    //                          intial 5
    @GET("{comments}?_sort=id&_order=desc&_page=1&_limit=5")
    Call<java.util.ArrayList<ImgRepo>> displayimg(@Path("comments") String comments);

    @GET("{comments}?_sort=id&_order=desc&_page=1&_limit=12")
    Call<java.util.ArrayList<ImgRepo>> displaygridimg(@Path("comments") String comments);




    //                          on scroll
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


    //                          adding image
    @POST("/comments")Call<ImgRepo> addimg(@Body ImgRepo value);


    //                          calling all data from comments table
    @GET("{comments}")
    Call<java.util.ArrayList<ImgRepo>> allData (@Path("comments") String comments);



}
