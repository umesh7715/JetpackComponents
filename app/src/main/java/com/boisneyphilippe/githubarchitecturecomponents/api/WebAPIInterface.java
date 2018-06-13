package com.boisneyphilippe.githubarchitecturecomponents.api;


import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebAPIInterface {

    @GET("/posts")
    Call<List<Post>> getPosts();
}
