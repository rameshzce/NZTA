package com.tokkalo.nzta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExistingEventsActivity extends AppCompatActivity {
    CustomNumberPicker cnp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_events);

        //setTitle("Existing Events");

        ActionBar ab = getSupportActionBar();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setTypeface(font);
        btn1.setBackgroundColor(Color.parseColor("#b59206"));

        TextView txt1 = (TextView) findViewById(R.id.textView1);
        txt1.setTypeface(font);

        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        tv.setText("Existing events");

        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(font);

        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        NumberPicker np = (NumberPicker) findViewById(R.id.years);
        np.setMaxValue(2015);
        np.setMinValue(2010);

        //TextView et = (TextView) np.getChildAt(1);
        //et.setTypeface(font);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                //Toast toast= Toast.makeText(getApplicationContext(), "" + newVal, Toast.LENGTH_SHORT);
                //toast.show();
            }
        });

        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backBtn:
                Intent intent = new Intent(ExistingEventsActivity.this, MemberActivity.class);
                ExistingEventsActivity.this.startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getEvents(View view){
        cnp = (CustomNumberPicker) findViewById(R.id.years);
        String value = "" + cnp.getValue();
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

    }
}
