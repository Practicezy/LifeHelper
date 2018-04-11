package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UnitilsFragment extends Fragment {
    private static UnitilsFragment sUnitilsFragment;

    public static UnitilsFragment newInstance(){
        if (sUnitilsFragment == null){
            sUnitilsFragment = new UnitilsFragment();
        }
        Bundle args = new Bundle();
        sUnitilsFragment.setArguments(args);
        return sUnitilsFragment;
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
