package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.BookChapter;
import com.example.r.lifehelper.data.BookChapterAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class BookContentFragment extends Fragment {
    private TextView tvChapter;
    private ViewPager vpContent;
    private BookChapter mBookChapter;
    private List<BookChapter> mBookChapterList;
    private int position;
    private static final String CHAPTERS_ARGS = "com.example.r.lifehelper.fragment.chapters_args";
    private static final String POSITION_ARGS = "com.example.r.lifehelper.fragment.content_chapter_args";

    public static Fragment newInstance(String chaptersUrl,int position){
        BookContentFragment fragment = new BookContentFragment();
        Bundle args = new Bundle();
        args.putString(CHAPTERS_ARGS, chaptersUrl);
        args.putInt(POSITION_ARGS, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(POSITION_ARGS);
        String chapters = getArguments().getString(CHAPTERS_ARGS);
        try {
            mBookChapterList = new BookChapterAsyncTask().execute(chapters).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mBookChapter = mBookChapterList.get(position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_content,container,false);
        initTitle(view);
        initPager(view);
        return view;
    }

    private void initTitle(View view) {
        tvChapter = view.findViewById(R.id.tv_book_chapter);
        tvChapter.setText(mBookChapter.getTitle());
    }

    private void initPager(View view) {
        vpContent = view.findViewById(R.id.vp_book_content);
        vpContent.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BookChapter bookChapter = mBookChapterList.get(position);
                return BookPagerFragment.newInstance(bookChapter.getChapterUrl());
            }

            @Override
            public int getCount() {
                return mBookChapterList.size();
            }
        });
        vpContent.setCurrentItem(position);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BookChapter bookChapter = mBookChapterList.get(position);
                tvChapter.setText(bookChapter.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
