package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.dumb.dumb_deaf_system.Adapters.AdapterCategories;
import com.dumb.dumb_deaf_system.Adapters.adapter_talks_By_Cat;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import me.jagar.chatvoiceplayerlibrary.VoicePlayerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class talks extends AppCompatActivity implements adapter_talks_By_Cat.ontalkclick{

    ImageView imageView;
    Toolbar toolbar;
    String category="";
    RecyclerView recyclerView;
    List<model_Talksby_Cat> list;
    adapter_talks_By_Cat adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        voicePlayerView.pause();
//        voicePlayerView.kill();
    }

    JcPlayerView voicePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);



        toolbar=findViewById(R.id.tool);

        recyclerView=findViewById(R.id.rec);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                startActivity(new Intent(talks.this,MainActivity.class));
            }
        });

        category=getIntent().getStringExtra("category");
        toolbar.setTitle(category);


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


        if (category!=null) {
            Call<List<model_Talksby_Cat>> api = RetrofitClass.getInstanceGson().showtalkbycategory(category);

            api.enqueue(new Callback<List<model_Talksby_Cat>>() {
                @Override
                public void onResponse(Call<List<model_Talksby_Cat>> call, Response<List<model_Talksby_Cat>> response) {

                    if (response.isSuccessful()) {
                        list = response.body();
                        recyclerView.hasFixedSize();
                        recyclerView.setLayoutManager(new LinearLayoutManager(talks.this));
                        adapter = new adapter_talks_By_Cat(list, talks.this);
                        recyclerView.setAdapter(adapter);
                        adapter.onclick(talks.this);

                    }
                }


                @Override
                public void onFailure(Call<List<model_Talksby_Cat>> call, Throwable t) {

                }
            });
        }

        }

    @Override
    public void ontalksclick(String id, String file_type, String description, String category, String date, String gig, String video,String audio,String urdu,String title) {

        Intent intent=new Intent(talks.this,details.class);
        intent.putExtra("id",id);
        intent.putExtra("type",file_type);
        intent.putExtra("title",title);
        intent.putExtra("details",description);
        intent.putExtra("category",category);
        intent.putExtra("date",date);
        intent.putExtra("gif",gig);
        intent.putExtra("video",video);
        intent.putExtra("audio",audio);
        intent.putExtra("urdu",urdu);
        startActivity(intent);

    }
}



