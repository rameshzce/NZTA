package com.tokkalo.nzta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by rameshkolamala on 27/02/16.
 */
public class CustomEventAdapter extends ArrayAdapter<String> {
    String[] color_names;
    Integer[] image_id;
    Context context;

    public CustomEventAdapter(Activity context,Integer[] image_id, String[] text){
        super(context, R.layout.list_item, text);
        // TODO Auto-generated constructor stub
        this.color_names = text;
        this.image_id = image_id;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.list_row, null, true);
        //TextView textView = (TextView) single_row.findViewById(R.id.textView);
        ImageView imageView = (ImageView) single_row.findViewById(R.id.imageView);
        //textView.setText(color_names[position]);
        imageView.setImageResource(image_id[position]);
        //imageView.requestLayout();
        //imageView.getLayoutParams().height = 80;
        return single_row;
    }
}
