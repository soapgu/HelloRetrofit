package com.soapgu.helloretrofit.restful.base;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

public abstract class MockApiAdapter  <T,V extends T> extends ApiAdapter<T> implements ApiAccess<T> {
    private final BehaviorDelegate<T> delegate;
    private T api;
    private final boolean mock;

    public MockApiAdapter(Retrofit retrofit, MockRetrofit mockRetrofit, Class<T> classOfT , boolean mock){
        super(retrofit,classOfT);
        this.delegate = mockRetrofit.create(classOfT);
        this.mock = mock;
    }

    public T getApi(){
        if( !mock ){
            return super.getApi();
        }
        if( api == null ){
            synchronized ( this ){
                if( api == null ) {
                    this.api = createMockService(this.delegate);
                }
            }
        }
        return api;
    }

    protected abstract V createMockService( BehaviorDelegate<T> delegate );
}
