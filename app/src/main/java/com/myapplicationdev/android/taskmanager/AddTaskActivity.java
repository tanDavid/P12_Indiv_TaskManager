package com.myapplicationdev.android.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    int requestCode = 12345;
    int notificationID = 888;
    EditText etName, etDesc, etSec;
    Button btnAdd, btnCancel;
    ArrayList<Task> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        etSec = findViewById(R.id.etSec);

        al = new ArrayList<Task>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);

                if (!etName.getText().toString().isEmpty() && !etDesc.getText().toString().isEmpty() && !etSec.getText().toString().isEmpty()) {
                    //inserting task
                    String name = etName.getText().toString();
                    String desc = etDesc.getText().toString();

                    DBHelper db = new DBHelper(AddTaskActivity.this);
                    db.insertTask(desc, name);
                    Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
//                    Log.i("info", db.getTasks().get(db.getTasks().size()-1).getId() + "");

                    int id = db.getTasks().get(db.getTasks().size()-1).getId();

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, Integer.parseInt(etSec.getText().toString()));

                    Intent intent = new Intent(AddTaskActivity.this,
                            ScheduledNotificationReceiver.class);

                    intent.putExtra("title", etName.getText().toString());
                    intent.putExtra("desc", etDesc.getText().toString());
                    intent.putExtra("id", String.valueOf(id));

                    db.close();

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            AddTaskActivity.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)
                            getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            pendingIntent);

                    etName.setText("");
                    etDesc.setText("");

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Do not leave any fields blank",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}