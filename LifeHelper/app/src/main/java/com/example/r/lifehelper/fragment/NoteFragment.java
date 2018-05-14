package com.example.r.lifehelper.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.activity.NoteActivity;
import com.example.r.lifehelper.bean.Note;
import com.example.r.lifehelper.bean.NoteLab;

import java.util.Date;
import java.util.UUID;

public class NoteFragment extends Fragment {
    private View mView;
    private FloatingActionButton fab;
    private Toolbar mToolbar;
    private Note mNote;
    private EditText etContent;
    private CollapsingToolbarLayout ctlTitle;
    private static final String ARGS_NOTE = "com.example.r.lifehelper.args_id";

    public static NoteFragment newInstance(UUID uuid){
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_NOTE, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID id = (UUID) getArguments().getSerializable(ARGS_NOTE);
        mNote = NoteLab.getInstance(getActivity()).getNote(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_note,container,false);
        initToolbar();
        ctlTitle = mView.findViewById(R.id.ctl_note_title);
        ctlTitle.setTitle(mNote.getTitle());
        etContent = mView.findViewById(R.id.note_content);
        etContent.setText(mNote.getContent());
        showSoftInputFromWindow(getActivity(),etContent);

        fab = mView.findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(fab,"rotation",0f,360f);
                oa.setDuration(800);
                oa.start();
                oa.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final AlertDialog dialog = builder.create();
                        View dialogView = View.inflate(getActivity(), R.layout.note_dialog,null);
                        dialog.setView(dialogView);
                        dialog.show();

                        Button btnCancel = dialogView.findViewById(R.id.btn_note_cancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        final EditText etTitle = dialogView.findViewById(R.id.et_note_name);
                        Button btnOK = dialogView.findViewById(R.id.btn_note_ok);
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mNote.setTitle(etTitle.getText().toString());
                                NoteLab.getInstance(getActivity()).updateNote(mNote);
                                ctlTitle.setTitle(mNote.getTitle());
                                dialog.dismiss();

                            }
                        });

                        final ImageView ivSave = dialogView.findViewById(R.id.note_save);
                        final ImageView ivSaveSelected = dialogView.findViewById(R.id.note_save_selected);
                        ivSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivSave,"rotation",0f,180f);
                                ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivSave,"alpha",1f,0f);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(ivSaveSelected,"rotation",180f,360f);
                                ObjectAnimator animator4 = ObjectAnimator.ofFloat(ivSaveSelected,"alpha",0f,1f);
                                ObjectAnimator animator5 = ObjectAnimator.ofFloat(ivSaveSelected,"alpha",0f,0f);
                                AnimatorSet animatorSet = new AnimatorSet();
                                animatorSet.play(animator1).with(animator2).before(animator3).before(animator4).with(animator5);
                                animatorSet.setDuration(800);
                                animatorSet.start();
                                mNote.setContent(etContent.getText().toString());
                                mNote.setDate(new Date(System.currentTimeMillis()));
                                NoteLab.getInstance(getActivity()).updateNote(mNote);
                                etContent.setText(mNote.getContent());
                                animatorSet.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
        return mView;
    }

    /*当输入时显示软键盘*/
    private static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void initToolbar() {
        mToolbar = mView.findViewById(R.id.note_toolbar);
        mToolbar.setTitle(mNote.getTitle());
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }
}
