package com.dumb.dumb_deaf_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp_screen extends AppCompatActivity {

    String name,email,phone,address,password,image,confirm_password,gender,type;
    FirebaseAuth firebaseAuth;
    EditText otp;
    TextView resend;
    Button register;
    String verificationid;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;

    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        otp=findViewById(R.id.otp);
        register=findViewById(R.id.register);
        resend=findViewById(R.id.resend);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");
        address=getIntent().getStringExtra("address");
        password=getIntent().getStringExtra("password");
        image=getIntent().getStringExtra("image");
        confirm_password=getIntent().getStringExtra("con_password");
        gender=getIntent().getStringExtra("gender");
        type=getIntent().getStringExtra("type");

        firebaseAuth=FirebaseAuth.getInstance();

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));


        toolbar=findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(otp_screen.this, "yes", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(otp_screen.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationid=s;
                ResendingToken=forceResendingToken;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mcallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phone)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(otp_screen.this)
                                .setForceResendingToken(ResendingToken)// Activity (for callback binding)
                                .setCallbacks(mcallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().isEmpty()){
                    otp.setError("Enter otp");
                }
                else {

                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,otp.getText().toString());
                    firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Call<String> api1 = RetrofitClass.getInstanceScaler().signup(name, email, password, confirm_password, phone, image, address, gender, type);

                            api1.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                    if (response.isSuccessful()){
                                        if (response.body().trim().equals("Register Successfully")){
                                            Toast.makeText(otp_screen.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(otp_screen.this,Loginpage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(otp_screen.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(otp_screen.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                        });

                        }
                    });


                }



            }
        });


    }

}