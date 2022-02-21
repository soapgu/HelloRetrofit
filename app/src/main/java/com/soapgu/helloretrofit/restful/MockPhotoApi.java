package com.soapgu.helloretrofit.restful;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.soapgu.helloretrofit.models.Photo;
import com.soapgu.helloretrofit.models.PhotoUrl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import retrofit2.mock.BehaviorDelegate;

public class MockPhotoApi implements PhotoApi{
    private final BehaviorDelegate<PhotoApi> delegate;
    private Context context;

    public MockPhotoApi(BehaviorDelegate<PhotoApi> service, Application application){
        this.delegate = service;
        this.context = application.getApplicationContext();
    }

    @Override
    public Single<Photo> getRandomPhoto(String clientId) {
        Photo photo = new Photo();
        photo.id = "11111";
        photo.alt_description = "photo from mock";
        photo.width = 3353;
        photo.height = 5030;
        PhotoUrl url = new PhotoUrl();
        url.small = "just mock small";
        url.thumb = "just mock thumb";
        photo.urls = url;
        return this.delegate.returningResponse(Single.just(photo)).getRandomPhoto(clientId);
    }

    @Override
    public Single<ResponseBody> getImageFile(String url) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(),1);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 500 ,stream );
        byte[] contents = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(contents);
        Source source = Okio.source(bis);
        BufferedSource bufferedSource = Okio.buffer(source);
        ResponseBody body = new RealResponseBody("image/jpeg",contents.length,bufferedSource);
        return this.delegate.returningResponse(Single.just(body)).getImageFile(url);
    }
}
