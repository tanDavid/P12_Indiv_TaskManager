package com.myapplicationdev.android.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    Button btnAddTask;
    ArrayList<Task> tasklist;
    ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        btnAddTask = findViewById(R.id.btnAdd);
        tasklist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasklist);
        listview.setAdapter(adapter);

        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        tasklist.clear();

        DBHelper dbHelper = new DBHelper(MainActivity.this);
        tasklist.addAll(dbHelper.getTasks());
        dbHelper.close();

        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent editIntent = new Intent(MainActivity.this, EditTaskActivity.class);
//                Log.d("TAG", "onItemClick: " + tasklist.get(i).getName());

                editIntent.putExtra("idToEdit", tasklist.get(i).getId());
                editIntent.putExtra("titleToEdit", tasklist.get(i).getName());
                editIntent.putExtra("descToEdit", tasklist.get(i).getDesc());

                Log.d("ssdfsdf", "onItemClick: " + tasklist.get(i).getId());

                startActivity(editIntent);

            }
        });

    }
}