package com.example.r.lifehelper.fragment;

import android.app.ProgressDialog;
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

import java.util.concurrent.ExecutionException;

public class BookDetailFragment extends Fragment {
    private TextView tvTitle,tvAuthor,tvDate,tvIntro;
    private ImageView ivImg;
    private Button btnRead;
    private Book mBook;
    private ProgressDialog dialog;
    private static final String BOOK_ARGS = "com.example.r.lifehelper.fragment.book_args";

    public static Fragment newInstance(String title){
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(BOOK_ARGS, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = ProgressDialog.show(getActivity(), "提示：","loading...");
        dialog.show();
        initData();
    }

    private void initData() {
        String title = getArguments().getString(BOOK_ARGS);
        Book book = BookLab.getBookLab(getActivity()).getBook(title);
        try {
            mBook = new BookDetailAsyncTask().execute(book.getUrl()).get();
            mBook.setTitle(book.getTitle());
            mBook.setImageUrl(book.getImageUrl());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail,container,false);
        initView(view);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = BookChapterFragment.newInstance(mBook.getDetailUrl());
                fm.beginTransaction()
                        .replace(R.id.book_fragment_container,fragment)
                        .addToBackStack(null)
                        .show(fragment)
                        .commit();
            }
        });
        return view;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.book_detail_title);
        tvAuthor = view.findViewById(R.id.book_detail_author);
        tvDate = view.findViewById(R.id.book_detail_date);
        tvIntro = view.findViewById(R.id.book_detail_intro);
        ivImg = view.findViewById(R.id.book_detail_img);
        btnRead = view.findViewById(R.id.btn_read_book);

        tvTitle.setText(mBook.getTitle());
        tvAuthor.setText(mBook.getAuthor());
        tvDate.setText(mBook.getDate());
        tvIntro.setText(mBook.getIntro());
        ivImg.setTag(mBook.getImageUrl());
        new ImageLoader(getActivity()).loadBitmapByThread(ivImg,mBook.getImageUrl(),500,500);
    }
}
