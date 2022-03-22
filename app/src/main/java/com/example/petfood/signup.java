package com.example.petfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class signup extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputLayout fullname_var, email_var, phonenumber_var, password_var;
    Button regbtn,logbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fullname_var = findViewById(R.id.fullname_field);
        email_var = findViewById(R.id.email_field);
        phonenumber_var = findViewById(R.id.phone_number_field);
        password_var = findViewById(R.id.password_field);
        regbtn=findViewById(R.id.register_button);
        logbtn=findViewById(R.id.login_button);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        /*  if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(signup.this,login.class));
            finish();
        }
        */

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=email_var.getEditText().getText().toString().trim();
                String password=password_var.getEditText().getText().toString().trim();
                String fullName = fullname_var.getEditText().getText().toString();
                String phone = phonenumber_var.getEditText().getText().toString().trim() ;


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

                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())   {
                        Toast.makeText(signup.this, "Account Created.", Toast.LENGTH_SHORT).show();
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(fullName);
                        Map<String, Object> user = new HashMap<>();
                        user.put("userID",userID);
                        user.put("fName",fullName);
                        user.put("email",email);
                        user.put("phone",phone);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG,"onSuccess : user Profile is Created for "+ userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG , "onFailure : "+e.toString());
                            }
                        });
                        startActivity(new Intent(signup.this,login.class));
                        finish();
                    }else{
                        Toast.makeText(signup.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
        });



    }

    public void loginbuttonclick(View view) {
        Intent intent = new Intent(signup.this, login.class);
        startActivity(intent);
        finish();
    }

}