package com.example.petfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    Button forgetbtn ;
    TextInputLayout email;
    String email_;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        fAuth = FirebaseAuth.getInstance();
        forgetbtn = findViewById(R.id.forgetbtn);

        email = findViewById(R.id.forgetemail);


        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


    }

    private void validate() {
        email_ = email.getEditText().getText().toString();
        if(email_.isEmpty()){
            email.setError("Required");
        }else{
            forgetpassword();
        }
    }

    private void forgetpassword() {
        fAuth.sendPasswordResetEmail(email_).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this,login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ForgetPassword.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}