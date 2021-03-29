package com.dumb.dumb_deaf_system.Network;

import com.dumb.dumb_deaf_system.models.modelGetDataByLogin;
import com.dumb.dumb_deaf_system.models.model_Talksby_Cat;
import com.dumb.dumb_deaf_system.models.model_myrequests;
import com.dumb.dumb_deaf_system.models.model_mytalks;
import com.dumb.dumb_deaf_system.models.modelcategories;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface apis {

    @FormUrlEncoded
    @POST("userLogin.php")
    Call<String> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("userSignUP.php")
    Call<String> signup(@Field("username") String name,@Field("email") String email, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("phone") String phone, @Field("image") String image, @Field("address") String address, @Field("gender") String gender, @Field("type") String type);

    @POST("showCategories.php")
    Call<List<modelcategories>> categories();

    @FormUrlEncoded
    @POST("userProfileShow.php")
    Call<List<modelGetDataByLogin>> getprofiledata(@Field("id") String id);

    @FormUrlEncoded
    @POST("updateName.php")
    Call<String> updateName(@Field("id") String id, @Field("name") String name);

    @FormUrlEncoded
    @POST("updateAddress.php")
    Call<String> updateAddress(@Field("id") String id, @Field("address") String address);

    @FormUrlEncoded
    @POST("updatePhone.php")
    Call<String> updatePhone(@Field("id") String id, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("updateImage.php")
    Call<String> updateImage(@Field("id") String id, @Field("image") String image);

    @FormUrlEncoded
    @POST("updateGender.php")
    Call<String> updategender(@Field("id") String id, @Field("gender") String gender);

    @FormUrlEncoded
    @POST("updateType.php")
    Call<String> updatetype(@Field("id") String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("showCategoryData.php")
    Call<List<model_Talksby_Cat>> showtalkbycategory(@Field("category") String category);

    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<String> updatepass(@Field("id") String id, @Field("password") String password,@Field("old_password") String oldpassword);

    @FormUrlEncoded
    @POST("request.php")
    Call<String> request(@Field("id") String id, @Field("viewer") String viewer,@Field("title") String title,@Field("description") String description,@Field("audio") String audio,@Field("gif") String gif,@Field("video") String video,@Field("category") String category);

    @FormUrlEncoded
    @POST("showMyData.php")
    Call<List<model_mytalks>> showmydata(@Field("id") String id);

    @FormUrlEncoded
    @POST("showRequests.php")
    Call<List<model_myrequests>> showmyrequests(@Field("id") String id);

    @FormUrlEncoded
    @POST("notification.php")
    Call<String> notification(@Field("id") String id);

    @FormUrlEncoded
    @POST("updateNotification.php")
    Call<String> updateNotification(@Field("id") String id);
}