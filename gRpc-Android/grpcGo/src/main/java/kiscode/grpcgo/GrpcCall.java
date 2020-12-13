package kiscode.grpcgo;

import java.lang.reflect.Method;

import io.grpc.Channel;
import kiscode.grpcgo.annotation.GrpcAnnotaion;

/**
 * Description: GRPC网络请求
 * Author: KENO
 * Date : 2020/11/19 13:21
 **/
public class GrpcCall<T> implements Call<T> {
    private static final String TAG = "GrpcCall";
    private ServiceMethod<T> serviceMethod;
    private Object[] args;

    public GrpcCall(ServiceMethod<T> serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public T execute() throws Exception {
        return serviceMethod.invoke(args);
    }
}