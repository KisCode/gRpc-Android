package demo.grpc.sample.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import demo.grpc.sample.annotation.GrpcAnnotaion;
import demo.grpc.sample.interceptor.HeaderClientInterceptor;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 15:06
 **/
public class RPCMananger {
    private static final String TAG = "RPCMananger";

    public static <T> T create(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }

        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                Log.i(TAG, proxy.getClass() + "\t" + args.length + "\t" + args[0].toString());
                // 1. invoke执行方法
                //2. 根据返回值类型进行转换

                //获取该方法上的注解
                GrpcAnnotaion annotation = method.getAnnotation(GrpcAnnotaion.class);

//                Log.i(TAG, method.getName() + "\t" + annotation.className() + "." + annotation.methodName());

                Class aClass = annotation.className();
                String staticMethod = "newBlockingStub";
                Method newBlockingStubMethod = aClass.getMethod(staticMethod, Channel.class);
                Object stub = newBlockingStubMethod.invoke(null, RPCMananger.getChannel());
//                Log.i(TAG, "newBlockingStub:" + stub.getClass());

                String methodName = annotation.methodName();
                Class<?>[] parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
//                    Log.i(TAG, "parameterTypes:" + parameterTypes[i]);
                }
                Method realMethod = stub.getClass().getMethod(methodName, parameterTypes);
//                Log.i(TAG, "realMethod:" + realMethod.getName());
                return realMethod.invoke(stub, args);
            }
        });
    }

    public static Channel getChannel() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("grpctest.test.rlair.net")
                .useTransportSecurity()
                .build();
        HeaderClientInterceptor headerClientInterceptor = new HeaderClientInterceptor("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJFZmIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibGlob25namlhbmciLCJqdGkiOiIyZTI4YWRiMi04ZTcwLTRjMjYtYjJhNS1hMjc0MTA2NDNmMTgiLCJpYXQiOiIxNjA1MjMyOTA4IiwibmJmIjoxNjA1MjMyOTA4LCJleHAiOjE2MDUyNzYxMDgsImlzcyI6ImVmYi5ybGFpci5uZXQiLCJhdWQiOiJFZmIifQ.FS9-S8stYBHHHPUV9mgzFUbsXjhsJMrfbgzCFrd_QFgeM6AO5EANCCsBMu8JbdaIxNV1FLufpdvZdyFpgqpMV9UFlY_0pdGyPVsByiWqgdZ2VkGeOy5YfL7wIG4KORlhZTLOqoA2-MNTjt0DkrY1Ex0AZPbhpe-VNdWXENqOJ0M2bgV9msd9llyozR_gKYjnSMnpwQDup4RRHRbZrEFYA5_rny_MKKEsKN2xe2IhEicXm1W3Q-g68vjfwCYphsUIwmCsiSVnY4sIEadIaDfI6lsWRUmNB518sqjAlpEcLJHQJ1yFtol-P5tDmlshYqB_q2-Q7pnjqqqypALac6PEYw");
        Channel channel = ClientInterceptors.intercept(managedChannel, headerClientInterceptor);
        return channel;
    }
} 