package com.tokkalo.nzta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class UserConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmation);

        getSupportActionBar().hide();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");

        TextView txt1 = (TextView) findViewById(R.id.textView);
        txt1.setTypeface(font);

        TextView txt2 = (TextView) findViewById(R.id.textView1);
        txt2.setTypeface(font);

        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setTypeface(font);

        TextInputLayout til1 = (TextInputLayout) findViewById(R.id.otpLayout);
        til1.getEditText().setTypeface(font);
        til1.setTypeface(font);
    }

    public void confirmMobile(View v) {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        EditText otp = (EditText) findViewById(R.id.editOtp);
        String editOtp = otp.getText().toString();
        updateDoner(phone, editOtp);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(UserConfirmationActivity.this, MemberActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 10000);
    }



    private void updateDoner(String phone, String otp){
        final ProgressDialog progressDialog  = new ProgressDialog(UserConfirmationActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramPhone = params[0];
                String paramOtp = params[1];

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", paramPhone));
                nameValuePairs.add(new BasicNameValuePair("otp", paramOtp));

                String result = null;
                InputStream inputStream = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://www.tokkalo.com/api/1/confirm_user.php");
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
                        String status = jsonObj.getString("status");
                        String message = jsonObj.getString("message");

                        if (status.equals("SUCCESS")) {
                            View b = findViewById(R.id.button);
                            b.setVisibility(View.INVISIBLE);

                            View otp = findViewById(R.id.editOtp);
                            otp.setVisibility(View.GONE);

                            TextInputLayout til1 = (TextInputLayout) findViewById(R.id.otpLayout);
                            til1.setVisibility(View.GONE);

                            TextView textViewToChange2 = (TextView) findViewById(R.id.textView1);
                            textViewToChange2.setVisibility(View.GONE);

                            TextView textViewToChange = (TextView) findViewById(R.id.textView);
                            textViewToChange.setText(message);



                        }else {

                            Toast.makeText(UserConfirmationActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(UserConfirmationActivity.this, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserConfirmationActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(phone, otp);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserConfirmationActivity.this, MemberActivity.class);
        UserConfirmationActivity.this.startActivity(intent);
    }
}
