package com.myapplicationdev.android.taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduledNotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;
    int notificationID = 888;
    ArrayList<Task> tasklist;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }


        Intent i = new Intent(context, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode,
                i, PendingIntent.FLAG_CANCEL_CURRENT);


        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String id = intent.getStringExtra("id");
//        Log.d("TAG", "onReceive: " + id);

        tasklist = new ArrayList<>();


        //launch task manager

        Intent intentTaskManager = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentTM = PendingIntent.getActivity(context, 0, intentTaskManager, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pendingIntentTM).build();

        //launch reply
        Intent intentreply = new Intent(context,
                ReplyActivity.class);
        intentreply.putExtra("id", id);
        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentreply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        //launch reply2 add
        Intent intentAdd = new Intent(context,
                Wear_Add_Activity.class);

        PendingIntent pendingintentAdd = PendingIntent.getActivity
                (context, 0, intentAdd,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri2 = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Run 2.4km Tonight", "Not yet"})
                .build();

        NotificationCompat.Action action3 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Add Task",
                pendingintentAdd)
                .addRemoteInput(ri2)
                .build();


        // build notification
        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);
        extender.addAction(action3);

        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.sentosa);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = {0, 100, 200, 300};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task");
        builder.setContentText(title + "\n" + desc);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
//        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(picture).bigLargeIcon(null));
        builder.setSound(alarmSound);
        builder.setVibrate(vibrate);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);

        // Attach the action for Wear notification created above
        builder.extend(extender);

        Notification n = builder.build();
        notificationManager.notify(notificationID, n);
    }
}
