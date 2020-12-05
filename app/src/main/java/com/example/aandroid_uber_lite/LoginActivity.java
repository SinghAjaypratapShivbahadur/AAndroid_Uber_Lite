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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
        EditText mEmail,mPassword;
        Button mLogBtn;
        TextView mcreateBtn;
        TextView forgotText;
        ProgressBar progressBar;
        FirebaseAuth fAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.loginactivity);

            mEmail= findViewById(R.id.Email_id);
            mPassword= findViewById(R.id.password);
            progressBar= findViewById(R.id.progressBar);
            fAuth= FirebaseAuth.getInstance();
            mcreateBtn= findViewById(R.id.Regbutton);
            forgotText= findViewById(R.id.ForgotPass1);

            mLogBtn.setOnClickListener(v -> {
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

                //Authenticate The User

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivityTemp.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error."+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                });



            });

    mcreateBtn.setOnClickListener(v ->  startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

    forgotText.setOnClickListener(v -> {

        EditText resetMail= new EditText(v.getContext());
        AlertDialog.Builder passwordResetDialog= new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter Your Email_id.");
        passwordResetDialog.setView(resetMail);

       passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
           //Extract email_id and set reset link.
           String mail= resetMail.getText().toString();
           fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email_Id", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error Occurred!!!"+e.getMessage(), Toast.LENGTH_SHORT).show());
       });

       passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
           //Redirected back to the Login page.
       });

       passwordResetDialog.create().show();

    });


        }
    }

