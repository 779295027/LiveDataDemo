package com.sss.live.data.demo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sss.live.data.demo.databinding.ActivityMainBinding;
import com.sss.live.data.demo.db.DatabaseCreator;
import com.sss.live.data.demo.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseCreator creator;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        creator = DatabaseCreator.getInstance();
        creator.create(getApplicationContext());
        binding.button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                // 不能在主线程进行数据库操作
                new AsyncTask<Context, Void, User>() {
                    @Override
                    protected User doInBackground(Context... contexts) {
                        List<? extends User> list = creator.getDatabase().userDao().getAll();
                        return list.get(0);
                    }

                    @Override
                    protected void onPostExecute(User user) {
                        binding.setUser(user);
                    }
                }.execute(getApplicationContext());
                break;
        }
    }
}
