package com.nextslides.queue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class ChangeActivity extends AppCompatActivity {

    TextInputLayout counterIDWrapper;
    TextInputLayout userNumberWrapper;
    JSONParser jsonParser = new JSONParser();
    String url_CheckIn;
    String email = null;
    String password = null;
    String userNumber = null;
    String counterID = null;
    FancyButton btnSync;
    FancyButton btnQRCodeScanner;
    Boolean exit = false;
    String TAG = "ChangeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        //setup actionbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_layout);
        }

        //get server URL
        ServerURL serverURL = ((ServerURL) getApplicationContext());
        url_CheckIn = serverURL.getUrl_CheckIn();

        btnSync = (FancyButton) findViewById(R.id.btnSync);
        btnQRCodeScanner = (FancyButton) findViewById(R.id.btnQRScan);

        //textbox
        counterIDWrapper = (TextInputLayout) findViewById(R.id.counterIDWrapper);
        userNumberWrapper = (TextInputLayout) findViewById(R.id.userNumberWrapper);
        counterIDWrapper.setHint("Unique Counter ID");
        userNumberWrapper.setHint("4 digit number");

        //load last check in data
        loadLastCheckIn();

        btnSync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //save current check in
                saveCheckIn();

                hideKeyboard();
                counterID = counterIDWrapper.getEditText().getText().toString();
                userNumber = userNumberWrapper.getEditText().getText().toString();

                if (counterID.equals("")) {
                    counterIDWrapper.setError("Not a valid counter ID!");
                } else if (!validateUserNumber(userNumber)) {
                    userNumberWrapper.setError("Not a valid ticket number!");
                } else {
                    counterIDWrapper.setErrorEnabled(false);
                    userNumberWrapper.setErrorEnabled(false);
                    new tryCheckIn().execute();
                }

            }
        });//end of btnSync listener


        btnQRCodeScanner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, QRCodeScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });//end of btnSync listener


    }//end of onCreate


    //to get result from QRCode Scanner activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra("RESULT");
        Log.d(TAG, "Data from QR Code : " + result);
        counterIDWrapper.getEditText().setText(result, TextView.BufferType.EDITABLE);
        Toast.makeText(ChangeActivity.this, "A Counter ID is captured via QR Code", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //save current checkin to sharedpref
    public void saveCheckIn() {
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);
        SharedPreferences.Editor editor = shpref.edit();
        counterID = counterIDWrapper.getEditText().getText().toString();
        userNumber = userNumberWrapper.getEditText().getText().toString();
        editor.putString("counterID", counterID);
        editor.putString("userNumber", userNumber);
        editor.commit();
    }

    //load last checkin from sharedpref
    public void loadLastCheckIn() {
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);
        counterID = shpref.getString("counterID", "");
        userNumber = shpref.getString("userNumber", "");
        counterIDWrapper.getEditText().setText(counterID);
        userNumberWrapper.getEditText().setText(userNumber);
    }

    //validate 4 digit number
    public boolean validateUserNumber(String userNumber) {
        /*
        final String USERNUMBER_PATTERN = "(\\\\d{4})";
        Pattern userNumberPattern = Pattern.compile(USERNUMBER_PATTERN);
        Matcher matcher = userNumberPattern.matcher(userNumber);
        return matcher.matches();
        */
        return true;
    }

    //hide keyboard
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //menu item action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //setup menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //on back press, kill the activity
    @Override
    public void onBackPressed() {
        if (exit) {
            ChangeActivity.this.finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    //Background task for Checking In
    class tryCheckIn extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = null;

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("counterID", counterID);
                params.put("userNumber", userNumber);
                response = jsonParser.makeHttpRequest(url_CheckIn, "GET", params);

                Log.d("debug", "Params for post_check_in.php : " + params.toString());
                Log.d("debug", "Response of post_check_in.php : " + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {

            //start display activity
            Intent intent = new Intent(ChangeActivity.this, DisplayActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("counterID", counterID);
            intent.putExtra("userNumber", userNumber);
            startActivity(intent);

            //clean up
            ChangeActivity.this.finish();
            Log.d("debug", "Destorying ChangeActivity. Load up DisplayActivity");
        }
    }//end of tryCheckIn


}
