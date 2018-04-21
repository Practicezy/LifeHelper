package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovieListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setMinimumWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setMinimumHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        return layout;
    }
}
