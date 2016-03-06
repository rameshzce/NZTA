package com.tokkalo.nzta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReferFriendActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private String fromMobile;
    private String toMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friend);

        getSupportActionBar().hide();

        editTextMobile = (EditText) findViewById(R.id.editTextMobile);

        SharedPreferences prefs = getSharedPreferences("com.tokkalo.nzta", Context.MODE_PRIVATE);

        fromMobile = prefs.getString("mobileNumber", "");

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        TextView txt1 = (TextView) findViewById(R.id.textView1);
        txt1.setTypeface(font);

        TextView txt2 = (TextView) findViewById(R.id.textViewResult);
        txt2.setTypeface(font);

        editTextMobile.setTypeface(font);
    }

    public void submit(View view){
        toMobile = editTextMobile.getText().toString();

        if(toMobile.isEmpty()){
            showToast("Please enter a mobile number");
        } else if(toMobile.length() != 10 ){
            showToast("Mobile number must be 10 digits");
        } else {
            insertToDatabase(fromMobile, toMobile);
        }
    }

    public void sendMessage(View view){
        Intent intent = new Intent(ReferFriendActivity.this, SendMessageActivity.class);
        ReferFriendActivity.this.startActivity(intent);
    }

    private void insertToDatabase(String fromMobile, String toMobile){
        final ProgressDialog progressDialog  = new ProgressDialog(ReferFriendActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fromMobile", params[0]));
                nameValuePairs.add(new BasicNameValuePair("toMobile", params[1]));

                String result = null;
                InputStream inputStream = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://www.tokkalo.com/api/1/invite_friend.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
                        //String status = jsonObj.getString("status");
                        String message = jsonObj.getString("message");

                        //Toast.makeText(ReferFriendActivity.this, message, Toast.LENGTH_SHORT).show();

                        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                        textViewResult.setText(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ReferFriendActivity.this, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ReferFriendActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(fromMobile, toMobile);
    }

    public void showToast(String msg){
        Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}
