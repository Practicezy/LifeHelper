package com.example.r.lifehelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.r.lifehelper.fragment.VideoFragment;

public class VideoActivity extends AppCompatActivity {
    private static final String EXTRA_VIDEO_ID = "com.example.r.lifehelper.extra_video_id";

    public static Intent newIntent(Context context,int id){
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int id = getIntent().getIntExtra(EXTRA_VIDEO_ID, 0);
        Fragment fragment = VideoFragment.newInstance(id);
        transaction.add(R.id.video_fragment_container,fragment)
                .show(fragment).commit();

    }
}
