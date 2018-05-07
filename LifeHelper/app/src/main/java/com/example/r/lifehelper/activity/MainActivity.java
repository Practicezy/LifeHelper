package com.example.r.lifehelper.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.fragment.BaseFragment.IdealFragment;
import com.example.r.lifehelper.fragment.BaseFragment.LifeFragment;
import com.example.r.lifehelper.fragment.BaseFragment.NewsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*初始化状态栏*/
        initToolbar();

        /*初始化底部导航栏*/
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        /*设置viewpager效果*/
        initViewpager();
        /*设置标签选择事件*/
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            System.gc();
        }
    }

    private void initViewpager() {
        mViewPager = findViewById(R.id.vp_fragment_container);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new NewsFragment();
                    case 1:
                        return new LifeFragment();
                    case 2:
                        return new IdealFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mBottomNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(0);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_news:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_life:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_ideal:
                mViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

}
