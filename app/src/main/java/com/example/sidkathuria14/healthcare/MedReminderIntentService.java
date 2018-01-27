package com.example.sidkathuria14.healthcare;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.tv.TvTrackInfo;
import android.support.v4.app.NotificationCompat;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MedReminderIntentService extends IntentService {


    public MedReminderIntentService() {
        super("MedReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            generateNotification(intent.getStringExtra("MedName"));
        }
    }

    public void generateNotification(String medName){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        CharSequence name = getString(R.string.app_name);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel =
                    new NotificationChannel(
                            "channel_01",
                            name,
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
            notificationManager.createNotificationChannel(mNotificationChannel);
        }
        Intent notifyIntent = new Intent(getApplicationContext(),MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(notifyIntent);

        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder
                 = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_location_on_red_900_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_location_on_red_900_24dp))
                .setColor(Color.RED)
                .setContentTitle("Please Take "+medName)
                .setContentIntent(notificationPendingIntent);

        builder.setChannelId("channel_01");

        builder.setAutoCancel(true);

        notificationManager.notify(0,builder.build());


    }


}
