package com.sss.live.data.demo.fragment.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sss.live.data.demo.R;
import com.sss.live.data.demo.databinding.ItemUserListBinding;
import com.sss.live.data.demo.fragment.adapter.itemclick.OnItemWithIdClickListener;
import com.sss.live.data.demo.model.User;

import java.util.List;

/**
 * Created by sss on 2017/9/22.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<? extends User> list;
    private LayoutInflater inflater;
    private OnItemWithIdClickListener onItemClickListener;

    public UserListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<? extends User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemWithIdClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_user_list, parent, false);
        binding.setOnClick(onItemClickListener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = list.get(position);
        holder.getBinding().setUser(user);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemUserListBinding binding;

        ViewHolder(ItemUserListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        ItemUserListBinding getBinding() {
            return binding;
        }
    }


}
