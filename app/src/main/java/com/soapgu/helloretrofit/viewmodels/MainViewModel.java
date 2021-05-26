package com.soapgu.helloretrofit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.orhanobut.logger.Logger;
import com.soapgu.helloretrofit.restful.PhotoApiAdapter;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class MainViewModel extends ObservableViewModel {

    private String photoInfo;
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

    public void newRandomPhoto(){
        Logger.i( "---new random photo----" );
        disposables.add( apiAdapter.getRandomPhoto()
                            .subscribe( photo -> this.setPhotoInfo( photo.alt_description ),
                                        e -> Logger.e( e, "get randomPhoto error" )) );
    }
}
