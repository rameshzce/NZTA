package com.tokkalo.nzta;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
//import com.twitter.sdk.android.Twitter;
//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.TwitterAuthConfig;
//import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;




import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;




public class MainActivity extends AppCompatActivity {
    //private TwitterLoginButton loginButton;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    //private static final String TWITTER_KEY = "tNMYriDJoxKnASqEvLCUdhES4";
    //private static final String TWITTER_SECRET = "vZwTgq47Ixd67kzykhFAwULOHSL2ZnTubYqi4X17l8THSRman6";

    private EditText editTextName;
    private EditText editTextMobile;
    private EditText editTextEmail;

    private TextView info;
    private LoginButton loginButtonFb;
    private CallbackManager callbackManager;


    //Context applicationContext;

    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String regId = "";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    AsyncTask<Void, Void, String> createRegIdTask;

    public static final String REG_ID = "regId";
    public static final String EMAIL_ID = "eMailId";
    public static final String MOBILE_NUMBER = "mobileNumber";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig));

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/handlee-regular.ttf");



        info = (TextView)findViewById(R.id.fbLoginInfo);
        info.setTypeface(font);
        loginButtonFb = (LoginButton)findViewById(R.id.login_button);

        loginButtonFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userId = loginResult.getAccessToken().getUserId();
                /*info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                insertToDatabase(userId, userId, userId);
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });



        TextView txt1 = (TextView) findViewById(R.id.textView1);
        txt1.setTypeface(font);

        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setTypeface(font);

        EditText txt2 = (EditText) findViewById(R.id.editTextName);
        txt2.setTypeface(font);

        EditText txt3 = (EditText) findViewById(R.id.editTextMobile);
        txt3.setTypeface(font);

        EditText txt4 = (EditText) findViewById(R.id.editTextEmail);
        txt4.setTypeface(font);


        TextInputLayout til1 = (TextInputLayout) findViewById(R.id.nameLayout);
        til1.getEditText().setTypeface(font);
        til1.setTypeface(font);

        TextInputLayout til2 = (TextInputLayout) findViewById(R.id.mobLayout);
        til2.getEditText().setTypeface(font);
        til2.setTypeface(font);

        TextInputLayout til3 = (TextInputLayout) findViewById(R.id.emailLayout);
        til3.getEditText().setTypeface(font);
        til3.setTypeface(font);



        /*loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                insertToDatabase(session.getUserName(), session.getUserName(), session.getUserName());
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });*/


        getSupportActionBar().hide();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        applicationContext = getApplicationContext();

        applicationContext = getApplicationContext();

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        SharedPreferences prefs = getSharedPreferences("com.tokkalo.nzta", Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");

        String mobileNumber = prefs.getString(MOBILE_NUMBER, "");

        if (!TextUtils.isEmpty(mobileNumber)) {
            Intent i = new Intent(applicationContext, MemberActivity.class);
            //startActivity(i);
            //finish();
        }

        //When Email ID is set in Sharedpref, User will be taken to HomeActivity
        if (!TextUtils.isEmpty(registrationId)) {
            Intent i = new Intent(applicationContext, MemberActivity.class);
            i.putExtra("regId", registrationId);
            startActivity(i);
            finish();
        }


    }

    public void insert(View view){


        String name = editTextName.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String email = editTextEmail.getText().toString();

        if(name.isEmpty()){
            showToast("Please enter a name");
        } else if(mobile.isEmpty()){
            showToast("Please enter a mobile number");
        } else if(mobile.length() != 10 ){
            showToast("Mobile number must be 10 digits");
        } else if(email.isEmpty()){
            showToast("Please enter an email id");
        } else {
            insertToDatabase(name, mobile, email);
        }

    }

    private void insertToDatabase(final String name, String mobileNumber,String organization){
        final ProgressDialog progressDialog  = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("fname", params[0]));
                nameValuePairs.add(new BasicNameValuePair("mobile", params[1]));
                nameValuePairs.add(new BasicNameValuePair("organization", params[2]));

                String result = null;
                InputStream inputStream = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://www.tokkalo.com/api/1/submit_user.php");
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

                //TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText(result);

                String jsonStr = result;
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        String status = jsonObj.getString("status");
                        String message = jsonObj.getString("message");

                        if (status.equals("SUCCESS")) {


                            String phone = jsonObj.getString("phone");
                            String otp = jsonObj.getString("otp");

                            RegisterUser(phone, name);

                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                            if(phone.equals(name)){
                                Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                                MainActivity.this.startActivity(intent);
                            }else {
                                Intent intent = new Intent(MainActivity.this, UserConfirmationActivity.class);
                                intent.putExtra("phone", phone);
                                intent.putExtra("otp", otp);
                                MainActivity.this.startActivity(intent);
                            }

                        } else if (status.equals("FAILURE")) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, mobileNumber,organization);
    }

    // When Register Me button is clicked
    public void RegisterUser(String phone, String name) {
        //String mobile = editTextMobile.getText().toString();
        //String name = editTextName.getText().toString();

        if (checkPlayServices()) {
            // Register Device in GCM Server
            registerInBackground(phone, name);
        }
    }


    // AsyncTask to register Device in GCM Server
    private void registerInBackground(final String mobile, final String name) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    // Store RegId created by GCM Server in SharedPref
                    storeRegIdinSharedPref(applicationContext, regId, mobile);
                    /*Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.nn"
                                    + msg, Toast.LENGTH_SHORT).show();*/
                } else {
                    Toast.makeText(
                            applicationContext,
                            "Reg ID Creation Failed.nnEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    // Store  RegId and Email entered by User in SharedPref
    private void storeRegIdinSharedPref(Context context, String regId,
                                        String mobile) {
        SharedPreferences prefs = getSharedPreferences("com.tokkalo.nzta",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putString(MOBILE_NUMBER, mobile);
        editor.commit();
        storeRegIdinServer(mobile);

    }

    // Share RegID with GCM Server Application (Php)
    private void storeRegIdinServer(String mobile) {
        //String mobileNumber = editTextMobile.getText().toString();
        prgDialog.show();
        params.put("regId", regId);
        params.put("mobileNumber", mobile);
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ApplicationConstants.APP_SERVER_URL, params,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        /*Toast.makeText(applicationContext,
                                "Reg Id shared successfully with Web App ",
                                Toast.LENGTH_LONG).show();*/
                        //Intent i = new Intent(applicationContext, HomeActivity.class);
                        //i.putExtra("regId", regId);
                        //startActivity(i);
                        //finish();
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(applicationContext,
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(applicationContext,
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    applicationContext,
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void showToast(String msg){
        Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    // Check if Google Playservices is installed in Device or not
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //Toast.makeText(
                //applicationContext,
                //"This device doesn't support Play services, App will not work normally",
                //Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            //Toast.makeText(
            //applicationContext,
            //"This device supports Play services, App will work normally",
            //Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        //loginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
