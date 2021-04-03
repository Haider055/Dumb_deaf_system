package com.dumb.dumb_deaf_system.talk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dumb.dumb_deaf_system.Adapters.adapter_myrequests;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.fragments.no_personal_talks;
import com.dumb.dumb_deaf_system.fragments.no_requests;
import com.dumb.dumb_deaf_system.models.model_myrequests;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_requests extends AppCompatActivity {

    RecyclerView recyclerView;
    adapter_myrequests adapter;
    List<model_myrequests> list;

    private static final String MY_INFO = "MYINFO";

    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        toolbar=findViewById(R.id.tool);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.rec);

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));


        getdata();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
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
        Call<List<model_myrequests>> api= RetrofitClass.getInstanceGson().showmyrequests(sharedPreferences.getString("id",""));

        api.enqueue(new Callback<List<model_myrequests>>() {
            @Override
            public void onResponse(Call<List<model_myrequests>> call, Response<List<model_myrequests>> response) {
                if (response.isSuccessful()){

                    list=response.body();
                    if (list.isEmpty()){
                        no_requests no_requests = new no_requests();
                        FragmentManager fragmentManagerpro = getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionpro = fragmentManagerpro.beginTransaction();
                        fragmentTransactionpro.replace(R.id.linear,no_requests);
                        fragmentTransactionpro.commit();
                    }
                    else {

                        List<model_myrequests> list1 = new ArrayList<>();


                        for (int i = list.size() - 1; i >= 0; i--) {
                            list1.add(list.get(i));
                        }

                        recyclerView.hasFixedSize();
                        recyclerView.setLayoutManager(new LinearLayoutManager(my_requests.this));

                        adapter = new adapter_myrequests(list1, my_requests.this);

                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<model_myrequests>> call, Throwable t) {

            }
        });


    }
}