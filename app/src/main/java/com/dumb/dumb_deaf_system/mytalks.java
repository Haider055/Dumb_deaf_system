package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dumb.dumb_deaf_system.Adapters.adaptermytalks;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.models.model_mytalks;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class mytalks extends AppCompatActivity implements adaptermytalks.onclick{

    List<model_mytalks> list;
    String id="";
    RecyclerView recyclerView;
    adaptermytalks adapter_mytalks;


    private static final String MY_INFO = "MYINFO";

    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytalks);



        recyclerView=findViewById(R.id.rec);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerView.hasFixedSize();

        getdata();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipe);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                pullToRefresh.setRefreshing(false);
            }
        });


    }

    private void getdata() {
        SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
        Call<List<model_mytalks>> apicall= RetrofitClass.getInstanceGson().showmydata(sharedPreferences.getString("id",""));

        apicall.enqueue(new Callback<List<model_mytalks>>() {
            @Override
            public void onResponse(Call<List<model_mytalks>> call, Response<List<model_mytalks>> response) {

                if (response.isSuccessful()){

                    list=response.body();

                    recyclerView.setLayoutManager(new LinearLayoutManager(mytalks.this));
                    adapter_mytalks=new adaptermytalks(mytalks.this,list);
                    recyclerView.setAdapter(adapter_mytalks);
                    adapter_mytalks.onclick(mytalks.this);

                }
            }

            @Override
            public void onFailure(Call<List<model_mytalks>> call, Throwable t) {

            }
        });




    }

    @Override
    public void onclick(String id, String file_type, String description, String user_id, String category, String date, String gif, String video, String audio, String urdu_description, String title) {
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(mytalks.this,details.class);
        intent.putExtra("id",id);
        intent.putExtra("type",file_type);
        intent.putExtra("gif",gif);
        intent.putExtra("video",video);
        intent.putExtra("date",date);
        intent.putExtra("title",title);
        intent.putExtra("category",category);
        intent.putExtra("audio",audio);
        intent.putExtra("details",description);
        intent.putExtra("urdu",urdu_description);
        startActivity(intent);

    }
}