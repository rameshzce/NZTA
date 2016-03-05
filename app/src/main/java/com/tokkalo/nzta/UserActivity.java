package com.tokkalo.nzta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    TextView info;

    String color_names[] = {"red", "green", "blue", "yellow"};
    Integer image_id[] = {R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4};
    Context applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        applicationContext = getApplicationContext();

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String loginType = intent.getStringExtra("loginType");

        //info = (TextView)findViewById(R.id.info);
        //info.setText("Hi " + userId + ", you have logged in with " + loginType);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        CustomListAdapter adapter = new CustomListAdapter(this, image_id, color_names);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(UserActivity.this, "" + position + id, Toast.LENGTH_SHORT).show();
                if(position == 0){
                    Intent i = new Intent(applicationContext, UCEventsActivity.class);
                    startActivity(i);
                    finish();
                }else if(position == 1){
                    Intent i = new Intent(applicationContext, ExistingEventsActivity.class);
                    startActivity(i);
                    finish();
                }else if(position == 2){
                    Intent i = new Intent(applicationContext, NewZealandActivity.class);
                    startActivity(i);
                    finish();
                }else if(position == 3){
                    Intent i = new Intent(applicationContext, InviteFriendActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //if(login == true) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        //}

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent profileIntent = new Intent(UserActivity.this, ProfileActivity.class);
                UserActivity.this.startActivity(profileIntent);
                return true;
            case R.id.item2:
                Intent membershipIntent = new Intent(UserActivity.this, MembershipActivity.class);
                UserActivity.this.startActivity(membershipIntent);
                return true;
            case R.id.item3:
                Intent referfriendIntent = new Intent(UserActivity.this, ReferFriendActivity.class);
                UserActivity.this.startActivity(referfriendIntent);
                return true;
            case R.id.item5:
                //alertMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
