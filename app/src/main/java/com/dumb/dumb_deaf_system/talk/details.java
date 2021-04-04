package com.dumb.dumb_deaf_system.talk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dumb.dumb_deaf_system.Adapters.adapter_talks_By_Cat;
import com.dumb.dumb_deaf_system.BuildConfig;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class details extends AppCompatActivity implements adapter_talks_By_Cat.ontalkclick{

    String id,category,details,date,gif,video,type,audio,urdu,title;
    VideoView player;
    MediaController mediaController;
    Toolbar toolbar;
    Button share,download;

    URL url;
    List<model_Talksby_Cat> list;
    adapter_talks_By_Cat adapter;

    RecyclerView rec;
    CardView cardView;


    @Override
    protected void onStart() {
        super.onStart();

        player.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        voicePlayerView.pause();

        if (type.equals("video"))
            player.pause();
    }

    ImageView imageView;
    JcPlayerView voicePlayerView;
    int REQUEST_CODE=1;
    TextView head,detail,urdu_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar=findViewById(R.id.tool);
        cardView=findViewById(R.id.cardofimage);

        rec=findViewById(R.id.rec);
        download=findViewById(R.id.download);

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
        imageView = findViewById(R.id.image);



        player = findViewById(R.id.videoplayer);
        mediaController=new MediaController(this);
        player.setMediaController(mediaController);


        Toast.makeText(this, video, Toast.LENGTH_SHORT).show();

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));


        head=findViewById(R.id.head);
        detail=findViewById(R.id.detail);
        urdu_desc=findViewById(R.id.urdu);
        share=findViewById(R.id.share);


        toolbar.setTitle(title);
        head.setText(title);
        detail.setText(details);
        urdu_desc.setText(urdu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitle(category);

        share.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//                if (type.equals("gif")){
//                intent.putExtra(Intent.EXTRA_TEXT, category+"\n"+title+"\n"+details+"\n"+urdu+"\n"+gif);
//                }
//                else {
//                    intent.putExtra(Intent.EXTRA_TEXT, category+"\n"+title+"\n"+details+"\n"+urdu+"\n"+video);
//                }
//                intent.setType("text/plain");
//                startActivity(Intent.createChooser(intent, "Send to friend"));

                if (type.equals("gif")){
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission error","You have permission");
                        Toast.makeText(details.this, "Downloading please wait...", Toast.LENGTH_SHORT).show();
                        String imageFileName = "DumbIMAGES"+id+ ".jpg";
                        AltexImageDownloader.writeToDisk(details.this,gif, imageFileName);
                        getBitmapFromURL(gif);
                    }
                    else {
                        haveStoragePermission();
                    }
                }

                else {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(details.this, "Downloading please wait...", Toast.LENGTH_SHORT).show();
                        AltexImageDownloader.writeToDisk(details.this,video, "Videos");
//                        downloadVideoAndSaveInStorage(details.this,video,"Videos");


                    }else {

                        haveStoragePermission();
                    }
                }
            }
        });
            //gif
        if (type.equals("gif")){
            Glide.with(this).load(gif).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
            player.setVisibility(View.GONE);
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

            player.setVideoPath(video);
            player.start();

            imageView.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }

        download.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Toast.makeText(details.this, "Downloading please wait...", Toast.LENGTH_SHORT).show();
                if (type.equals("gif"))
                AltexImageDownloader.writeToDisk(details.this,gif,"Easy Communication");
                else
                    AltexImageDownloader.writeToDisk(details.this,video,"Easy Communication");

            }
        });
        showrelated(category);

    }


    private void showrelated(String cat) {

        rec.hasFixedSize();
        rec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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
    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED && requestCode==REQUEST_CODE){
            //you have the permission now
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }
    public void getBitmapFromURL(String src) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.net.URL url = new java.net.URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    int status=connection.getResponseCode();
                    if(status<400){

                        InputStream input = connection.getInputStream();

                        Bitmap myBitmap = BitmapFactory.decodeStream(input);
                        if(myBitmap!=null){
                            Log.d("work",myBitmap.toString());
                            sentOnWatsuppImage("",myBitmap);
                        }

                    }
                    else {
                        InputStream input = connection.getErrorStream();
                        Toast.makeText(getApplication(), input.toString(), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void sentOnWatsuppImage(String pack, Bitmap bitmap) {
        PackageManager pm = getPackageManager();
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
            Uri imageUri = Uri.parse(path);

            @SuppressWarnings("unused")
            PackageInfo info = pm.getPackageInfo(pack, PackageManager.GET_META_DATA);

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("image/jpeg");
            waIntent.setPackage(pack);
            waIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
            waIntent.putExtra(Intent.EXTRA_TEXT, pack);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
//            Toast.makeText(getApplicationContext(), "App not Installed", Toast.LENGTH_SHORT).show();
        }
    }

   void sentVideoToWatsupp(Uri uri){
       StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
       StrictMode.setVmPolicy(builder.build());
     Log.d("here","ask");
       File myFile = new File(uri.getPath());

       Uri photoURI = FileProvider.getUriForFile(details.this, BuildConfig.APPLICATION_ID + ".provider",myFile);
       Log.d("url",photoURI.toString());
       Intent share = new Intent(Intent.ACTION_SEND);
       share.setPackage("com.whatsapp");
       share.putExtra(Intent.EXTRA_STREAM, uri);
       share.setType("Video/*");
       share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
       startActivity(Intent.createChooser(share, "Share image File"));
    }

    void downloadVideoAndSaveInStorage(Context context, @NonNull String imageUrl, @NonNull String downloadSubfolder){
        Uri imageUri = Uri.parse(imageUrl);

        String fileName = imageUri.getLastPathSegment();
        String downloadSubpath = downloadSubfolder + fileName;
        Uri pathOfFile = getDownloadDestination(downloadSubpath);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(video));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDescription(video);
        request.allowScanningByMediaScanner();
        request.setDestinationUri(pathOfFile);

        downloadManager.enqueue(request);
        Log.d("path",pathOfFile.toString());

    }
    @NonNull
    public static Uri getDownloadDestination(String downloadSubpath) {
        File picturesFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File destinationFile = new File(picturesFolder, downloadSubpath);
        destinationFile.mkdirs();
        return Uri.fromFile(destinationFile);
    }
}
