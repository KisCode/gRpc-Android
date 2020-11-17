package demo.grpc.sample.core;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import demo.grpc.sample.annotation.GrpcAnnotaion;
import demo.grpc.sample.interceptor.HeaderClientInterceptor;
import grpc.sample.UserResp;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Description:
 * Author: kanjianxiong
 * Date : 2020/11/12 15:06
 **/
public class RPCMananger {
    private static final String TAG = "RPCMananger";
    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();

    private String baseUrl;
    private HeaderFactory headerFactory;

    public RPCMananger(String baseUrl, HeaderFactory headerFactory) {
        this.baseUrl = baseUrl;
        this.headerFactory = headerFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }

        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {
                // 1. invoke执行方法
                //2. 根据返回值类型进行转换
                Log.i(TAG, method.getName() + "\t,ReturnType:" + method.getGenericReturnType());

                ServiceMethod serviceMethod = loadServiceMethod(method);

                //获取该方法上的注解
                GrpcAnnotaion annotation = method.getAnnotation(GrpcAnnotaion.class);
                if (annotation == null) {
                    throw new IllegalAccessException("API method not found GrpcAnnotaion annotaion");
                }

//                Log.i(TAG, method.getName() + "\t" + annotation.className() + "." + annotation.methodName());

                Class aClass = annotation.className();
                String staticMethod = "newBlockingStub";

                Method newBlockingStubMethod = aClass.getMethod(staticMethod, Channel.class);
                //工厂方法 获取Channel
                final Object stub = newBlockingStubMethod.invoke(null, getChannel(baseUrl, headerFactory.createHeaders()));
//                Log.i(TAG, "newBlockingStub:" + stub.getClass());

                String methodName = annotation.methodName();
                Class<?>[] parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
//                    Log.i(TAG, "parameterTypes:" + parameterTypes[i]);
                }
                final Method realMethod = stub.getClass().getMethod(methodName, parameterTypes);
                Log.i(TAG, "realMethod:" + realMethod.getName() + ",returnType:" + realMethod.getGenericReturnType());
//                return realMethod.invoke(stub, args);

                return Observable.create(new ObservableOnSubscribe<UserResp>() {
                    @Override
                    public void subscribe(ObservableEmitter<UserResp> emitter) throws Exception {
                        UserResp userResp = (UserResp) realMethod.invoke(stub, args);
                        emitter.onNext(userResp);
                    }
                });
            }
        });
    }


    ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public static final class Builder {
        private String baseUrl;
        private HeaderFactory headerFactory;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setHeaderFactory(HeaderFactory headerFactory) {
            this.headerFactory = headerFactory;
            return this;
        }

        public RPCMananger build() {
            return new RPCMananger(baseUrl, headerFactory);
        }
    }

    public Channel getChannel(String baseUrl, HashMap<String, String> headers) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(baseUrl)
                .useTransportSecurity()
                .build();
        //抽象方法
        HeaderClientInterceptor headerClientInterceptor = new HeaderClientInterceptor(headers);
        Channel channel = ClientInterceptors.intercept(managedChannel, headerClientInterceptor);
        return channel;
    }
} 