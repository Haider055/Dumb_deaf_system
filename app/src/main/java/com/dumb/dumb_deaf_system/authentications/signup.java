package com.dumb.dumb_deaf_system.authentications;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dumb.dumb_deaf_system.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;

    EditText name,email,phone,address,password,confirm_password;
    String nameString,emailString,phoneString,addressString,passwordString,confirm_passwordString;
    String genderString,typeString;
    RadioGroup gender,type;

    String imageUpdateString="";
    FloatingActionButton choseimg;
    Uri imageUri;

    Button signup;

    CircleImageView profileimage;
    int CAMERA_PERMISSION_CODE=100;
    LinearLayout camera,gallary;
    AlertDialog alertDialog;


    private static final int REQUEST_IMAGE_CAPTURE = 1,imageGalary=2;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar=findViewById(R.id.toolbar);
        signup=findViewById(R.id.signup);

        genderString="";
        typeString="";

        gender=findViewById(R.id.radiogender);
        type=findViewById(R.id.radiotype);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.female)
                genderString="Female";
                else
                genderString="Male";
            }
        });

        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.deaf);
                typeString="Deaf";
                if (checkedId==R.id.dumb);
                typeString="Dumb";
                if (checkedId==R.id.both)
                typeString="Both";
                if (checkedId==R.id.none)
                typeString="none";
            }
        });

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        password=findViewById(R.id.pass);
        confirm_password=findViewById(R.id.confirmpass);

        choseimg = findViewById(R.id.floatingActionButton);
        profileimage = findViewById(R.id.imageview_account_profile);

        choseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(signup.this);
                View viewimg = LayoutInflater.from(signup.this).inflate(R.layout.dialogforimage, null);
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
                        if (ContextCompat.checkSelfPermission(signup.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(signup.this.getPackageManager()) != null) {
                                try {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                } catch (Exception e) {
                                    Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            requestStoragePermission();
                        }
                    }
                });

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, Loginpage.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap image=((BitmapDrawable)profileimage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageInByte = stream.toByteArray();
                imageUpdateString= Base64.encodeToString(imageInByte,Base64.DEFAULT);

                nameString=name.getText().toString();
                emailString=email.getText().toString();
                phoneString=phone.getText().toString();
                addressString=address.getText().toString();
                passwordString=password.getText().toString();
                confirm_passwordString=confirm_password.getText().toString();

                if (nameString.isEmpty())
                    name.setError("Enter Name");
                if (emailString.isEmpty())
                    email.setError("Enter Email");
                if (phoneString.isEmpty())
                    phone.setError("Enter Phone Number");
                if (addressString.isEmpty())
                    address.setError("Enter Address");
                if (passwordString.isEmpty())
                    password.setError("Set Password");
                if (confirm_passwordString.isEmpty())
                    confirm_password.setError("Enter confirm password");
                if (nameString.length()<4)
                    Toast.makeText(signup.this, "Name must be of atleast 4 characters", Toast.LENGTH_SHORT).show();
                if (!confirm_passwordString.equals(passwordString))
                    Toast.makeText(signup.this, "Both passwords not matched", Toast.LENGTH_SHORT).show();

                if (nameString.isEmpty()||emailString.isEmpty()||phoneString.isEmpty()||addressString.isEmpty()||passwordString.isEmpty()||confirm_passwordString.isEmpty()|| !passwordString.equals(confirm_passwordString) || nameString.length()<4 ){}
                else {

                    if (genderString.equals("")) {
                        Toast.makeText(signup.this, "Select gender", Toast.LENGTH_SHORT).show();
                    } else {

                        if (typeString.equals("")) {
                            Toast.makeText(signup.this, "Select disability type", Toast.LENGTH_SHORT).show();
                        } else {

                            //start retrofit
//                            Call<String> api1 = RetrofitClass.getInstanceScaler().signup(nameString, emailString, passwordString, confirm_passwordString, phoneString, imageUpdateString, addressString, genderString, typeString);
//
//                            api1.enqueue(new Callback<String>() {
//                                @Override
//                                public void onResponse(Call<String> call, Response<String> response) {
//
//                                    if (response.isSuccessful()){
//                                        if (response.body().trim().equals("Register Successfully")){
//                                            Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                                            Intent intent=new Intent(signup.this,Loginpage.class);
//                                            intent.putExtra("name",nameString);
//                                            intent.putExtra("email",emailString);
//                                            intent.putExtra("phone",phoneString);
//                                            intent.putExtra("address",addressString);
//                                            intent.putExtra("password",passwordString);
//                                            intent.putExtra("image",imageUpdateString);
//                                            intent.putExtra("con_password",confirm_passwordString);
//                                            intent.putExtra("gender",genderString);
//                                            intent.putExtra("type",typeString);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Call<String> call, Throwable t) {
//
//                                }
//                            });

                                            Intent intent=new Intent(signup.this,otp_screen.class);
                                            intent.putExtra("name",nameString);
                                            intent.putExtra("email",emailString);
                                            intent.putExtra("phone",phoneString);
                                            intent.putExtra("address",addressString);
                                            intent.putExtra("password",passwordString);
                                            intent.putExtra("image",imageUpdateString);
                                            intent.putExtra("con_password",confirm_passwordString);
                                            intent.putExtra("gender",genderString);
                                            intent.putExtra("type",typeString);
                                            startActivity(intent);

                        }

                        }
                    }
                }

        });




        //activity end
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(signup.this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(signup.this.getPackageManager()) != null) {
                    try {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } catch (Exception e) {
                        Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }




            } else {
                Toast.makeText(signup.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(signup.this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(signup.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of Camera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(signup.this,
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
            ActivityCompat.requestPermissions(signup.this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == imageGalary && resultCode == RESULT_OK)
        {
            imageUri=data.getData();
            profileimage.setImageURI(imageUri);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null){

            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
            profileimage.setImageBitmap(bitmap);

        }

    }


}