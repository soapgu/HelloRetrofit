package com.soapgu.helloretrofit.restful;

import androidx.core.util.Pair;

import com.soapgu.helloretrofit.models.Photo;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class PhotoApiAdapter {
    private final PhotoApi api;
    private static final String client_id = "ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";

    public PhotoApiAdapter( Retrofit retrofit ){
        api = retrofit.create( PhotoApi.class );
    }

    public Single<Photo> getRandomPhoto() {
        return this.api.getRandomPhoto( client_id );
    }

    public Single<Pair<ResponseBody,String>> getImageFile( String url , String photoId ){
        return Single.zip( this.api.getImageFile(url), Single.just(photoId) , (Pair::create) ) ;
    }
}
