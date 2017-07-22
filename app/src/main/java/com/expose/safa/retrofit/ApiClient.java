package com.expose.safa.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vineesh on 03/04/2017.
 */
public class ApiClient {


    private static final String BASE_URL="http://54.218.76.33/SAFA/";
    private ApiInterface mApiInterface;

    public ApiInterface getApiInterface() {
        if (mApiInterface == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mApiInterface = retrofit.create(ApiInterface.class);
        }
        return mApiInterface;
    }
}