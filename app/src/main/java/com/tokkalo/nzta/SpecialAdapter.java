package com.tokkalo.nzta;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rameshkolamala on 12/03/16.
 */
public class SpecialAdapter extends SimpleAdapter {
    private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

    private int[] listItemBackground = new int[] { R.drawable.list_background1, R.drawable.list_background2 };

    public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        //int colorPos = position % colors.length;
        //view.setBackgroundColor(colors[colorPos]);



        TextView tv = (TextView) view.findViewById(R.id.id);
        Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), "fonts/handlee-regular.ttf");
        tv.setTypeface(font);

        TextView tv2 = (TextView) view.findViewById(R.id.address);

        tv2.setTypeface(font);

        int colorPos = position % listItemBackground.length;
        view.setBackgroundResource(listItemBackground[colorPos]);
        return view;
    }
}
