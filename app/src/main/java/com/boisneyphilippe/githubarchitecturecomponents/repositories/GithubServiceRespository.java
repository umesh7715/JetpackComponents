package com.boisneyphilippe.githubarchitecturecomponents.repositories;

import android.arch.lifecycle.LiveData;

import com.boisneyphilippe.githubarchitecturecomponents.api.GithubServiceAPI;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.githubSearch.SearchResult;
import com.boisneyphilippe.githubarchitecturecomponents.di.module.ExampleIntercepter;
import com.boisneyphilippe.githubarchitecturecomponents.executors.AppExecutors;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GithubServiceRespository {


    private static String BASE_URL = "https://api.github.com/";
    private final GithubServiceAPI webservice;
    private final AppExecutors executor;
    private final ExampleIntercepter exampleIntercepter;


    @Inject
    public GithubServiceRespository(GithubServiceAPI webservice, AppExecutors executor, ExampleIntercepter exampleIntercepter) {
        this.webservice = webservice;
        this.executor = executor;
        this.exampleIntercepter = exampleIntercepter;
        setBaseURL();
    }

    private void setBaseURL() {
        exampleIntercepter.setInterceptor(BASE_URL);
    }

    public LiveData<ApiResponse<SearchResult>> searchRepositoriesWithoutDatabaseResource(String query, int page, int itemPerPage) {
        return webservice.searchQuery(query, page, itemPerPage, "stars");
    }


}
