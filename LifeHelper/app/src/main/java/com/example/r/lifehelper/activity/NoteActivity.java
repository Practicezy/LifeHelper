package com.example.r.lifehelper.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.bean.NoteLab;
import com.example.r.lifehelper.fragment.NoteFragment;

import java.util.UUID;

public class NoteActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE = "com.example.r.lifehelper.note_id";

    public static Intent newIntent(Context context, UUID uuid){
        Intent intent = new Intent(context, NoteActivity.class);
        intent.putExtra(EXTRA_NOTE, uuid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_NOTE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = NoteFragment.newInstance(id);
        transaction.add(R.id.note_fragment_container,fragment)
                .show(fragment)
                .commit();

    }

}