package demo.grpc.sample.core;

import android.util.Log;


/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 13:24
 **/
public class RxCallAdapter<T> implements CallAdapter<T> {
    @Override
    public <R> T adapt(Call<R> call) throws Exception {
        Log.i("grpc", "RxCallAdapter adapt");
        if (call instanceof GrpcCall) {
            GrpcCall rawCall = (GrpcCall) call;
//            rawCall.getServiceMethod().rpcMananger.
            //1.执行
            //2.转换 convert
            return (T) call.excute();
        } else {
            throw new IllegalArgumentException("not support call type :" + call.getClass());
        }
    }
}