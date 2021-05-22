package com.dumb.dumb_deaf_system.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClass {

    private static final String BASE_URL_SITE = "https://anoweb.co/deaf/Api/";

//    public static final String BASE_URL = BASE_URL_SITE + "Api/";

//    public static final String ImageCheck = BASE_URL_SITE+"images/";

    private static apis apis;

    public static apis getInstanceGson() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_SITE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apis = retrofit.create(apis.class);
        return apis;

    }
    public static apis getInstanceScaler() {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SITE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            apis = retrofit.create(apis.class);

            return apis;

    }
}

