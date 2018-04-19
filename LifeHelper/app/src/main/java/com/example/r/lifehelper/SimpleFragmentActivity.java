package com.example.r.lifehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SimpleFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();

    @LayoutRes
    protected int getLayout(){
        return R.layout.activity_simple_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        if (findViewById(R.id.fragment_container) != null){
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = createFragment();
            if (fragment != null){
                fm.beginTransaction()
                        .add(R.id.fragment_container,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
