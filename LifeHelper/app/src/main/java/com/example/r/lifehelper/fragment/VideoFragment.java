package com.example.r.lifehelper.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Movie;
import com.example.r.lifehelper.bean.MovieLab;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment implements View.OnClickListener {
    private TextView tvTitle,tvCategory,tvDescription;
    private VideoView mVideoView;
    private SeekBar mSeekBar;
    private Movie mMovie;
    private int[] res = {R.id.ibtn_video_play,R.id.ibtn_video_pause,R.id.ibtn_video_replay,R.id.ibtn_video_share};
    private List<ImageButton> mImageButtonList = new ArrayList<ImageButton>();
    private boolean flag = true;
    private boolean isPlaying;
    private static final String ARGS_ID = "com.example.r.lifehelper.video_id";

    public static VideoFragment newInstance(int id){
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getArguments().getInt(ARGS_ID);
        mMovie = MovieLab.getMovieLab().getMovie(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_video,container,false);

        for (int i = 0; i < res.length; i++) {
            ImageButton imageButton = view.findViewById(res[i]);
            imageButton.setOnClickListener(this);
            mImageButtonList.add(imageButton);
        }
        tvTitle = view.findViewById(R.id.tv_playTitle);
        tvCategory = view.findViewById(R.id.tv_playCategory);
        tvDescription = view.findViewById(R.id.tv_playDescription);
        tvTitle.setText(mMovie.getTitle());
        tvCategory.setText(mMovie.getCategory());
        tvDescription.setText(mMovie.getDescription());

        mSeekBar = view.findViewById(R.id.sb_video);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (mVideoView != null && mVideoView.isPlaying()){
                    mVideoView.seekTo(progress);
                }
            }
        });
        mVideoView = view.findViewById(R.id.vv_normal);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_video_play:
                if (flag){
                    play(mMovie.getUrl(),0);
                    startAnim();
                }else {
                    if (!isPlaying){
                        clickAnim(0);
                        play(mMovie.getUrl(), mVideoView.getCurrentPosition());
                        isPlaying = true;
                    }
                }
                break;
            case R.id.ibtn_video_pause:
                if (isPlaying){
                    clickAnim(1);
                    mVideoView.pause();
                    isPlaying = false;
                }
                break;
            case R.id.ibtn_video_replay:
                if (!isPlaying){
                    clickAnim(2);
                    play(mMovie.getUrl(),0);
                    isPlaying = true;
                }
                break;
            case R.id.ibtn_video_share:
                if (!isPlaying){
                    clickAnim(3);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,mMovie.getUrl());
                    startActivity(Intent.createChooser(intent,"分享"));
                }
                break;
        }
    }

    private void startAnim() {
        for (int i = 0; i < res.length; i++) {
            mImageButtonList.get(i).setVisibility(View.VISIBLE);
            ObjectAnimator oa = ObjectAnimator.ofFloat(mImageButtonList.get(i),"translationX",0f,i*300);
            oa.setDuration(1000);
            oa.setStartDelay(i*500);
            oa.setInterpolator(new BounceInterpolator());
            oa.start();
            flag = false;
        }
    }

    private void clickAnim(int i){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageButtonList.get(i),"rotation",0f,360f);
        animator.setDuration(800);
        animator.start();
    }

    private void play(final String path, int msec){
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.seekTo(msec);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mSeekBar.setMax(mVideoView.getDuration());
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                isPlaying = true;
                while (isPlaying){
                    try {
                        int current = mVideoView.getCurrentPosition();
                        Message msg = MyHandler.obtainMessage();
                        msg.arg1 = current;
                        MyHandler.sendMessage(msg);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mImageButtonList.get(0).setEnabled(true);
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                play(path,0);
                isPlaying = false;
                return false;
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler MyHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress(msg.arg1);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPlaying = false;
    }
}
