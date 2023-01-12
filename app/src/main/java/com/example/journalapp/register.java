package com.example.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText Firstname, Surname, Email, Password;
    Button Signup;
    TextView Login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firstname = findViewById(R.id.firstname);
        Surname   = findViewById(R.id.lastname);
        Email     = findViewById(R.id.email);
        Password  = findViewById(R.id.password);
        Signup    = findViewById(R.id.signup);
        Login     = findViewById(R.id.loginpg);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();
        }

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = Firstname.getText().toString().trim();
                String lastname = Surname.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(firstname)) {
                    Firstname.setError("Please enter your first name");
                    return;
                }if (TextUtils.isEmpty(lastname)) {
                    Surname.setError("Please enter your surname");
                    return;
                }if (TextUtils.isEmpty(email)) {
                    Email.setError("Please enter your email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Password.setError("Please enter password");
                    return;
                }
                if(password.length() < 6){
                    Password.setError("More than 6 letters requried");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(register.this, "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else{
                            Toast.makeText(register.this, "Error detected !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }

}





