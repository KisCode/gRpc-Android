package demo.grpc.sample.core;

import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import demo.grpc.sample.interceptor.HeaderClientInterceptor;
import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Description:
 * Author: KENO
 * Date : 2020/11/12 15:06
 **/
public class RPCMananger {
    private static final String TAG = "RPCMananger";
    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();

    private String baseUrl;
    private HeaderFactory headerFactory;
    private List<CallAdapter.Factory> adapterFactories;

    public RPCMananger(String baseUrl, HeaderFactory headerFactory, List<CallAdapter.Factory> adapterFactories) {
        this.baseUrl = baseUrl;
        this.headerFactory = headerFactory;
        this.adapterFactories = adapterFactories;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
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
                return serviceMethod.invoke(args);
           /*     GrpcCall grpcCall = new GrpcCall(serviceMethod, args);
                return serviceMethod.callAdapter.adapt(grpcCall);*/

                /*
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
                return realMethod.invoke(stub, args);*/

            /*    return Observable.create(new ObservableOnSubscribe<UserResp>() {
                    @Override
                    public void subscribe(ObservableEmitter<UserResp> emitter) throws Exception {
                        UserResp userResp = (UserResp) realMethod.invoke(stub, args);
                        emitter.onNext(userResp);
                    }
                });*/
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


    public Channel getChannel(String baseUrl, Map<String, String> headers) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(baseUrl)
                .useTransportSecurity()
                .build();
        //抽象方法
        HeaderClientInterceptor headerClientInterceptor = new HeaderClientInterceptor(headers);
        Channel channel = ClientInterceptors.intercept(managedChannel, headerClientInterceptor);
        return channel;
    }

    public CallAdapter<?> callAdapter(Type returnType, Annotation[] annotations) {
        return null; //todo
//        return nextCallAdapter(returnType, annotations);
    }

    private CallAdapter<?> nextCallAdapter(Type returnType, Annotation[] annotations) {
        for (CallAdapter.Factory adapterFactory : adapterFactories) {
            CallAdapter<?> callAdapter = adapterFactory.get(returnType);
            if (callAdapter != null) {
                return callAdapter;
            }
        }
        throw new IllegalArgumentException("not Found CallAdapter");
    }


    public static final class Builder {
        private String baseUrl;
        private HeaderFactory headerFactory;
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setHeaderFactory(HeaderFactory headerFactory) {
            this.headerFactory = headerFactory;
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            adapterFactories.add(callAdapterFactory);
            return this;
        }

        public RPCMananger build() {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("Not set baseUrl");
            }

            if (headerFactory == null) {
                //tips
            }

            return new RPCMananger(baseUrl, headerFactory, adapterFactories);
        }
    }
} 