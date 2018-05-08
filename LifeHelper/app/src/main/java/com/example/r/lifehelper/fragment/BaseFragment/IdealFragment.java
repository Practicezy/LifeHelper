package com.example.r.lifehelper.fragment.BaseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.activity.NoteActivity;
import com.example.r.lifehelper.adapter.NoteAdapter;
import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.bean.NoteLab;
import com.example.r.lifehelper.utils.EmptyRecyclerView;

import java.util.List;

public class IdealFragment extends Fragment {
    private EmptyRecyclerView rvNotesList;
    private List<Note> mNotes;
    private NoteAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_ideal,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.notes_menu_add:
                Note note = new Note();
                NoteLab.getInstance(getActivity()).addNote(note);
                Intent intent = NoteActivity.newIntent(getActivity(), note.getId());
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ideal,container,false);
        rvNotesList = view.findViewById(R.id.rv_notes_list);
        rvNotesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotesList.setItemAnimator(new DefaultItemAnimator());
        View emptyView = view.findViewById(R.id.empty_view);
        rvNotesList.setEmptyView(emptyView);
        updateList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        mNotes = NoteLab.getInstance(getActivity()).getNotes();
        if (mAdapter == null){
            mAdapter = new NoteAdapter(mNotes, getActivity());
            rvNotesList.setAdapter(mAdapter);
        }else {
            mAdapter.updateItem(mNotes);
        }
    }
}
