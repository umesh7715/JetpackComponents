package com.boisneyphilippe.githubarchitecturecomponents.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.Resource;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.PostRepository;

import java.util.List;

import javax.inject.Inject;

public class PostListViewModel extends ViewModel {

    private PostRepository postRespository;

    @Inject
    public PostListViewModel(PostRepository postRespository) {
        this.postRespository = postRespository;
    }

    public LiveData<Resource<List<Post>>> getPostsWithDatabaseResource() {
        return postRespository.getPostsWithDatabaseResource("post");
    }

    public LiveData<ApiResponse<List<Post>>> getPostsWithoutDatabaseResource() {
        return postRespository.getPostsWithoutDatabaseResource();
    }


    public void updatePost(Post post) {
        postRespository.updatePost(post);
    }
}