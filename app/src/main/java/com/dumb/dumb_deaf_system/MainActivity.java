package com.dumb.dumb_deaf_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dumb.dumb_deaf_system.Adapters.AdapterCategories;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.authentications.Loginpage;
import com.dumb.dumb_deaf_system.favorites.myfavourites;
import com.dumb.dumb_deaf_system.models.modelcategories;
import com.dumb.dumb_deaf_system.profile.profile;
import com.dumb.dumb_deaf_system.talk.my_requests;
import com.dumb.dumb_deaf_system.talk.mytalks;
import com.dumb.dumb_deaf_system.talk.requesttalk;
import com.dumb.dumb_deaf_system.talk.talks;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterCategories.oncategoryclick, NavigationView.OnNavigationItemSelectedListener {

    private static final String MY_INFO = "MYINFO";
    Toolbar toolbar;
    SearchView searchView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView reccategory;
    List<modelcategories> list;
    String notification_count="";

    String CHannel_ID = "notify";
    AdapterCategories holdercategories;
    TextView nameinheader;
    CircleImageView imageInHeader;
    CardView cardView;

    AlertDialog.Builder builderlogin;
    AlertDialog alertlogin;

    SharedPreferences shared;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bindings
        toolbar=findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation);
        SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
        reccategory=findViewById(R.id.categoryrec);
        list=new ArrayList<>();

        searchView=findViewById(R.id.searchView);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                searchView.onActionViewExpanded();
            }
        });

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));

        //set header of side bar
        View header=navigationView.getHeaderView(0);

        imageInHeader=header.findViewById(R.id.profile);
        nameinheader=header.findViewById(R.id.name);

        if (sharedPreferences.getString("loginstatus","false").equals("true")){
            nameinheader.setText(sharedPreferences.getString("name","").trim());
        }

        try {
            if (sharedPreferences.getString("image","").equals("")){
                imageInHeader.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.noimageprofile));
            }
            else {
            Glide.with(this).load(sharedPreferences.getString("image","")).into(imageInHeader);
            }
//            Picasso.get().load(sharedPreferences.getString("image","")).networkPolicy(NetworkPolicy.NO_CACHE).into(imageInHeader);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //sethamberger
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.parseColor("#ffffff"));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //click on item
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);
        //api call

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

        shared=getSharedPreferences(MY_INFO, MODE_PRIVATE);

        if (shared.getString("loginstatus","false").equals("true")) {

            Call<String> stringCall = RetrofitClass.getInstanceScaler().notification(shared.getString("id", ""));
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        notification_count = response.body().trim();
                        if (notification_count.equals("1")) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel notificationChannel = new NotificationChannel(CHannel_ID, "personal", NotificationManager.IMPORTANCE_HIGH);
                                notificationChannel.setDescription("heloo");
                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.createNotificationChannel(notificationChannel);
                            }

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, CHannel_ID)
                                    .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                                    .setAutoCancel(true)
                                    .setContentTitle("Dumb deaf")
                                    .setPriority(Notification.PRIORITY_MAX)
                                    .setContentText("Your request has been approved!");

//                        mBuilder.setContentIntent(pendingIntent);

                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(002, mBuilder.build());

                            Call<String> apiupdate_noti = RetrofitClass.getInstanceScaler().updateNotification(shared.getString("id", ""));
                            apiupdate_noti.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
        Call<List<modelcategories>> call=RetrofitClass.getInstanceGson().categories();
        call.enqueue(new Callback<List<modelcategories>>() {
            @Override
            public void onResponse(Call<List<modelcategories>> call, Response<List<modelcategories>> response) {
                list=response.body();
                holdercategories=new AdapterCategories(list,MainActivity.this);
                reccategory.hasFixedSize();
                holdercategories.setOnCatClickListener(MainActivity.this);
                reccategory.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                reccategory.setAdapter(holdercategories);

            }

            @Override
            public void onFailure(Call<List<modelcategories>> call, Throwable t) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (holdercategories!=null){
                    holdercategories.getFilter().filter(newText);
                }
                return false;
            }
        });





    }

    @Override
    public void oncat(String id, String name) {

        if (shared.getString("loginstatus","false").equals("true")){

            Intent intent=new Intent(MainActivity.this, talks.class);
            intent.putExtra("id",id);
            intent.putExtra("category",name);
            startActivity(intent);
        }
        else {


            builderlogin = new AlertDialog.Builder(this);
            View viewlogin = LayoutInflater.from(this).inflate(R.layout.popupforlogin, null);
            builderlogin.setView(viewlogin);
            alertlogin = builderlogin.create();
            alertlogin.show();

            Button login;
            login=viewlogin.findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Loginpage.class));
                }
            });

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                if (shared.getString("loginstatus","false").equals("true")){
                startActivity(new Intent(MainActivity.this, profile.class));
                drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    Toast.makeText(this, "You are not login", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:
//                startActivity(new Intent(MainActivity.this,Loginpage.class));
                SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();
                editors.putString("loginstatus","false");
                editors.putString("image","");
                imageInHeader.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.noimageprofile));
                nameinheader.setText("");
                editors.apply();
                drawerLayout.closeDrawer(Gravity.LEFT);
//                  startActivity(new Intent(MainActivity.this,MainActivity.class));
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_talk:
                if (shared.getString("loginstatus","false").equals("true")){
                    startActivity(new Intent(MainActivity.this, requesttalk.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    Toast.makeText(this, "You are not login", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.my:
                if (shared.getString("loginstatus","false").equals("true")){
                    startActivity(new Intent(MainActivity.this, mytalks.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    Toast.makeText(this, "You are not login", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_home:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.myrequest:
                if (shared.getString("loginstatus","false").equals("true")){
                    startActivity(new Intent(MainActivity.this, my_requests.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    Toast.makeText(this, "You are not login", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login:
                if (shared.getString("loginstatus","false").equals("true")){
                    Toast.makeText(this, "You are already login", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                else {
                    startActivity(new Intent(MainActivity.this,Loginpage.class));
                }
                break;
            case R.id.fav:
                    startActivity(new Intent(MainActivity.this, myfavourites.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
        if (sharedPreferences.getString("loginstatus","false").equals("true")){
        try {
            Glide.with(this).load(sharedPreferences.getString("image","")).into(imageInHeader);
//            Picasso.get().load(sharedPreferences.getString("image","")).networkPolicy(NetworkPolicy.NO_CACHE).into(imageInHeader);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
    }
}