package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class journaldetails extends AppCompatActivity {
    private TextView Title,Detail;
    FloatingActionButton editnote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);

        Title= findViewById(R.id.title);
        Detail= findViewById(R.id.Detail);
        editnote= findViewById(R.id.gotoedit);
        Toolbar toolbar = findViewById(R.id.tooldetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        editnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),editJournal.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("detail",data.getStringExtra("detail"));
                intent.putExtra("journID",data.getStringExtra("journID"));
                v.getContext().startActivity(intent);
            }
        });

        Detail.setText(data.getStringExtra("detail"));
        Title.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}