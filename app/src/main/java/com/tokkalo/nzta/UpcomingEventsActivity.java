package com.tokkalo.nzta;

    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Color;
    import android.graphics.Point;
    import android.graphics.Typeface;
    import android.graphics.drawable.ColorDrawable;
    import android.os.AsyncTask;
    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.TypedValue;
    import android.view.Display;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.Surface;
    import android.view.View;
    import android.view.WindowManager;
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

        String jsonData = "{\"status\":\"SUCCESS\",\"message\":\"9 doner(s) found for your search.\",\"remarks\":\"\",\"doners\":{\"result\":[{\"id\":\"Sankranti\",\"name\":1,\"address\":\"Rangoli competition & kite festival on 17-1-2016 \"},{\"id\":\"Ugadi\",\"name\":2,\"address\":\"Event at epsom on 1-3-2016 Saturday at 6.00pm, all are welcome and followed by dinner \"},{\"id\":\"Batukamma\",\"name\":3,\"address\":\"War memorial hall, Mount eden on 1-6-2016 Friday 6.00pm, all are welcome and followed by dinner.\"},{\"id\":\"Diwali\",\"name\":4,\"address\":\"Diwali stall opens at 2 Pm at queens street. reworks and programs starts at 7 Pm.\\r\\nThe next day we have diwali celabrations\\r\\nat avondale. children participating Dances and some programs. please participate and enjoy the celebrations at 7.00 PM. Followed \"},{\"id\":\"X'mas\",\"name\":5,\"address\":\"War memorial hall, Mount eden,\\r\\non 01-06-16 friday, 6.00 pm, all are welcome and followed by dinner.\"},{\"id\":\"Sankranthi 2016\",\"name\":6,\"address\":\"Rangoli competation & kite festival on 17-01-16 sunday.\"},{\"id\":\"Ugadi\",\"name\":7,\"address\":\"Eevent at epsom on 01-03-16 Saturday at 6.00 pm, all are welcome\\r\\nand followed by dinner.\"},{\"id\":\"Batukamma\",\"name\":8,\"address\":\"War memorial hall, Mount eden,\\r\\non 01-06-16 friday, 6.00 pm, all are welcome and followed by dinner.\"},{\"id\":\"Diwali\",\"name\":9,\"address\":\"Diwali stall opens at 2 Pm at queens street. reworks and programs starts at 7 Pm.\\r\\nThe next day we have diwali celabrations\\r\\nat avondale. children participating Dances and some programs. please participate and enjoy the celebrations at 7.00 PM. Followed \"}]}}";

        JSONArray peoples = null;

        ArrayList<HashMap<String, String>> personList;

        ListView list;

        int width;
        String rotation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upcoming_events);

            list = (ListView) findViewById(R.id.listView);
            personList = new ArrayList<HashMap<String,String>>();

            //RelativeLayout.LayoutParams lpimgHeader = new RelativeLayout.LayoutParams(list.getLayoutParams());
            //lpimgHeader.setMargins(50, 0, 50, 0);
            //list.setLayoutParams(lpimgHeader);


            ActionBar ab = getSupportActionBar();

            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

            ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9d1457")));

            TextView tv = new TextView(getApplicationContext());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, // Width of TextView
                    AbsListView.LayoutParams.WRAP_CONTENT); // Height of TextView

            tv.setLayoutParams(lp);

            tv.setText("Upcoming events 2016");

            tv.setGravity(Gravity.CENTER);

            tv.setTypeface(font);

            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            ab.setCustomView(tv);

            //getSearchResults();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            int height = size.y;


            rotation = getRotation(getApplicationContext());

            showList();

        }


        protected void showList(){
            try {
                //JSONObject jsonObj = new JSONObject(myJSON);
                JSONObject jsonObj1 = new JSONObject(jsonData);
                String events = jsonObj1.getString("doners");

                JSONObject jsonObj = new JSONObject(events);
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

                //Toast.makeText(UpcomingEventsActivity.this, width + " " + rotation, Toast.LENGTH_SHORT).show();



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

        public String getRotation(Context context) {
            final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
            switch (rotation) {
                case Surface.ROTATION_0:
                    //return "portrait";
                    return "P";
                case Surface.ROTATION_90:
                    //return "landscape";
                    return "L";
                case Surface.ROTATION_180:
                    //return "reverse portrait";
                    return "P";
                default:
                    //return "reverse landscape";
                    return "L";
            }
        }
    }

