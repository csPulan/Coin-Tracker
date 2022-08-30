package com.example.cryptoscholartracker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    TextInputEditText mName,mEmail,mPassword,mPhone;
    Button mRegister;
    TextView mLogbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    boolean passwordVisible;
    String userID;
    TextInputLayout mtextNameLayout;
    TextInputLayout mtextEmailLayout;
    TextInputLayout mtextPhoneLayout;
    TextInputLayout mtextPasswordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        mName = findViewById(R.id.textNameEditText);
        mEmail = findViewById(R.id.textEmailEdittext);
        mPassword = findViewById(R.id.textPasswordEdittext);
        mPhone = findViewById(R.id.textPhoneEdittext);
        mRegister = findViewById(R.id.buttonLogIn);
        mLogbtn = findViewById(R.id.textBtnregister);
        mtextNameLayout = findViewById(R.id.textNameLayout);
        mtextEmailLayout = findViewById(R.id.textEmailLayout);
        mtextPhoneLayout = findViewById(R.id.textPhoneLayout);
        mtextPasswordLayout = findViewById(R.id.textPasswordLayout);




        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean b)
            {
                if(b)
                {
                    mtextNameLayout.setHintTextAppearance(R.style.active);
                }
                else
                    mtextNameLayout.setHintTextAppearance(R.style.inactive);
            }
        });

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mName.getText().toString().trim().isEmpty())
                {
                    mtextNameLayout.setError("Please Fill in Empty Fields");
                }
                else{
                    mtextNameLayout.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean b)
            {
                if(b)
                {
                    mtextEmailLayout.setHintTextAppearance(R.style.active);
                }
                else
                    mtextEmailLayout.setHintTextAppearance(R.style.inactive);
            }
        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mEmail.getText().toString().trim().isEmpty())
                {
                    mtextEmailLayout.setError("Please Fill in Empty Fields");
                }
                else{
                    mtextEmailLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean b)
            {
                if(b)
                {
                    mtextPhoneLayout.setHintTextAppearance(R.style.active);
                }
                else
                    mtextPhoneLayout.setHintTextAppearance(R.style.inactive);
            }
        });

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mPhone.getText().toString().trim().isEmpty())
                {
                    mtextPhoneLayout.setError("Please Fill in Empty Fields");
                }
                else{
                    mtextPhoneLayout.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean b)
            {
                if(b)
                {
                    mtextPasswordLayout.setHintTextAppearance(R.style.active);
                }
                else
                    mtextPasswordLayout.setHintTextAppearance(R.style.inactive);
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(mPassword.getText().toString().trim().isEmpty())
                {
                    mtextPasswordLayout.setError("Please Fill in Empty Fields");
                }
                else{
                    mtextPasswordLayout.setError(null);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(mPassword.getText().toString().trim().length() < 6 )
                {
                    mtextPasswordLayout.setError("Please set 6 or more characters");
                } else{
                    mtextPasswordLayout.setError(null);
                }


            }
        });



        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mName.getText().toString().trim();
                String phoneNumber=mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Invalid Password! 6 or more characters required..");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser fUser = fAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(Register.this, "Email Verification Has Been Sent!", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not Sent!" +e.getMessage());
                                }
                            });

                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("number",phoneNumber);

                            documentReference.set(user).addOnSuccessListener((OnSuccessListener) (aVoid) ->  {
                                Log.d(TAG, "onSuccess: User Profile is created for "+ userID);
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d(TAG, "onFailure:  "+ e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));


                        } else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });
            }

        });

        mLogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}