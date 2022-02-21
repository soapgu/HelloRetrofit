package com.soapgu.helloretrofit;

import android.app.Application;

import com.soapgu.helloretrofit.restful.PhotoApiAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
@InstallIn(SingletonComponent.class)
public class MyModule {

    private boolean isMock = true;

    @Singleton
    @Provides
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public MockRetrofit provideMockRetrofit(Retrofit retrofit){
        NetworkBehavior behavior = NetworkBehavior.create();
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Provides
    public PhotoApiAdapter providePhotoApiAdapter(Retrofit retrofit, MockRetrofit mockRetrofit, Application application){
        if( this.isMock ){
            return new PhotoApiAdapter( mockRetrofit, application );
        }
        return new PhotoApiAdapter(retrofit);
    }
}
