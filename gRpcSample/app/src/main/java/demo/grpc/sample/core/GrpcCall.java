package demo.grpc.sample.core;

import android.util.Log;

import java.lang.reflect.Method;

import demo.grpc.sample.core.annotation.GrpcAnnotaion;
import io.grpc.Channel;

/**
 * Description:
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
        //获取该方法上的注解
        GrpcAnnotaion annotation = serviceMethod.methodGrpcAnnotation;
        Class aClass = annotation.className();
        String staticMethod = "newBlockingStub";
        Channel channel = serviceMethod.rpcMananger.getChannel(serviceMethod.rpcMananger.getBaseUrl(), serviceMethod.rpcMananger.getHeaderFactory().createHeaders());
        //工厂方法 获取Channel
        Method newBlockingStubMethod = aClass.getMethod(staticMethod, Channel.class);
        final Object stub = newBlockingStubMethod.invoke(null, channel);
        String methodName = annotation.methodName();
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        final Method realMethod = stub.getClass().getMethod(methodName, parameterTypes);
        Log.i(TAG, "realMethod:" + realMethod.getName() + ",returnType:" + realMethod.getGenericReturnType());
        return (T) realMethod.invoke(stub, args);
    }

    @Override
    public Call<T> clone() {
        return new GrpcCall<>(serviceMethod, args);
    }
}