package com.example.r.lifehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LifeFragment extends Fragment {
    private TabLayout mTabLayout;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.fragment_life,menu);
//        MenuItem menuItem = menu.findItem(R.id.menu_search);
//        SearchView mSearchView = (SearchView) menuItem.getActionView();
//        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
//        mSearchView.setMaxWidth(800);
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                switch (mTabLayout.getSelectedTabPosition()){
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_life,container,false);
        initTabLayout(view);
        view.invalidate();
        return view;
    }

    private void initTabLayout(final View view) {
        mTabLayout = view.findViewById(R.id.tl_life);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.colorLightBlack),getResources().getColor(R.color.text));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.text));
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.book)),0,true);
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.movie)),1);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                    switch (tab.getPosition()){
                        case 0:
                            fm.beginTransaction()
                                    .add(R.id.life_fragment_container,new BookListFragment())
                                    .commit();
                            break;
                        case 1:
                            fm.beginTransaction()
                                    .add(R.id.life_fragment_container,new MovieListFragment())
                                    .commit();
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
    }

}
