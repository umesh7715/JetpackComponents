package com.boisneyphilippe.githubarchitecturecomponents.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.githubSearch.SearchResult;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.GithubServiceRespository;

import javax.inject.Inject;

public class GithubViewModel extends ViewModel {

    private GithubServiceRespository githubServiceRespository;

    @Inject
    public GithubViewModel(GithubServiceRespository githubServiceRespository) {
        this.githubServiceRespository = githubServiceRespository;
    }

    public LiveData<ApiResponse<SearchResult>> searchRepositoriesWithoutDatabaseResource() {
        return githubServiceRespository.searchRepositoriesWithoutDatabaseResource("Android", 1, 30);
    }

}
