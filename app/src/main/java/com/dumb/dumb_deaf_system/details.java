package com.dumb.dumb_deaf_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dumb.dumb_deaf_system.Adapters.adapter_talks_By_Cat;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.potyvideo.library.AndExoPlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class details extends AppCompatActivity implements adapter_talks_By_Cat.ontalkclick{

    String id,category,details,date,gif,video,type,audio,urdu,title;
    AndExoPlayerView andExoPlayerView;
    Toolbar toolbar;
    Button share;

    List<model_Talksby_Cat> list;
    adapter_talks_By_Cat adapter;

    RecyclerView rec;
    CardView cardView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        voicePlayerView.pause();
//        voicePlayerView.kill();

        if (type.equals("video"))
        andExoPlayerView.pausePlayer();
    }

    ImageView imageView;
    JcPlayerView voicePlayerView;

    TextView head,detail,urdu_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar=findViewById(R.id.tool);
        cardView=findViewById(R.id.cardofimage);

        rec=findViewById(R.id.rec);

        id=getIntent().getStringExtra("id");
        type=getIntent().getStringExtra("type");
        gif=getIntent().getStringExtra("gif");
        video=getIntent().getStringExtra("video");
        date=getIntent().getStringExtra("date");
        category=getIntent().getStringExtra("category");
        audio=getIntent().getStringExtra("audio");
        details=getIntent().getStringExtra("details");
        urdu=getIntent().getStringExtra("urdu");
        title=getIntent().getStringExtra("title");


//        if (type.equals("gif")){
//            andExoPlayerView.setVisibility(View.GONE);
//        }
//        else {
//            imageView.setVisibility(View.GONE);
//        }

        head=findViewById(R.id.head);
        detail=findViewById(R.id.detail);
        urdu_desc=findViewById(R.id.urdu);
        share=findViewById(R.id.share);

        imageView = findViewById(R.id.image);
        andExoPlayerView = findViewById(R.id.videoplayer);


        toolbar.setTitle(title);
        head.setText(title);
        detail.setText(details);
        urdu_desc.setText(urdu);

//        Toast.makeText(this, urdu, Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitle(category);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, category+"\n"+title+"\n"+details+"\n"+urdu);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Send to friend"));
            }
        });
            //gif
        if (type.equals("gif")){
            Glide.with(this).load(gif).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
            andExoPlayerView.setVisibility(View.GONE);
        }

        //voice
        voicePlayerView = findViewById(R.id.audioplayer);
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL("url audio",audio));
        jcAudios.add(JcAudio.createFromAssets("Asset audio", "audio.mp3"));

        voicePlayerView.initAnonPlaylist(jcAudios);


        voicePlayerView.setJcPlayerManagerListener(new JcPlayerManagerListener() {
            @Override
            public void onPreparedAudio(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onCompletedAudio() {
                voicePlayerView.pause();

                jcAudios.add(JcAudio.createFromURL("url audio",audio));
                jcAudios.add(JcAudio.createFromAssets("Asset audio", "audio.mp3"));
                voicePlayerView.initAnonPlaylist(jcAudios);
            }

            @Override
            public void onPaused(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onContinueAudio(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onPlaying(@NotNull JcStatus jcStatus) {

            }

            @Override
            public void onTimeChanged(@NotNull JcStatus jcStatus) {

            }
            @Override
            public void onStopped(@NotNull JcStatus jcStatus) {
                voicePlayerView.pause();
                jcAudios.add(JcAudio.createFromURL("url audio",jcStatus.getJcAudio().getPath()));
                jcAudios.add(JcAudio.createFromAssets("Asset audio", "audio.mp3"));
                voicePlayerView.initAnonPlaylist(jcAudios);
            }

            @Override
            public void onJcpError(@NotNull Throwable throwable) {

            }
        });


            //video
        if (type.equals("video")){
            andExoPlayerView.setSource(video);
            imageView.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }

        showrelated(category);


    }

    private void showrelated(String cat) {

        rec.hasFixedSize();
        rec.setLayoutManager(new LinearLayoutManager(this));

        Call<List<model_Talksby_Cat>> listCall= RetrofitClass.getInstanceGson().showtalkbycategory(cat);
        listCall.enqueue(new Callback<List<model_Talksby_Cat>>() {
            @Override
            public void onResponse(Call<List<model_Talksby_Cat>> call, Response<List<model_Talksby_Cat>> response) {
                if (response.isSuccessful()){

                    list=response.body();
                    adapter=new adapter_talks_By_Cat(list,details.this);
                    rec.setAdapter(adapter);
                    adapter.onclick(details.this);



                }
            }

            @Override
            public void onFailure(Call<List<model_Talksby_Cat>> call, Throwable t) {

            }
        });






    }


    @Override
    public void ontalksclick(String id, String file_type, String description, String category, String date, String gif, String video, String audio, String urdu_description, String title) {

        Intent intent=new Intent(details.this,details.class);
        intent.putExtra("id",id);
        intent.putExtra("type",file_type);
        intent.putExtra("title",title);
        intent.putExtra("details",description);
        intent.putExtra("category",category);
        intent.putExtra("date",date);
        intent.putExtra("gif",gif);
        intent.putExtra("video",video);
        intent.putExtra("audio",audio);
        intent.putExtra("urdu",urdu);
        startActivity(intent);

    }
}