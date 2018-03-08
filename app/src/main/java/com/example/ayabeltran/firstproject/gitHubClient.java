package com.example.ayabeltran.firstproject;

import java.util.*;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Lorenzo11 on 07/03/2018.
 */

public interface gitHubClient {
    @GET("/{user}")
    Call<java.util.List<GitHubRepo>> reposForUser(
            @Path("user") String user
    );

    @POST("posts")
    Call<GitHubRepo> adduser(@Body GitHubRepo posts);

    @Multipart
    @POST("comments")
    Call<Imagearr> uploadImage(@Part MultipartBody.Part image);
}
