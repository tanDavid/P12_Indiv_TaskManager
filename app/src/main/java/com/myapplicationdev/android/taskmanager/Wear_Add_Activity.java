package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

public class Wear_Add_Activity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_add);


        CharSequence reply = null;
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            reply = remoteInput.getCharSequence("status");
        }

        if (reply != null) {
//            Toast.makeText(ReplyActivity.this, "You have indicated: " + reply,
//                    Toast.LENGTH_LONG).show();

            tv = findViewById(R.id.tvDisplay);
            tv.setText("Indicated: " + reply);


            DBHelper db = new DBHelper(Wear_Add_Activity.this);
            db.insertTask(reply.toString(), "New Task");
            Intent x = new Intent(Wear_Add_Activity.this, MainActivity.class);
            startActivity(x);


        }


    }
}
