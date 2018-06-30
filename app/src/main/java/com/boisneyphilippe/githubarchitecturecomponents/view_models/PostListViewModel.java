package com.boisneyphilippe.githubarchitecturecomponents.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.Resource;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.PostRepository;

import java.util.List;

import javax.inject.Inject;

public class PostListViewModel extends ViewModel {

    private LiveData<List<Post>> posts;
    private PostRepository postRespository;

    @Inject
    public PostListViewModel(PostRepository postRespository) {
        this.postRespository = postRespository;
    }

    public LiveData<Resource<List<Post>>> getPosts() {
        return postRespository.getPosts("post");
    }

}