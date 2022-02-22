package com.soapgu.helloretrofit.restful;

import android.app.Application;

import androidx.core.util.Pair;

import com.soapgu.helloretrofit.models.Photo;
import com.soapgu.helloretrofit.restful.base.ApiAccess;
import com.soapgu.helloretrofit.restful.base.MockApiAdapter;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

public class PhotoApiAdapter extends MockApiAdapter<PhotoApi,MockPhotoApi> {
    //private final PhotoApi api;
    private Application application;
    private static final String client_id = "ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM";

    /*
    public PhotoApiAdapter( Retrofit retrofit ){
        api = retrofit.create( PhotoApi.class );
    }
     */

    public PhotoApiAdapter(Retrofit retrofit , MockRetrofit mockRetrofit , Application application , boolean mock){
        super(retrofit,mockRetrofit,PhotoApi.class,mock);
        this.application = application;
        this.setApi();
    }

    public Single<Photo> getRandomPhoto() {
        return this.getApi().getRandomPhoto( client_id );
    }

    public Single<Pair<ResponseBody,String>> getImageFile( String url , String photoId ){
        return Single.zip( this.getApi().getImageFile(url), Single.just(photoId) , (Pair::create) ) ;
    }

    @Override
    protected MockPhotoApi createMockService(BehaviorDelegate<PhotoApi> delegate) {
        return new MockPhotoApi(delegate,application);
    }
}
