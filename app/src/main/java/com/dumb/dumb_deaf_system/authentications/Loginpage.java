package com.dumb.dumb_deaf_system.authentications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dumb.dumb_deaf_system.MainActivity;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.modelGetDataByLogin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginpage extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private static final String MY_INFO = "MYINFO";
    TextView sigup;
    List<modelGetDataByLogin> logins;
        Button login;
        EditText email,password;
        String emails,passwords;
        String nameToStore,emailToStore,passwordToStore,genderToStore,addressToStore,typeToStore,imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);


        logins=new ArrayList<>();
        sigup=findViewById(R.id.signup);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loginpage.this, signup.class));
            }
        });



        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Loginpage.this,MainActivity.class));
                emails=email.getText().toString();
                passwords=password.getText().toString();

                if (email.getText().toString().isEmpty()){
                    email.setError("Enter email");
                }
                if (password.getText().toString().isEmpty()){
                    password.setError("Enter Password");
                }
                if (email.getText().toString().isEmpty()||password.getText().toString().isEmpty()){

                }
                else {

                    Call<String> api1=RetrofitClass.getInstanceScaler().login(emails, passwords);
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    api1.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, @NotNull Response<String> response) {
                            if (response.isSuccessful()){

                                if(response.body().trim().equals("Please enter Email"))
                                {    Toast.makeText(Loginpage.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().trim().equals("Please enter your password."))
                                {
                                    Toast.makeText(Loginpage.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().trim().equals("No account found with that email."))
                                {
                                    Toast.makeText(Loginpage.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().trim().equals("Oops! Something went wrong. Please try again later."))
                                {
                                    Toast.makeText(Loginpage.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().trim().equals("The password you entered was not valid."))
                                {
                                    Toast.makeText(Loginpage.this, response.body().trim(), Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    editors.putString("id",response.body().trim());

                                    Call<List<modelGetDataByLogin>> api2=RetrofitClass.getInstanceGson().getprofiledata(response.body());
                                    api2.enqueue(new Callback<List<modelGetDataByLogin>>() {
                                        @Override
                                        public void onResponse(Call<List<modelGetDataByLogin>> call, Response<List<modelGetDataByLogin>> response) {
                                            if (response.isSuccessful()){

                                                logins=response.body();
                                                editors.putString("password",password.getText().toString());
                                                editors.putString("name",logins.get(0).getName());
                                                editors.putString("email",logins.get(0).getEmail());
                                                editors.putString("address",logins.get(0).getAddress());
                                                editors.putString("phone",logins.get(0).getPhone());
                                                editors.putString("image",logins.get(0).getImage());
                                                editors.putString("gender",logins.get(0).getGender());
                                                editors.putString("type",logins.get(0).getType());
                                                editors.putString("loginstatus","true");
                                                editors.apply();

                                                Toast.makeText(Loginpage.this, "Logged in", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Loginpage.this, MainActivity.class));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<modelGetDataByLogin>> call, Throwable t) {
                                            Toast.makeText(Loginpage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Loginpage.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}