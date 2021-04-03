package com.dumb.dumb_deaf_system.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dumb.dumb_deaf_system.Network.RetrofitClass;
import com.dumb.dumb_deaf_system.R;
import com.dumb.dumb_deaf_system.models.modelGetDataByLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    TextView nameTextView,phoneTextView,passwordTextView,genderTextView,typeTextView,addressTextView;

    TextView nameicon,phoneicon,passwordicon,gendericon,typeicon,addressicon;

    private static final String MY_INFO = "MYINFO";
    CircleImageView profileimage;
    FloatingActionButton choseimg;
    int CAMERA_PERMISSION_CODE=100;
    ImageView camera,gallary;
    AlertDialog alertDialog;
    String Imagestring="";

    AlertDialog.Builder buildername;
    AlertDialog alertname;

    AlertDialog.Builder builderaddress;
    AlertDialog alertaddress;

    AlertDialog.Builder builderphone;
    AlertDialog alertphone;

    AlertDialog.Builder buildergender;
    AlertDialog alertgender;

    AlertDialog.Builder buildertype;
    AlertDialog alerttype;

    AlertDialog.Builder builderpass;
    AlertDialog alertpass;


    String gender="";
    String type="";
    Uri imageUri;

    private static final int REQUEST_IMAGE_CAPTURE = 1,imageGalary=2;
    AlertDialog.Builder builder;

    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameicon=findViewById(R.id.nameupdate);
        addressicon=findViewById(R.id.addressupdate);
        phoneicon=findViewById(R.id.phoneupdate);
        gendericon=findViewById(R.id.genderupdate);
        typeicon=findViewById(R.id.typeupdate);
        passwordicon=findViewById(R.id.passwordupdate);

        toolbar=findViewById(R.id.tool);

        choseimg = findViewById(R.id.floatingActionButton);
        profileimage = findViewById(R.id.imageview_account_profile);

        SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);

        nameTextView=findViewById(R.id.name);
        addressTextView=findViewById(R.id.address);
        phoneTextView=findViewById(R.id.phone);
        genderTextView=findViewById(R.id.gender);
        typeTextView=findViewById(R.id.type);
        passwordTextView=findViewById(R.id.password);

        Window window =getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolcolor));



        nameTextView.setText(sharedPreferences.getString("name",""));
        addressTextView.setText(sharedPreferences.getString("address",""));
        phoneTextView.setText(sharedPreferences.getString("phone",""));
        genderTextView.setText(sharedPreferences.getString("gender",""));
        typeTextView.setText(sharedPreferences.getString("type",""));
