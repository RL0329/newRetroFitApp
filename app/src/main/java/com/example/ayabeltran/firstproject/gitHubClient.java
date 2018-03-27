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


    //                          create user
    @POST("users")
    Call<GitHubRepo>
    adduser(@Body GitHubRepo posts);

    @GET("/users")
        ////////////////////////////////method/////////column///////////value
    Call<java.util.List<GitHubRepo>> eUser(@Query("username") String name);


    //                          login
    @GET("/users")
    Call<java.util.List<GitHubRepo>> logIn(@Query("username")String uname, @Query("password") String pword);


    //                          intial 5
    @GET("{mediaItems}?_sort=id&_order=desc&_page=1&_limit=5")
    Call<java.util.ArrayList<ImgRepo>> displayimg(@Path("mediaItems") String mediaItems);

    @GET("{mediaItems}?_sort=id&_order=desc&_page=1&_limit=12")
    Call<java.util.ArrayList<ImgRepo>> displaygridimg(@Path("mediaItems") String mediaItems);




    //                          on scroll
    @GET("mediaItems")
    Call<java.util.ArrayList<ImgRepo>>fetchNewData (@Query("_sort") String id,
                                                    @Query("_order")String desc,
                                                    @Query("_limit") int limit,
                                                    @Query("_page") int pageNumber);

    @GET("mediaItems")
    Call<java.util.ArrayList<ImgRepo>>fetchNewGridData (@Query("_sort") String id,
                                                    @Query("_order")String desc,
                                                    @Query("_limit") int limit,
                                                    @Query("_page") int pageNumber);


    //                          adding image
    @POST("/mediaItems")Call<ImgRepo> addimg(@Body ImgRepo value);


    //                          calling all data from comments table
    @GET("{mediaItems}")
    Call<java.util.ArrayList<ImgRepo>> allData (@Path("mediaItems") String comments);

    @GET("comments/")
    Call<java.util.ArrayList<ImgRepo>>fetchSingleImg (@Query("idNo") int idNo);





}
