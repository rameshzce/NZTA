package com.tokkalo.nzta;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rameshkolamala on 23/03/16.
 */
public class CustomVideoListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;

    public CustomVideoListAdapter(Activity context,
                                  String[] web, Integer[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);

        Typeface font = Typeface.createFromAsset(txtTitle.getContext().getAssets(), "fonts/handlee-regular.ttf");
        txtTitle.setTypeface(font);
        txtTitle.setText(web[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);


        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
