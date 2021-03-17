package com.dumb.dumb_deaf_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpscreen extends AppCompatActivity {

    FirebaseAuth mAuth;

    String mverifiedId;
    PhoneAuthProvider.ForceResendingToken mforcetoken;

    String nameString,emailString,phoneString,addressString,passwordString,confirm_passwordString;
    String genderString,typeString,imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        mAuth=FirebaseAuth.getInstance();

        nameString=getIntent().getStringExtra("name");
        emailString=getIntent().getStringExtra("email");
        addressString=getIntent().getStringExtra("address");
        imageString=getIntent().getStringExtra("image");
        phoneString=getIntent().getStringExtra("phone");
        genderString=getIntent().getStringExtra("gender");
        typeString=getIntent().getStringExtra("type");
        typeString=getIntent().getStringExtra("password");
        confirm_passwordString=getIntent().getStringExtra("con_password");


        verifyphone(phoneString);






    }

    private void verifyphone(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mverifiedId=s;
            mforcetoken=forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if (code !=null){
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otpscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifycode(String code) {

        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(mverifiedId,code);

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(otpscreen.this, "completed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}


