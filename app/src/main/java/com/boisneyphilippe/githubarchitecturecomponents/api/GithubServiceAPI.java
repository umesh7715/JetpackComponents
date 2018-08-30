package com.boisneyphilippe.githubarchitecturecomponents.api;

import android.arch.lifecycle.LiveData;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.githubSearch.SearchResult;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubServiceAPI {

    @GET("/search/repositories")
    LiveData<ApiResponse<SearchResult>> searchQuery(@Query("q") String queryString,
                                                    @Query("page") int page,
                                                    @Query("per_page") int per_page,
                                                    @Query("sort") String sort);

    @GET("/search/repositories")
    Call<SearchResult> searchQuery1(@Query("q") String queryString,
                                    @Query("page") int page,
                                    @Query("per_page") int per_page,
                                    @Query("sort") String sort);
}
