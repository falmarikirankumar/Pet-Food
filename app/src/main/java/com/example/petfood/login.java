package com.example.petfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        Button signupbutton, loginbtn;
        TextInputLayout email_var, password_var;
        TextView openForget;

        signupbutton = findViewById(R.id.signupAt);
        loginbtn = findViewById(R.id.loginbutton);
        email_var = findViewById(R.id.logemail);
        password_var = findViewById(R.id.logpassword);
        openForget = findViewById(R.id.openforgetpassword);
        fAuth = FirebaseAuth.getInstance();

        String email_ = email_var.getEditText().getText().toString();
        String password_ = email_var.getEditText().getText().toString();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_var.getEditText().getText().toString().trim();
                String password=password_var.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email_var.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    password_var.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    password_var.setError("Password Must be >= 6 Characters");
                    return;
                }

                //authenticate

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())   {
                            Toast.makeText(login.this, "Log In Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(login.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });

        openForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(login.this,ForgetPassword.class);
               // startActivity(intent);
                startActivity(new Intent(login.this,ForgetPassword.class));

            }
        });


        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,signup.class);
                startActivity(intent);

            }
        });

    }

}
