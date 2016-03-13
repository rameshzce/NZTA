package com.tokkalo.nzta;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by rameshkolamala on 13/03/16.
 */
public class CustomNumberPicker extends android.widget.NumberPicker {

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if(view instanceof TextView){
            ((TextView) view).setTextSize(34);
            ((TextView) view).setTextColor(Color.parseColor("#b59206"));
            Typeface font = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/handlee-regular.ttf");
            ((TextView) view).setTypeface(font);
        }
    }
}
