package com.verma.mobile.android.pushdemo.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.verma.mobile.android.pushdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sourav.verma on 19-03-2018.
 */

public class Constants {

    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Simplified Coding Notification";
    public static final String CHANNEL_DESCRIPTION = "www.souravverma.com";


    public static String getCurrentTime(){
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();*/
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        System.out.println(datetime);
        return  datetime;

    }

    public void createNotification(Context pContext,String pMesageSubject ,String pMesageBody){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) pContext.getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
        MyNotificationManager.getInstance(pContext).displayNotification(pMesageSubject, pMesageBody);
    }
}
