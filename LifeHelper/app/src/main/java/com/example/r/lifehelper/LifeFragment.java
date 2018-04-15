package com.example.r.lifehelper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.r.lifehelper.unitils.LifeBannerLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LifeFragment extends Fragment {
    private Banner mLifeBanner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_life,container,false);
        initBanner(view);


        return view;
    }

    private void initBanner(View view) {
        mLifeBanner = view.findViewById(R.id.life_banner);
        mLifeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mLifeBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(getActivity()).load(path).into(imageView);
            }
        });
        List<String> urls = LifeBannerLoader.loadBannerByAsyncTask();
        mLifeBanner.setImages(urls);
        mLifeBanner.setDelayTime(3000);
        mLifeBanner.start();
    }
}
