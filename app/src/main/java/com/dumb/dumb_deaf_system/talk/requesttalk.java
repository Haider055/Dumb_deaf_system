package com.dumb.dumb_deaf_system.talk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.modelcategories;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class requesttalk extends AppCompatActivity {

    EditText title,detail,urdu;

    private static final String MY_INFO = "MYINFO";
    RadioGroup radio,radioselect;
    Button submit;
    Button uploadvideobutton,uploadaudiobutton,uploadimagebutton;
    String type="";
    String videourl="";
    String audiourl="";
    String imageurl="";
    String category="";
    androidx.appcompat.widget.Toolbar toolbar;

    me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;

    TextView uploadtext,uploadaudiotext;
    String[] country;
    Spinner spinner;
    List<modelcategories> listofcategory;

    String selectedPath;

    private static final int SELECT_VIDEO = 3;
    private static final int SELECT_AUDIO = 4;
    private static final int SELECT_IMAGE = 2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data!=null) {
            if (requestCode == SELECT_VIDEO) {
                assert data != null;
                Uri videoUri = data.getData();
                uploadvideotofirebase(videoUri);
//                selectedPath = getPath(selectedImageUri);

//                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
//
//                SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                byte[] imageInByte = stream.toByteArray();
//                String Imagestring= Base64.encodeToString(imageInByte,Base64.DEFAULT);
//                Log.d("data",Imagestring);
            }
        }

        if (resultCode == RESULT_OK && data!=null) {
            if (requestCode == SELECT_AUDIO) {
                assert data != null;
                Uri audioUri = data.getData();
                uploadaudiotofirebase(audioUri);
//                selectedPath = getPath(selectedImageUri);

//                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
//
//                SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                byte[] imageInByte = stream.toByteArray();
//                String Imagestring= Base64.encodeToString(imageInByte,Base64.DEFAULT);
//                Log.d("data",Imagestring);
            }
        }

        if (resultCode == RESULT_OK && data!=null) {
            if (requestCode == SELECT_IMAGE) {
                assert data != null;
                Uri imageUri = data.getData();
                uploadimagetofirebase(imageUri);
//                selectedPath = getPath(selectedImageUri);

//                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
//
//                SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                byte[] imageInByte = stream.toByteArray();
//                String Imagestring= Base64.encodeToString(imageInByte,Base64.DEFAULT);
//                Log.d("data",Imagestring);
            }
        }
    }

    private void uploadimagetofirebase(Uri imageUri) {
        Toast.makeText(this, "Uploading please wait...", Toast.LENGTH_SHORT).show();
        FirebaseStorage.getInstance().getReference().child("image").child(String.valueOf(System.currentTimeMillis()))
                .putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageurl=uri.toString();
                        videourl="";
                        Toast.makeText(requesttalk.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        uploadtext.setVisibility(View.VISIBLE);
                        uploadtext.setText("Image uploaded");
                        //https://aditya58singh.medium.com/how-to-get-audio-files-from-the-internal-storage-in-android-d905b2488ddc
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void uploadvideotofirebase(Uri videoUri) {
        Toast.makeText(this, "Uploading please wait...", Toast.LENGTH_SHORT).show();
        FirebaseStorage.getInstance().getReference().child("video").child(String.valueOf(System.currentTimeMillis()))
                .putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        videourl=uri.toString();
                        imageurl="";
                        Toast.makeText(requesttalk.this, "Video uploaded", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        uploadtext.setVisibility(View.VISIBLE);
                        uploadtext.setText("Video uploaded");

                        //https://aditya58singh.medium.com/how-to-get-audio-files-from-the-internal-storage-in-android-d905b2488ddc
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void uploadaudiotofirebase(Uri videoUri) {
        Toast.makeText(this, "Uploading please wait...", Toast.LENGTH_SHORT).show();
        FirebaseStorage.getInstance().getReference().child("audio").child(String.valueOf(System.currentTimeMillis()))
                .putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        audiourl=uri.toString();
                        Toast.makeText(requesttalk.this, "Audio uploaded", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        uploadaudiotext.setVisibility(View.VISIBLE);
                        //https://aditya58singh.medium.com/how-to-get-audio-files-from-the-internal-storage-in-android-d905b2488ddc
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requesttalk.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

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
        urdu=findViewById(R.id.urdu);

        radio=findViewById(R.id.radio);
        radioselect=findViewById(R.id.radios);
        submit=findViewById(R.id.submit);

        uploadimagebutton=findViewById(R.id.uploadimagebutton);
        uploadvideobutton=findViewById(R.id.uploadvideobutton);
        uploadaudiobutton=findViewById(R.id.uploadaudiobutton);

        uploadimagebutton.setVisibility(View.GONE);
        uploadvideobutton.setVisibility(View.GONE);

        progressBar=findViewById(R.id.progress);

        uploadtext=findViewById(R.id.imageupload);
        uploadtext.setVisibility(View.GONE);


        uploadaudiotext=findViewById(R.id.audioupload);
        uploadaudiotext.setVisibility(View.GONE);

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));

        spinner=findViewById(R.id.spin);

        Call<List<modelcategories>> apiofcategory=RetrofitClass.getInstanceGson().categories();
        apiofcategory.enqueue(new Callback<List<modelcategories>>() {
            @Override
            public void onResponse(Call<List<modelcategories>> call, Response<List<modelcategories>> response) {
                listofcategory=response.body();

                country=new String[listofcategory.size()+1];

                country[0]="Select category";

                for (int i=1;i<=listofcategory.size();i++){
                    country[i]=listofcategory.get(i-1).getCategory();
                }
//                listofcategory.toArray(country);

                ArrayAdapter aa = new ArrayAdapter(requesttalk.this,android.R.layout.simple_spinner_item,country);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                spinner.setAdapter(aa);

            }
            @Override
            public void onFailure(Call<List<modelcategories>> call, Throwable t) {
                Toast.makeText(requesttalk.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=country[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadvideobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
            }
        });

        uploadaudiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Audio "), SELECT_AUDIO);

            }
        });

        uploadimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select an Image "), SELECT_IMAGE);
            }
        });





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


        radioselect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.image){
                    uploadvideobutton.setVisibility(View.INVISIBLE);
                    uploadimagebutton.setVisibility(View.VISIBLE);
                }
                else {
                    uploadimagebutton.setVisibility(View.INVISIBLE);
                    uploadvideobutton.setVisibility(View.VISIBLE);
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().isEmpty()&& urdu.getText().toString().isEmpty()){
                    Toast.makeText(requesttalk.this, "Enter title in ENG or URDU", Toast.LENGTH_SHORT).show();;
                }
                if (detail.getText().toString().isEmpty()){
                    detail.setError("Enter title");
                }
                if (category.equals("")||category.equals("Select category")){
                    Toast.makeText(requesttalk.this, "Select category", Toast.LENGTH_SHORT).show();
                }
                if (type.equals("")){
                    Toast.makeText(requesttalk.this, "Select request type", Toast.LENGTH_SHORT).show();
                }
                if (videourl.equals("")&&imageurl.equals("")){
                    Toast.makeText(requesttalk.this, "You must select a video or image", Toast.LENGTH_SHORT).show();
                }
                if (title.getText().toString().isEmpty()||detail.getText().toString().isEmpty()||type.equals("")||category.equals("")||category.equals("Select category")){
                }
                else {
                    String titles,details,urdus;
                    titles=title.getText().toString();
                    details=detail.getText().toString();
                    urdus=detail.getText().toString();

                    SharedPreferences shared=getSharedPreferences(MY_INFO,MODE_PRIVATE);
                    String id=shared.getString("id","");

                    Call<String> apirequest= RetrofitClass.getInstanceScaler().request(id,type,titles,details,audiourl,imageurl,videourl,category,urdus);

                    apirequest.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(requesttalk.this," Requested", Toast.LENGTH_SHORT).show();
                                title.setText("");
                                detail.setText("");
                                urdu.setText("");
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