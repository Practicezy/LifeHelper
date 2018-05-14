package com.example.r.lifehelper.adapter;

import android.content.Context;
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
    private int selectPosition;

    public BookCategoryAdapter(List<BookCategory> bookCategories, Context context) {
        mBookCategories = bookCategories;
        mContext = context;
    }

    /*书类别列表数目*/
    @Override
    public int getCount() {
        return mBookCategories.size();
    }

    /*返回对应位置的视图*/
    @Override
    public Object getItem(int i) {
        return mBookCategories.get(i);
    }

    /*返回对应位置*/
    @Override
    public long getItemId(int i) {
        return i;
    }

    /*创建和绑定UI数据*/
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_category_book, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BookCategory bookCategory = mBookCategories.get(i);
        viewHolder.tvCategory.setText(bookCategory.getCategory());
        if (i == selectPosition) {
            viewHolder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.colorLightAccent));
        } else {
            viewHolder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.text));
        }
        viewHolder.ivCategory.setImageDrawable(mContext.getResources().getDrawable(bookCategory.getIconId()));
        return convertView;
    }

    public void getSelectPositon(int positon) {
        selectPosition = positon;
    }

    class ViewHolder {
        ImageView ivCategory;
        TextView tvCategory;
        private View itemView;

        ViewHolder(View view) {
            itemView = view;
            ivCategory = itemView.findViewById(R.id.iv_book_category);
            tvCategory = itemView.findViewById(R.id.tv_book_category);
        }
    }
}
