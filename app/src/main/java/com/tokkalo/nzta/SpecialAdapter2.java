package com.tokkalo.nzta;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rameshkolamala on 13/03/16.
 */
public class SpecialAdapter2 extends SimpleAdapter {
    private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

    private int[] listItemBackground = new int[] { R.drawable.list_background3, R.drawable.list_background4 };
    private int[] galleryBackground = new int[] { R.drawable.gallery_bg, R.drawable.gallery_bg2};

    public SpecialAdapter2(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        //int colorPos = position % colors.length;
        //view.setBackgroundColor(colors[colorPos]);

        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //params.width = 1200;

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.layout1);

        RelativeLayout.LayoutParams lpimgHeader = new RelativeLayout.LayoutParams(ll.getLayoutParams());
        lpimgHeader.setMargins(150, 0, 0, 0);
        ll.setLayoutParams(lpimgHeader);


        TextView tv = (TextView) view.findViewById(R.id.id);
        Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), "fonts/handlee-regular.ttf");
        tv.setTypeface(font);

        TextView tv2 = (TextView) view.findViewById(R.id.address);

        tv2.setTypeface(font);

        TextView tv3 = (TextView) view.findViewById(R.id.photoGallery);
        String tvs3 = tv3.getText().toString();

        tv3.setTypeface(font);

        TextView tv4 = (TextView) view.findViewById(R.id.videoGallery);
        String tvs4 = tv4.getText().toString();
        tv4.setTypeface(font);

        TableRow tr = (TableRow) view.findViewById(R.id.tableRow1);

        SpannableString content = new SpannableString(tvs3);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        SpannableString content2 = new SpannableString(tvs4);
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);


        int colorPos = position % listItemBackground.length;
        ll.setBackgroundResource(listItemBackground[colorPos]);
        tr.setBackgroundResource(galleryBackground[colorPos]);

        ImageView im1 = (ImageView) view.findViewById(R.id.imageGallery);
        ImageView im2 = (ImageView) view.findViewById(R.id.imageVideo);

        if(colorPos == 0){
            tv3.setText(content);
            tv4.setText(content2);

            im1.setVisibility(View.GONE);
            im2.setVisibility(View.GONE);

            lpimgHeader.setMargins(0, 0, 150, 0);
            ll.setLayoutParams(lpimgHeader);
        }


        //view.setLayoutParams(params);
        //view.setRight(100);
        return view;
    }
}
