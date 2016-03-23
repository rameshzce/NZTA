package com.tokkalo.nzta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class PlayVideoActivity extends AppCompatActivity {
    String galleryType;
    String year;
    String video;

    ProgressBar progressBar = null;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Intent intent = getIntent();
        galleryType = intent.getStringExtra("galleryType");
        year = intent.getStringExtra("year");
        video = intent.getStringExtra("video");

        ActionBar ab = getSupportActionBar();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b59206")));

        TextView pg = (TextView) findViewById(R.id.photoGallery);
        pg.setTypeface(font);

        TextView vg = (TextView) findViewById(R.id.videoGallery);
        vg.setTypeface(font);

        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        tv.setText(video);

        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(font);

        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        if (galleryType.equalsIgnoreCase("Photo Gallery")) {
            vg.setBackgroundColor(Color.parseColor("#b59206"));
            pg.setBackgroundColor(Color.parseColor("#ffd428"));

            vg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlayVideoActivity.this, VideoGalleryActivity.class);
                    intent.putExtra("galleryType", "Video Gallery");
                    intent.putExtra("year", year);
                    PlayVideoActivity.this.startActivity(intent);
                }
            });
        }

        if (galleryType.equalsIgnoreCase("Video Gallery")) {
            pg.setBackgroundColor(Color.parseColor("#b59206"));
            vg.setBackgroundColor(Color.parseColor("#ffd428"));

            pg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlayVideoActivity.this, GalleryActivity.class);
                    intent.putExtra("galleryType", "Photo Gallery");
                    intent.putExtra("year", year);
                    PlayVideoActivity.this.startActivity(intent);
                }
            });
        }

        final VideoView videoView =
                (VideoView) findViewById(R.id.videoView);

        videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
        //videoView.setVideoPath("http://tokkalo.com/api/1/video1.mp4");

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();


        progressBar.setVisibility(View.VISIBLE);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });

            }
        });
    }
}
