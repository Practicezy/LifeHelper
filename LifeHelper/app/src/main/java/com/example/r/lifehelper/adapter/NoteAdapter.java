package com.example.r.lifehelper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.activity.NoteActivity;
import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.bean.NoteLab;

import java.lang.reflect.Field;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> mNoteList;
    private Context mContext;

    public NoteAdapter(List<Note> noteList, Context context) {
        mNoteList = noteList;
        mContext = context;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notes,parent,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteHolder holder, final int position) {
        final Note note = mNoteList.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.tvDate.setText(note.getDate().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = NoteActivity.newIntent(mContext, note.getId());
                mContext.startActivity(intent);
            }
        });

        holder.ibMore.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.ibMore);
                Field field;
                try {
                    field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
                    mHelper.setForceShowIcon(true);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.notes_pop_menu,popupMenu.getMenu());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.notes_menu_edit:
                                Intent intent = NoteActivity.newIntent(mContext, note.getId());
                                mContext.startActivity(intent);
                                break;
                            case R.id.notes_menu_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("提示：");
                                builder.setMessage("请问你确定要删除吗?");
                                builder.setPositiveButton("当然，没毛病", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        removeItem(position);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNeutralButton("手滑，失误", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case R.id.notes_menu_share:
                                Intent intentShare = new Intent(Intent.ACTION_SEND);
                                intentShare.setType("text/plain");
                                intentShare.putExtra(Intent.EXTRA_TEXT,note.getContent());
                                mContext.startActivity(Intent.createChooser(intentShare,"分享"));
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public void removeItem(int position){
        notifyItemRemoved(position);
        NoteLab.getInstance(mContext).deleteNote(mNoteList.get(position));
        mNoteList = NoteLab.getInstance(mContext).getNotes();
        updateItem(mNoteList);
    }

    public void updateItem(List<Note> notes){
        mNoteList = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvContent,tvDate;
        ImageButton ibMore;
        NoteHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.note_title_text);
            tvContent = itemView.findViewById(R.id.note_content_text);
            tvDate = itemView.findViewById(R.id.note_last_edit_text);
            ibMore = itemView.findViewById(R.id.note_more);
        }
    }
}
