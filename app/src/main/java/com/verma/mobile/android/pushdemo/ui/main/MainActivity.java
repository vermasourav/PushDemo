package com.verma.mobile.android.pushdemo.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.verma.mobile.android.pushdemo.R;
import com.verma.mobile.android.pushdemo.ui.BaseApp;
import com.verma.mobile.android.pushdemo.ui.Constants;
import com.verma.mobile.android.pushdemo.ui.MyNotificationManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sourav.verma on 19-03-2018.
 */

public class MainActivity extends BaseApp{


    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        String pMesageBody = getString(R.string.app_name) + " Local Message is created at " + Constants.getCurrentTime();
        String pMesageSubject = "Greetings";
        new Constants().createNotification(this,pMesageSubject,pMesageBody);
    }



   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       if (getIntent().getExtras() != null) {

           for (String key : getIntent().getExtras().keySet()) {
               String value = getIntent().getExtras().getString(key);

               if (key.equals("AnotherActivity") && value.equals("True")) {
                   /*Intent intent = new Intent(this, AnotherActivity.class);
                   intent.putExtra("value", value);
                   startActivity(intent);
                   finish();*/
               }

           }
       }

       subscribeToPushService();

   }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Log.d("AndroidBash", "Subscribed");
        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d("AndroidBash", token);
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
    }


}
