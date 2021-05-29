package com.soapgu.helloretrofit.restful;

import com.soapgu.helloretrofit.models.Photo;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface PhotoApi {

    @GET("photos/random")
    Single<Photo> getRandomPhoto( @Query("client_id")String clientId );

    @GET
    @Streaming
    Single<ResponseBody> getImageFile( @Url String url );
}
