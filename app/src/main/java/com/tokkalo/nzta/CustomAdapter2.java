package com.tokkalo.nzta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rameshkolamala on 23/03/16.
 */
public class CustomAdapter2 extends BaseAdapter {

    String[] result;
    Context context;
    int[] imageId;
    int width;
    private static LayoutInflater inflater = null;
    String yearSelected;

    public CustomAdapter2(VideoGalleryActivity galleryActivity, String[] prgmNameList, int[] prgmImages, int newWidth, String year) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = galleryActivity;
        imageId = prgmImages;
        width = newWidth;
        yearSelected = year;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.program_list1, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        holder.img.getLayoutParams().width = 600;

        Typeface font = Typeface.createFromAsset(holder.tv.getContext().getAssets(), "fonts/handlee-regular.ttf");
        holder.tv.setTypeface(font);

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, PlayVideoActivity.class);
                // passing array index
                i.putExtra("id", position);
                i.putExtra("yearSelected", yearSelected);
                i.putExtra("galleryType", "Video Gallery");
                i.putExtra("year", yearSelected);
                i.putExtra("video", result[position]);
                context.startActivity(i);
            }
        });

        return rowView;
    }

}
