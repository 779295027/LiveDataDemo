package com.sss.live.data.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sss.live.data.demo.databinding.ActivityMainBinding;
import com.sss.live.data.demo.fragment.UserListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        changeFragment(new UserListFragment());
    }


    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
