package com.boisneyphilippe.githubarchitecturecomponents.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PostDao {

    @Insert(onConflict = REPLACE)
    void insertPost(Post post);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPosts(List<Post> posts);

    @Query("SELECT * FROM post WHERE id = :id")
    LiveData<Post> load(long id);

    @Query("SELECT * FROM post")
    LiveData<List<Post>> getPosts();
}
