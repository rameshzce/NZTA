package com.tokkalo.nzta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoGalleryActivity extends AppCompatActivity {
    GridView gv;
    Context context;
    ArrayList prgmName;
    String galleryType;
    int newWidth;
    String year;

    public static String[] prgmNameList = {
            "sravya, geetha, radha dance", "madhu acting play", "sravya, geetha, radha dance",
            "madhu acting play", "sravya, geetha, radha dance", "madhu acting play"
    };
    public static Integer[] prgmImages = {
            R.drawable.video1, R.drawable.video2, R.drawable.video3,
            R.drawable.video4, R.drawable.video5, R.drawable.video6
    };

    ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);

        Intent intent = getIntent();
        galleryType = intent.getStringExtra("galleryType");
        year = intent.getStringExtra("year");

        ActionBar ab = getSupportActionBar();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b59206")));

        TextView pg = (TextView) findViewById(R.id.photoGallery);
        pg.setTypeface(font);

        TextView vg = (TextView) findViewById(R.id.videoGallery);
        vg.setTypeface(font);

        if (galleryType.equalsIgnoreCase("Photo Gallery")) {
            vg.setBackgroundColor(Color.parseColor("#b59206"));
            pg.setBackgroundColor(Color.parseColor("#ffd428"));

            vg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VideoGalleryActivity.this, VideoGalleryActivity.class);
                    intent.putExtra("galleryType", "Video Gallery");
                    intent.putExtra("year", year);
                    VideoGalleryActivity.this.startActivity(intent);
                }
            });
        }

        if (galleryType.equalsIgnoreCase("Video Gallery")) {
            pg.setBackgroundColor(Color.parseColor("#b59206"));
            vg.setBackgroundColor(Color.parseColor("#ffd428"));

            pg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VideoGalleryActivity.this, GalleryActivity.class);
                    intent.putExtra("galleryType", "Photo Gallery");
                    intent.putExtra("year", year);
                    VideoGalleryActivity.this.startActivity(intent);
                }
            });
        }



        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        tv.setText("Existing Events " + year);

        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(font);

        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        CustomVideoListAdapter adapter = new
                CustomVideoListAdapter(VideoGalleryActivity.this, prgmNameList, prgmImages);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(VideoGalleryActivity.this, "You Clicked at " + prgmNameList[+position], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VideoGalleryActivity.this, PlayVideoActivity.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra("yearSelected", year);
                i.putExtra("galleryType", "Video Gallery");
                i.putExtra("year", year);
                i.putExtra("video", prgmNameList[position]);
                VideoGalleryActivity.this.startActivity(i);
            }
        });

        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        String rotation = getRotation(getApplicationContext());

        //String msg = "Width: " + width + ", height: " + height + ", rotation: " + rotation;

        //Toast.makeText(GalleryActivity.this, msg, Toast.LENGTH_LONG).show();


        newWidth = ((width - 30) / 3);

        if (rotation.equalsIgnoreCase("L")) {
            newWidth = ((width - 50) / 5);
        }

        gv = (GridView) findViewById(R.id.gridView2);
        gv.setAdapter(new CustomAdapter2(this, prgmNameList, prgmImages, newWidth, year));*/




    }

    public String getRotation(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                //return "portrait";
                return "P";
            case Surface.ROTATION_90:
                //return "landscape";
                return "L";
            case Surface.ROTATION_180:
                //return "reverse portrait";
                return "P";
            default:
                //return "reverse landscape";
                return "L";
        }
    }
}
