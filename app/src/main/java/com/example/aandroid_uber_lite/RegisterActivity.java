package com.example.aandroid_uber_lite;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText mFN,mLN,mEmail,mPhone,mPassword;
    Button mRegBtn;
    TextView mLogBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);

        mFN= findViewById(R.id.FirstNAme);
        mLN= findViewById(R.id.LasName);
        mEmail= findViewById(R.id.Email_id);
        mPhone= findViewById(R.id.Number);
        mRegBtn= findViewById(R.id.Register);
        mLogBtn= findViewById(R.id.Regbutton);

        fAuth= FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivityTemp.class));
            finish();
        }


        mRegBtn.setOnClickListener(v -> {
            String email= mEmail.getText().toString().trim();
            String password= mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                mEmail.setError("Email Is Required!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password Is Required!");
                return;
            }

            if (password.length() < 7) {
                mPassword.setError("Password Must Be Greater Or Equal To Seven Characters!");
                return;
            }
                progressBar.setVisibility(View.VISIBLE);

            //Register The User In Firebase

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "User Registered.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivityTemp.class));
            }
            else {
                Toast.makeText(RegisterActivity.this, "Error."+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            });
        });
    mLogBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

    }
}