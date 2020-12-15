package com.kiscode.grpcgo.adapter.rxjava;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import kiscode.grpcgo.Call;
import kiscode.grpcgo.CallAdapter;


/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:24
 **/
public class RxCallAdapter implements CallAdapter<Observable<?>> {
    private final Type responseType;

    public RxCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    public Type getResponseType() {
        return responseType;
    }

    @Override
    public <R> Observable<R> adapt(final Call<R> call) throws Exception {
        Log.i("grpc", "RxCallAdapter adapt");
//            rawCall.getServiceMethod().rpcMananger.
        //1.执行
        //2.转换 convert
        ObservableOnSubscribe<R> source = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) {
                try {
                    R response = call.execute();
                    emitter.onNext(response);
                } catch (Throwable e) {
                    if (e instanceof InvocationTargetException) {
                        Throwable exception = ((InvocationTargetException) e).getTargetException();
                        Log.i("RxCallAdapterETarget", "" + exception);
                        emitter.onError(exception);
                    }else {
                        emitter.onError(e);
                    }
                    e.printStackTrace();
                }
            }
        };
        return Observable.create(source);
    }
}