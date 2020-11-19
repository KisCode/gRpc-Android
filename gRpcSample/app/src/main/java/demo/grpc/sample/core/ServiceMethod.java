package demo.grpc.sample.core;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import demo.grpc.sample.annotation.GrpcAnnotaion;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/17 21:46
 */

public class ServiceMethod<T> {
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

    private T invoke() throws NoSuchMethodException {
        Class aClass = methodGrpcAnnotation.className();
        String staticMethod = "newBlockingStub";
//
/*        Method newBlockingStubMethod = aClass.getMethod(staticMethod, Channel.class);
        //工厂方法 获取Channel
        final Object stub = newBlockingStubMethod.invoke(null, getChannel(baseUrl, headerFactory.createHeaders()));
//                Log.i(TAG, "newBlockingStub:" + stub.getClass());

        String methodName = methodGrpcAnnotation.methodName();
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
//                    Log.i(TAG, "parameterTypes:" + parameterTypes[i]);
        }
        final Method realMethod = stub.getClass().getMethod(methodName, parameterTypes);
        Log.i(TAG, "realMethod:" + realMethod.getName() + ",returnType:" + realMethod.getGenericReturnType());
//                return realMethod.invoke(stub, args);*/
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
