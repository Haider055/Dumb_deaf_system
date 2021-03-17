package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dumb.dumb_deaf_system.Network.RetrofitClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class requesttalk extends AppCompatActivity {

    EditText title,detail;

    private static final String MY_INFO = "MYINFO";
    RadioGroup radio;
    Button submit;
    String type="";
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesttalk);

        title=findViewById(R.id.title);
        detail=findViewById(R.id.detail);
        radio=findViewById(R.id.radio);
        submit=findViewById(R.id.submit);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.my){
                    type="myself";
                }
                else {
                    type="overall";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().isEmpty()){
                    title.setError("Enter title");
                }
                if (detail.getText().toString().isEmpty()){
                    detail.setError("Enter title");
                }
                if (type.equals("")){
                    Toast.makeText(requesttalk.this, "Select request type", Toast.LENGTH_SHORT).show();
                }
                if (title.getText().toString().isEmpty()||detail.getText().toString().isEmpty()||type.equals("")){

                }
                else {
                    String titles,details;
                    titles=title.getText().toString();
                    details=detail.getText().toString();

                    SharedPreferences shared=getSharedPreferences(MY_INFO,MODE_PRIVATE);
                    String id=shared.getString("id","");

                    Call<String> apirequest= RetrofitClass.getInstanceScaler().request(id,type,titles,details);

                    apirequest.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(requesttalk.this," Requested", Toast.LENGTH_SHORT).show();
                                title.setText("");
                                detail.setText("");
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });





    }

}