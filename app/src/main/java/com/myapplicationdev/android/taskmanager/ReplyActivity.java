package com.myapplicationdev.android.taskmanager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import org.w3c.dom.Text;

public class ReplyActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

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

            if (reply.toString().equalsIgnoreCase("completed")) {
                Log.d("TAG", "onCreate: " + id);
                DBHelper db = new DBHelper(ReplyActivity.this);
                db.deleteTask(Integer.parseInt(id));
                Intent x = new Intent(ReplyActivity.this, MainActivity.class);
                startActivity(x);
                Toast.makeText(ReplyActivity.this, "Deletion Successful",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ReplyActivity.this, "Nothing",
                        Toast.LENGTH_LONG).show();
                Intent x = new Intent(ReplyActivity.this, MainActivity.class);
                startActivity(x);
            }

        }

    }
}