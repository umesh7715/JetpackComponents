package com.boisneyphilippe.githubarchitecturecomponents.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.clickHandler.PostClickHandler;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.Post;
import com.boisneyphilippe.githubarchitecturecomponents.databinding.PostListRowBinding;
import com.boisneyphilippe.githubarchitecturecomponents.databinding.PostListRowV2Binding;
import com.boisneyphilippe.githubarchitecturecomponents.utils.RemoteConfigUtils;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.PostListViewModel;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private final LinearLayoutManager layoutManager;
    private List<Post> posts;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private PostListViewModel postListViewModel;

    public PostAdapter(Activity activity, List<Post> posts, PostListViewModel viewModel, LinearLayoutManager layoutManager) {

        this.posts = posts;
        this.activity = activity;
        this.postListViewModel = viewModel;
        this.layoutManager = layoutManager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding;

        String resource = FirebaseRemoteConfig.getInstance().getString(RemoteConfigUtils.POST_LIST_FRAGMENT_UI_LAYOUT_ID);
        int resID = activity.getResources().getIdentifier(resource,
                "layout", activity.getPackageName());
        binding =
                DataBindingUtil.inflate(layoutInflater, resID, parent, false);


        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PostClickHandler handlers = new PostClickHandler(activity, postListViewModel);

        if (holder.holderBinderType == 1) {
            holder.binding.setPost(posts.get(position));
            holder.binding.setHandlers(handlers);
        } else if (holder.holderBinderType == 2) {
            holder.postListRowV2Binding.setPost(posts.get(position));
            holder.postListRowV2Binding.setHandlers(handlers);
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private PostListRowBinding binding;
        private PostListRowV2Binding postListRowV2Binding;
        private int holderBinderType;

        public MyViewHolder(final ViewDataBinding itemBinding) {
            super(itemBinding.getRoot());
            if (itemBinding instanceof PostListRowBinding) {
                this.binding = (PostListRowBinding) itemBinding;
                this.holderBinderType = 1;
            } else if (itemBinding instanceof PostListRowV2Binding) {
                this.postListRowV2Binding = (PostListRowV2Binding) itemBinding;
                this.holderBinderType = 2;
            }
        }
    }
}
