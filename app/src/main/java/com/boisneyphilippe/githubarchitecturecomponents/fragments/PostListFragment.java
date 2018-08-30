package com.boisneyphilippe.githubarchitecturecomponents.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.adapters.PostAdapter;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.Resource;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.PostListViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class PostListFragment extends Fragment {

    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private Unbinder binder;
    private PostAdapter postAdapter;
    private PostListViewModel viewModel;
    private List<Post> postList;
    private LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        binder = ButterKnife.bind(this, view);

        rvPosts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);
        postList = new ArrayList<>();

        //initializePostList();

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

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(PostListViewModel.class);
        viewModel.getPostsWithoutDatabaseResource().observe(getActivity(), this::updateUI);

    }

    private void updateUI(ApiResponse<List<Post>> listApiResponse) {

        if (null != listApiResponse.body) {
            postAdapter = new PostAdapter(getActivity(), listApiResponse.body, viewModel, layoutManager);
            rvPosts.setAdapter(postAdapter);
           // myRef.setValue(listApiResponse.body);
        }
    }

    public void updateUI(Resource<List<Post>> listResource) {

        if (null != listResource.data) {
            postAdapter = new PostAdapter(getActivity(), listResource.data, viewModel, layoutManager);
            rvPosts.setAdapter(postAdapter);
        }
    }


}
