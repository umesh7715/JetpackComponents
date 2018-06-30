package com.boisneyphilippe.githubarchitecturecomponents.clickHandler;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.PostDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;

import javax.inject.Inject;
import javax.inject.Singleton;


public class PostClickHandler {

    Context context;
    Activity activity;

    public PostClickHandler(Context context) {
        this.context = context;

    }


    @Inject
    public void onItemClickHandler(Post post) {
        //Toast.makeText(context, "Title " + post.getTitle(), Toast.LENGTH_SHORT).show();
        post.setViews(post.getViews() + 1);
        post.setTitle("Mu title");


    }

}
