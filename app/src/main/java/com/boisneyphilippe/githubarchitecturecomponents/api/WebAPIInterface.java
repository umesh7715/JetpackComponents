package com.boisneyphilippe.githubarchitecturecomponents.api;


import android.arch.lifecycle.LiveData;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebAPIInterface {

    @GET("/posts")
    LiveData<ApiResponse<List<Post>>> getPosts();

    @GET("/posts")
    LiveData<ApiResponse<Post>> getPost(long postId);
}
