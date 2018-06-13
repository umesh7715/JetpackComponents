package com.boisneyphilippe.githubarchitecturecomponents.repositories;



import android.arch.lifecycle.LiveData;

import com.boisneyphilippe.githubarchitecturecomponents.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.api.WebAPIInterface;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.PostDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PostRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 3;

    private final WebAPIInterface webservice;
    private final PostDao userDao;
    private final Executor executor;

    @Inject
    public PostRepository(WebAPIInterface webservice, PostDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }




    public LiveData<List<Post>> getPosts() {
        refreshUser(); // try to refresh data if possible from Github Api
        return userDao.getPosts(); // return a LiveData directly from the database.
    }

    private void refreshUser() {
        executor.execute(() -> {

            webservice.getPosts().enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                    // Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();

                    executor.execute(() -> {
                        List<Post> posts = response.body();

                        for (Post post : posts) {
                            userDao.insertPost(post);
                        }


                    });
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                }
            });

        });
    }

}
