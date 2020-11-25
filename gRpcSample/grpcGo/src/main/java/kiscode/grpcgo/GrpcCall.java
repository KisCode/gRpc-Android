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
    private final String METHOD_NEW_BLOCKING_STUB = "newBlockingStub";
    private ServiceMethod<T> serviceMethod;
    private Object[] args;

    public GrpcCall(ServiceMethod<T> serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    @Override
    public T execute() throws Exception {
        return serviceMethod.invoke(args);
/*        //获取该方法上的注解
        GrpcAnnotaion annotation = serviceMethod.methodGrpcAnnotation;
        Class grpcServiceClass = annotation.className();
        Channel channel = serviceMethod.createChannel();
        Method newBlockingStubMethod = grpcServiceClass.getMethod(METHOD_NEW_BLOCKING_STUB, Channel.class);
        final Object stub = newBlockingStubMethod.invoke(null, channel);
        String methodName = annotation.methodName();
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        final Method realMethod = stub.getClass().getMethod(methodName, parameterTypes);
        return (T) realMethod.invoke(stub, args);*/
    }
}