package com.dumb.dumb_deaf_system.talk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.dumb.dumb_deaf_system.Adapters.adapter_talks_By_Cat;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class talks extends AppCompatActivity implements adapter_talks_By_Cat.ontalkclick{

    SearchView searchView;
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

        searchView=findViewById(R.id.search);

        recyclerView=findViewById(R.id.rec);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                startActivity(new Intent(talks.this,MainActivity.class));
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                searchView.onActionViewExpanded();
            }
        });

        category=getIntent().getStringExtra("category");
        toolbar.setTitle(category);

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));




        getdata();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipe);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                pullToRefresh.setRefreshing(false);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter!=null){
                    adapter.getFilter().filter(newText);
                }
                return false ;
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

        Intent intent=new Intent(talks.this, details.class);
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



