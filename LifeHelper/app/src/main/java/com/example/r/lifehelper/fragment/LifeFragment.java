package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.lifehelper.R;

import java.util.List;

public class LifeFragment extends Fragment {
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_life,container,false);
        addFragments();
        initTabLayout(view);
        return view;
    }

    /*初始化标签*/
    private void initTabLayout(final View view) {
        mTabLayout = view.findViewById(R.id.tl_life);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.colorLightBlack),getResources().getColor(R.color.text));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.text));
        /*listener必须在添加tab前，否则会出现首页加载不出来的情况*/
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        showIndex(0);
                        break;
                    case 1:
                        showIndex(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.book)),0);
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.movie)),1);
    }

    /*分别为标签创建对应的Fragment*/
    private void addFragments(){
        BookListFragment fragment1 = new BookListFragment();
        MovieListFragment fragment2 = new MovieListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.life_fragment_container,fragment1).show(fragment1);
        transaction.add(R.id.life_fragment_container,fragment2).hide(fragment2);
        transaction.commit();
    }

    /*根据点击显示对应的Fragment*/
    private void showIndex(int index){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index){
                transaction.show(fragments.get(i));
            }else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

}