//        passwordTextView.setText(sharedPreferences.getString("password","*****"));
        passwordTextView.setText("******");

        Picasso.get().load(sharedPreferences.getString("image","")).into(profileimage);

        Glide.with(this)
                .load(sharedPreferences.getString("image",""))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(profileimage);

        nameicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildername = new AlertDialog.Builder(profile.this);
                View viewname = LayoutInflater.from(profile.this).inflate(R.layout.popupfornameupdate, null);
                buildername.setView(viewname);
                alertname = buildername.create();
                alertname.show();

                EditText namefield;
                Button updatename;

                namefield=viewname.findViewById(R.id.name);
                updatename=viewname.findViewById(R.id.update);

                namefield.setText(sharedPreferences.getString("name",""));

                updatename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (namefield.getText().toString().isEmpty()){
                            namefield.setError("Enter name");
                        }
                        else {
                            updatenamefunc(sharedPreferences.getString("id",""),namefield.getText().toString());
                        }
                    }
                });
            }
        });
        addressicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builderaddress = new AlertDialog.Builder(profile.this);
                View viewaddress = LayoutInflater.from(profile.this).inflate(R.layout.popupforaddressupdate, null);
                builderaddress.setView(viewaddress);
                alertaddress = builderaddress.create();
                alertaddress.show();

                EditText addressfield;
                Button updateaddress;

                addressfield=viewaddress.findViewById(R.id.address);
                updateaddress=viewaddress.findViewById(R.id.update);


                addressfield.setText(sharedPreferences.getString("address",""));

                updateaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addressfield.getText().toString().isEmpty()){
                            addressfield.setError("Enter Address");
                        }
                        else {
                            updateaddressfunc(sharedPreferences.getString("id",""),addressfield.getText().toString());
                        }
                    }
                });

            }
        });
        phoneicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderphone = new AlertDialog.Builder(profile.this);
                View viewphone = LayoutInflater.from(profile.this).inflate(R.layout.popupforphoneupdate, null);
                builderphone.setView(viewphone);
                alertphone = builderphone.create();
                alertphone.show();

                EditText phonefield;
                Button updatephone;

                phonefield=viewphone.findViewById(R.id.phone);
                updatephone=viewphone.findViewById(R.id.update);


                phonefield.setText(sharedPreferences.getString("phone",""));

                updatephone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (phonefield.getText().toString().isEmpty()){
                            phonefield.setError("Enter Phone");
                        }
                        else {
                            updatephonefunc(sharedPreferences.getString("id",""),phonefield.getText().toString());
                        }
                    }
                });
            }
        });

        gendericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildergender = new AlertDialog.Builder(profile.this);
                View viewgender = LayoutInflater.from(profile.this).inflate(R.layout.popupforgenderupdate, null);
                buildergender.setView(viewgender);
                alertgender = buildergender.create();
                alertgender.show();

                RadioGroup gendergroup;
                Button updategender;

                gendergroup=viewgender.findViewById(R.id.gender);
                updategender=viewgender.findViewById(R.id.update);

                gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId==R.id.male)
                            gender="Male";
                        else
                            gender="Female";
                    }
                });

                updategender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gender.equals("")){
                            Toast.makeText(profile.this, "Select gender", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            updategenderfunc(sharedPreferences.getString("id",""),gender);
                        }
                    }
                });

            }
        });

        typeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildertype = new AlertDialog.Builder(profile.this);
                View viewtype = LayoutInflater.from(profile.this).inflate(R.layout.popupfortypeupdate, null);
                buildertype.setView(viewtype);
                alerttype = buildertype.create();
                alerttype.show();

                RadioGroup typegroup;
                Button updatetype;


                typegroup=viewtype.findViewById(R.id.radiotype);
                updatetype=viewtype.findViewById(R.id.update);


                typegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId==R.id.dumb)
                            type="Dumb";
                        if (checkedId==R.id.deaf)
                            type="Deaf";
                        if (checkedId==R.id.both)
                            type="Both";
                        if (checkedId==R.id.none)
                            type="None";

                    }
                });

                updatetype.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equals("")){
                            Toast.makeText(profile.this, "Select disbility type", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            updatetypefunc(sharedPreferences.getString("id",""),type);
                        }
                    }
                });
            }
        });

        passwordicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builderpass = new AlertDialog.Builder(profile.this);
                View viewpass = LayoutInflater.from(profile.this).inflate(R.layout.popupforpasswordupdate, null);
                builderpass.setView(viewpass);
                alertpass = builderpass.create();
                alertpass.show();

                EditText pre_password=viewpass.findViewById(R.id.pre_password);
                EditText password=viewpass.findViewById(R.id.password);
                Button update=viewpass.findViewById(R.id.update);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (pre_password.getText().toString().isEmpty()){
                            pre_password.setError("Enter previous password");
                        }
                        if (password.getText().toString().isEmpty()){
                            pre_password.setError("Enter new password");
                        }
                        if (pre_password.getText().toString().isEmpty()||password.getText().toString().isEmpty()){}
                        else {
                            if (pre_password.getText().toString().equals(sharedPreferences.getString("password","123"))){
                                updatepasswordfunc(sharedPreferences.getString("id",""),password.getText().toString(),sharedPreferences.getString("password","123"));
                            }
                            else {
                                Toast.makeText(profile.this, "Both passwords not matched", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
                });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(profile.this,MainActivity.class));
                onBackPressed();
            }
        });

        choseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateimage();

            }
        });

    }
    private void updatepasswordfunc(String id,String password,String oldpass) {

        Call<String> api= RetrofitClass.getInstanceScaler().updatepass(id,password,oldpass);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();
                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("password",password);
                    editors.apply();
                    alertpass.dismiss();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void updatetypefunc(String id, String type) {

        Call<String> api= RetrofitClass.getInstanceScaler().updatetype(id,type);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("type",type);
                    editors.apply();
                    alerttype.dismiss();
                    typeTextView.setText(type);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        }

    private void updategenderfunc(String id, String gender) {
        Call<String> api= RetrofitClass.getInstanceScaler().updategender(id,gender);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("gender",gender);
                    editors.apply();
                    alertgender.dismiss();
                    genderTextView.setText(gender);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void updatephonefunc(String id, String phone) {
        Call<String> api= RetrofitClass.getInstanceScaler().updatePhone(id,phone);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("phone",phone);
                    editors.apply();
                    alertphone.dismiss();
                    phoneTextView.setText(phone);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void updateaddressfunc(String id, String address) {
        Call<String> api= RetrofitClass.getInstanceScaler().updateAddress(id,address);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("address",address);
                    editors.apply();
                    alertaddress.dismiss();
                    addressTextView.setText(address);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void updatenamefunc(String id,String name) {
        Call<String> api= RetrofitClass.getInstanceScaler().updateName(id,name);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();

                    Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();
                    editors.putString("name",name);
                    editors.apply();
                    alertname.dismiss();
                    nameTextView.setText(name);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(profile.this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(profile.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for Camera usage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(profile.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(profile.this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }



    }

    private void updateimage() {


        builder = new AlertDialog.Builder(profile.this);
        View viewimg = LayoutInflater.from(profile.this).inflate(R.layout.dialogforimage, null);
        builder.setView(viewimg).setTitle("Select You Image");
        alertDialog = builder.create();
        alertDialog.show();
        camera = viewimg.findViewById(R.id.camraImage);
        gallary = viewimg.findViewById(R.id.galaryImage);
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, imageGalary);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                if (ContextCompat.checkSelfPermission(profile.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(profile.this.getPackageManager()) != null) {
                        try {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        } catch (Exception e) {
                            Toast.makeText(profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    requestStoragePermission();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == imageGalary && resultCode == RESULT_OK)
        {
            imageUri=data.getData();
            profileimage.setImageURI(imageUri);

            SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);

            Bitmap image=((BitmapDrawable)profileimage.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] imageInByte = stream.toByteArray();
            Imagestring= Base64.encodeToString(imageInByte,Base64.DEFAULT);

            Call<String> apiforimage=RetrofitClass.getInstanceScaler().updateImage(sharedPreferences.getString("id",""),Imagestring);
            apiforimage.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){


                        Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();

                        Call<List<modelGetDataByLogin>> apiforurl=RetrofitClass.getInstanceGson().getprofiledata(sharedPreferences.getString("id",""));
                        apiforurl.enqueue(new Callback<List<modelGetDataByLogin>>() {
                            @Override
                            public void onResponse(Call<List<modelGetDataByLogin>> call, Response<List<modelGetDataByLogin>> response) {

                                SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();
                                editors.putString("image", response.body().get(0).getImage());
                                editors.apply();

                                Glide.with(profile.this)
                                        .load(sharedPreferences.getString("image",""))
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                        .into(profileimage);
                            }

                            @Override
                            public void onFailure(Call<List<modelGetDataByLogin>> call, Throwable t) {

                            }
                        });



//                        Picasso.get().load(Imagestring).networkPolicy(NetworkPolicy.NO_STORE).into(profileimage);

                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null){

            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            profileimage.setImageBitmap(bitmap);

            SharedPreferences sharedPreferences=getSharedPreferences(MY_INFO,MODE_PRIVATE);

            Bitmap image=((BitmapDrawable)profileimage.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] imageInByte = stream.toByteArray();
            Imagestring= Base64.encodeToString(imageInByte,Base64.DEFAULT);

            Call<String> apiforimage=RetrofitClass.getInstanceScaler().updateImage(sharedPreferences.getString("id",""),Imagestring);
            apiforimage.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(profile.this, "Image updated", Toast.LENGTH_SHORT).show();

//                        SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();
                        Toast.makeText(profile.this, response.body().trim().toString(), Toast.LENGTH_SHORT).show();

                        Call<List<modelGetDataByLogin>> apiforurl=RetrofitClass.getInstanceGson().getprofiledata(sharedPreferences.getString("id",""));
                        apiforurl.enqueue(new Callback<List<modelGetDataByLogin>>() {
                            @Override
                            public void onResponse(Call<List<modelGetDataByLogin>> call, Response<List<modelGetDataByLogin>> response) {

                                SharedPreferences.Editor editors =getSharedPreferences(MY_INFO, MODE_PRIVATE).edit();
                                editors.putString("image", response.body().get(0).getImage());
                                editors.apply();

                                Glide.with(profile.this)
                                        .load(sharedPreferences.getString("image",""))
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                        .into(profileimage);
                            }

                            @Override
                            public void onFailure(Call<List<modelGetDataByLogin>> call, Throwable t) {

                            }
                        });




//                        editors.putString("image", Imagestring);
//                        Glide.with(profile.this)
//                                .load(Imagestring)
//                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                                .into(profileimage);
//                        editors.apply();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }




    }
}