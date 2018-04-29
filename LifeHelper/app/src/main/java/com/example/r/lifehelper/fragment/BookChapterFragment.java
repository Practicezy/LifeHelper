package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.adapter.BookChapterAdapter;
import com.example.r.lifehelper.bean.BookChapter;
import com.example.r.lifehelper.data.BookChapterAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookChapterFragment extends Fragment {
    private RecyclerView rvBookChapter;
    private TextView tvChapterSize;
    private List<BookChapter> mBookChapterList;
    private String url;
    private static final String CHAPTER_ARGS = "com.example.r.lifehelper.fragment.chapter_args";

    public static Fragment newInstance(String urlSpec) {
        BookChapterFragment fragment = new BookChapterFragment();
        Bundle args = new Bundle();
        args.putString(CHAPTER_ARGS, urlSpec);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*获取章节信息*/
        url = getArguments().getString(CHAPTER_ARGS);
        try {
            mBookChapterList = new BookChapterAsyncTask().execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_chapter, container, false);
        initChapterSize(view);
        initList(view);
        return view;
    }

    /*上方显示合计章节数*/
    private void initChapterSize(View view) {
        tvChapterSize = view.findViewById(R.id.tv_chapter_size);
        tvChapterSize.setText("合计" + mBookChapterList.size() + "章");
    }

    /*章节数详情*/
    private void initList(View view) {
        rvBookChapter = view.findViewById(R.id.rv_book_chapter);
        rvBookChapter.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookChapter.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        BookChapterAdapter adapter = new BookChapterAdapter(mBookChapterList, getActivity(), url);
        rvBookChapter.setAdapter(adapter);
    }
}
