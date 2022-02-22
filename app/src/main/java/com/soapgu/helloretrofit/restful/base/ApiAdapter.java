package com.soapgu.helloretrofit.restful.base;

import retrofit2.Retrofit;

public abstract class ApiAdapter<T> implements ApiAccess<T> {
    private final T api;

    public T getApi(){
        return api;
    }

    public ApiAdapter(Retrofit retrofit , Class<T> classOfT ){
        this.api = retrofit.create(classOfT);
    }
}
