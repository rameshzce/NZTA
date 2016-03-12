package com.tokkalo.nzta;

    import android.app.ProgressDialog;
    import android.content.Context;
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
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AbsListView;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.RelativeLayout;
    import android.widget.SimpleAdapter;

    import org.apache.http.HttpEntity;
    import org.apache.http.HttpResponse;
    import org.apache.http.client.ClientProtocolException;
    import org.apache.http.client.HttpClient;
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

    import android.widget.TextView;
    import android.widget.Toast;

    import org.apache.http.NameValuePair;
    import org.apache.http.client.entity.UrlEncodedFormEntity;

    import java.util.List;


    public class UpcomingEventsActivity extends AppCompatActivity {
        String myJSON;
        TextView textView;

        private static final String TAG_RESULTS="result";
        private static final String TAG_ID = "id";
        private static final String TAG_NAME = "name";
        private static final String TAG_ADD ="address";
        SharedPreferences prefs;



        JSONArray peoples = null;

        ArrayList<HashMap<String, String>> personList;

        ListView list;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upcoming_events);

            list = (ListView) findViewById(R.id.listView);
            personList = new ArrayList<HashMap<String,String>>();

            RelativeLayout.LayoutParams lpimgHeader = new RelativeLayout.LayoutParams(list.getLayoutParams());
            lpimgHeader.setMargins(50, 0, 50, 0);
            list.setLayoutParams(lpimgHeader);


            ActionBar ab = getSupportActionBar();

            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

            // Set the ActionBar background color
            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9d1457")));

            // Create a TextView programmatically.
            TextView tv = new TextView(getApplicationContext());

            // Create a LayoutParams for TextView
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                    AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

            // Apply the layout parameters to TextView widget
            tv.setLayoutParams(lp);

            // Set text to display in TextView
            // This will set the ActionBar title text
            tv.setText("Upcoming events 2016");

            tv.setGravity(Gravity.CENTER);

            // Set the serif font for TextView text
            // This will change ActionBar title text font
            tv.setTypeface(font);
            //tv.setTypeface(null, Typeface.BOLD);

            // Set the text color of TextView
            // This will change the ActionBar title text color
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            // Finally, set the newly created TextView as ActionBar custom view
            ab.setCustomView(tv);

            getSearchResults();

        }


        protected void showList(){
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i=0;i<peoples.length();i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String id = c.getString(TAG_ID);
                    String address = c.getString(TAG_ADD);

                    HashMap<String,String> persons = new HashMap<String,String>();

                    persons.put(TAG_ID,id);
                    persons.put(TAG_ADD,address);

                    personList.add(persons);
                }

                SpecialAdapter adapter = new SpecialAdapter(
                        UpcomingEventsActivity.this, personList, R.layout.list_item,
                        new String[]{TAG_ID,TAG_ADD},
                        new int[]{R.id.id, R.id.address}
                );

                list.setAdapter(adapter);






            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                    Intent intent = new Intent(UpcomingEventsActivity.this, MainActivity.class);
                    UpcomingEventsActivity.this.startActivity(intent);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void getSearchResults(){
            final ProgressDialog progressDialog  = new ProgressDialog(UpcomingEventsActivity.this);
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
                                Toast.makeText(UpcomingEventsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UpcomingEventsActivity.this, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpcomingEventsActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            sendPostReqAsyncTask.execute();
        }

        public void showMenu(View view){

            Toast.makeText(UpcomingEventsActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();


        }
    }

