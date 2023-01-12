package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editJournal extends AppCompatActivity {
    Intent data;
    EditText edittitle, editdetail;
    FloatingActionButton editnote;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journal);

        edittitle=findViewById(R.id.editjourn);
        editdetail=findViewById(R.id.editDetail);
        editnote=findViewById(R.id.saveEdit);
        data=getIntent();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar=findViewById(R.id.edittool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        editnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newtitle=edittitle.getText().toString();
                String newdetail=editdetail.getText().toString();
                //Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

                if (newtitle.isEmpty()||newdetail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Its empty",Toast.LENGTH_SHORT).show();
                }else {
                    DocumentReference documentReference=firebaseFirestore.collection("Journals").document(firebaseUser.getUid()).collection("myJournals").document(data.getStringExtra("journID"));
                    Map<String,Object> journal = new HashMap<>();
                    journal.put("title", newtitle);
                    journal.put("detail",newdetail);
                    documentReference.set(journal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Update complete",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(editJournal.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Update failed successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        String title=data.getStringExtra("title");
        String detail=data.getStringExtra("detail");
        editdetail.setText(detail);
        edittitle.setText(title);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }}
