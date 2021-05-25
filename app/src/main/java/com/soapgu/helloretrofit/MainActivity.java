package com.soapgu.helloretrofit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.soapgu.helloretrofit.databinding.ActivityMainBinding;
import com.soapgu.helloretrofit.restful.PhotoApiAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PhotoApiAdapter apiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiAdapter = new PhotoApiAdapter();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonNew.setOnClickListener( v -> {
            apiAdapter.getRandomPhoto()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( photo -> binding.tvPhoto.setText( photo.alt_description ),
                                e -> Logger.e( e, "get randomPhoto error" ));
        } );
    }
}