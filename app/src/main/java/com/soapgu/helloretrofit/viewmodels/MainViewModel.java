package com.soapgu.helloretrofit.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.blankj.utilcode.util.FileIOUtils;
import com.orhanobut.logger.Logger;
import com.soapgu.helloretrofit.restful.PhotoApiAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class MainViewModel extends ObservableViewModel {

    private String photoInfo;
    private Bitmap bitmap;
    private final PhotoApiAdapter apiAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Inject
    public MainViewModel(@NonNull Application application,PhotoApiAdapter apiAdapter) {
        super(application);
        this.apiAdapter = apiAdapter;
    }

    @Bindable
    public String getPhotoInfo() {
        return photoInfo;
    }

    public void setPhotoInfo(String photoInfo) {
        this.photoInfo = photoInfo;
        this.notifyPropertyChanged(BR.photoInfo);
    }

    @Bindable
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.notifyPropertyChanged(BR.bitmap);
    }

    public void newRandomPhoto(){
        Logger.i( "---new random photo----" );
        disposables.add( apiAdapter.getRandomPhoto()
                            .flatMap( photo -> {
                                this.setPhotoInfo( photo.alt_description );
                                return apiAdapter.getImageFile(photo.urls.small,photo.id);
                            } )
                            .flatMap( responseBodyStringPair -> {
                                File file = new File( getApplication().getExternalCacheDir(), String.format( "%s.jpg" ,responseBodyStringPair.second ) );
                                assert responseBodyStringPair.first != null;
                                InputStream stream = responseBodyStringPair.first.byteStream();
                                return Single.fromSupplier( () ->{
                                    if( FileIOUtils.writeFileFromIS( file , stream ) ){
                                        return file;
                                    }
                                    throw new IOException( "Write File Error" );
                                } );
                            } )
                            .map( file -> BitmapFactory.decodeFile(file.getPath()) )
                            .subscribe(this::setBitmap,
                                        e -> Logger.e( e, "get randomPhoto error" )) );
    }
}
