package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.dumb.dumb_deaf_system.Adapters.adapter_myfav;
import com.dumb.dumb_deaf_system.Network.dbhandler;
import com.dumb.dumb_deaf_system.models.model_mytalks;

import java.util.ArrayList;
import java.util.List;

public class myfavourites extends AppCompatActivity implements adapter_myfav.del{

    List<model_mytalks> list;
    adapter_myfav adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfavourites);

    toolbar=findViewById(R.id.tool);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });


    recyclerView=findViewById(R.id.rec);

    list=new ArrayList<>();
    dbhandler dbhandler=new dbhandler(this);
    list=dbhandler.retrievecart();
    dbhandler.close();

    recyclerView.hasFixedSize();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter=new adapter_myfav(list,this);
    recyclerView.setAdapter(adapter);
    adapter.setMdel(this);

    }

    @Override
    public void delete(String id) {

        dbhandler dbhandler=new dbhandler(this);
        dbhandler.deletetalks(id);
        dbhandler.close();


        dbhandler dbhandler1=new dbhandler(this);
        list=dbhandler1.retrievecart();
        dbhandler1.close();

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new adapter_myfav(list,this);
        recyclerView.setAdapter(adapter);
        adapter.setMdel(this);




        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));




    }
}