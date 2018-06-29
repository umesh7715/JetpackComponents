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

import com.boisneyphilippe.githubarchitecturecomponents.PostAdapter;
import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.PostListViewModel;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.UserProfileViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class PostListFragment extends Fragment {

    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;

    private Unbinder binder;
    private PostAdapter postAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private PostListViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        binder = ButterKnife.bind(this, view);

        rvPosts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);

        //initializePostList();

        return view;
    }

    private void initializePostList() {

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

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostListViewModel.class);
        viewModel.getPosts().observe(this, this::updateUI);

       // viewModel1 = ViewModelProviders.of(this, viewModelFactory).get(UserProfileViewModel.class);
       // viewModel1.getUser().observe(this, user -> updateUI(null));



    }

    private void updateUI(List<Post> user) {

        postAdapter = new PostAdapter(getActivity(),user);
        rvPosts.setAdapter(postAdapter);


    }
}
