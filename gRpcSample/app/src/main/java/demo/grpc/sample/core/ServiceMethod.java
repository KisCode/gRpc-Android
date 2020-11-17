package demo.grpc.sample.core;


import java.lang.reflect.Method;
import java.lang.reflect.Type;

import demo.grpc.sample.annotation.GrpcAnnotaion;

/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/17 21:46
 */

public class ServiceMethod<T> {
    //方法返回值类型
    final Type responseType;
    //被注解方法
    final Method method;
    //方法grpc注解
    final GrpcAnnotaion methodGrpcAnnotation;
    //        final Annotation[][] parameterAnnotationsArray;
    //参数类型数组
    final Type[] parameterTypes;

    private ServiceMethod(Builder builder) {
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

        //方法返回值类型
        private Type responseType;
        //被注解方法
        final Method method;
        //方法grpc注解
        final GrpcAnnotaion methodGrpcAnnotation;
        //        final Annotation[][] parameterAnnotationsArray;
        //参数类型数组
        final Type[] parameterTypes;

        public Builder(RPCMananger rpcMananger, Method method) {
            this.method = method;
            this.methodGrpcAnnotation = method.getAnnotation(GrpcAnnotaion.class);
            this.parameterTypes = method.getParameterTypes();
            this.responseType = method.getGenericReturnType();
        }


        public ServiceMethod build() {
            if (methodGrpcAnnotation == null) {
//                throw new IllegalArgumentException("方法未被GrpcAnnotaion注解");
                throw new IllegalArgumentException("API method not found GrpcAnnotaion annotaion");
            }


            return new ServiceMethod(this);
        }
    }
}
