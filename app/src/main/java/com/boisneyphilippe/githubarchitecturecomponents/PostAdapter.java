package com.boisneyphilippe.githubarchitecturecomponents;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;

import java.util.List;

import butterknife.BindView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts) {

        this.posts = posts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Post post = posts.get(position);

        holder.bind(position, post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPostTitle)
        AppCompatTextView tvPostTitle;
        @BindView(R.id.tvPostBody)
        AppCompatTextView tvPostBody;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position, Post post) {
            tvPostTitle.setText(post.getTitle());
            tvPostBody.setText(post.getBody());
        }
    }
}
