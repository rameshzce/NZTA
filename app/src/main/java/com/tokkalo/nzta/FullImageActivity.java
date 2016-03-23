package com.tokkalo.nzta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class FullImageActivity extends FragmentActivity implements
        View.OnClickListener, ViewPager.OnPageChangeListener {

    private Button btnImagePrevious, btnImageNext;
    private int position = 0, totalImage;
    private ViewPager viewPage;
    private ArrayList<Integer> itemData;
    private FragmentPagerAdapter adapter;
    private Images imageId;

    String galleryType;
    String year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);


        viewPage = (ViewPager) findViewById(R.id.viewPager);
        btnImagePrevious = (Button) findViewById(R.id.btnImagePrevious);
        btnImageNext = (Button) findViewById(R.id.btnImageNext);
        imageId = new Images();
        itemData = imageId.getImageItem();
        totalImage = itemData.size();
        setPage(position);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                itemData);
        viewPage.setAdapter(adapter);
        viewPage.setOnPageChangeListener(FullImageActivity.this);

        btnImagePrevious.setOnClickListener(this);
        btnImageNext.setOnClickListener(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        TextView pg = (TextView) findViewById(R.id.photoGallery);
        pg.setTypeface(font);

        TextView vg = (TextView) findViewById(R.id.videoGallery);
        vg.setTypeface(font);

        pg.setBackgroundColor(Color.parseColor("#ffd428"));
        vg.setBackgroundColor(Color.parseColor("#b59206"));

        Button btn1 = (Button) findViewById(R.id.btnImageNext);
        btn1.setTypeface(font);
        btn1.setTextColor(Color.parseColor("#FFFFFF"));

        Button btn2 = (Button) findViewById(R.id.btnImagePrevious);
        btn2.setTypeface(font);
        btn2.setTextColor(Color.parseColor("#FFFFFF"));



    }

    @Override
    public void onClick(View v) {
        if (v == btnImagePrevious) {
            position--;
            viewPage.setCurrentItem(position);
        } else if (v == btnImageNext) {
            position++;
            viewPage.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        setPage(position);
    }

    private void setPage(int page) {
        if (page == 0 && totalImage > 0) {
            btnImageNext.setVisibility(View.VISIBLE);
            btnImagePrevious.setVisibility(View.INVISIBLE);
        } else if (page == totalImage - 1 && totalImage > 0) {
            btnImageNext.setVisibility(View.INVISIBLE);
            btnImagePrevious.setVisibility(View.VISIBLE);
        } else {
            btnImageNext.setVisibility(View.VISIBLE);
            btnImagePrevious.setVisibility(View.VISIBLE);
        }
    }
}
