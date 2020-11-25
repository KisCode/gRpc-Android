package demo.grpc.sample.core;


import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import demo.grpc.sample.core.annotation.GrpcAnnotaion;
import io.grpc.Channel;
import io.reactivex.Observable;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/17 21:46
 */

public class ServiceMethod<T> {
    private static final String TAG = "ServiceMethod";
    final RPCMananger rpcMananger;
    //方法返回值类型
    final Type responseType;
    //被注解方法
    final Method method;
    //方法grpc注解
    final GrpcAnnotaion methodGrpcAnnotation;
    //        final Annotation[][] parameterAnnotationsArray;
    //参数类型数组
    final Type[] parameterTypes;

    CallAdapter<?> callAdapter;

    private ServiceMethod(Builder builder) {
        this.rpcMananger = builder.rpcMananger;
        this.callAdapter = builder.callAdapter;
        this.responseType = builder.responseType;
        this.method = builder.method;
        this.methodGrpcAnnotation = builder.methodGrpcAnnotation;
        this.parameterTypes = builder.parameterTypes;
    }

    public T invoke(Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Type decalairMethodReturnType = method.getGenericReturnType();
        Log.i(TAG, "Method:" + method.getName() + ",returnType:" + decalairMethodReturnType);

        GrpcAnnotaion annotation = methodGrpcAnnotation;
        Class aClass = annotation.className();
        String staticMethod = "newBlockingStub";
        Channel channel = rpcMananger.getChannel(rpcMananger.getBaseUrl(), rpcMananger.getHeaderFactory().createHeaders());
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
        Object result = realMethod.invoke(stub, args);
        Class type = realMethod.getGenericReturnType().getClass();
        if (realMethod.getGenericReturnType() == method.getGenericReturnType()) {
            //直接返回Response
            return (T) realMethod.invoke(stub, args);
        } else if (method.getGenericReturnType() instanceof Observable) {
            //套接Observable

        }
        return null;
    }

    public static class Builder<T> {
        final RPCMananger rpcMananger;
        //被注解方法
        final Method method;
        //方法grpc注解
        final GrpcAnnotaion methodGrpcAnnotation;
        //        final Annotation[][] parameterAnnotationsArray;
        //参数类型数组
        final Type[] parameterTypes;
        //方法返回值类型
        private Type responseType;
        private CallAdapter<?> callAdapter;

        public Builder(RPCMananger rpcMananger, Method method) {
            this.rpcMananger = rpcMananger;
            this.method = method;
            this.methodGrpcAnnotation = method.getAnnotation(GrpcAnnotaion.class);
            this.parameterTypes = method.getParameterTypes();
            this.responseType = method.getGenericReturnType();
        }


        public ServiceMethod build() {
            callAdapter = createCallAdapter();

            if (methodGrpcAnnotation == null) {
//                throw new IllegalArgumentException("方法未被GrpcAnnotaion注解");
                throw new IllegalArgumentException("API method not found GrpcAnnotaion annotaion");
            }


            return new ServiceMethod(this);
        }

        private CallAdapter<?> createCallAdapter() {
            Type returnType = method.getGenericReturnType();
            Annotation[] annotations = method.getAnnotations();
//            if (Utils.hasUnresolvableType(returnType)) {
//                throw methodError(
//                        "Method return type must not include a type variable or wildcard: %s", returnType);
//            }
//            if (returnType == void.class) {
//                throw methodError("Service methods cannot return void.");
//            }
//            try {
//                return retrofit.callAdapter(returnType, annotations);
//            } catch (RuntimeException e) { // Wide exception range because factories are user code.
//                throw methodError(e, "Unable to create call adapter for %s", returnType);
//            }

            //
            return rpcMananger.callAdapter(returnType, annotations);

        }
    }
}
