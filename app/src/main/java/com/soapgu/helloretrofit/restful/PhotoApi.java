package com.soapgu.helloretrofit.restful;

import com.soapgu.helloretrofit.models.Photo;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoApi {

    @GET("photos/random")
    Single<Photo> getRandomPhoto( @Query("client_id")String clientId );
}
