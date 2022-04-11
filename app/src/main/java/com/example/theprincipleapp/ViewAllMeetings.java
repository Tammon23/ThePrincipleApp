package com.example.theprincipleapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.theprincipleapp.db.UserDatabase;
import com.example.theprincipleapp.db.Weekday;
import com.example.theprincipleapp.helpers.ViewAllMeetingAdapter;

public class ViewAllMeetings extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewAllMeetingAdapter adapter;
    int cid;
    Weekday today;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_meetings);


        cid = getIntent().getIntExtra("cid", -1);
        recyclerView = findViewById(R.id.recyclerViewAllMeetings);
        adapter = new ViewAllMeetingAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AsyncTask.execute(() -> {
            adapter.meetings = cid < 0
                    ?   UserDatabase.UDB.meetingDao().getAll()
                    :   UserDatabase.UDB.meetingDao().getFrom(cid);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_viewallclasses, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(this, NewMeeting.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
