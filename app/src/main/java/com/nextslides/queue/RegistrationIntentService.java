package com.nextslides.queue;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.util.HashMap;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);

        try {
            // Start register_for_gcm. R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "Retrieved GCM Registration Token: " + token);


            // Save GCM Registration Token to Shared Pref if it is new
            SharedPreferences.Editor editor = shpref.edit();
            String old_token = shpref.getString("token", null);
            editor.putString("token", token);
            editor.commit();
            Log.d(TAG, "New Token : " + token);
            Log.d(TAG, "Old Token from SharedPref : " + old_token);
            Log.d(TAG, "New GCM Registration Token is saved.");

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

        } catch (Exception e) {
            Log.d(TAG, "Failed to acquire GCM Registration Token", e);
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent("REGISTRATION_COMPLETE");
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * This method send the received GCM Registration token to our server
     */
    private void sendRegistrationToServer(String token) {

        //get GCM registration token
        SharedPreferences shpref = getSharedPreferences("MyQueuePref", 0);
        token = shpref.getString("token", null);
        String email = shpref.getString("email", null);


        //get server URL
        ServerURL serverURL = ((ServerURL) getApplicationContext());
        String url_UpdateGCMToken = serverURL.getUrl_UpdateGCMToken();


        //send to server
        String response = null;
        JSONParser jsonParser = new JSONParser();
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("token", token);
            response = jsonParser.makeHttpRequest(url_UpdateGCMToken, "GET", params);
            Log.d(TAG, "GCM Registration Token is updated to our server");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
