package com.sss.live.data.demo.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sss.live.data.demo.R;
import com.sss.live.data.demo.databinding.FragmentUserBinding;
import com.sss.live.data.demo.db.entity.UserEntity;
import com.sss.live.data.demo.viewmodel.UserViewModel;

/**
 * Created by sss on 2017/9/22.
 */

public class UserFragment extends Fragment {
    private FragmentUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserViewModel userViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> aClass) {
                return (T) new UserViewModel(getActivity().getApplication(), getArguments().getInt("userId"));
            }
        }).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                binding.setUser(userEntity);
            }
        });
    }
}
