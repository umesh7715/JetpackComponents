package com.boisneyphilippe.githubarchitecturecomponents.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.adapters.RepositoryAdapter;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.githubSearch.SearchResult;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.GithubViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class GithubRespositorySearchFragment extends Fragment {

    @BindView(R.id.rvRepositoryResults)
    RecyclerView rvRepositoryResults;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private Unbinder binder;
    private GithubViewModel githubViewModel;
    private RepositoryAdapter repositoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_github_search_result_list, container, false);
        binder = ButterKnife.bind(this, view);

        rvRepositoryResults.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRepositoryResults.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {

        githubViewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubViewModel.class);
        githubViewModel.searchRepositoriesWithoutDatabaseResource().observe(this, this::updateUIGithubRespo);


    }



    private void updateUIGithubRespo(ApiResponse<SearchResult> searchResultApiResponse) {

        if (null != searchResultApiResponse && null != searchResultApiResponse.body) {

            repositoryAdapter = new RepositoryAdapter(getActivity(), searchResultApiResponse.body.getItems(), githubViewModel);
            rvRepositoryResults.setAdapter(repositoryAdapter);
        }
    }
}
