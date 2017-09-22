package com.sss.live.data.demo.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sss.live.data.demo.MainActivity;
import com.sss.live.data.demo.R;
import com.sss.live.data.demo.fragment.adapter.UserListAdapter;
import com.sss.live.data.demo.databinding.FragmentUserListBinding;
import com.sss.live.data.demo.db.entity.UserEntity;
import com.sss.live.data.demo.fragment.adapter.itemclick.OnItemWithIdClickListener;
import com.sss.live.data.demo.viewmodel.UserListViewModel;

import java.util.List;

/**
 * Created by sss on 2017/9/22.
 */

public class UserListFragment extends Fragment {
    private FragmentUserListBinding binding;
    private UserListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("---->", "--->" + "onCreateView");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false);
        adapter = new UserListAdapter(getContext());
        adapter.setOnItemClickListener(new OnItemWithIdClickListener() {
            @Override
            public void onClick(int id) {
                UserFragment userFragment = new UserFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("userId",id);
                userFragment.setArguments(bundle);
                ((MainActivity) getActivity()).changeFragment(userFragment);
            }
        });
        binding.recyclerview.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("---->", "--->" + "onActivityCreated");
        UserListViewModel model = new UserListViewModel(getActivity().getApplication());
        model.getListLiveData().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(@Nullable List<UserEntity> userEntities) {
                Log.e("---->", "--->" + (userEntities != null ? userEntities.size() : ""));
                adapter.setList(userEntities);
            }
        });
    }
}
