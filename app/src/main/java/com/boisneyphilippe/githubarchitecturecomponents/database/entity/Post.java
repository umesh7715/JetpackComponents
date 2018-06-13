
package com.boisneyphilippe.githubarchitecturecomponents.database.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Post {

    @SerializedName("body")
    @Expose
    private String body;

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("userId")
    @Expose
    private Long userId;

    public String getBody() {
        return body;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public static class Builder {

        private String body;
        private Long id;
        private String title;
        private Long userId;

        public Post.Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Post.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Post.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Post.Builder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Post build() {
            Post post = new Post();
            post.body = body;
            post.id = id;
            post.title = title;
            post.userId = userId;
            return post;
        }

    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
