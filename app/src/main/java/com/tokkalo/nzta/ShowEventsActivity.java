package com.tokkalo.nzta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowEventsActivity extends AppCompatActivity {
    String myJSON;
    TextView textView;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD ="address";
    SharedPreferences prefs;


    String jsonData = "{\"status\":\"SUCCESS\",\"message\":\"9 doner(s) found for your search.\",\"remarks\":\"\",\"doners\":{\"result\":[{\"id\":\"Sankranti\",\"name\":1,\"address\":\"Rangoli competition & kite festival on 17-1-2016 \"},{\"id\":\"Ugadi\",\"name\":2,\"address\":\"Event at epsom on 1-3-2016 Saturday at 6.00pm, all are welcome and followed by dinner \"},{\"id\":\"Batukamma\",\"name\":3,\"address\":\"War memorial hall, Mount eden on 1-6-2016 Friday 6.00pm, all are welcome and followed by dinner.\"},{\"id\":\"Diwali\",\"name\":4,\"address\":\"Diwali stall opens at 2 Pm at queens street. reworks and programs starts at 7 Pm.\\r\\nThe next day we have diwali celabrations\\r\\nat avondale. children participating Dances and some programs. please participate and enjoy the celebrations at 7.00 PM. Followed \"},{\"id\":\"X'mas\",\"name\":5,\"address\":\"War memorial hall, Mount eden,\\r\\non 01-06-16 friday, 6.00 pm, all are welcome and followed by dinner.\"},{\"id\":\"Sankranthi 2016\",\"name\":6,\"address\":\"Rangoli competation & kite festival on 17-01-16 sunday.\"},{\"id\":\"Ugadi\",\"name\":7,\"address\":\"Eevent at epsom on 01-03-16 Saturday at 6.00 pm, all are welcome\\r\\nand followed by dinner.\"},{\"id\":\"Batukamma\",\"name\":8,\"address\":\"War memorial hall, Mount eden,\\r\\non 01-06-16 friday, 6.00 pm, all are welcome and followed by dinner.\"},{\"id\":\"Diwali\",\"name\":9,\"address\":\"Diwali stall opens at 2 Pm at queens street. reworks and programs starts at 7 Pm.\\r\\nThe next day we have diwali celabrations\\r\\nat avondale. children participating Dances and some programs. please participate and enjoy the celebrations at 7.00 PM. Followed \"}]}}";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        Intent intent = getIntent();
        final String year = intent.getStringExtra("year");

        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();

        /*RelativeLayout.LayoutParams lpimgHeader = new RelativeLayout.LayoutParams(list.getLayoutParams());
        lpimgHeader.setMargins(50, 0, 50, 0);
        list.setLayoutParams(lpimgHeader);*/

        ActionBar ab = getSupportActionBar();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b59206")));

        TextView tv = new TextView(getApplicationContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

        tv.setLayoutParams(lp);

        tv.setText("Existing Events " + year);

        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(font);

        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ab.setCustomView(tv);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(ShowEventsActivity.this, GalleryActivity.class);
                intent.putExtra("galleryType", "Photo Gallery");
                intent.putExtra("year", year);
                ShowEventsActivity.this.startActivity(intent);

            }
        });

        //getSearchResults();

        showList();
    }

    protected void showList() {
        try {
            //JSONObject jsonObj = new JSONObject(myJSON);
            JSONObject jsonObj1 = new JSONObject(jsonData);
            String events = jsonObj1.getString("doners");

            JSONObject jsonObj = new JSONObject(events);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String address = c.getString(TAG_ADD);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_ADD, address);

                personList.add(persons);
            }

            SpecialAdapter2 adapter = new SpecialAdapter2(
                    ShowEventsActivity.this, personList, R.layout.list_item2,
                    new String[]{TAG_ID, TAG_ADD},
                    new int[]{R.id.id, R.id.address}
            );

            list.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getSearchResults(){
        final ProgressDialog progressDialog  = new ProgressDialog(ShowEventsActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();



                String result = null;
                InputStream inputStream = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://www.tokkalo.com/api/1/show_events.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    //httpPost.setHeader("Content-type", "application/json");


                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();



                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setProgress(0);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.hide();

                String jsonStr = result;
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        String status = jsonObj.getString("status");
                        String message = jsonObj.getString("message");

                        if (status.equals("SUCCESS")) {
                            String doners = jsonObj.getString("doners");

                            myJSON=doners;
                            showList();

                        } else if (status.equals("FAILURE")) {
                            textView = (TextView) findViewById(R.id.textView);
                            textView.setText("Sorry, no doners are available for your search at this time.");
                            textView.setTextColor(Color.RED);
                        } else {
                            Toast.makeText(ShowEventsActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ShowEventsActivity.this, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShowEventsActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


}
