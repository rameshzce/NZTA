package com.tokkalo.nzta;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    GridView gv;
    Context context;
    ArrayList prgmName;
    String galleryType;
    int newWidth;
    String year;

    public static String [] prgmNameList={
            "Let Us C","c++","JAVA",
            "Jsp","Microsoft .Net","Android",
            "PHP","Jquery","JavaScript",
            "Let Us C","c++","JAVA",
            "Jsp","Microsoft .Net","Android",
            "PHP","Jquery","JavaScript",
            "Let Us C","c++","JAVA",
            "Jsp","Microsoft .Net","Android",
            "PHP","Jquery","JavaScript",
            "Let Us C","c++","JAVA",
            "Jsp","Microsoft .Net","Android",
            "PHP","Jquery","JavaScript"
    };
    public static int [] prgmImages={
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Intent intent = getIntent();
        String galleryType = intent.getStringExtra("galleryType");
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
                    Intent intent = new Intent(GalleryActivity.this, VideoGalleryActivity.class);
                    intent.putExtra("galleryType", "Video Gallery");
                    intent.putExtra("year", year);
                    GalleryActivity.this.startActivity(intent);
                }
            });
        }

        if (galleryType.equalsIgnoreCase("Video Gallery")) {
            pg.setBackgroundColor(Color.parseColor("#b59206"));
            vg.setBackgroundColor(Color.parseColor("#ffd428"));

            pg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GalleryActivity.this, GalleryActivity.class);
                    intent.putExtra("galleryType", "Photo Gallery");
                    intent.putExtra("year", year);
                    GalleryActivity.this.startActivity(intent);
                }
            });
        }



        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        tv.setText(galleryType + " " + year);

        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(font);

        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        Display display = getWindowManager().getDefaultDisplay();
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

        gv = (GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages, newWidth, year));


        gv.setNumColumns(3);

        if (rotation.equalsIgnoreCase("L")) {
            gv.setNumColumns(5);
        }

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
