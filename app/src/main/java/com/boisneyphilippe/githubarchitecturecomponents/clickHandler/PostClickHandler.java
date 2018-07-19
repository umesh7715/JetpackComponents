package com.boisneyphilippe.githubarchitecturecomponents.clickHandler;

import android.app.Activity;
import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.PostListViewModel;

import javax.inject.Inject;


public class PostClickHandler {

    Context context;
    Activity activity;
    PostListViewModel postListViewModel;

    public PostClickHandler(Context context, PostListViewModel postListViewModel) {
        this.context = context;
        this.postListViewModel = postListViewModel;

    }


    @Inject
    public void onItemClickHandler(Post post) {
        post.setViews(post.getViews() + 1);
        //post.setTitle("Mu title");

       // postListViewModel.updatePost(post);


    }

}
