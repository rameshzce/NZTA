package com.tokkalo.nzta;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rameshkolamala on 19/03/16.
 */
public class CustomGridViewAdapter extends ArrayAdapter<ClipData.Item> {
    Context context;
    int layoutResourceId;
    ArrayList<ClipData.Item> data = new ArrayList<ClipData.Item>();

    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<ClipData.Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        ClipData.Item item = data.get(position);
        //holder.txtTitle.setText(item.getTitle());
        //holder.imageItem.setImageBitmap(item.getImage());
        holder.txtTitle.setText("rams");
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }
}
