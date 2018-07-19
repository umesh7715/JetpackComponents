package com.boisneyphilippe.githubarchitecturecomponents;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.clickHandler.PostClickHandler;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.databinding.PostListRowBinding;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.PostListFragment_ViewBinding;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.PostListViewModel;

import java.util.List;

import butterknife.BindView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> posts;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private PostListViewModel postListViewModel;

    public PostAdapter(Activity activity, List<Post> posts, PostListViewModel viewModel) {

        this.posts = posts;
        this.activity = activity;
        this.postListViewModel = viewModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        PostListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.post_list_row, parent, false);


        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.binding.setPost(posts.get(position));
        PostClickHandler handlers = new PostClickHandler(activity,postListViewModel);
        holder.binding.setHandlers(handlers);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final PostListRowBinding binding;

        public MyViewHolder(final PostListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
