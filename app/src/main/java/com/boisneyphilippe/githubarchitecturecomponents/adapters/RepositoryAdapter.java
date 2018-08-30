package com.boisneyphilippe.githubarchitecturecomponents.adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.githubSearch.Item;
import com.boisneyphilippe.githubarchitecturecomponents.databinding.RepositoryListRowBinding;
import com.boisneyphilippe.githubarchitecturecomponents.view_models.GithubViewModel;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.MyViewHolder> {

    private final GithubViewModel githubViewModel;
    private final List<Item> itemList;
    private final Activity activity;
    private LayoutInflater layoutInflater;

    public RepositoryAdapter(Activity activity, List<Item> itemList, GithubViewModel githubViewModel) {

        this.itemList = itemList;
        this.activity = activity;
        this.githubViewModel = githubViewModel;
    }

    @NonNull
    @Override
    public RepositoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RepositoryListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.repository_list_row, parent, false);


        return new RepositoryAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryAdapter.MyViewHolder holder, int position) {
        holder.binding.setItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final RepositoryListRowBinding binding;

        public MyViewHolder(final RepositoryListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
