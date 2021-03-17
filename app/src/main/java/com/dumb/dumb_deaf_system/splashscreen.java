package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class splashscreen extends AppCompatActivity {


    CardView background;
    ImageView next;
    int a=0;
    String status;
    private final Handler mWaitHandler = new Handler();
    private static final String MY_INFO = "MYINFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);



        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        findViewById(R.id.go).setVisibility(View.INVISIBLE);
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        a = 1;
//                        startActivity(new Intent(splashscreen.this, MainActivity.class));
//                        finish();
//                    }
//                });




        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //The following code will execute after the 5 seconds.
                try {
//                    if (a == 0) {
                        //Go to next page i.e, start the next activity.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
//                    }
                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);
    }
    @Override
    protected void onStart() {
        super.onStart();

//        SharedPreferences prefs = getSharedPreferences(MY_INFO, MODE_PRIVATE);
//        status = prefs.getString("loginstatus", "true");
//        if (status.equals("true")) {
//            a=1;
//            findViewById(R.id.go).setVisibility(View.INVISIBLE);
//            mWaitHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //The following code will execute after the 5 seconds.
//                    try {
//                        startActivity(new Intent(splashscreen.this, MainActivity.class));
//                        //Let's Finish Splash Activity since we don't want to show this when user press back button.
//                        finish();
//                    } catch (Exception ignored) {
//                        ignored.printStackTrace();
//                    }
//                }
//            }, 3000);
//        }

    }
}