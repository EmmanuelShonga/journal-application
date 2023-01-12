package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class newjournal extends AppCompatActivity {

    EditText mjournTitle,mjournDetail;
    ImageButton mimg1, mimg2;
    FloatingActionButton msave;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        mjournTitle = findViewById(R.id.journtitle);
        mjournDetail = findViewById(R.id.journDetail);

        msave = findViewById(R.id.saveJournal);

        Toolbar toolbar = findViewById(R.id.toolb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mjournTitle.getText().toString();
                String detail= mjournDetail.getText().toString();

                if (title.isEmpty() || detail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Fill in all feilds",Toast.LENGTH_SHORT).show();
                }else{

                    DocumentReference documentReference = firebaseFirestore.collection("Journals").document(firebaseUser.getUid()).collection("myJournals").document();
                    Map<String, Object> journal = new HashMap<>();
                    journal.put("title",title);
                    journal.put("detail",detail);

                    documentReference.set(journal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Journal Entry Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(newjournal.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Journal Entry Successfully Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}