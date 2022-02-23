package com.soapgu.helloretrofit.restful;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.soapgu.helloretrofit.R;
import com.soapgu.helloretrofit.models.Photo;
import com.soapgu.helloretrofit.models.PhotoUrl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

public class MockPhotoApi implements PhotoApi{
    private final BehaviorDelegate<PhotoApi> delegate;
    private final Context context;

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
        return this.delegate.returningResponse(photo).getRandomPhoto(clientId);
    }

    @Override
    public Single<ResponseBody> getImageFile(String url) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.picture);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 ,stream );
        int length = stream.size();
        InputStream in;
        try {
            in = this.convertStream(stream);
        }
        catch (Exception ex){
            return fakeException(ex);
        }
        Source source = Okio.source(in);
        BufferedSource bufferedSource = Okio.buffer(source);
        ResponseBody body = new RealResponseBody("image/jpeg",length,bufferedSource);
        return this.delegate.returningResponse(body).getImageFile(url);
    }

    private InputStream convertStream( ByteArrayOutputStream source ) throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        new Thread(() -> {
            try {
                // write the original OutputStream to the PipedOutputStream
                // note that in order for the below method to work, you need
                // to ensure that the data has finished writing to the
                // ByteArrayOutputStream
                source.writeTo(out);
            }
            catch (IOException e) {
                // logging and exception handling should go here
            }
            finally {
                // close the PipedOutputStream here because we're done writing data
                // once this thread has completed its run
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return in;
    }

    private Single<ResponseBody> fakeException(Exception exception){
        return this.delegate.returning(Calls.failure( exception )).getImageFile("");
    }

    private Single<ResponseBody> getImageFile404(String url){
        Response<ResponseBody> response = Response.error(404,ResponseBody.create(MediaType.parse("application/json"),""));
        return this.delegate.returning(Calls.response(response)).getImageFile(url);
    }

}
