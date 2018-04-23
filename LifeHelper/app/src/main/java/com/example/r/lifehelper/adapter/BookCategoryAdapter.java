package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.BookCategory;

import java.util.List;

public class BookCategoryAdapter extends BaseAdapter {
    private List<BookCategory> mBookCategories;
    private Context mContext;

    public BookCategoryAdapter(List<BookCategory> bookCategories, Context context) {
        mBookCategories = bookCategories;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBookCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return mBookCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_category_book,viewGroup,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BookCategory bookCategory = mBookCategories.get(i);
        viewHolder.tvCategory.setText(bookCategory.getCategory());
        viewHolder.ivCategory.setImageDrawable(mContext.getResources().getDrawable(bookCategory.getIconId()));
        return convertView;
    }

    class ViewHolder{
        ImageView ivCategory;
        TextView tvCategory;
        private View itemView;

        public ViewHolder(View view) {
            itemView = view;
            ivCategory = itemView.findViewById(R.id.iv_book_category);
            tvCategory = itemView.findViewById(R.id.tv_book_category);
        }
    }
}
