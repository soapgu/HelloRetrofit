package com.soapgu.helloretrofit.restful;

import com.soapgu.helloretrofit.models.Photo;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoApiAdapter {
    private final PhotoApi api;
    private static final String client_id = "ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";
    public PhotoApiAdapter(){
        api = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create( PhotoApi.class );
    }

    public Single<Photo> getRandomPhoto() {
        return this.api.getRandomPhoto( client_id );
    }
}
