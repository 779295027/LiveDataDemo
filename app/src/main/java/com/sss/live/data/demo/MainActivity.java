package com.sss.live.data.demo;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sss.live.data.demo.databinding.ActivityMainBinding;
import com.sss.live.data.demo.db.DatabaseCreator;
import com.sss.live.data.demo.db.entity.UserEntity;
import com.sss.live.data.demo.viewmodel.UserViewModel;

public class MainActivity extends LifecycleActivity implements View.OnClickListener {
    private DatabaseCreator creator;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.button.setOnClickListener(this);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUserLiveData().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                binding.setUser(userEntity);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                // 不能在主线程进行数据库操作
                Toast.makeText(this, "此按钮暂时无用", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
