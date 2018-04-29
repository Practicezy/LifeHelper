package com.example.r.lifehelper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.BookLab;
import com.example.r.lifehelper.data.BookDetailAsyncTask;
import com.example.r.lifehelper.utils.ImageLoader;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class BookDetailFragment extends Fragment {
    private TextView tvTitle, tvAuthor, tvDate, tvIntro;
    private ImageView ivImg;
    private Button btnRead, btnHistory;
    private Book mBook;
    private static final String BOOK_ARGS = "com.example.r.lifehelper.fragment.book_args";

    public static Fragment newInstance(UUID uuid) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(BOOK_ARGS, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    /*通过标题得到对应的书籍详情信息*/
    private void initData() {
        UUID uuid = (UUID) getArguments().getSerializable(BOOK_ARGS);
        Book book = BookLab.getBookLab(getActivity()).getBook(uuid);
        try {
            mBook = new BookDetailAsyncTask().execute(book.getUrl()).get();
            mBook.setTitle(book.getTitle());
            mBook.setImageUrl(book.getImageUrl());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        /*初始化界面*/
        initView(view);
        /*点击跳转至章节列表页*/
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = BookChapterFragment.newInstance(mBook.getDetailUrl());
                fm.beginTransaction()
                        .replace(R.id.book_fragment_container, fragment)
                        .addToBackStack(null)
                        .show(fragment)
                        .commit();
            }
        });
        /*点击跳转至历史阅读页
         * 如无历史记录则不能跳转*/
        String title = mBook.getTitle().replace("《", "").replace("》", "");
        File file = new File("/data/data/com.example.r.lifehelper/shared_prefs/" + title + ".xml");
        if (!file.exists()) {
            btnHistory.setClickable(false);
            btnHistory.setText("暂无记录");
            btnHistory.setTextColor(getResources().getColor(R.color.colorLightBlack));
        } else {
            btnHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment fragment = BookHistoryFragment.newInstance(mBook.getTitle());
                    fm.beginTransaction()
                            .replace(R.id.book_fragment_container, fragment)
                            .addToBackStack(null)
                            .show(fragment)
                            .commit();
                }
            });
        }
        return view;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.book_detail_title);
        tvAuthor = view.findViewById(R.id.book_detail_author);
        tvDate = view.findViewById(R.id.book_detail_date);
        tvIntro = view.findViewById(R.id.book_detail_intro);
        ivImg = view.findViewById(R.id.book_detail_img);
        btnRead = view.findViewById(R.id.btn_read_book);
        btnHistory = view.findViewById(R.id.btn_book_history);

        tvTitle.setText(mBook.getTitle());
        tvAuthor.setText(mBook.getAuthor());
        tvDate.setText(mBook.getDate());
        tvIntro.setText(mBook.getIntro());
        ivImg.setTag(mBook.getImageUrl());
        new ImageLoader(getActivity()).loadRoundBitmapByThread(ivImg, mBook.getImageUrl(), 500, 500);
    }
}
