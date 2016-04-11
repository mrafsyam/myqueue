package com.nextslides.queue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;


public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    TextView txtCurrentNo;
    FancyButton btnSync;
    String currentNo;
    JSONParser jsonParser = new JSONParser();
    String url_getCurrentNo;
    String url_getLogin;
    String email = null;
    String password = null;
    TextInputLayout emailWrapper;
    TextInputLayout passwordWrapper;
    Boolean exit = false;
    Context context;
    GoogleCloudMessaging gcm;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private boolean isReceiverRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        //set server URL
        ServerURL serverURL = ((ServerURL) getApplicationContext());
        url_getCurrentNo = serverURL.getUrl_getCurrentNo();
        url_getLogin = serverURL.getUrl_getLogin();

        //logo decorator animation
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        WanderingCubes wanderingCubes = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wanderingCubes);


        //gcm
        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d(TAG, getString(R.string.gcm_send_message));
                } else {
                    Log.d(TAG, getString(R.string.token_error_message));
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        //setup login screen
        emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        emailWrapper.setHint("Email");
        passwordWrapper.setHint("Password");
        btnSync = (FancyButton) findViewById(R.id.btnSync);
        CheckBox cbSaveLogin = (CheckBox) findViewById(R.id.cbSaveLogin);
        cbSaveLogin.setChecked(true);

        //load savedlogin information
        loadSavedLogin();

        btnSync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();
                email = emailWrapper.getEditText().getText().toString();
                password = passwordWrapper.getEditText().getText().toString();

                if (!validateEmail(email)) {
                    emailWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    new tryLogin().execute();
                }
            }
        });


    }//end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                GooglePlayServicesUtil.getErrorDialog(1, MainActivity.this, 1);
                finish();
            }
            return false;
        }
        return true;
    }

    //save email and passw to sharedpref
    public void saveLogin() {

        CheckBox cbSaveLogin = (CheckBox) findViewById(R.id.cbSaveLogin);
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);
        SharedPreferences.Editor editor = shpref.edit();

        if (cbSaveLogin.isChecked()) {
            editor.putString("email", email);
            editor.putString("password", password);
            editor.commit();
        } else {
            editor.clear();
            editor.commit();
        }
    }

    //load last login detail
    public void loadSavedLogin() {

        //load up saved login details
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);
        email = shpref.getString("email", "");
        password = shpref.getString("password", "");

        if (email != "") {
            emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
            emailWrapper.getEditText().setText(email);
            passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
            passwordWrapper.getEditText().setText(password);
        }
    }

    //on back press, kill the activity
    @Override
    public void onBackPressed() {
        if (exit) {
            MainActivity.this.finish();
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

    //hide keyboard
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //validate email
    public boolean validateEmail(String email) {
        final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    //validate password
    public boolean validatePassword(String password) {
        return password.length() > 4;
    }

    class tryLogin extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            String response = null;

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                response = jsonParser.makeHttpRequest(url_getLogin, "GET", params);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }


        protected void onPostExecute(String response) {

            //reset params for checking
            email = null;
            password = null;

            try {
                JSONObject jsonResponse = new JSONObject(response);
                email = jsonResponse.getString("email");
                password = jsonResponse.getString("password");
                Log.d("Login Response : ", email + "\t" + password);

                //start new activity when login is valid
                if (password != null) {
                    //save login details
                    saveLogin();

                    //start new activity
                    Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
                    intent.putExtra("email", email);
                    //intent.putExtra("password", password);
                    startActivity(intent);

                    //clean up
                    MainActivity.this.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//end of tryLogin

}








