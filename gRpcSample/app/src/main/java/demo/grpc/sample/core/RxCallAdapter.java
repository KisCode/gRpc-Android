package demo.grpc.sample.core;

import android.util.Log;

import java.lang.reflect.Type;

import rx.Observable;


/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:24
 **/
public class RxCallAdapter<T> implements CallAdapter<Observable<?>> {
    private final Type responseType;

    public RxCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    public Type getResponseType() {
        return responseType;
    }

    @Override
    public <R> Observable<T> adapt(Call<R> call) throws Exception {
        Log.i("grpc", "RxCallAdapter adapt");

        GrpcCall rawCall = (GrpcCall) call;
//            rawCall.getServiceMethod().rpcMananger.
        //1.执行
        //2.转换 convert
//        return (T) call.excute();
        return null;
    }
}