
package com.boisneyphilippe.githubarchitecturecomponents.database.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Post extends BaseObservable {

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

    @Ignore
    private long views;

    public String getBody() {
        return body;
    }

    public Long getId() {
        return id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    @Bindable
    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
        notifyPropertyChanged(BR.views);
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);


    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
