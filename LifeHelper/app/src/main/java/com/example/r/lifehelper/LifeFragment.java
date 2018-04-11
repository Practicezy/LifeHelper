package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LifeFragment extends Fragment {
    private static LifeFragment sLifeFragment;

    public static LifeFragment newInstance(){
        if (sLifeFragment == null){
            sLifeFragment = new LifeFragment();
        }
        Bundle args = new Bundle();
        sLifeFragment.setArguments(args);
        return sLifeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
