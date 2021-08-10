package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent editIntent = getIntent();
        int id = editIntent.getIntExtra("idToEdit", 0);
        String name = editIntent.getStringExtra("titleToEdit");
        String desc = editIntent.getStringExtra("descToEdit");

        Log.d("TAG", "onCreate: " + id);

        etName.setText(name);
        etDesc.setText(desc);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etName.getText().toString().isEmpty() && !etDesc.getText().toString().isEmpty()) {
                    //inserting task


                    DBHelper db = new DBHelper(EditTaskActivity.this);
                    Task t1 = new Task(id, etName.getText().toString(), etDesc.getText().toString());
                    db.updateTask(t1);
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
//                    Log.i("info", db.getTasks().get(db.getTasks().size()-1).getId() + "");

                    db.close();

                    etName.setText("");
                    etDesc.setText("");

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Do not leave any fields blank",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(EditTaskActivity.this);
                 db.deleteTask(id);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

                db.close();

                etName.setText("");
                etDesc.setText("");

                finish();
            }
        });


    }
}
