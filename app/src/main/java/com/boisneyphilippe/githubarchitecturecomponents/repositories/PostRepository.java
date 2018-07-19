package com.boisneyphilippe.githubarchitecturecomponents.repositories;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.boisneyphilippe.githubarchitecturecomponents.api.WebAPIInterface;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.PostDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.executors.AppExecutors;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.ApiResponse;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.NetworkBoundResource;
import com.boisneyphilippe.githubarchitecturecomponents.networkBoundResource.Resource;
import com.boisneyphilippe.githubarchitecturecomponents.utils.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PostRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 3;

    private final WebAPIInterface webservice;
    private final PostDao postDao;
    private final AppExecutors executor;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(FRESH_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);

    @Inject
    public PostRepository(WebAPIInterface webservice, PostDao userDao, AppExecutors executor) {
        this.webservice = webservice;
        this.postDao = userDao;
        this.executor = executor;
    }


    public LiveData<Resource<List<Post>>> getPostsWithDatabaseResource(String owner) {
        return new NetworkBoundResource<List<Post>, List<Post>>(executor, true) {
            @Override
            protected void saveCallResult(@NonNull List<Post> item) {
                postDao.insertPosts(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Post> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner);
            }

            @Override
            protected LiveData<List<Post>> performDatabaseOperation() {
                return postDao.getPosts();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Post>>> createCall() {
                return webservice.getPosts();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(owner);
            }
        }.asLiveData();
    }


    public LiveData<Resource<Post>> getPost(String owner, long id) {

        return new NetworkBoundResource<Post, Post>(executor, true) {
            @Override
            protected void saveCallResult(@NonNull Post item) {
                postDao.insertPost(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Post data) {
                return data == null || repoListRateLimit.shouldFetch(owner);
            }

            @NonNull
            @Override
            protected LiveData<Post> performDatabaseOperation() {
                return postDao.load(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Post>> createCall() {
                return webservice.getPost(id);
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(owner);
            }
        }.asLiveData();
    }


    public LiveData<ApiResponse<List<Post>>> getPostsWithoutDatabaseResource() {
        return webservice.getPosts();
    }


    public void updatePost(Post post) {
        executor.networkIO().execute(() -> postDao.updatePost(post));
    }
}
