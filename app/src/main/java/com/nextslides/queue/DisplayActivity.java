package com.nextslides.queue;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class DisplayActivity extends AppCompatActivity {

    //UI
    TextView txtCurrentNo;
    Toolbar myToolbar;
    FancyButton btnChange;
    FancyButton btnGetNotified;
    TextView lblCurrentNo;
    TextView txtPeopleInFront;
    TextView txtWaitedFor;

    //var
    String currentNo = null;
    String counterName = null;
    String locationName = null;
    String email;
    String counterID;
    String userNumber;
    JSONParser jsonParser = new JSONParser();
    ServerURL serverURL;
    String url_getCurrentNo;
    Boolean exit = false;
    Intent intent;
    Timestamp lastSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //action bar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.abs_layout);
        }

        //get server URL
        serverURL = ((ServerURL) getApplicationContext());
        url_getCurrentNo = serverURL.getUrl_getCurrentNo();

        //get data from previous activity
        intent = getIntent();
        email = intent.getStringExtra("email");
        counterID = intent.getStringExtra("counterID");
        userNumber = intent.getStringExtra("userNumber");


        //get current number in a background task
        new getCurrentNo().execute();

        //setup btnChange
        btnChange = (FancyButton) findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //start ChangeActivity
                Intent intent = new Intent(DisplayActivity.this, ChangeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

                //clean up
                DisplayActivity.this.finish();
            }
        });

        //TO DO
        //setup btnGetNotified
        btnGetNotified = (FancyButton) findViewById(R.id.btnGetNotified);
        btnGetNotified.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //start GetNotifiedActivity
                //TO DO

                //clean up
                DisplayActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //on back press, kill the activity
    @Override
    public void onBackPressed() {
        //start ChangeActivity
        Intent intent = new Intent(DisplayActivity.this, ChangeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);

        //clean up
        DisplayActivity.this.finish();
    }

    //return diff in minutes between two timestamp
    public Integer compareTwoTimeStamps(Timestamp newerTime, Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = newerTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffMinutesLong = diff / (60 * 1000);
        Integer diffMinutes = (int) (long) diffMinutesLong;
        return diffMinutes;

    }

    class getCurrentNo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = null;

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("counterID", counterID);
                Log.d("debug", "Params for get_current_num.php : " + params.toString());
                response = jsonParser.makeHttpRequest(url_getCurrentNo, "GET", params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String response) {

            Log.d("debug", "Response : " + response);

            if (!(response.equals(""))) {

                //set UI
                String[] tokens = response.split(",");
                currentNo = tokens[0];
                counterName = tokens[1];
                locationName = tokens[2];
                txtCurrentNo = (TextView) findViewById(R.id.txtCurrentNo);
                txtCurrentNo.setText(currentNo);
                lblCurrentNo = (TextView) findViewById(R.id.lblCurrentNo);
                lblCurrentNo.setText("Current number served for " + counterName + " at " + locationName + " is ");


                //set num of people in front
                Integer peopleInfront = Integer.parseInt(userNumber) - Integer.parseInt(currentNo);
                txtPeopleInFront = (TextView) findViewById(R.id.txtPeopleInFront);
                txtPeopleInFront.setText(peopleInfront.toString());
                Log.d("debug", peopleInfront.toString());

                //set minutes
                try {
                    //get last sync time
                    Log.d("debug", "Last sync time : " + tokens[3]);
                    lastSync = Timestamp.valueOf(tokens[3]);

                    //get current time
                    java.util.Date date = new java.util.Date();
                    Timestamp timestampNow = new Timestamp(date.getTime());
                    Log.d("debug", "Current time : " + timestampNow.toString());

                    //get waiting time
                    Integer waitInMinutes = compareTwoTimeStamps(timestampNow, lastSync);
                    Log.d("debug", waitInMinutes.toString());

                    //set UI
                    txtWaitedFor = (TextView) findViewById(R.id.txtWaitedFor);
                    txtWaitedFor.setText(waitInMinutes.toString() + " minutes");

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                //if failed, go back to ChangeActivity
                Toast.makeText(DisplayActivity.this, "Could not locate this Counter ID. \n Please re-enter", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DisplayActivity.this, ChangeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                DisplayActivity.this.finish();
            }
        }
    }//end of getCurrentNo

}
